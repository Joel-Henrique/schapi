package br.ufjf.schapi.model.repository;
import br.ufjf.schapi.model.entity.Hospedagem;
import br.ufjf.schapi.model.entity.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospedagemRepository  extends JpaRepository<Hospedagem, Long> {
    List<Hospedagem> findByHospede(Hospede hospede);

}
