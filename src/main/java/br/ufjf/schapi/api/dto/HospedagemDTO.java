package br.ufjf.schapi.api.dto;

import br.ufjf.schapi.model.entity.Funcionario;
import br.ufjf.schapi.model.entity.Hospedagem;
import br.ufjf.schapi.model.entity.Hospede;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospedagemDTO {
    private Long id;

    private Hospede hospede;
    private Date dataEntrada;
    private Date dataSaida;
    private float valorTotal;
    private String status;

    public static HospedagemDTO create(Hospedagem hospedagem) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(hospedagem, HospedagemDTO.class);
    }
}
