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
    private Long id;
    private String nome;
    private String cpf;
    private String cargo;
    private String telefone;
    private Long usuarioSistemaId;

    public static FuncionarioDTO create(Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);

        if (funcionario.getUsuarioSistema() != null) {
            dto.usuarioSistemaId = funcionario.getUsuarioSistema().getId();
        }
        return dto;
    }
}
