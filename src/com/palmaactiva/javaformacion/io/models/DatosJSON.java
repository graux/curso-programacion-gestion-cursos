package com.palmaactiva.javaformacion.io.models;

import com.palmaactiva.javaformacion.io.Datos;
import com.palmaactiva.javaformacion.io.ProveedorDatos;
import com.palmaactiva.javaformacion.lib.Alumno;
import com.palmaactiva.javaformacion.lib.Curso;
import com.palmaactiva.javaformacion.lib.Profesor;
import com.palmaactiva.javaformacion.lib.Tabulable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class DatosJSON {

    private final Alumno[] alumnos;
    private final Profesor[] docentes;
    private final CursoJSON[] cursos;

    public DatosJSON(Alumno[] alumnos, Profesor[] docentes, Curso[] cursos) {
        this.alumnos = alumnos;
        this.docentes = docentes;
        List<CursoJSON> cursosJSON = Arrays.asList(cursos).stream().map(c -> new CursoJSON(c)).collect(Collectors.toList());
        this.cursos = cursosJSON.toArray(new CursoJSON[cursosJSON.size()]);
    }

    public DatosJSON(Collection<Alumno> alumnos, Collection<Profesor> docentes, Collection<Curso> cursos) {
        this.alumnos = alumnos.toArray(new Alumno[alumnos.size()]);
        this.docentes = docentes.toArray(new Profesor[docentes.size()]);
        List<CursoJSON> cursosJSON = cursos.stream().map(c -> new CursoJSON(c)).collect(Collectors.toList());
        this.cursos = cursosJSON.toArray(new CursoJSON[cursosJSON.size()]);
    }

    public Alumno[] getAlumnos() {
        return alumnos;
    }

    public Profesor[] getDocentes() {
        return docentes;
    }

    public Curso[] getCursos(ProveedorDatos datos) {
        List<Curso> cursosNormales = Arrays.asList(cursos).stream().map(cjson -> cjson.getCurso(datos)).collect(Collectors.toList());
        return cursosNormales.toArray(new Curso[cursosNormales.size()]);
    }

    public Tabulable[] getTabulables() {
        ProveedorDatos datosTemporal = new Datos();
        ArrayList<Tabulable> tabulables = new ArrayList<>(Arrays.asList(this.alumnos));
        tabulables.addAll(Arrays.asList(this.docentes));
        tabulables.stream().forEach(tab -> {
            datosTemporal.añadirTabulable(tab, null);
        });
        List<Curso> cursosNormales = Arrays.asList(cursos).stream().map(cjson -> cjson.getCurso(datosTemporal)).collect(Collectors.toList());
        tabulables.addAll(cursosNormales);
        return tabulables.toArray(new Tabulable[tabulables.size()]);
    }
}
