package io.github.matheusgit11.msavaliadorcredito.application.exception;

public class DadosClienteNotFoundException extends Exception {

    public DadosClienteNotFoundException() {
        super("Dados do cliente nao encontrados para o CPF informado");
    }
}
