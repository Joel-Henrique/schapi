package br.ufjf.schapi.api.dto;
import br.ufjf.schapi.model.entity.TipoServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoServicoDTO {
    private Long id;

    private String descricao;
    private String status;
    private float valor;

    public static TipoServicoDTO create(TipoServico tipoServico) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(tipoServico, TipoServicoDTO.class);
    }
}
