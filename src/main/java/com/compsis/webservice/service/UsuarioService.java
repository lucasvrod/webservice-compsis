package com.compsis.webservice.service;

import com.compsis.webservice.controller.exception.UserAlreadyExistsException;
import com.compsis.webservice.dto.GrupoDTO;
import com.compsis.webservice.dto.UsuarioDTO;
import com.compsis.webservice.model.Grupo;
import com.compsis.webservice.model.Usuario;
import com.compsis.webservice.repository.GrupoUsuarioRepository;
import com.compsis.webservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

    public Usuario login(UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findByUsuario(dto.getUsuario());
        if (usuario != null && usuario.getSenha().equals(dto.getSenha())) {
            return usuario;
        } else {
            return null;
        }
    }

    public Optional<Usuario> buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioDTO createUser(UsuarioDTO usuarioDTO) throws UserAlreadyExistsException {
        if (usuarioRepository.findByUsuario(usuarioDTO.getUsuario()) != null) {
            throw new UserAlreadyExistsException("Já existe um usuário com esse nome de usuário.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setDataAlteracao(LocalDateTime.now());

        if (usuarioDTO.getGrupos() != null && !usuarioDTO.getGrupos().isEmpty()) {
            List<Grupo> grupos = new ArrayList<>();
            for (GrupoDTO grupoDTO : usuarioDTO.getGrupos()) {
                Grupo grupo = new Grupo();
                grupo.setNome(grupoDTO.getNome());
                grupo.setDataCriacao(LocalDateTime.now());
                grupo.setDataAlteracao(LocalDateTime.now());
                grupos.add(grupo);
            }
            usuario.setGrupos(grupos);
        }

        Usuario savedUser = usuarioRepository.save(usuario);

        return new UsuarioDTO(
                savedUser.getId(),
                savedUser.getUsuario(),
                savedUser.getNome(),
                savedUser.getDataCriacao(),
                savedUser.getDataAlteracao(),
                usuario.getGrupos()
        );
    }

    public List<UsuarioDTO> searchUsers(Long id, String nome) {
        List<Usuario> users = usuarioRepository.findByNome(nome);

        List<UsuarioDTO> userDTOs = new ArrayList<>();
        for (Usuario user : users) {
            userDTOs.add(new UsuarioDTO(user.getId(), user.getUsuario(), user.getNome(), user.getDataCriacao(),
                    user.getDataAlteracao(), user.getGrupos()));
        }
        return userDTOs;
    }

    public Usuario getUserById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> getUsersByName(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    public Usuario findByNome(String username) {
        return usuarioRepository.findByUsuario(username);
    }
}