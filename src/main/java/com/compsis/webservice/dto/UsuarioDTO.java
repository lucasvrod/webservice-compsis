package com.compsis.webservice.dto;

import com.compsis.webservice.model.Grupo;
import com.compsis.webservice.model.Usuario;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UsuarioDTO {

    private Integer id;

    private String usuario;

    private String nome;

    private String senha;

    private String datacriacao;

    private String dataalteracao;

    private List<GrupoDTO> grupos;


    public UsuarioDTO() {
    }

    public UsuarioDTO(Integer id, String usuario, String nome, LocalDateTime dataCriacao, LocalDateTime dataAlteracao, List<Grupo> grupos) {
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
        this.senha = "";
        this.datacriacao = dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.dataalteracao = dataAlteracao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.grupos = grupos != null ? formatarGrupos(grupos) : null;
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.usuario = usuario.getUsuario();
        this.nome = usuario.getNome();
        this.senha = "";
        this.datacriacao = usuario.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.dataalteracao = usuario.getDataAlteracao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.grupos = formatarGrupos(usuario.getGrupos());
    }

    private List<GrupoDTO> formatarGrupos(List<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> new GrupoDTO(grupo, "dd/MM/yyyy"))
                .collect(Collectors.toList());
    }
}
