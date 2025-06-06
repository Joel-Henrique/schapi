package br.ufjf.schapi.api.dto;
import br.ufjf.schapi.model.entity.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Long id;

    private Date dataEntrada;
    private Date dataSaida;
    private String status;

    public static ReservaDTO create(Reserva reserva) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(reserva, ReservaDTO.class);
    }
}
