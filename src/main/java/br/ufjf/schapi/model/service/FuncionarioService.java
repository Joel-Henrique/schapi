package br.ufjf.schapi.model.service;

import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.Funcionario;
import br.ufjf.schapi.model.repository.FuncionarioRepository;
import br.ufjf.schapi.model.repository.UsuarioSistemaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final UsuarioSistemaRepository usuarioSistemaRepository;

    public FuncionarioService(FuncionarioRepository repository, UsuarioSistemaRepository usuarioSistemaRepository) {
        this.repository = repository;
        this.usuarioSistemaRepository = usuarioSistemaRepository;
    }

    public List<Funcionario> getFuncionarios() {
        return repository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Funcionario salvar(Funcionario funcionario, Long usuarioSistemaId) {
        var usuarioSistema = usuarioSistemaRepository.findById(usuarioSistemaId)
                .orElseThrow(() -> new RegraNegocioException("Usuário do sistema inválido"));

        funcionario.setUsuarioSistema(usuarioSistema);

        validar(funcionario);
        return repository.save(funcionario);
    }

    @Transactional
    public void excluir(Funcionario funcionario) {
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }

    public void validar(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome é obrigatório");
        }

        if (funcionario.getCpf() == null || funcionario.getCpf().trim().isEmpty()) {
            throw new RegraNegocioException("CPF é obrigatório");
        }

        if (funcionario.getCargo() == null || funcionario.getCargo().trim().isEmpty()) {
            throw new RegraNegocioException("Cargo é obrigatório");
        }

        if (funcionario.getTelefone() == null || funcionario.getTelefone().trim().isEmpty()) {
            throw new RegraNegocioException("Telefone é obrigatório");
        }

        if (funcionario.getUsuarioSistema() == null || funcionario.getUsuarioSistema().getId() == null) {
            throw new RegraNegocioException("Usuário do sistema inválido");
        }
    }
}
