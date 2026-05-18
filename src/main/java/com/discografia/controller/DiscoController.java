package com.discografia.controller;

import com.discografia.model.Disco;
import com.discografia.repository.IDiscoRepository;
import com.discografia.repository.IArtistaRepository;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    private final IDiscoRepository discoRepo;
    private final IArtistaRepository artistaRepo;

    public DiscoController(IDiscoRepository discoRepo, IArtistaRepository artistaRepo) {
        this.discoRepo = discoRepo;
        this.artistaRepo = artistaRepo;
    }

    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> HandlePostDiscoRequest(@RequestBody Disco disco) {

        if (!artistaRepo.existsById(disco.idArtista)) {
            return ResponseEntity
                    .status(404)
                    .body("El artista no existe");
        }

        Disco saved = discoRepo.save(disco);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {

        return ResponseEntity.ok(discoRepo.findAll());
    }

    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> HandleGetDiscoRequest(@PathVariable String id) {

        Optional<Disco> disco = discoRepo.findById(id);

        if (disco.isPresent()) {
            return ResponseEntity.ok(disco.get());
        }

        return ResponseEntity.status(404).body("Disco no encontrado");
    }

    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {

        List<Disco> discos = discoRepo.findDiscosByIdArtista(id);
        return ResponseEntity.ok(discos);
    }
}