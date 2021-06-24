package com.bykenyodarz.mockitojunit.repositories;

import com.bykenyodarz.mockitojunit.models.Examen;

import java.util.List;

public interface ExamenRepository {
    List<Examen> findAll();
}
