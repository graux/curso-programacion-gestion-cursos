package com.palmaactiva.javaformacion.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Fran Grau <fran@kydemy.com>
 */
public class Alumno extends Persona {

    public static final long serialVersionUID = 901L;

    public Alumno(Persona per) {
        this(per.nombre, per.apellidos, per.fechaNacimiento, per.numeroDNI, per.letraNIE);
    }

    public Alumno(String nombre, String apellidos, Date fechaNacimiento, int DNI, char letraNIE) {
        super(nombre, apellidos, fechaNacimiento, DNI, letraNIE);
    }

    public Alumno(String nombre, String apellidos, Date fechaNacimiento, int DNI) {
        this(nombre, apellidos, fechaNacimiento, DNI, (char) Character.UNASSIGNED);
    }

    public static String[] getColumnas() {
        List<String> columnas = new ArrayList<>(Arrays.asList(Persona.getColumnas()));
        columnas.add("Cursos");
        return columnas.toArray(new String[columnas.size()]);
    }

    @Override
    public Object[] getValores() {
        List<Object> valores = new ArrayList<>(Arrays.asList(super.getValores()));
        int numCursos = this.cursos == null ? 0 : this.cursos.size();
        valores.add(numCursos);
        return valores.toArray(new Object[valores.size()]);
    }

    @Override
    public void addCurso(Curso nuevoCurso) {
        if (this.cursos == null || !this.cursos.contains(nuevoCurso)) {
            super.addCurso(nuevoCurso);
            nuevoCurso.addAlumno(this);
        }
    }

    @Override
    public void clearCursos() {
        for (Curso curso : this.cursos) {
            curso.removeAlumno(this);
        }
        super.clearCursos();
    }
}
