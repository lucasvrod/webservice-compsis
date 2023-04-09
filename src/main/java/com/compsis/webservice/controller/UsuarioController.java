package com.compsis.webservice.controller;

import com.compsis.webservice.controller.exception.UserAlreadyExistsException;
import com.compsis.webservice.dto.UsuarioDTO;
import com.compsis.webservice.model.Usuario;
import com.compsis.webservice.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioLogado = usuarioService.login(usuarioDTO);
        if (usuarioLogado != null) {
            return ResponseEntity.ok().body("Usúario logado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        }
    }

    @PostMapping("/usuario")
    public ResponseEntity criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            usuarioService.createUser(usuarioDTO);
            return ResponseEntity.ok().body("Usuário criado com sucesso!");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com esse nome de usuário.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar usuário");
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<UsuarioDTO>> buscarUsuarios(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nome) {

        List<Usuario> usuarios = new ArrayList<>();

        if (id != null) {
            Usuario usuario = usuarioService.getUserById(id);
            if (usuario != null) {
                usuarios.add(usuario);
            }
        } else if (nome != null) {
            usuarios = usuarioService.getUsersByName(nome);
        } else {
            usuarios = usuarioService.getAllUsers();
        }

        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<UsuarioDTO> usuariosEncontrados = usuarios.stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuariosEncontrados);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Integer id) {
        Optional<Usuario> usuarioEncontrado = usuarioService.buscarUsuarioPorId(id);
        if (usuarioEncontrado.isPresent()) {
            UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioEncontrado.get());
            if (usuarioDTO.getId() == null) {
                usuarioDTO.setId(usuarioEncontrado.get().getId());
            }
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}


