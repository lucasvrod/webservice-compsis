package com.compsis.webservice.service;

import com.compsis.webservice.controller.exception.GroupAlreadyExistsException;
import com.compsis.webservice.dto.GrupoDTO;
import com.compsis.webservice.model.Grupo;
import com.compsis.webservice.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public GrupoDTO createGroup(GrupoDTO grupoDTO) throws GroupAlreadyExistsException {
        if (grupoRepository.findByNome(grupoDTO.getNome()) != null) {
            throw new GroupAlreadyExistsException("Já existe um grupo com esse nome.");
        }

        Grupo grupo = new Grupo();
        grupo.setNome(grupoDTO.getNome());
        grupo.setDataCriacao(LocalDateTime.now());
        grupo.setDataAlteracao(LocalDateTime.now());

        Grupo savedGroup = grupoRepository.save(grupo);
        return new GrupoDTO(savedGroup.getId(), savedGroup.getNome(),
                savedGroup.getDataCriacao(), savedGroup.getDataAlteracao());
    }

    public Grupo getGroupByName(String nome) {
        return grupoRepository.findByNome(nome);
    }

    public List<Grupo> getAllgroups() {
        return grupoRepository.findAll();
    }

    public Optional<Grupo> buscarGrupoPorId(Integer id) {
        return grupoRepository.findById(id);
    }
}
