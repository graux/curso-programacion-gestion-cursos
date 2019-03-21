package com.palmaactiva.javaformacion.lib;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.palmaactiva.javaformacion.io.ProveedorDatos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class FabricaTabulables {

    private static FabricaTabulables instancia;
    private static final char[] LETRAS_NIE = new char[]{Character.UNASSIGNED, Character.UNASSIGNED, Character.UNASSIGNED, Character.UNASSIGNED, Character.UNASSIGNED, 'X', 'Y', 'Z'};
    private Faker creadorAleatorio;
    private ProveedorDatos datos;

    public static FabricaTabulables getInstance(ProveedorDatos datos) {
        if (instancia == null) {
            instancia = new FabricaTabulables(datos);
        }
        return instancia;
    }

    private FabricaTabulables(ProveedorDatos datos) {
        this.datos = datos;
        this.creadorAleatorio = new Faker(new Locale("es", "ES"));
    }

    public Alumno generarAlumno() throws NoTabulableException {
        Alumno alumno = new Alumno(this.generarPersona());
        int numCursos = this.creadorAleatorio.random().nextInt(1, 6);
        for (int indice = 0; indice < numCursos; indice++) {
            alumno.addCurso(this.getCursoAleatorio(alumno.getCursos()));
        }
        return alumno;
    }

    public Profesor generarProfesor() throws NoTabulableException {
        Profesor profe = new Profesor(this.generarPersona());
        int numCursos = this.creadorAleatorio.random().nextInt(1, 2);
        for (int indice = 0; indice < numCursos; indice++) {
            profe.addCurso(this.getCursoAleatorio(profe.getCursos()));
        }
        profe.addCategoria(this.getCategoriaAleatoria());
        return profe;
    }

    protected Persona generarPersona() {
        Name nombrePersona = this.creadorAleatorio.name();
        String nombre = nombrePersona.firstName();
        String apellido = nombrePersona.lastName();
        Date fechaNacimiento = this.creadorAleatorio.date().birthday(14, 85);
        int numDni;
        char letraNIE;
        do {
            numDni = this.creadorAleatorio.number().numberBetween(10000000, 60000000);
            letraNIE = LETRAS_NIE[this.creadorAleatorio.random().nextInt(LETRAS_NIE.length)];
        } while (this.datos.existeDNINIE(numDni, letraNIE));

        return new Persona(nombre, apellido, fechaNacimiento, numDni, letraNIE);
    }

    public Curso generarCurso() {
        Profesor profe = null;
        try {
            profe = this.getProfesorAleatorio();
        } catch (NoTabulableException nte) {
            System.out.println("No existen profesores para añadir a esta clase.");
        }
        Curso.CategoríaCurso cat = this.getCategoriaAleatoria();
        int idCurso = this.getIDCursoAleatorio();
        String nombreCurso = this.creadorAleatorio.gameOfThrones().city() + " " + this.creadorAleatorio.gameOfThrones().dragon();
        Curso nuevoCurso = new Curso(idCurso, nombreCurso, cat, profe);

        try {
            int numAlumnos = this.creadorAleatorio.random().nextInt(4, 12);
            for (int indice = 0; indice < numAlumnos; indice++) {
                nuevoCurso.addAlumno(this.getAlumnoAleatorio(nuevoCurso.getAlumnos()));
            }
        } catch (NoTabulableException nte) {
            System.out.println("No existen (más) alumnos para añadir a la clase.");
        }

        return nuevoCurso;
    }

    private int getIDCursoAleatorio() {
        int idCurso = 0;
        do {
            idCurso = this.creadorAleatorio.random().nextInt(1000, 9999);
        } while (datos.existeCurso(idCurso));
        return idCurso;
    }

    private Curso.CategoríaCurso getCategoriaAleatoria() {
        int numValores = Curso.CategoríaCurso.values().length;
        int indice = this.creadorAleatorio.random().nextInt(numValores);
        return Curso.CategoríaCurso.values()[indice];
    }

    private Profesor getProfesorAleatorio() throws NoTabulableException {
        Collection<Profesor> profesores = this.datos.getDocentes();
        if (profesores == null || profesores.isEmpty()) {
            throw new NoTabulableException(Profesor.class);
        }
        int indiceAleatorio = this.creadorAleatorio.random().nextInt(profesores.size());
        return new ArrayList<>(profesores).get(indiceAleatorio);
    }

    private Alumno getAlumnoAleatorio(List<Alumno> excluirAlumnos) throws NoTabulableException {
        Collection<Alumno> alumnos = this.datos.getAlumnos();
        if (alumnos == null || alumnos.isEmpty() || alumnos.size() == excluirAlumnos.size()) {
            throw new NoTabulableException(Alumno.class);
        }
        int indiceAleatorio;
        Alumno nuevoAlumno = null;
        Alumno[] arrAlumnos = alumnos.toArray(new Alumno[alumnos.size()]);
        do {
            indiceAleatorio = this.creadorAleatorio.random().nextInt(alumnos.size());
            nuevoAlumno = arrAlumnos[indiceAleatorio];
        } while (excluirAlumnos.contains(nuevoAlumno));

        return nuevoAlumno;
    }

    private Curso getCursoAleatorio(List<Curso> excluirCursos) throws NoTabulableException {
        Collection<Curso> cursos = this.datos.getCursos();
        if (cursos == null || cursos.isEmpty() || cursos.size() == excluirCursos.size()) {
            throw new NoTabulableException(Curso.class);
        }
        int indiceAleatorio;
        Curso cursoAleatorio = null;
        Curso[] arrCursos = cursos.toArray(new Curso[cursos.size()]);
        do {
            indiceAleatorio = this.creadorAleatorio.random().nextInt(cursos.size());
            cursoAleatorio = arrCursos[indiceAleatorio];
        } while (excluirCursos.contains(cursoAleatorio));
        return cursoAleatorio;
    }
}
