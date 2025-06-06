package br.ufjf.schapi.api.dto;
import br.ufjf.schapi.model.entity.UsuarioSistema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSistemaDTO {
    private Long id;

    private String usuario;
    private String senha;
    private String tipo;

    public static UsuarioSistemaDTO create(UsuarioSistema usuarioSistema) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(usuarioSistema, UsuarioSistemaDTO.class);
    }

}
