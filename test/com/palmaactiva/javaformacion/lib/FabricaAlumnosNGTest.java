/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palmaactiva.javaformacion.lib;

import com.palmaactiva.javaformacion.io.MockDatos;
import com.palmaactiva.javaformacion.io.ProveedorDatos;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class FabricaAlumnosNGTest {

    private FabricaTabulables fabricaAlumnos;
    private ProveedorDatos datos;

    public FabricaAlumnosNGTest() {
        this.datos = new MockDatos();
        this.fabricaAlumnos = FabricaTabulables.getInstance(datos);
    }

    @Test
    public void testGenerarPersona() {
        Persona result = this.fabricaAlumnos.generarPersona();
        assertNotNull(result);
        assertFalse(result.getNombre().isEmpty());
    }

    @Test
    public void testGenerarPersonaDNI() {
        Persona result = this.fabricaAlumnos.generarPersona();
        assertNotNull(result);
        assertTrue(result.getNumeroDNI() > 9999999);
        assertTrue(result.getNumeroDNI() < 80000000);
    }
}
