package com.palmaactiva.javaformacion.io.models;

import com.palmaactiva.javaformacion.io.ProveedorDatos;
import com.palmaactiva.javaformacion.lib.Alumno;
import com.palmaactiva.javaformacion.lib.Curso;
import java.util.List;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class CursoJSON {

    private final int codigo;
    private final String nombre;
    private final Curso.CategoríaCurso categoria;
    private long dniDocente;
    private long[] dniAlumnos;

    public CursoJSON(Curso curso) {
        this.codigo = curso.getCodigo();
        this.nombre = curso.getNombre();
        if (curso.getDocente() != null) {
            this.dniDocente = curso.getDocente().getNumeroDNIComputado();
        }
        List<Alumno> alumnos = curso.getAlumnos();
        if (alumnos != null && alumnos.size() > 0) {
            this.dniAlumnos = new long[alumnos.size()];
            for (int indice = 0; indice < this.dniAlumnos.length; indice++) {
                this.dniAlumnos[indice] = alumnos.get(indice).getNumeroDNIComputado();
            }
        }
        this.categoria = curso.getCategoria();
    }

    public Curso getCurso(ProveedorDatos datos) {
        Curso nuevoCurso = new Curso(this.codigo, this.nombre, this.categoria);
        if (this.dniDocente > 0) {
            nuevoCurso.setDocente(datos.buscarProfesor(this.dniDocente));
        }
        if (this.dniAlumnos != null && this.dniAlumnos.length > 0) {
            for (long dniAlumno : this.dniAlumnos) {
                nuevoCurso.addAlumno(datos.buscarAlumno(dniAlumno));
            }
        }
        return nuevoCurso;
    }
}
