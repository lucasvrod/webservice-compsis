package com.compsis.webservice.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sis_grupo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grupoid")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "datacriacao")
    private LocalDateTime dataCriacao;

    @Column(name = "dataalteracao")
    private LocalDateTime dataAlteracao;

    public Grupo(Grupo grupo, String dataFormat) {
        this.id = grupo.getId();
        this.nome = grupo.getNome();
        this.dataCriacao = grupo.getDataCriacao();
        this.dataAlteracao = grupo.getDataAlteracao();
        if (dataFormat != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dataFormat);
            if (this.dataCriacao != null) {
                this.dataCriacaoStr = this.dataCriacao.format(formatter);
            }
            if (this.dataAlteracao != null) {
                this.dataAlteracaoStr = this.dataAlteracao.format(formatter);
            }
        }
    }

    private String dataCriacaoStr;

    private String dataAlteracaoStr;
}


