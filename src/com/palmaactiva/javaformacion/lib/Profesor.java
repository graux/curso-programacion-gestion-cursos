package com.palmaactiva.javaformacion.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class Profesor extends Persona {

    public static final long serialVersionUID = 902L;

    private List<Curso.CategoríaCurso> categorias;

    public static String[] getColumnas() {
        List<String> columnas = new ArrayList<>(Arrays.asList(Persona.getColumnas()));
        columnas.add("Categorias");
        columnas.add("Cursos");
        return columnas.toArray(new String[columnas.size()]);
    }

    @Override
    public Object[] getValores() {
        List<Object> valores = new ArrayList<>(Arrays.asList(super.getValores()));
        String cats = "";
        if (this.categorias != null) {
            String[] categoriasString = new String[this.categorias.size()];
            for (int indice = 0; indice < this.categorias.size(); indice++) {
                categoriasString[indice] = this.categorias.get(indice).toString();
            }
            cats = String.join(", ", categoriasString);
        }
        valores.add(cats);

        if (this.cursos != null) {
            valores.add(this.cursos.stream().map(c -> c.getNombre()).collect(Collectors.joining(", ")));
        } else {
            valores.add("");
        }
        return valores.toArray(new Object[valores.size()]);
    }

    public Profesor(Persona per) {
        this(per.nombre, per.apellidos, per.fechaNacimiento, per.numeroDNI, per.letraNIE);
    }

    public Profesor(String nombre, String apellidos, Date fechaNacimiento, int DNI) {
        this(nombre, apellidos, fechaNacimiento, DNI, (char) Character.UNASSIGNED);
    }

    public Profesor(String nombre, String apellidos, Date fechaNacimiento, int DNI, char letraNIE) {
        super(nombre, apellidos, fechaNacimiento, DNI, letraNIE);
        this.categorias = new ArrayList<>();
    }

    public void addCategoria(Curso.CategoríaCurso nuevaCategoria) {
        this.categorias.add(nuevaCategoria);
    }

    public Curso.CategoríaCurso[] getCategorias() {
        return this.categorias.toArray(new Curso.CategoríaCurso[this.categorias.size()]);
    }

    @Override
    public void addCurso(Curso nuevoCurso) {
        super.addCurso(nuevoCurso);
        nuevoCurso.setDocente(this);
    }
}
