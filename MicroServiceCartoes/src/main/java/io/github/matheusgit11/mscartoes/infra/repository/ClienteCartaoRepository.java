package io.github.matheusgit11.mscartoes.infra.repository;

import io.github.matheusgit11.mscartoes.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao,Long> {

    List<ClienteCartao> findByCpf(String cpf);
}
