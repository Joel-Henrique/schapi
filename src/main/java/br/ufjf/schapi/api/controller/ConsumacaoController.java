package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.api.dto.ConsumacaoDTO;
import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.Consumacao;
import br.ufjf.schapi.model.entity.Hospedagem;
import br.ufjf.schapi.model.entity.Produto;
import br.ufjf.schapi.model.service.ConsumacaoService;
import br.ufjf.schapi.model.service.HospedagemService;
import br.ufjf.schapi.model.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consumacoes")
@RequiredArgsConstructor
public class ConsumacaoController {

    private final ConsumacaoService service;
    private final ProdutoService produtoService;
    private final HospedagemService hospedagemService;

    @GetMapping
    public ResponseEntity<List<ConsumacaoDTO>> listarTodas() {
        List<Consumacao> consumacoes = service.getConsumacao();
        List<ConsumacaoDTO> dtos = consumacoes.stream()
                .map(ConsumacaoDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Consumacao> consumacao = service.getConsumacaoById(id);
        if (!consumacao.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumo não encontrado");
        }
        return ResponseEntity.ok(ConsumacaoDTO.create(consumacao.get()));
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody ConsumacaoDTO dto) {
        try {
            Consumacao consumacao = converter(dto);
            consumacao = service.salvar(consumacao);
            return new ResponseEntity<>(ConsumacaoDTO.create(consumacao), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ConsumacaoDTO dto) {
        Optional<Consumacao> existente = service.getConsumacaoById(id);
        if (!existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumo não encontrado");
        }

        try {
            Consumacao consumacao = converter(dto);
            consumacao.setId(id);
            consumacao = service.salvar(consumacao);
            return ResponseEntity.ok(ConsumacaoDTO.create(consumacao));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Consumacao> consumacao = service.getConsumacaoById(id);
        if (!consumacao.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumo não encontrado");
        }

        try {
            service.excluir(consumacao.get());
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Consumacao converter(ConsumacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Consumacao entity = modelMapper.map(dto, Consumacao.class);

        if (dto.getIdProduto() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            produto.ifPresent(entity::setProduto);
        }

        if (dto.getIdHospedagem() != null) {
            Optional<Hospedagem> hospedagem = hospedagemService.getHospedagemById(dto.getIdHospedagem());
            hospedagem.ifPresent(entity::setHospedagem);
        }

        return entity;
    }
}
