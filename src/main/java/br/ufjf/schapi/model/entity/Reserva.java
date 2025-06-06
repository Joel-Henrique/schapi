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

public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataEntrada;
    private Date dataSaida;
    private String status;

    @ManyToOne
    private Hospede hospede;
    @ManyToOne
    private Hospedagem hospedagem;
}
