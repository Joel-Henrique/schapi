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

    private Long hospedagemId;
    private Long funcionarioId;
    private Long tipoServicoId;

    public static ServicoDTO create(Servico servico) {
        ModelMapper modelMapper = new ModelMapper();
        ServicoDTO dto = modelMapper.map(servico, ServicoDTO.class);

        if (servico.getHospedagem() != null)
            dto.setHospedagemId(servico.getHospedagem().getId());

        if (servico.getFuncionario() != null)
            dto.setFuncionarioId(servico.getFuncionario().getId());

        if (servico.getTipoServico() != null)
            dto.setTipoServicoId(servico.getTipoServico().getId());

        return dto;
    }
}
