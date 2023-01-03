package io.github.matheusgit11.msavaliadorcredito.domain.model.rabbitMq;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DadosSolicitacaoCartao {
    private Long idCartao;
    private String cpf;
    private String endereco;
    private BigDecimal limiteLiberado;
}
