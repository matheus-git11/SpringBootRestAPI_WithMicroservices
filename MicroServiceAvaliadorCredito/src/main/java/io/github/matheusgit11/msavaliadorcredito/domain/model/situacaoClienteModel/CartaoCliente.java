package io.github.matheusgit11.msavaliadorcredito.domain.model.situacaoClienteModel;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoCliente {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;
}
