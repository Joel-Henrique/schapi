package br.ufjf.schapi.model.repository;
import br.ufjf.schapi.model.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository  extends JpaRepository<Servico, Long> {
}