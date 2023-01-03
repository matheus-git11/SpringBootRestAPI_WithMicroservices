package io.github.matheusgit11.msavaliadorcredito.application.controller;

import io.github.matheusgit11.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.matheusgit11.msavaliadorcredito.application.exception.ErroComunicacaoMicroserviceException;
import io.github.matheusgit11.msavaliadorcredito.application.exception.ErrorSolicitacaoCartaoException;
import io.github.matheusgit11.msavaliadorcredito.application.service.AvaliadorCreditoService;
import io.github.matheusgit11.msavaliadorcredito.domain.model.dadosAvaliacaoModel.DadosAvaliacao;
import io.github.matheusgit11.msavaliadorcredito.domain.model.dadosAvaliacaoModel.RetornoAvaliacaoCliente;
import io.github.matheusgit11.msavaliadorcredito.domain.model.rabbitMq.DadosSolicitacaoCartao;
import io.github.matheusgit11.msavaliadorcredito.domain.model.rabbitMq.ProtocoloSolicitacaoCartao;
import io.github.matheusgit11.msavaliadorcredito.domain.model.situacaoClienteModel.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService service;

    @GetMapping
    public String status() {
        return "ok";
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultarSituacaoCliente(@RequestParam("cpf") String cpf) {

        try {
            SituacaoCliente situacaoCliente = service.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);

        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (ErroComunicacaoMicroserviceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados) {

        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = service.realizarAvaliacao(dados.getCpf(), dados.getRenda());
           return ResponseEntity.ok(retornoAvaliacaoCliente);

        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (ErroComunicacaoMicroserviceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitaCartao(@RequestBody DadosSolicitacaoCartao dados){
        try{
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = service.solicitarEmissaoCartao(dados);
            return ResponseEntity.ok(protocoloSolicitacaoCartao);

        }catch (ErrorSolicitacaoCartaoException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
















}

