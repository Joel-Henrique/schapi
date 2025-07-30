package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.api.dto.TipoServicoDTO;
import br.ufjf.schapi.model.entity.TipoServico;
import br.ufjf.schapi.model.service.TipoServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos-servico")
public class TipoServicoController {

    private final TipoServicoService service;

    public TipoServicoController(TipoServicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoServicoDTO>> listar() {
        List<TipoServicoDTO> tipos = service.getTiposServico()
                .stream()
                .map(TipoServicoDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoServicoDTO> buscarPorId(@PathVariable Long id) {
        Optional<TipoServico> tipo = service.getTipoServicoById(id);
        return tipo.map(t -> ResponseEntity.ok(TipoServicoDTO.create(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoServicoDTO> salvar(@RequestBody TipoServicoDTO dto) {
        TipoServico tipoServico = new ModelMapper().map(dto, TipoServico.class);
        tipoServico = service.salvar(tipoServico);
        return new ResponseEntity<>(TipoServicoDTO.create(tipoServico), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoServicoDTO> atualizar(@PathVariable Long id, @RequestBody TipoServicoDTO dto) {
        if (!service.getTipoServicoById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TipoServico tipoServico = new ModelMapper().map(dto, TipoServico.class);
        tipoServico.setId(id);
        tipoServico = service.salvar(tipoServico);
        return ResponseEntity.ok(TipoServicoDTO.create(tipoServico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<TipoServico> tipoServico = service.getTipoServicoById(id);
        if (!tipoServico.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        service.excluir(tipoServico.get());
        return ResponseEntity.noContent().build();
    }
}
