package br.ufjf.schapi.model.service;

import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.TipoServico;
import br.ufjf.schapi.model.repository.TipoServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoServicoService {

    private final TipoServicoRepository repository;

    public TipoServicoService(TipoServicoRepository repository) {
        this.repository = repository;
    }

    public List<TipoServico> getTiposServico() {
        return repository.findAll();
    }

    public Optional<TipoServico> getTipoServicoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoServico salvar(TipoServico tipoServico) {
        validar(tipoServico);
        return repository.save(tipoServico);
    }

    @Transactional
    public void excluir(TipoServico tipoServico) {
        Objects.requireNonNull(tipoServico.getId());
        repository.delete(tipoServico);
    }

    public void validar(TipoServico tipoServico) {
        if (tipoServico.getDescricao() == null || tipoServico.getDescricao().trim().isEmpty()) {
            throw new RegraNegocioException("Descrição é obrigatória");
        }

        if (tipoServico.getStatus() == null || tipoServico.getStatus().trim().isEmpty()) {
            throw new RegraNegocioException("Status é obrigatório");
        }

        if (tipoServico.getValor() <= 0) {
            throw new RegraNegocioException("Valor deve ser maior que zero");
        }
    }
}
