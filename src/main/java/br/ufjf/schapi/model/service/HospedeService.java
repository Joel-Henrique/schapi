package br.ufjf.schapi.model.service;

import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.Hospede;
import br.ufjf.schapi.model.repository.HospedeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HospedeService {

    private final HospedeRepository repository;

    public HospedeService(HospedeRepository repository) {
        this.repository = repository;
    }

    public List<Hospede> getHospedes() {
        return repository.findAll();
    }

    public Optional<Hospede> getHospedeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Hospede salvar(Hospede hospede) {
        validar(hospede);
        return repository.save(hospede);
    }

    @Transactional
    public void excluir(Hospede hospede) {
        Objects.requireNonNull(hospede.getId());
        repository.delete(hospede);
    }

    public void validar(Hospede hospede) {
        if (hospede.getNome() == null || hospede.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome é obrigatório");
        }

        if (hospede.getCpf() == null || hospede.getCpf().trim().isEmpty()) {
            throw new RegraNegocioException("CPF é obrigatório");
        }

        if (hospede.getEmail() == null || hospede.getEmail().trim().isEmpty()) {
            throw new RegraNegocioException("Email é obrigatório");
        }

        if (hospede.getTelefone() == null || hospede.getTelefone().trim().isEmpty()) {
            throw new RegraNegocioException("Telefone é obrigatório");
        }
    }
}
