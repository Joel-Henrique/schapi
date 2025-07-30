package br.ufjf.schapi.model.service;

import br.ufjf.schapi.model.entity.Consumacao;
import br.ufjf.schapi.model.repository.ConsumacaoRepository;
import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConsumacaoService {

    private ConsumacaoRepository repository;

    public ConsumacaoService(ConsumacaoRepository repository) {
        this.repository = repository;
    }

    public List<Consumacao> getConsumacao() {
        return repository.findAll();
    }

    public Optional<Consumacao> getConsumacaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Consumacao salvar(Consumacao consumacao) {
        validar(consumacao);
        return repository.save(consumacao);
    }

    @Transactional
    public void excluir(Consumacao consumacao) {
        Objects.requireNonNull(consumacao.getId());
        repository.delete(consumacao);
    }

    public void validar(Consumacao consumacao) {
        if (consumacao.getDescricao() == null || consumacao.getDescricao().trim().isEmpty()) {
            throw new RegraNegocioException("Descrição inválida");
        }
        if (consumacao.getValor() <= 0) {
            throw new RegraNegocioException("Valor deve ser maior que zero");
        }
        if (consumacao.getDataCompra() == null) {
            throw new RegraNegocioException("Data da compra é obrigatória");
        }
        if (consumacao.getProduto() == null || consumacao.getProduto().getId() == null) {
            throw new RegraNegocioException("Produto inválido");
        }
        if (consumacao.getHospedagem() == null || consumacao.getHospedagem().getId() == null) {
            throw new RegraNegocioException("Hospedagem inválida");
        }
    }

}

