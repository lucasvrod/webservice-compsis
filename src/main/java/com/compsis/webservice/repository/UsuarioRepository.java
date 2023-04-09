package com.compsis.webservice.repository;

import com.compsis.webservice.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByUsuario(String usuario);

    List<Usuario> findByNome(String nome);

}
