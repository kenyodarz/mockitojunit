package com.bykenyodarz.mockitojunit.repositories;

import java.util.List;

public interface PreguntaRepository {
    List<String> findPreguntaByExamenId(Long id);

    /* Parte 2 */
    void guardarVarias(List<String> preguntas);
}
