package com.palmaactiva.javaformacion;

import com.palmaactiva.javaformacion.io.Datos;
import com.palmaactiva.javaformacion.ui.VentanaPrincipal;

/**
 *
 * @author Fran Grau - fran@kydemy.com
 */
public class JavaFormacion {

    private static JavaFormacion instanciaJavaFormacion = null;
    private final VentanaPrincipal ventanaPrincipal;
    private final Datos gestionDatos;

    public static JavaFormacion getInstance() {
        if (instanciaJavaFormacion == null) {
            instanciaJavaFormacion = new JavaFormacion();
        }
        return instanciaJavaFormacion;
    }

    private JavaFormacion() {
        this.gestionDatos = new Datos();
        this.ventanaPrincipal = new VentanaPrincipal(this.gestionDatos);
    }

    public void lanzarVentanaPrincipal() {
        ventanaPrincipal.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Iniciando JavaFormación (" + Datos.getSistemaOperativo() + ")");
        JavaFormacion miApp = JavaFormacion.getInstance();
        miApp.lanzarVentanaPrincipal();
    }

}
