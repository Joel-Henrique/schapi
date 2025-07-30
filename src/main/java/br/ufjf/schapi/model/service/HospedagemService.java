package br.ufjf.schapi.model.service;

import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.Hospedagem;
import br.ufjf.schapi.model.repository.HospedagemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HospedagemService {

    private final HospedagemRepository repository;

    public HospedagemService(HospedagemRepository repository) {
        this.repository = repository;
    }

    public List<Hospedagem> getHospedagens() {
        return repository.findAll();
    }

    public Optional<Hospedagem> getHospedagemById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Hospedagem salvar(Hospedagem hospedagem) {
        validar(hospedagem);
        return repository.save(hospedagem);
    }

    @Transactional
    public void excluir(Hospedagem hospedagem) {
        Objects.requireNonNull(hospedagem.getId());
        repository.delete(hospedagem);
    }

    public void validar(Hospedagem hospedagem) {
        if (hospedagem.getDataEntrada() == null) {
            throw new RegraNegocioException("Data de entrada é obrigatória");
        }

        if (hospedagem.getDataSaida() == null) {
            throw new RegraNegocioException("Data de saída é obrigatória");
        }

        if (hospedagem.getValorTotal() < 0) {
            throw new RegraNegocioException("Valor total deve ser positivo");
        }

        if (hospedagem.getStatus() == null || hospedagem.getStatus().trim().isEmpty()) {
            throw new RegraNegocioException("Status é obrigatório");
        }

        if (hospedagem.getHospede() == null || hospedagem.getHospede().getId() == null) {
            throw new RegraNegocioException("Hóspede inválido");
        }
    }
}
