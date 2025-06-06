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

public class Consumacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private float valor;
    private Date dataCompra;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Hospedagem hospedagem;
}
