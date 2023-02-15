package com.rasmoo.api.rasfood.rasfoodapi.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.rasfoodapi.entity.Categoria;
import com.rasmoo.api.rasfood.rasfoodapi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/categoria")
@RestController
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Categoria>> consultarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> consultarPorId(@PathVariable("id") final Integer id) {
        Optional<Categoria> Categoria = categoriaRepository.findById(id);

        return Categoria.map(value -> ResponseEntity.status(HttpStatus.OK)
                        .body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody final Categoria Categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoriaRepository.save(Categoria));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable("id") final Integer id, @RequestBody final Categoria Categoria) throws JsonMappingException {
        Optional<Categoria> CategoriaEncontrado = this.categoriaRepository.findById(id);
        if(CategoriaEncontrado.isPresent()) {
            objectMapper.updateValue(CategoriaEncontrado.get(), Categoria);
            return ResponseEntity.status(HttpStatus.OK).body(this.categoriaRepository.save(CategoriaEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPorId(@PathVariable("id") final Integer id) {
        Optional<Categoria> Categoria = categoriaRepository.findById(id);

        if(Categoria.isPresent()) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
