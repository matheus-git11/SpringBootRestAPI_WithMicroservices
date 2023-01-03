package io.github.matheusgit11.mscartoes.application.representation;

import io.github.matheusgit11.mscartoes.domain.Cartao;
import io.github.matheusgit11.mscartoes.domain.enumeration.BandeiraCartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;


    public Cartao toModel(){
        return new Cartao(nome,bandeira ,renda,limite);
    }
}
