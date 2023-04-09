package com.compsis.webservice.dto;

import com.compsis.webservice.model.Grupo;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class GrupoDTO {

    private Integer grupoid;
    private String nome;
    private String datacriacao;
    private String dataalteracao;

    public GrupoDTO(){}

    public GrupoDTO(Integer id, String nome, LocalDateTime dataCriacao, LocalDateTime dataAlteracao) {
    }

    public GrupoDTO(Grupo grupo) {
        this.grupoid = grupo.getId();
        this.nome = grupo.getNome();
        this.datacriacao = grupo.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.dataalteracao = grupo.getDataAlteracao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public GrupoDTO(Grupo grupo, String dataFormat) {
        this.grupoid = grupo.getId();
        this.nome = grupo.getNome();
        this.datacriacao = grupo.getDataCriacao().format(DateTimeFormatter.ofPattern(dataFormat));
        this.dataalteracao = grupo.getDataAlteracao().format(DateTimeFormatter.ofPattern(dataFormat));
    }
}
