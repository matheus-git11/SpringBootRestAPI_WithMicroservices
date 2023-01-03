package io.github.matheusgit11.mscartoes.application.representation;

import io.github.matheusgit11.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteResponse {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorClienteResponse fromModel(ClienteCartao clienteCartao){
        return new CartoesPorClienteResponse(
                clienteCartao.getCartao().getNome(),
                clienteCartao.getCartao().getBandeiraCartao().toString(),
                clienteCartao.getLimite()
        );
    }
}
