package br.ufjf.schapi.model.service;

import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.TipoQuarto;
import br.ufjf.schapi.model.repository.TipoQuartoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoQuartoService {

    private final TipoQuartoRepository repository;

    public TipoQuartoService(TipoQuartoRepository repository) {
        this.repository = repository;
    }

    public List<TipoQuarto> getTiposQuarto() {
        return repository.findAll();
    }

    public Optional<TipoQuarto> getTipoQuartoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoQuarto salvar(TipoQuarto tipoQuarto) {
        validar(tipoQuarto);
        return repository.save(tipoQuarto);
    }

    @Transactional
    public void excluir(TipoQuarto tipoQuarto) {
        Objects.requireNonNull(tipoQuarto.getId());
        repository.delete(tipoQuarto);
    }

    public void validar(TipoQuarto tipoQuarto) {
        if (tipoQuarto.getNome() == null || tipoQuarto.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome é obrigatório");
        }

        if (tipoQuarto.getDescricao() == null || tipoQuarto.getDescricao().trim().isEmpty()) {
            throw new RegraNegocioException("Descrição é obrigatória");
        }

        if (tipoQuarto.getCapacidade() <= 0) {
            throw new RegraNegocioException("Capacidade deve ser maior que zero");
        }

        if (tipoQuarto.getValor() <= 0) {
            throw new RegraNegocioException("Valor deve ser maior que zero");
        }
    }
}
