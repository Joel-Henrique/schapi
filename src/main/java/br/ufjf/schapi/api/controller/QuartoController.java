package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.api.dto.QuartoDTO;
import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.Quarto;
import br.ufjf.schapi.model.entity.TipoQuarto;
import br.ufjf.schapi.model.service.QuartoService;
import br.ufjf.schapi.model.service.TipoQuartoService;
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
    private final TipoQuartoService tipoQuartoService;

    public QuartoController(QuartoService service, TipoQuartoService tipoQuartoService) {
        this.service = service;
        this.tipoQuartoService = tipoQuartoService;
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
    public ResponseEntity<?> salvar(@RequestBody QuartoDTO dto) {
        try {
            Quarto quarto = new Quarto();
            quarto.setNumero(dto.getNumero());
            quarto.setStatus(dto.getStatus());

            if (dto.getTipoQuartoId() != null) {
                TipoQuarto tipoQuarto = tipoQuartoService.getTipoQuartoById(dto.getTipoQuartoId())
                        .orElseThrow(() -> new RegraNegocioException("Tipo de quarto não encontrado."));
                quarto.setTipoQuarto(tipoQuarto);
            }

            quarto = service.salvar(quarto);
            return new ResponseEntity<>(QuartoDTO.create(quarto), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody QuartoDTO dto) {
        Optional<Quarto> optional = service.getQuartoById(id);

        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Quarto quarto = optional.get();
            quarto.setNumero(dto.getNumero());
            quarto.setStatus(dto.getStatus());

            if (dto.getTipoQuartoId() != null) {
                TipoQuarto tipoQuarto = tipoQuartoService.getTipoQuartoById(dto.getTipoQuartoId())
                        .orElseThrow(() -> new RegraNegocioException("Tipo de quarto não encontrado."));
                quarto.setTipoQuarto(tipoQuarto);
            } else {
                quarto.setTipoQuarto(null);
            }

            quarto = service.salvar(quarto);
            return ResponseEntity.ok(QuartoDTO.create(quarto));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
