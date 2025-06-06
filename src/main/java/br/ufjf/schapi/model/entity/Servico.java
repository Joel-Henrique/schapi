package br.ufjf.schapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String descricao;
    private Date dataServico;

    @ManyToOne
    private Hospedagem hospedagem;
    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private TipoServico tipoServico;
}
