package com.rasmoo.api.rasfood.rasfoodapi.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.rasfoodapi.dto.CardapioDTO;
import com.rasmoo.api.rasfood.rasfoodapi.entity.Cardapio;
import com.rasmoo.api.rasfood.rasfoodapi.repository.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/cardapio")
@RestController
public class CardapioController {

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Cardapio>> consultarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAll());
    }

    @GetMapping("/nome/{nome}/disponivel")
    public ResponseEntity<List<CardapioDTO>> consultarTodos(@PathVariable("nome") final String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAllByNome(nome));
    }

    @GetMapping("/{categoriaId}/disponivel")
    public ResponseEntity<List<Cardapio>> consultarTodos(@PathVariable("categoriaId") final Integer categoriaId) {
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAllByCatgegoria(categoriaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cardapio> consultarPorId(@PathVariable("id") final Integer id) {
        Optional<Cardapio> cardapio = cardapioRepository.findById(id);

        return cardapio.map(value -> ResponseEntity.status(HttpStatus.OK)
                       .body(value))
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body(null));
    }

    @PostMapping
    public ResponseEntity<Cardapio> criar(@RequestBody final Cardapio cardapio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.cardapioRepository.save(cardapio));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cardapio> atualizar(@PathVariable("id") final Integer id, @RequestBody final Cardapio cardapio) throws JsonMappingException {
        Optional<Cardapio> cardapioEncontrado = this.cardapioRepository.findById(id);
        if(cardapioEncontrado.isPresent()) {
            objectMapper.updateValue(cardapioEncontrado.get(), cardapio);
            return ResponseEntity.status(HttpStatus.OK).body(this.cardapioRepository.save(cardapioEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPorId(@PathVariable("id") final Integer id) {
        Optional<Cardapio> cardapio = cardapioRepository.findById(id);

        if(cardapio.isPresent()) {
            cardapioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
