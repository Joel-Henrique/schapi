package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.Hospedagem;
import br.ufjf.schapi.model.service.HospedagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hospedagens")
public class HospedagemController {

    private final HospedagemService service;

    public HospedagemController(HospedagemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Hospedagem>> listar() {
        return ResponseEntity.ok(service.getHospedagens());
    }

    @GetMapping("{id}")
    public ResponseEntity<Hospedagem> buscarPorId(@PathVariable("id") Long id) {
        Optional<Hospedagem> hospedagem = service.getHospedagemById(id);
        return hospedagem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Hospedagem hospedagem) {
        try {
            Hospedagem hospedagemSalva = service.salvar(hospedagem);
            return new ResponseEntity<>(hospedagemSalva, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody Hospedagem hospedagem) {
        Optional<Hospedagem> existente = service.getHospedagemById(id);

        if (!existente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        try {
            hospedagem.setId(id);
            Hospedagem atualizado = service.salvar(hospedagem);
            return ResponseEntity.ok(atualizado);
        } catch (RegraNegocioException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
        Optional<Hospedagem> hospedagem = service.getHospedagemById(id);

        if (!hospedagem.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        try {
            service.excluir(hospedagem.get());
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
