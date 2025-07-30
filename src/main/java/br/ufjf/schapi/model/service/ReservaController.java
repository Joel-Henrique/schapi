package br.ufjf.schapi.model.service;

import br.ufjf.schapi.api.dto.ReservaDTO;
import br.ufjf.schapi.model.entity.Reserva;
import br.ufjf.schapi.model.service.ReservaService;
import org.modelmapper.ModelMapper;
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

    public ReservaController(ReservaService service) {
        this.service = service;
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
        Reserva reserva = new ModelMapper().map(dto, Reserva.class);
        reserva = service.salvar(reserva);
        return new ResponseEntity<>(ReservaDTO.create(reserva), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> atualizar(@PathVariable Long id, @RequestBody ReservaDTO dto) {
        if (!service.getReservaById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Reserva reserva = new ModelMapper().map(dto, Reserva.class);
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
}
