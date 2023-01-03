package io.github.matheusgit11.msavaliadorcredito.domain.model.dadosAvaliacaoModel;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoAprovado {

    private String cartao;
    private String bandeira;
    private BigDecimal limiteAprovado;
}
