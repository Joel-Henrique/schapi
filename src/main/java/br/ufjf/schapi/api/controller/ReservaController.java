package br.ufjf.schapi.api.controller;

import br.ufjf.schapi.api.dto.ReservaDTO;
import br.ufjf.schapi.exception.RegraNegocioException;
import br.ufjf.schapi.model.entity.Hospedagem;
import br.ufjf.schapi.model.entity.Hospede;
import br.ufjf.schapi.model.entity.Reserva;
import br.ufjf.schapi.model.service.ReservaService;
import br.ufjf.schapi.model.repository.HospedagemRepository;
import br.ufjf.schapi.model.repository.HospedeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService service;
    private final HospedeRepository hospedeRepository;
    private final HospedagemRepository hospedagemRepository;

    public ReservaController(ReservaService service, HospedeRepository hospedeRepository, HospedagemRepository hospedagemRepository) {
        this.service = service;
        this.hospedeRepository = hospedeRepository;
        this.hospedagemRepository = hospedagemRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listar() {
        List<ReservaDTO> reservas = service.getReservas()
                .stream()
                .map(ReservaDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> buscarPorId(@PathVariable Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        return reserva.map(r -> ResponseEntity.ok(ReservaDTO.create(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> salvar(@RequestBody ReservaDTO dto) {
        Reserva reserva = converter(dto);
        reserva = service.salvar(reserva);
        return new ResponseEntity<>(ReservaDTO.create(reserva), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> atualizar(@PathVariable Long id, @RequestBody ReservaDTO dto) {
        Optional<Reserva> reservaExistente = service.getReservaById(id);
        if (!reservaExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Reserva reserva = converter(dto);
        reserva.setId(id);
        reserva = service.salvar(reserva);
        return ResponseEntity.ok(ReservaDTO.create(reserva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if (!reserva.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        service.excluir(reserva.get());
        return ResponseEntity.noContent().build();
    }

    private Reserva converter(ReservaDTO dto) {
        Reserva reserva = new Reserva();
        reserva.setId(dto.getId());
        reserva.setDataEntrada(dto.getDataEntrada());
        reserva.setDataSaida(dto.getDataSaida());
        reserva.setStatus(dto.getStatus());

        if (dto.getHospedeId() != null) {
            Hospede hospede = hospedeRepository.findById(dto.getHospedeId())
                    .orElseThrow(() -> new RegraNegocioException("Hóspede não encontrado"));
            reserva.setHospede(hospede);
        }

        if (dto.getHospedagemId() != null) {
            Hospedagem hospedagem = hospedagemRepository.findById(dto.getHospedagemId())
                    .orElseThrow(() -> new RegraNegocioException("Hospedagem não encontrada"));
            reserva.setHospedagem(hospedagem);
        }

        return reserva;
    }
}
