package br.ufjf.schapi.api.dto;

import br.ufjf.schapi.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {
    private String cargo;
    private String telefone;

    public static FuncionarioDTO create(Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }
}
