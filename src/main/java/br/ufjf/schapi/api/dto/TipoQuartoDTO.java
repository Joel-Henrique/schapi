package br.ufjf.schapi.api.dto;
import br.ufjf.schapi.model.entity.TipoQuarto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoQuartoDTO {
    private Long id;

    private String nome;
    private String descricao;
    private int capacidade;
    private float valor;

    public static TipoQuartoDTO create(TipoQuarto tipoQuarto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(tipoQuarto, TipoQuartoDTO.class);
    }
}
