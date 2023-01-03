package io.github.matheusgit11.mscartoes.infra.mqueue;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.matheusgit11.mscartoes.domain.Cartao;
import io.github.matheusgit11.mscartoes.domain.ClienteCartao;
import io.github.matheusgit11.mscartoes.domain.DadosSolicitacaoCartao;
import io.github.matheusgit11.mscartoes.infra.repository.CartaoRepository;
import io.github.matheusgit11.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            DadosSolicitacaoCartao dados = mapper.readValue(payload, DadosSolicitacaoCartao.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteRepository.save(clienteCartao);

        } catch (Exception e) {
            log.error("Erro ao receber solicitacao de emissao de cartao : {}",e.getMessage());
        }
    }
}

