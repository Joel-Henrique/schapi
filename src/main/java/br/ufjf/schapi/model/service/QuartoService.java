package br.ufjf.schapi.model.service;

import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.Quarto;
import br.ufjf.schapi.model.repository.QuartoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuartoService {

    private final QuartoRepository repository;

    public QuartoService(QuartoRepository repository) {
        this.repository = repository;
    }

    public List<Quarto> getQuartos() {
        return repository.findAll();
    }

    public Optional<Quarto> getQuartoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Quarto salvar(Quarto quarto) {
        validar(quarto);
        return repository.save(quarto);
    }

    @Transactional
    public void excluir(Quarto quarto) {
        Objects.requireNonNull(quarto.getId());
        repository.delete(quarto);
    }

    public void validar(Quarto quarto) {
        if (quarto.getNumero() <= 0) {
            throw new RegraNegocioException("Número do quarto deve ser maior que zero");
        }

        if (quarto.getStatus() == null || quarto.getStatus().trim().isEmpty()) {
            throw new RegraNegocioException("Status do quarto é obrigatório");
        }
    }
}
