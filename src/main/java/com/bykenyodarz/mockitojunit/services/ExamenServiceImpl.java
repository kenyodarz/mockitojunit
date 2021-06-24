package com.bykenyodarz.mockitojunit.services;

import com.bykenyodarz.mockitojunit.models.Examen;
import com.bykenyodarz.mockitojunit.repositories.ExamenRepository;
import com.bykenyodarz.mockitojunit.repositories.PreguntaRepository;

public class ExamenServiceImpl implements ExamenService{

    private final ExamenRepository repository;
    private final PreguntaRepository preguntaRepository;

    public ExamenServiceImpl(ExamenRepository repository,
                             PreguntaRepository preguntaRepository) {
        this.repository = repository;
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public Examen findExamenByNombre(String nombre) {
        return repository.findAll()
                .stream()
                .filter(examen -> examen.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Examen findExamenByNombreAndPreguntas(String nombre) {
        var examen = repository.findAll()
                .stream()
                .filter(e -> e.getNombre().equals(nombre))
                .findFirst().orElseThrow();
        examen.setPreguntas(preguntaRepository.findPreguntaByExamenId(examen.getId()));
        return examen;
    }
}
