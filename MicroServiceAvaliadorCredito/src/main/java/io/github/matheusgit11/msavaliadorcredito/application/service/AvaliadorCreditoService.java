package io.github.matheusgit11.msavaliadorcredito.application.service;

import feign.FeignException;
import io.github.matheusgit11.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.matheusgit11.msavaliadorcredito.application.exception.ErroComunicacaoMicroserviceException;
import io.github.matheusgit11.msavaliadorcredito.application.exception.ErrorSolicitacaoCartaoException;
import io.github.matheusgit11.msavaliadorcredito.domain.model.dadosAvaliacaoModel.Cartao;
import io.github.matheusgit11.msavaliadorcredito.domain.model.dadosAvaliacaoModel.CartaoAprovado;
import io.github.matheusgit11.msavaliadorcredito.domain.model.dadosAvaliacaoModel.RetornoAvaliacaoCliente;
import io.github.matheusgit11.msavaliadorcredito.domain.model.rabbitMq.DadosSolicitacaoCartao;
import io.github.matheusgit11.msavaliadorcredito.domain.model.rabbitMq.ProtocoloSolicitacaoCartao;
import io.github.matheusgit11.msavaliadorcredito.domain.model.situacaoClienteModel.CartaoCliente;
import io.github.matheusgit11.msavaliadorcredito.domain.model.situacaoClienteModel.DadosCliente;
import io.github.matheusgit11.msavaliadorcredito.domain.model.situacaoClienteModel.SituacaoCliente;
import io.github.matheusgit11.msavaliadorcredito.infra.client.CartaoResourceClient;
import io.github.matheusgit11.msavaliadorcredito.infra.client.ClienteResourceClient;
import io.github.matheusgit11.msavaliadorcredito.infra.mq.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientResource;
    private final CartaoResourceClient cartaoResource;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException ,ErroComunicacaoMicroserviceException {

        try {
            //obterDadosCliente do MSCLIENTES
            ResponseEntity<DadosCliente> dadosClienteResponse = clientResource.dadosCliente(cpf);

            //obterDadosCartoes do MSCARTOES
            ResponseEntity<List<CartaoCliente>> dadosCartaoResponse = cartaoResource.getCartoesByCliente(cpf);

            //construindo classe baseada nas informacoes de clientes e cartoes e buildando ela
            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(dadosCartaoResponse.getBody())
                    .build();

        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);

        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf , Long renda)  throws DadosClienteNotFoundException ,ErroComunicacaoMicroserviceException {

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientResource.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> dadosCartaoResponse = cartaoResource.getCartoesRendaAte(renda);

            List<Cartao> cartoes = dadosCartaoResponse.getBody();
            List<CartaoAprovado>  listaCartoesAprovados = cartoes.stream().map(cartao -> {

                DadosCliente ClienteBody = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(ClienteBody.getIdade());
                BigDecimal fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeiraCartao());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        }catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);
        }

    }
    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoCartao dados){
        try{
            emissaoCartaoPublisher.solicitarCartao(dados);
            String protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);

        }catch (Exception e){
            throw new ErrorSolicitacaoCartaoException(e.getMessage());
        }
    }

}
