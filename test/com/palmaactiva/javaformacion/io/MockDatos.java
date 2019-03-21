/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palmaactiva.javaformacion.io;

import com.palmaactiva.javaformacion.lib.Alumno;
import com.palmaactiva.javaformacion.lib.Curso;
import com.palmaactiva.javaformacion.lib.Profesor;
import com.palmaactiva.javaformacion.lib.Tabulable;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class MockDatos implements ProveedorDatos {

    @Override
    public void añadirTabulable(Tabulable nuevaInstancia, Actualizable ventana) {

    }

    @Override
    public void cargarDatos(Actualizable progresable) {

    }

    @Override
    public void eliminarTabulable(Tabulable elemento, Actualizable ventana) {

    }

    @Override
    public Collection<Alumno> getAlumnos() {
        return new ArrayList<Alumno>();
    }

    @Override
    public Collection<Curso> getCursos() {
        return new ArrayList<Curso>();
    }

    @Override
    public Collection<Profesor> getDocentes() {
        return new ArrayList<Profesor>();
    }

    @Override
    public void guardarDatos(Actualizable actualizable) {
    }

    @Override
    public void guardarDatos(Actualizable actualizable, String rutaArchivo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportarDatos(Actualizable actualizable, String rutaArchivo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void importarDatos(Actualizable actualizable, String rutaArchivo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeCurso(int IDCurso) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeDNI(long DNIcomputado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
