package com.rasmoo.api.rasfood.rasfoodapi.repository;

import com.rasmoo.api.rasfood.rasfoodapi.dto.CardapioDTO;
import com.rasmoo.api.rasfood.rasfoodapi.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Integer> {

    @Query("SELECT new com.rasmoo.api.rasfood.rasfoodapi.dto.CardapioDTO(c.nome, c.descricao, c.valor, c.categoria.nome) " +
            "FROM Cardapio c WHERE c.nome LIKE UPPER('%'||:nome||'%') AND c.disponivel = true")
    List<CardapioDTO> findAllByNome(final String nome);

    @Query(value = "SELECT * FROM cardapio c WHERE c.categoria_id = ?1 AND c.disponivel = true ", nativeQuery = true)
    List<Cardapio> findAllByCatgegoria(final Integer categoria);
}
