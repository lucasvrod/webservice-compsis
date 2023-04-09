package com.compsis.webservice.repository;

import com.compsis.webservice.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

    Grupo findByNome(String nome);

}
