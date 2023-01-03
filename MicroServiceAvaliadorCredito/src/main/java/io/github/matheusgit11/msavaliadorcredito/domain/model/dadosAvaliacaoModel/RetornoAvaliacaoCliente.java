package io.github.matheusgit11.msavaliadorcredito.domain.model.dadosAvaliacaoModel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RetornoAvaliacaoCliente {

    private List<CartaoAprovado> cartoesAprovados;
}
