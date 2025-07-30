package br.ufjf.schapi.model.repository;
import br.ufjf.schapi.model.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReservaRepository  extends JpaRepository<Reserva, Long> {
}