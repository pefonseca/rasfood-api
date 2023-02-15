package com.rasmoo.api.rasfood.rasfoodapi.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.rasfoodapi.entity.Endereco;
import com.rasmoo.api.rasfood.rasfoodapi.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/endereco")
@RestController
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Endereco>> consultarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findAll());
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<List<Endereco>> consultarTodosPorCep(@PathVariable("cep") final String cep) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findByCep(cep));
    }

    @GetMapping("/{value}")
    public ResponseEntity<Endereco> consultarEnderecoPorEmailCpf(@PathVariable("value") String value) {
        return enderecoRepository.findEnderecoByEmailOrCpf(value)
                .map(e -> ResponseEntity.status(HttpStatus.OK).body(e))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping("/{value}")
    public ResponseEntity<Optional<Endereco>> atualizar(@PathVariable("value") final String value, @RequestBody final Endereco endereco) throws JsonMappingException {
        Optional<Endereco> enderecoEncontrado = enderecoRepository.findEnderecoByEmailOrCpf(value);
        if(enderecoEncontrado.isPresent()) {
            objectMapper.updateValue(enderecoEncontrado.get(), endereco);
            return ResponseEntity.status(HttpStatus.OK).body(Optional.of(this.enderecoRepository.save(enderecoEncontrado.get())));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
