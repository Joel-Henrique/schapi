package br.ufjf.schapi.model.repository;

import br.ufjf.schapi.model.entity.Consumacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumacaoRepository extends JpaRepository<Consumacao, Long> {

}