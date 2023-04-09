package com.compsis.webservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sis_grupo_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "grupoid")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "usuarioid")
    private Usuario usuario;
}


