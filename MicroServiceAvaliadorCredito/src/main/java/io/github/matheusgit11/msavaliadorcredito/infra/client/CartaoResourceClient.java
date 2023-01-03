package io.github.matheusgit11.msavaliadorcredito.infra.client;

import io.github.matheusgit11.msavaliadorcredito.domain.model.dadosAvaliacaoModel.Cartao;
import io.github.matheusgit11.msavaliadorcredito.domain.model.situacaoClienteModel.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscartoes" , path="/cartoes")
public interface CartaoResourceClient {

    @GetMapping(params = "cpf")
     ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);

    @GetMapping(params = "renda")
     ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda);
}
