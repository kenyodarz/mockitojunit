package com.bykenyodarz.mockitojunit.services;

import com.bykenyodarz.mockitojunit.models.Examen;
import com.bykenyodarz.mockitojunit.repositories.ExamenRepository;
import com.bykenyodarz.mockitojunit.repositories.PreguntaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;

import static com.bykenyodarz.mockitojunit.services.Datos.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {

    @Mock
    PreguntaRepository preguntaRepository;
    @Mock
    ExamenRepository examenRepository;
    @InjectMocks
    //ExamenService service;
    ExamenServiceImpl service;

    /*@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //this.preguntaRepository = mock(PreguntaRepository.class);
        //this.examenRepository = mock(ExamenRepository.class);
        //service = new ExamenServiceImpl(examenRepository, preguntaRepository);
    }*/

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
                () -> assertTrue(examen.getPreguntas().contains("integrales")),
                () -> verify(examenRepository).findAll(),
                () -> verify(preguntaRepository).findPreguntaByExamenId(anyLong())
        );
    }

    @Test
    void testSaveExamen() {
        var e = EXAMEN;
        e.setPreguntas(PREGUNTAS);
        when(examenRepository.save(any(Examen.class))).thenReturn(EXAMEN);
        var examen = service.saveExamen(e);
        assertAll(
                () -> verify(examenRepository).save(any(Examen.class)),
                () -> verify(preguntaRepository).guardarVarias(anyList()),
                () -> assertNotNull(examen.getId()),
                () -> assertEquals(8L, examen.getId()),
                () -> assertEquals("Fisica", examen.getNombre())
        );
    }

    @Test
    void testSaveExamenIncremental() {
        // Given
        var e = EXAMEN_INCREMENTAL;
        e.setPreguntas(PREGUNTAS);

        when(examenRepository.save(any(Examen.class))).then(new Answer<Examen>() {
            Long secuencia = 8L;

            @Override
            public Examen answer(InvocationOnMock invocationOnMock) throws Throwable {
                Examen examen = invocationOnMock.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
        });

        // When
        var examen = service.saveExamen(e);

        // Then
        assertAll(
                () -> assertNotNull(examen.getId()),
                () -> assertEquals(8L, examen.getId()),
                () -> assertEquals("Fisica", examen.getNombre())
        );
    }

    @Test
    void testFindExamenByNombreAndPreguntasHandleException() {
        when(examenRepository.findAll()).thenReturn(EXAMEN_LIST);
        when(preguntaRepository.findPreguntaByExamenId(anyLong())).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> {
            service.findExamenByNombreAndPreguntas("Matematicas");
        });

    }
}