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

    private Long idProduto;
    private Long idHospedagem;

    public static ConsumacaoDTO create(Consumacao consumacao) {
        ModelMapper modelMapper = new ModelMapper();
        ConsumacaoDTO dto = modelMapper.map(consumacao, ConsumacaoDTO.class);

        dto.descricao = consumacao.getDescricao();
        dto.valor = consumacao.getValor();
        dto.dataCompra = consumacao.getDataCompra();

        if (consumacao.getProduto() != null) {
            dto.idProduto = consumacao.getProduto().getId();
        }
        if (consumacao.getHospedagem() != null) {
            dto.idHospedagem = consumacao.getHospedagem().getId();
        }

        return dto;
    }
}
