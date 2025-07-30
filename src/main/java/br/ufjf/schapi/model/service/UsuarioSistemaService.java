package br.ufjf.schapi.model.service;

import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.UsuarioSistema;
import br.ufjf.schapi.model.repository.UsuarioSistemaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioSistemaService {

    private final UsuarioSistemaRepository repository;

    public UsuarioSistemaService(UsuarioSistemaRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioSistema> getUsuariosSistema() {
        return repository.findAll();
    }

    public Optional<UsuarioSistema> getUsuarioSistemaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public UsuarioSistema salvar(UsuarioSistema usuarioSistema) {
        validar(usuarioSistema);
        return repository.save(usuarioSistema);
    }

    @Transactional
    public void excluir(UsuarioSistema usuarioSistema) {
        Objects.requireNonNull(usuarioSistema.getId());
        repository.delete(usuarioSistema);
    }

    public void validar(UsuarioSistema usuarioSistema) {
        if (usuarioSistema.getUsuario() == null || usuarioSistema.getUsuario().trim().isEmpty()) {
            throw new RegraNegocioException("Usuário é obrigatório");
        }

        if (usuarioSistema.getSenha() == null || usuarioSistema.getSenha().trim().isEmpty()) {
            throw new RegraNegocioException("Senha é obrigatória");
        }

        if (usuarioSistema.getTipo() == null || usuarioSistema.getTipo().trim().isEmpty()) {
            throw new RegraNegocioException("Tipo é obrigatório");
        }
    }
}
