package br.ufjf.schapi.model.repository;

import br.ufjf.schapi.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository  extends JpaRepository<Funcionario, Long> {
}
