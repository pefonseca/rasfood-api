package com.rasmoo.api.rasfood.rasfoodapi.repository;

import com.rasmoo.api.rasfood.rasfoodapi.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
