package com.rasmoo.api.rasfood.rasfoodapi.repository;

import com.rasmoo.api.rasfood.rasfoodapi.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Query("SELECT e FROM Endereco e WHERE e.cliente.clienteId.email = :value OR e.cliente.clienteId.cpf = :value")
    Optional<Endereco> findEnderecoByEmailOrCpf(final String value);

    List<Endereco> findByCep(String cep);
}
