package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.api.dto.HospedeDTO;
import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.Hospede;
import br.ufjf.schapi.model.entity.UsuarioSistema;
import br.ufjf.schapi.model.service.HospedeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hospedes")
public class HospedeController {

    private final HospedeService service;

    public HospedeController(HospedeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<HospedeDTO>> getAll() {
        List<Hospede> hospedes = service.getHospedes();
        List<HospedeDTO> dtos = hospedes.stream()
                .map(HospedeDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospedeDTO> getById(@PathVariable Long id) {
        Optional<Hospede> hospede = service.getHospedeById(id);
        return hospede.map(value -> ResponseEntity.ok(HospedeDTO.create(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody HospedeDTO dto) {
        try {
            Hospede hospede = converter(dto);
            Hospede salvo = service.salvar(hospede);
            return new ResponseEntity<>(HospedeDTO.create(salvo), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody HospedeDTO dto) {
        return service.getHospedeById(id).map(existing -> {
            try {
                Hospede hospede = converter(dto);
                hospede.setId(existing.getId());
                Hospede atualizado = service.salvar(hospede);
                return ResponseEntity.ok(HospedeDTO.create(atualizado));
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.getHospedeById(id).map(hospede -> {
            service.excluir(hospede);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElse(ResponseEntity.notFound().build());
    }

    private Hospede converter(HospedeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Hospede hospede = modelMapper.map(dto, Hospede.class);

        if (dto.getUsuarioSistemaId() != null) {
            UsuarioSistema usuario = new UsuarioSistema();
            usuario.setId(dto.getUsuarioSistemaId());
            hospede.setUsuarioSistema(usuario);
        }

        return hospede;
    }
}
