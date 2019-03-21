package com.palmaactiva.javaformacion.io;

import com.palmaactiva.javaformacion.lib.Alumno;
import com.palmaactiva.javaformacion.lib.Curso;
import com.palmaactiva.javaformacion.lib.Persona;
import com.palmaactiva.javaformacion.lib.Profesor;
import com.palmaactiva.javaformacion.lib.Tabulable;
import java.util.Collection;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public interface ProveedorDatos {

    Collection<Alumno> getAlumnos();

    Collection<Curso> getCursos();

    Collection<Profesor> getDocentes();

    void cargarDatos(Actualizable progresable);

    void guardarDatos(Actualizable actualizable);

    void guardarDatos(Actualizable actualizable, String rutaArchivo);

    void exportarDatos(Actualizable actualizable, String rutaArchivo);

    void importarDatos(Actualizable actualizable, String rutaArchivo);

    void añadirTabulable(Tabulable nuevaInstancia, Actualizable ventana);

    void eliminarTabulable(Tabulable elemento, Actualizable ventana);

    boolean existeCurso(int IDCurso);

    boolean existeDNI(long DNIcomputado);

    default boolean existeDNINIE(int DNI, char NIE) {
        return existeDNI(Persona.computarDNI(DNI, NIE));
    }

}
