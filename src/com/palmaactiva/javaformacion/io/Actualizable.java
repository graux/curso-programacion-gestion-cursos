package com.palmaactiva.javaformacion.io;

/**
 * @author Fran Grau <fran@kydemy.com>
 */
public interface Actualizable {

    public void tareaCompletada();

    public void actualizarDatos();

    public void errorTarea(Exception ex);
}
