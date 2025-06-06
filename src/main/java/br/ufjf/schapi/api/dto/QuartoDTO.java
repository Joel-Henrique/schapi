package br.ufjf.schapi.api.dto;
import br.ufjf.schapi.model.entity.Quarto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuartoDTO {
    private Long id;

    private int numero;
    private String status;

    public static QuartoDTO create(Quarto quarto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(quarto, QuartoDTO.class);
    }
}
