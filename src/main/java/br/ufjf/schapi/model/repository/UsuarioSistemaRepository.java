package br.ufjf.schapi.model.repository;
import br.ufjf.schapi.model.entity.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioSistemaRepository  extends JpaRepository<UsuarioSistema, Long> {
    Optional<UsuarioSistema> findByUsuario(String usuario);

}
