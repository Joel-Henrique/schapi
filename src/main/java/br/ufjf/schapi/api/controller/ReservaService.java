package br.ufjf.schapi.model.service;

import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.Reserva;
import br.ufjf.schapi.model.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository repository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> getReservas() {
        return repository.findAll();
    }

    public Optional<Reserva> getReservaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Reserva salvar(Reserva reserva) {
        validar(reserva);
        return repository.save(reserva);
    }

    @Transactional
    public void excluir(Reserva reserva) {
        Objects.requireNonNull(reserva.getId());
        repository.delete(reserva);
    }

    public void validar(Reserva reserva) {
        Date entrada = reserva.getDataEntrada();
        Date saida = reserva.getDataSaida();

        if (entrada == null) {
            throw new RegraNegocioException("Data de entrada é obrigatória");
        }

        if (saida == null) {
            throw new RegraNegocioException("Data de saída é obrigatória");
        }

        if (saida.before(entrada)) {
            throw new RegraNegocioException("Data de saída não pode ser antes da data de entrada");
        }

        if (reserva.getStatus() == null || reserva.getStatus().trim().isEmpty()) {
            throw new RegraNegocioException("Status é obrigatório");
        }
    }
}
