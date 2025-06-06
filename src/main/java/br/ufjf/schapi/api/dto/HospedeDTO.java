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
    private String email;
    private String telefone;
    public static HospedeDTO create(Hospede hospede) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(hospede, HospedeDTO.class);
    }
}
