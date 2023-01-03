package io.github.matheusgit11.msclientes.infra.repository;
import io.github.matheusgit11.msclientes.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    Optional<Cliente> findByCpf(String cpf);
}

