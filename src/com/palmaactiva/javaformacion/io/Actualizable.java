package com.palmaactiva.javaformacion.io;

/**
 * @author Fran Grau <fran@kydemy.com>
 */
public interface Actualizable {

    public enum Acción {
        GUARDAR,
        GUARDAR_COMO,
        ABRIR,
        EXPORTAR,
        IMPORTAR,
        BORRAR
    }

    public void tareaCompletada(Acción accion);

    public void actualizarDatos();

    public void errorTarea(ExcepcionDatos ex);
}
