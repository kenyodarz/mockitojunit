package com.bykenyodarz.mockitojunit.services;

import com.bykenyodarz.mockitojunit.models.Examen;

import java.util.Arrays;
import java.util.List;

public final class Datos {
    public final static List<Examen> EXAMEN_LIST = Arrays.asList(
            new Examen(5L, "Matematicas"),
            new Examen(6L, "Lengua"),
            new Examen(7L, "Historia")
    );

    public final static List<String> PREGUNTAS = Arrays.asList(
            "aritmetica", "integrales", "derivadas", "trigonometria", "geometria"
    );

    /* Parte 2 */
    public final static Examen EXAMEN = new Examen(8L, "Fisica");
    /* Parte 3 */
    public final static Examen EXAMEN_INCREMENTAL = new Examen(null, "Fisica");
}
