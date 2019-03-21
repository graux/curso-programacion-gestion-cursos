package com.palmaactiva.javaformacion.lib;

/**
 * @author Fran Grau <fran@kydemy.com>
 */
public class NoTabulableException extends Exception {

    private java.lang.Class<? extends Tabulable> tipoTabulable;

    public <T extends Tabulable> NoTabulableException(java.lang.Class<T> tipoTabulable) {
        this.tipoTabulable = tipoTabulable;
    }

    @Override
    public String getMessage() {
        String mensaje = "";
        if (this.tipoTabulable == Alumno.class) {
            mensaje = "No existen alumnos o suficientes alumnos para realizar la acción.";
        } else if (this.tipoTabulable == Curso.class) {
            mensaje = "No existen cursos o suficientes cursos para realizar la acción.";
        } else if (this.tipoTabulable == Profesor.class) {
            mensaje = "No existen profesores o suficientes profesores para realizar la acción.";
        }
        return mensaje;
    }
}
