package com.compsis.webservice.service;

import com.compsis.webservice.controller.exception.UserAlreadyExistsException;
import com.compsis.webservice.dto.GrupoDTO;
import com.compsis.webservice.dto.UsuarioDTO;
import com.compsis.webservice.model.Grupo;
import com.compsis.webservice.model.Usuario;
import com.compsis.webservice.repository.GrupoUsuarioRepository;
import com.compsis.webservice.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private GrupoUsuarioRepository grupoUsuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() throws UserAlreadyExistsException {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuario("joao");
        usuarioDTO.setNome("João da Silva");
        usuarioDTO.setSenha("123456");
        usuarioDTO.setDatacriacao(String.valueOf(LocalDateTime.now()));
        usuarioDTO.setDataalteracao(String.valueOf(LocalDateTime.now()));

        GrupoDTO grupoDTO = new GrupoDTO();
        grupoDTO.setNome("Administradores");

        usuarioDTO.setGrupos(Collections.singletonList(grupoDTO));

        Usuario savedUser = new Usuario();
        savedUser.setId(1);
        savedUser.setUsuario("joao");
        savedUser.setNome("João da Silva");
        savedUser.setSenha("123456");
        savedUser.setDataCriacao(LocalDateTime.now());
        savedUser.setDataAlteracao(LocalDateTime.now());

        Grupo grupo = new Grupo();
        grupo.setId(1);
        grupo.setNome("Administradores");
        grupo.setDataCriacao(LocalDateTime.now());
        grupo.setDataAlteracao(LocalDateTime.now());

        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        when(usuarioRepository.findByUsuario("joao")).thenReturn(null);
        when(usuarioRepository.save(usuarioCaptor.capture())).thenReturn(savedUser);
        when(grupoUsuarioRepository.save(any())).thenReturn(null);

        UsuarioDTO createdUser = usuarioService.createUser(usuarioDTO);

        Usuario capturedUser = usuarioCaptor.getValue();
        assertEquals("joao", capturedUser.getUsuario());
        assertEquals("João da Silva", capturedUser.getNome());
        assertEquals("123456", capturedUser.getSenha());
        assertNotNull(capturedUser.getDataCriacao());
        assertNotNull(capturedUser.getDataAlteracao());
        assertEquals(1, capturedUser.getGrupos().size());
        Grupo capturedGroup = capturedUser.getGrupos().get(0);
        assertEquals("Administradores", capturedGroup.getNome());
        assertNotNull(capturedGroup.getDataCriacao());
        assertNotNull(capturedGroup.getDataAlteracao());

        assertEquals(1, createdUser.getId());
        assertEquals("joao", createdUser.getUsuario());
        assertEquals("João da Silva", createdUser.getNome());
        assertNotNull(createdUser.getDatacriacao());
        assertNotNull(createdUser.getDataalteracao());
        assertEquals(1, createdUser.getGrupos().size());
        GrupoDTO createdGroup = createdUser.getGrupos().get(0);
        assertEquals("Administradores", createdGroup.getNome());
    }

    @Test
    void testCreateUserThrowsUserAlreadyExistsException() {
        UsuarioDTO existingUserDTO = new UsuarioDTO();
        existingUserDTO.setUsuario("joao");
        when(usuarioRepository.findByUsuario("joao")).thenReturn(new Usuario());

        UsuarioDTO newUserDTO = new UsuarioDTO();
        newUserDTO.setUsuario("joao");

        assertThrows(UserAlreadyExistsException.class, () -> usuarioService.createUser(newUserDTO));
    }

}