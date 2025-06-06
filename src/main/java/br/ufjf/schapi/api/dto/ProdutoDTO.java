package br.ufjf.schapi.api.dto;
import br.ufjf.schapi.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;

    private float valor;
    private String descricao;
    private int quantidade;
    public static ProdutoDTO create(Produto produto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produto, ProdutoDTO.class);
    }
}
