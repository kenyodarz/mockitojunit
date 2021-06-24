package com.bykenyodarz.mockitojunit.services;

import com.bykenyodarz.mockitojunit.models.Examen;
import com.bykenyodarz.mockitojunit.repositories.ExamenRepository;
import com.bykenyodarz.mockitojunit.repositories.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static com.bykenyodarz.mockitojunit.services.Datos.*;
import static org.junit.jupiter.api.Assertions.*;

class ExamenServiceImplTest {

    PreguntaRepository preguntaRepository;
    ExamenRepository examenRepository;
    ExamenService service;

    @BeforeEach
    void setUp() {
        this.preguntaRepository = mock(PreguntaRepository.class);
        this.examenRepository = mock(ExamenRepository.class);
        service = new ExamenServiceImpl(examenRepository, preguntaRepository);
    }

    @Test
    void testFindExamenByNombre() {

        when(examenRepository.findAll()).thenReturn(EXAMEN_LIST);
        assertAll(
                () -> assertNotNull(service.findExamenByNombre("Matematicas")),
                () -> assertEquals(5L, service.findExamenByNombre("Matematicas").getId()),
                () -> assertEquals("Matematicas", service.findExamenByNombre("Matematicas").getNombre())
        );

    }

    @Test
    void testFindExamenByNombreEmpty() {
        List<Examen> datos = Collections.emptyList();
        when(examenRepository.findAll()).thenReturn(datos);
        assertNull(service.findExamenByNombre("Matematicas"));
    }

    @Test
    void testFindExamenByNombreAndPreguntas() {
        when(examenRepository.findAll()).thenReturn(EXAMEN_LIST);
        when(preguntaRepository.findPreguntaByExamenId(anyLong())).thenReturn(PREGUNTAS);
        var examen = service.findExamenByNombreAndPreguntas("Historia");
        assertAll(
                () -> {
                    assertEquals(5, examen.getPreguntas().size());
                },
                () -> {
                    assertTrue(examen.getPreguntas().contains("integrales"));
                }
        );
    }

    @Test
    void testFindExamenByNombreAndPreguntasVerify() {
        when(examenRepository.findAll()).thenReturn(EXAMEN_LIST);
        when(preguntaRepository.findPreguntaByExamenId(anyLong())).thenReturn(PREGUNTAS);
        var examen = service.findExamenByNombreAndPreguntas("Historia");
        assertAll(
                () -> assertEquals(5, examen.getPreguntas().size()),
                () -> assertTrue(examen.getPreguntas().contains("integrales"))
        );
        verify(examenRepository).findAll();
        verify(preguntaRepository).findPreguntaByExamenId(anyLong());
    }
}