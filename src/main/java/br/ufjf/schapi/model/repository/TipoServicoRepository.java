package br.ufjf.schapi.model.repository;
import br.ufjf.schapi.model.entity.TipoServico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoServicoRepository  extends JpaRepository<TipoServico, Long> {
}
