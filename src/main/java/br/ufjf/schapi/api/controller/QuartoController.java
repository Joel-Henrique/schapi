package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.api.dto.QuartoDTO;
import br.ufjf.schapi.model.entity.Quarto;
import br.ufjf.schapi.model.service.QuartoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quartos")
public class QuartoController {

    private final QuartoService service;

    public QuartoController(QuartoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<QuartoDTO>> listar() {
        List<QuartoDTO> quartos = service.getQuartos()
                .stream()
                .map(QuartoDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(quartos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuartoDTO> buscarPorId(@PathVariable Long id) {
        Optional<Quarto> quarto = service.getQuartoById(id);
        return quarto.map(q -> ResponseEntity.ok(QuartoDTO.create(q)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<QuartoDTO> salvar(@RequestBody QuartoDTO dto) {
        Quarto quarto = new ModelMapper().map(dto, Quarto.class);
        quarto = service.salvar(quarto);
        return new ResponseEntity<>(QuartoDTO.create(quarto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoDTO> atualizar(@PathVariable Long id, @RequestBody QuartoDTO dto) {
        if (!service.getQuartoById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Quarto quarto = new ModelMapper().map(dto, Quarto.class);
        quarto.setId(id);
        quarto = service.salvar(quarto);
        return ResponseEntity.ok(QuartoDTO.create(quarto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Quarto> quarto = service.getQuartoById(id);
        if (!quarto.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        service.excluir(quarto.get());
        return ResponseEntity.noContent().build();
    }
}
