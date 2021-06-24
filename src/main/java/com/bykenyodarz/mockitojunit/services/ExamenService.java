package com.bykenyodarz.mockitojunit.services;

import com.bykenyodarz.mockitojunit.models.Examen;

public interface ExamenService {
    Examen findExamenByNombre(String nombre);
    Examen findExamenByNombreAndPreguntas(String nombre);
}
