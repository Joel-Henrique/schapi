package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.api.dto.FuncionarioDTO;
import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.Funcionario;
import br.ufjf.schapi.model.service.FuncionarioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<FuncionarioDTO> listar() {
        return service.getFuncionarios().stream()
                .map(FuncionarioDTO::create)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        return funcionario
                .map(FuncionarioDTO::create)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody FuncionarioDTO dto) {
        try {
            Funcionario entidade = new ModelMapper().map(dto, Funcionario.class);
            Funcionario salvo = service.salvar(entidade, dto.getUsuarioSistemaId());
            return ResponseEntity.ok(FuncionarioDTO.create(salvo));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody FuncionarioDTO dto) {
        return service.getFuncionarioById(id).map(funcionarioExistente -> {
            try {
                Funcionario entidade = new ModelMapper().map(dto, Funcionario.class);
                entidade.setId(funcionarioExistente.getId());
                Funcionario atualizado = service.salvar(entidade, dto.getUsuarioSistemaId());
                return ResponseEntity.ok(FuncionarioDTO.create(atualizado));
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return service.getFuncionarioById(id).map(funcionario -> {
            service.excluir(funcionario);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
