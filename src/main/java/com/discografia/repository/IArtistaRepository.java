package com.discografia.repository;

import com.discografia.model.Artista;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IArtistaRepository extends MongoRepository<Artista, String> {
}