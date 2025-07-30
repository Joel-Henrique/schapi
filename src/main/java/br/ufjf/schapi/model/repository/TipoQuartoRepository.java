package br.ufjf.schapi.model.repository;

import br.ufjf.schapi.model.entity.TipoQuarto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoQuartoRepository  extends JpaRepository<TipoQuarto, Long> {
}
