package br.ufjf.schapi.model.service;

import br.ufjf.schapi.model.entity.Produto;
import br.ufjf.schapi.model.repository.ProdutoRepository;
import br.ufjf.schapi.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Optional<Produto> getProdutoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Produto salvar(Produto produto) {
        validar(produto);
        return repository.save(produto);
    }

    @Transactional
    public void excluir(Produto produto) {
        Objects.requireNonNull(produto.getId());
        repository.delete(produto);
    }

    public void validar(Produto produto) {
        if (produto.getDescricao() == null || produto.getDescricao().trim().isEmpty()) {
            throw new RegraNegocioException("Descrição inválida");
        }
        if (produto.getValor() <= 0) {
            throw new RegraNegocioException("Valor deve ser maior que zero");
        }
        if (produto.getQuantidade() < 0) {
            throw new RegraNegocioException("Quantidade não pode ser negativa");
        }
    }
}
