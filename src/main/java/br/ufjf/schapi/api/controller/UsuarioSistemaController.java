package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.api.dto.UsuarioSistemaDTO;
import br.ufjf.schapi.model.entity.UsuarioSistema;
import br.ufjf.schapi.model.service.UsuarioSistemaService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios-sistema")
public class UsuarioSistemaController {

    private final UsuarioSistemaService service;

    public UsuarioSistemaController(UsuarioSistemaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioSistemaDTO>> listar() {
        List<UsuarioSistema> usuarios = service.getUsuariosSistema();
        List<UsuarioSistemaDTO> dtos = usuarios.stream()
                .map(UsuarioSistemaDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioSistemaDTO> buscarPorId(@PathVariable Long id) {
        Optional<UsuarioSistema> usuario = service.getUsuarioSistemaById(id);
        return usuario.map(u -> ResponseEntity.ok(UsuarioSistemaDTO.create(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioSistemaDTO> salvar(@RequestBody UsuarioSistemaDTO dto) {
        UsuarioSistema usuario = new ModelMapper().map(dto, UsuarioSistema.class);
        usuario = service.salvar(usuario);
        return new ResponseEntity<>(UsuarioSistemaDTO.create(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioSistemaDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioSistemaDTO dto) {
        if (!service.getUsuarioSistemaById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UsuarioSistema usuario = new ModelMapper().map(dto, UsuarioSistema.class);
        usuario.setId(id);
        usuario = service.salvar(usuario);
        return ResponseEntity.ok(UsuarioSistemaDTO.create(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<UsuarioSistema> usuario = service.getUsuarioSistemaById(id);
        if (!usuario.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        service.excluir(usuario.get());
        return ResponseEntity.noContent().build();
    }
}
