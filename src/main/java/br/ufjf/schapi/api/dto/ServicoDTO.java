package br.ufjf.schapi.api.dto;
import br.ufjf.schapi.model.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoDTO {
    private Long id;

    private String tipo;
    private String descricao;
    private Date dataServico;

    public static ServicoDTO create(Servico servico) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(servico, ServicoDTO.class);
    }
}
