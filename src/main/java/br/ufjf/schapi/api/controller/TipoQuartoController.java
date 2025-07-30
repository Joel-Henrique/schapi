package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.api.dto.TipoQuartoDTO;
import br.ufjf.schapi.model.entity.TipoQuarto;
import br.ufjf.schapi.model.service.TipoQuartoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos-quarto")
public class TipoQuartoController {

    private final TipoQuartoService service;

    public TipoQuartoController(TipoQuartoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoQuartoDTO>> listar() {
        List<TipoQuartoDTO> tipos = service.getTiposQuarto()
                .stream()
                .map(TipoQuartoDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoQuartoDTO> buscarPorId(@PathVariable Long id) {
        Optional<TipoQuarto> tipo = service.getTipoQuartoById(id);
        return tipo.map(t -> ResponseEntity.ok(TipoQuartoDTO.create(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoQuartoDTO> salvar(@RequestBody TipoQuartoDTO dto) {
        TipoQuarto tipoQuarto = new ModelMapper().map(dto, TipoQuarto.class);
        tipoQuarto = service.salvar(tipoQuarto);
        return new ResponseEntity<>(TipoQuartoDTO.create(tipoQuarto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoQuartoDTO> atualizar(@PathVariable Long id, @RequestBody TipoQuartoDTO dto) {
        if (!service.getTipoQuartoById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TipoQuarto tipoQuarto = new ModelMapper().map(dto, TipoQuarto.class);
        tipoQuarto.setId(id);
        tipoQuarto = service.salvar(tipoQuarto);
        return ResponseEntity.ok(TipoQuartoDTO.create(tipoQuarto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<TipoQuarto> tipoQuarto = service.getTipoQuartoById(id);
        if (!tipoQuarto.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        service.excluir(tipoQuarto.get());
        return ResponseEntity.noContent().build();
    }
}
