package com.palmaactiva.javaformacion.io;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class ExcepcionDatos extends Exception {

    private final Actualizable.Acción accionError;
    private final Exception excepcionOriginal;

    public ExcepcionDatos(Actualizable.Acción accion, Exception excepcionOriginal) {
        this.accionError = accion;
        this.excepcionOriginal = excepcionOriginal;
    }

    public Actualizable.Acción getAccionError() {
        return accionError;
    }

    public Exception getExcepcionOriginal() {
        return excepcionOriginal;
    }
}
