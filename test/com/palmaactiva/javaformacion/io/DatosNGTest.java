/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palmaactiva.javaformacion.io;

import static org.testng.Assert.*;
import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class DatosNGTest {

    public DatosNGTest() {
    }

    @Test
    public void testGetSistemaOperativoLinux() {
        System.setProperty("os.name", "Linux");
        Datos.SistemaOperativo expResult = Datos.SistemaOperativo.LINUX;
        Datos.SistemaOperativo result = Datos.getSistemaOperativo();
        assertEquals(result, expResult);
    }

    @Test
    public void testGetSistemaOperativoWindows() {
        System.setProperty("os.name", "Windows");
        Datos.SistemaOperativo expResult = Datos.SistemaOperativo.WINDOWS;
        Datos.SistemaOperativo result = Datos.getSistemaOperativo();
        assertEquals(result, expResult);
    }

    @Test
    public void testGetSistemaOperativoMac() {
        System.setProperty("os.name", "Mac");
        Datos.SistemaOperativo expResult = Datos.SistemaOperativo.MACOS;
        Datos.SistemaOperativo result = Datos.getSistemaOperativo();
        assertEquals(result, expResult);
    }

    @Test
    public void testGetDatosDirUnix() {
        if (Datos.getSistemaOperativo() == Datos.SistemaOperativo.WINDOWS) {
            throw new SkipException("No estamos en un sistema UNIX");
        }
        Datos instance = new Datos();
        String expResult = System.getProperty("user.home") + "/.JavaFormacion";
        String result = instance.getDatosDir();
        assertEquals(result, expResult);
    }
    
    
    @Test
    public void testGetDatosDirWindows() {
        if (Datos.getSistemaOperativo() != Datos.SistemaOperativo.WINDOWS) {
            throw new SkipException("No estamos en un sistema Windows");
        }
        Datos instance = new Datos();
        String expResult = System.getenv("APPDATA") + "/JavaFormacion";
        String result = instance.getDatosDir();
        assertEquals(result, expResult);
    }

}
