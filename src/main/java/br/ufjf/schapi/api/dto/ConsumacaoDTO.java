package br.ufjf.schapi.api.dto;

import br.ufjf.schapi.model.entity.Consumacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumacaoDTO {
    private Long id;
    private String descricao;
    private float valor;
    private Date dataCompra;

    public static ConsumacaoDTO create(Consumacao consumacao) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(consumacao, ConsumacaoDTO.class);
    }
}
