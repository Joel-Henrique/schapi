package br.ufjf.schapi.api.dto;

import br.ufjf.schapi.model.entity.Hospede;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospedeDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Long usuarioSistemaId;

    public static HospedeDTO create(Hospede hospede) {
        ModelMapper modelMapper = new ModelMapper();
        HospedeDTO dto = modelMapper.map(hospede, HospedeDTO.class);
        if (hospede.getUsuarioSistema() != null) {
            dto.setUsuarioSistemaId(hospede.getUsuarioSistema().getId());
        }
        return dto;
    }

}
