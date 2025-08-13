package br.ufjf.schapi.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Hospedagem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataEntrada;
    private Date dataSaida;
    private float valorTotal;
    private String status;

    @ManyToOne
    private Hospede hospede;
    @ManyToOne //OneToMany???
    private Quarto quarto;
}
