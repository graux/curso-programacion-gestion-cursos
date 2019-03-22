package com.palmaactiva.javaformacion.lib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fran Grau <fran@kydemy.com>
 */
public class Curso implements Tabulable, Serializable, Comparable<Curso> {

    public static final long serialVersionUID = 903L;

    public enum CategoríaCurso implements Comparable<CategoríaCurso> {
        COMERCIO,
        COMPETENCIAS_TRANSVERSALES,
        CREACIÓN_GESTIÓN_EMPRESAS,
        FORMACIÓN_OFICIOS,
        FORMACIÓN_ONLINE,
        IDIOMAS,
        NÁUTICA,
        NUEVAS_TECNOLOGIAS;

        @Override
        public String toString() {
            return super.toString().replace("_", " ");
        }
    }

    public static String[] getColumnas() {
        return new String[]{
            "Código",
            "Nombre",
            "Profesor",
            "Matrículas",
            "Categoria"
        };
    }

    public static int[] getAnchoColumnas() {
        return new int[]{
            100,
            200,
            200,
            100,
            160
        };
    }

    private int codigo;
    private String nombre;
    private Profesor docente;
    private List<Alumno> alumnos;
    private CategoríaCurso categoria;

    public Curso(int codigo, String nombre, CategoríaCurso categoria) {
        this(codigo, nombre, categoria, null);
    }

    public Curso(int codigo, String nombre, CategoríaCurso categoria, Profesor profe) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.docente = profe;
        this.alumnos = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Profesor getDocente() {
        return docente;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public CategoríaCurso getCategoria() {
        return categoria;
    }

    public void setDocente(Profesor docente) {
        if (this.docente != null && !this.docente.equals(docente)) {
            this.docente.removeCurso(this);
        }
        this.docente = docente;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(CategoríaCurso categoria) {
        this.categoria = categoria;
    }

    public void clearAlumnos() {
        if (this.alumnos != null) {
            this.alumnos.clear();
        }
    }

    @Override
    public Object[] getValores() {
        String nombreDocente = this.docente == null ? "" : this.docente.getNombreCompleto();

        return new Object[]{
            this.codigo,
            this.nombre,
            nombreDocente,
            this.alumnos.size(),
            this.categoria.toString()
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Curso otroCurso = (Curso) obj;
        return this.codigo == otroCurso.codigo;
    }

    public void addAlumno(Alumno alumno) {
        if (this.alumnos == null) {
            this.alumnos = new ArrayList<>(16);
        }
        if (!this.alumnos.contains(alumno)) {
            this.alumnos.add(alumno);
            alumno.addCurso(this);
        }
    }

    public void removeAlumno(Alumno alumno) {
        if (this.alumnos != null && this.alumnos.contains(alumno)) {
            this.alumnos.remove(alumno);
        }
    }

    @Override
    public String toString() {
        String textCurso = this.nombre + " [" + this.categoria + "]";
        if (this.docente != null) {
            textCurso += " - " + this.docente.getNombreCompleto();
        }

        return textCurso;
    }

    @Override
    public int compareTo(Curso otroCurso) {
        return this.nombre.compareTo(otroCurso.nombre);
    }
}
