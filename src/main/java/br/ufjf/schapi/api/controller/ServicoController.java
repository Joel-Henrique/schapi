package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.api.dto.ServicoDTO;
import br.ufjf.schapi.model.entity.Servico;
import br.ufjf.schapi.model.service.ServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listar() {
        List<ServicoDTO> servicos = service.getServicos()
                .stream()
                .map(ServicoDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscarPorId(@PathVariable Long id) {
        Optional<Servico> servico = service.getServicoById(id);
        return servico.map(s -> ResponseEntity.ok(ServicoDTO.create(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServicoDTO> salvar(@RequestBody ServicoDTO dto) {
        Servico servico = new ModelMapper().map(dto, Servico.class);
        servico = service.salvar(servico, dto.getHospedagemId(), dto.getFuncionarioId(), dto.getTipoServicoId());
        return new ResponseEntity<>(ServicoDTO.create(servico), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> atualizar(@PathVariable Long id, @RequestBody ServicoDTO dto) {
        if (!service.getServicoById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Servico servico = new ModelMapper().map(dto, Servico.class);
        servico.setId(id);
        servico = service.salvar(servico, dto.getHospedagemId(), dto.getFuncionarioId(), dto.getTipoServicoId());
        return ResponseEntity.ok(ServicoDTO.create(servico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Servico> servico = service.getServicoById(id);
        if (!servico.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        service.excluir(servico.get());
        return ResponseEntity.noContent().build();
    }
}
