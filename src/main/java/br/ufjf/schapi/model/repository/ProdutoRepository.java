package br.ufjf.schapi.model.repository;
import br.ufjf.schapi.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository  extends JpaRepository<Produto, Long> {
}
