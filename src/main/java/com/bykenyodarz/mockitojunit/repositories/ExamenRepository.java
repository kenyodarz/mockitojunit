package com.bykenyodarz.mockitojunit.repositories;

import com.bykenyodarz.mockitojunit.models.Examen;

import java.util.List;

public interface ExamenRepository {
    List<Examen> findAll();

    /* Parte 2 */
    Examen save(Examen examen);
}
