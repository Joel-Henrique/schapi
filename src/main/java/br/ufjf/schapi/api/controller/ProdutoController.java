package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.api.dto.ProdutoDTO;
import br.ufjf.schapi.model.entity.Produto;
import br.ufjf.schapi.model.service.ProdutoService;
import br.ufjf.schapi.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        List<Produto> produtos = service.listarTodos();
        List<ProdutoDTO> dtos = produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        return ResponseEntity.ok(ProdutoDTO.create(produto.get()));
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody ProdutoDTO dto) {
        try {
            Produto produto = converter(dto);
            produto = service.salvar(produto);
            return new ResponseEntity<>(ProdutoDTO.create(produto), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        Optional<Produto> existente = service.getProdutoById(id);
        if (!existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        try {
            Produto produto = converter(dto);
            produto.setId(id);
            produto = service.salvar(produto);
            return ResponseEntity.ok(ProdutoDTO.create(produto));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        try {
            service.excluir(produto.get());
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Produto.class);
    }
}
