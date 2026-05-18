package com.discografia.controller;

import com.discografia.model.Artista;
import com.discografia.repository.IArtistaRepository;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    private final IArtistaRepository artistaRepo;

    public ArtistaController(IArtistaRepository artistaRepo) {
        this.artistaRepo = artistaRepo;
    }

    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {

        Artista saved = artistaRepo.save(artista);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {

        return ResponseEntity.ok(artistaRepo.findAll());
    }

    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> HandleGetArtistaRequest(@PathVariable String id) {

        Optional<Artista> artista = artistaRepo.findById(id);

        if (artista.isPresent()) {
            return ResponseEntity.ok(artista.get());
        }

        return ResponseEntity.status(404).body("Artista no encontrado");
    }

    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> HandleUpdateArtistaRequest(
            @PathVariable String id,
            @RequestBody Artista artistaData
    ) {

        if (!artistaRepo.existsById(id)) {
            return ResponseEntity.status(404).body("Artista no existe");
        }

        artistaData._id = id;

        Artista updated = artistaRepo.save(artistaData);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> HandleDeleteArtistaRequest(@PathVariable String id) {

        if (!artistaRepo.existsById(id)) {
            return ResponseEntity.status(404).body("Artista no existe");
        }

        artistaRepo.deleteById(id);
        return ResponseEntity.ok("Artista eliminado correctamente");
    }
}