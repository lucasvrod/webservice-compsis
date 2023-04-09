package com.compsis.webservice.controller;

import com.compsis.webservice.controller.exception.GroupAlreadyExistsException;
import com.compsis.webservice.dto.GrupoDTO;
import com.compsis.webservice.model.Grupo;
import com.compsis.webservice.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/grupo")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @PostMapping
    public ResponseEntity criarGrupo(@RequestBody GrupoDTO grupoDTO) {
        try {
            grupoService.createGroup(grupoDTO);
            return ResponseEntity.ok().body("Grupo criado com sucesso!");
        } catch (GroupAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um grupo com esse nome.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar grupo");
        }
    }

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> buscarGrupos(@RequestParam(required = false) String nome) {
        List<Grupo> grupos = new ArrayList<>();

        if (nome != null) {
            Grupo grupo = grupoService.getGroupByName(nome);
            if (grupo != null) {
                grupos.add(grupo);
            }
        } else {
            grupos = grupoService.getAllgroups();
        }

        if (grupos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<GrupoDTO> gruposEncontrados = grupos.stream()
                .map(GrupoDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(gruposEncontrados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoDTO> buscarGrupoPorId(@PathVariable Integer id) {
        Optional<Grupo> grupoEncontrado = grupoService.buscarGrupoPorId(id);
        if (grupoEncontrado.isPresent()) {
            GrupoDTO grupoDTO = new GrupoDTO(grupoEncontrado.get());
            if (grupoDTO.getGrupoid() == null) {
                grupoDTO.setGrupoid(grupoEncontrado.get().getId());
            }
            return ResponseEntity.ok(grupoDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
