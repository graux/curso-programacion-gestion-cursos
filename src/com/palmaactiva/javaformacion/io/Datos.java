package com.palmaactiva.javaformacion.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.palmaactiva.javaformacion.io.models.DatosJSON;
import com.palmaactiva.javaformacion.lib.Alumno;
import com.palmaactiva.javaformacion.lib.Curso;
import com.palmaactiva.javaformacion.lib.Profesor;
import com.palmaactiva.javaformacion.lib.Tabulable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class Datos implements ProveedorDatos {

    public static final String EXTENSION_JAVAFORMACION = "jfd";
    public static final String EXTENSION_JSON = "json";
    public static final String CARPETA = "JavaFormacion";
    private Map<Long, Alumno> alumnos;
    private Map<Long, Profesor> docentes;
    private Map<Long, Curso> cursos;
    private String rutaArchivoActual = null;
    private boolean datosGuardados = true;

    public enum SistemaOperativo {
        LINUX,
        WINDOWS,
        MACOS
    }

    public Datos() {
        this.alumnos = new HashMap<>();
        this.docentes = new HashMap<>();
        this.cursos = new HashMap<>();
        try {
            comprobarDirectorioDatos();
        } catch (IOException ex) {
            System.out.println("No se ha podido inicializar el directorio de datos de la aplicación: " + ex.getLocalizedMessage());
        }
    }

    private void notificarTareaCompletada(Actualizable actualizable, Actualizable.Acción accion) {
        if (actualizable != null) {
            actualizable.tareaCompletada(accion);
        }
    }

    private void notificarErrorTarea(Actualizable actualizable, ExcepcionDatos exDatos) {
        if (actualizable != null) {
            actualizable.errorTarea(exDatos);
        }
    }

    protected void guardarDatosEnArchivo(String rutaArchivo, Collection<? extends Tabulable> datos) throws Exception {
        try {
            comprobarDirectorioDatos();
            try (ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
                escritor.writeObject(datos.toArray(new Tabulable[datos.size()]));
                this.datosGuardados = true;
            } catch (FileNotFoundException fnfEx) {
                System.out.println("Error Guardando Datos, el archivo no existe: " + fnfEx.getLocalizedMessage());
                throw fnfEx;
            }
        } catch (IOException ioEx) {
            System.out.println("Error creando archivo/carpeta datos: " + ioEx.getLocalizedMessage());
            throw ioEx;
        }
    }

    private void comprobarDirectorioDatos() throws IOException {
        String datosDir = getDatosDir();
        if (!existeArchivo(datosDir)) {
            Files.createDirectories(Paths.get(datosDir));
        }
    }

    private void cargarDatosDesdeArchivo(String rutaArchivo) throws ClassNotFoundException, IOException {
        if (existeArchivo(rutaArchivo)) {
            try (ObjectInputStream lector = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
                Tabulable[] todosLosDatos = (Tabulable[]) lector.readObject();
                this.recargarDatos(todosLosDatos);
            } catch (ClassNotFoundException cnfEx) {
                System.out.println("Error deserializando clase: " + cnfEx.getLocalizedMessage());
                throw cnfEx;
            }
        }
    }

    private void recargarDatos(Tabulable[] todosLosDatos) {
        this.borrarDatos(null);
        for (Tabulable tabulable : todosLosDatos) {
            this.añadirTabulable(tabulable, null);
        }
        this.datosGuardados = true;
    }

    private boolean existeArchivo(String rutaArchivo) {
        return Files.exists(Paths.get(rutaArchivo));
    }

    protected String getArchivoDatos() {
        return getDatosDir() + "/datos." + EXTENSION_JAVAFORMACION;
    }

    protected String getDatosDir() {
        String path = null;
        switch (getSistemaOperativo()) {
            case WINDOWS:
                path = System.getenv("APPDATA");
                path = Paths.get(path, CARPETA).toString();
                break;
            default:
                path = System.getProperty("user.home");
                path = Paths.get(path, "." + CARPETA).toString();
        }
        return path;
    }

    public static SistemaOperativo getSistemaOperativo() {
        switch (System.getProperty("os.name").toLowerCase()) {
            case "mac":
                return SistemaOperativo.MACOS;
            case "unix":
            case "linux":
                return SistemaOperativo.LINUX;
            case "windows":
                return SistemaOperativo.WINDOWS;
        }
        return null;
    }

    protected void añadirAlumno(Alumno nuevoAlumno) {
        this.alumnos.put(nuevoAlumno.getNumeroDNIComputado(), nuevoAlumno);
    }

    protected void añadirProfesor(Profesor nuevoProfesor) {
        this.docentes.put(nuevoProfesor.getNumeroDNIComputado(), nuevoProfesor);
    }

    protected void añadirCurso(Curso curso) {
        this.cursos.put((long) curso.getCodigo(), curso);
        if (curso.getAlumnos() != null) {
            for (Alumno alumno : curso.getAlumnos()) {
                alumno.addCurso(curso);
            }
        }
    }

    @Override
    public Collection<Alumno> getAlumnos() {
        return alumnos.values();
    }

    @Override
    public Collection<Profesor> getDocentes() {
        return docentes.values();
    }

    @Override
    public Collection<Curso> getCursos() {
        return cursos.values();
    }

    @Override
    public void eliminarTabulable(Tabulable elemento, Actualizable ventana) {
        Map<Long, ? extends Tabulable> mapaDatos = null;
        long keyValue = 0;
        if (elemento instanceof Alumno) {
            mapaDatos = this.alumnos;
            keyValue = ((Alumno) elemento).getNumeroDNI();
        } else if (elemento instanceof Profesor) {
            mapaDatos = this.docentes;
            keyValue = ((Profesor) elemento).getNumeroDNI();
        } else if (elemento instanceof Curso) {
            mapaDatos = this.cursos;
            keyValue = ((Curso) elemento).getCodigo();
        }
        if (mapaDatos != null && mapaDatos.containsKey(keyValue)) {
            mapaDatos.remove(keyValue);
        }
        this.datosGuardados = false;
        ventana.actualizarDatos();
    }

    @Override
    public boolean existeCurso(int IDCurso) {
        return this.cursos.containsKey((long) IDCurso);
    }

    @Override
    public boolean existeDNI(long dni) {
        return this.docentes.containsKey(dni) && this.alumnos.containsKey(dni);
    }

    @Override
    public void cargarDatos(Actualizable actualizable) {
        this.cargarDatos(actualizable, this.getArchivoDatos());
    }

    @Override
    public void añadirTabulable(Tabulable nuevaInstancia, Actualizable ventana) {
        if (nuevaInstancia instanceof Alumno) {
            this.añadirAlumno((Alumno) nuevaInstancia);
        } else if (nuevaInstancia instanceof Profesor) {
            this.añadirProfesor((Profesor) nuevaInstancia);
        } else if (nuevaInstancia instanceof Curso) {
            this.añadirCurso((Curso) nuevaInstancia);
        }
        this.datosGuardados = false;
        if (ventana != null) {
            ventana.actualizarDatos();
        }
    }

    @Override
    public void cargarDatos(Actualizable actualizable, String rutaArchivo) {
        try {
            this.cargarDatosDesdeArchivo(rutaArchivo);
            this.rutaArchivoActual = rutaArchivo;
            this.notificarTareaCompletada(actualizable, Actualizable.Acción.ABRIR);
        } catch (IOException | ClassNotFoundException ex) {
            this.notificarErrorTarea(actualizable, new ExcepcionDatos(Actualizable.Acción.ABRIR, ex));
        }
    }

    @Override
    public void exportarDatos(Actualizable actualizable, String rutaArchivo) {
        DatosJSON datosAExportar = new DatosJSON(this.alumnos.values(), this.docentes.values(), this.cursos.values());
        Gson exportador = new GsonBuilder().setPrettyPrinting().create();
        try (OutputStreamWriter escritor = new OutputStreamWriter(new FileOutputStream(rutaArchivo))) {
            exportador.toJson(datosAExportar, escritor);
            this.notificarTareaCompletada(actualizable, Actualizable.Acción.EXPORTAR);
        } catch (IOException ex) {
            this.notificarErrorTarea(actualizable, new ExcepcionDatos(Actualizable.Acción.EXPORTAR, ex));
        }
    }

    @Override
    public void importarDatos(Actualizable actualizable, String rutaArchivo) {
        Gson importador = new GsonBuilder().setPrettyPrinting().create();
        try (InputStreamReader lector = new InputStreamReader(new FileInputStream(rutaArchivo))) {
            DatosJSON datos = importador.fromJson(lector, DatosJSON.class);
            this.recargarDatos(datos.getTabulables());
            this.notificarTareaCompletada(actualizable, Actualizable.Acción.IMPORTAR);
        } catch (IOException ex) {
            this.notificarErrorTarea(actualizable, new ExcepcionDatos(Actualizable.Acción.EXPORTAR, ex));
        }
    }

    @Override
    public void guardarDatos(Actualizable actualizable) {
        String archivoAGuardar = this.rutaArchivoActual == null ? this.getArchivoDatos() : this.rutaArchivoActual;
        this.guardarDatos(actualizable, this.getArchivoDatos());
    }

    @Override
    public void guardarDatos(Actualizable actualizable, String rutaArchivo) {
        Actualizable.Acción accion = rutaArchivo == this.getArchivoDatos() ? Actualizable.Acción.GUARDAR : Actualizable.Acción.GUARDAR_COMO;
        try {
            Collection<Tabulable> todosLosDatos = new ArrayList<>(this.alumnos.values());
            todosLosDatos.addAll(this.docentes.values());
            todosLosDatos.addAll(this.cursos.values());
            this.guardarDatosEnArchivo(rutaArchivo, todosLosDatos);
            actualizable.tareaCompletada(accion);
        } catch (Exception ex) {
            this.notificarErrorTarea(actualizable, new ExcepcionDatos(accion, ex));
        }
    }

    @Override
    public Profesor buscarProfesor(long dniComputado) {
        return this.docentes.get(dniComputado);
    }

    @Override
    public Alumno buscarAlumno(long dniComputado) {
        return this.alumnos.get(dniComputado);
    }

    @Override
    public void borrarDatos(Actualizable actualizable) {
        this.alumnos = new HashMap<>();
        this.docentes = new HashMap<>();
        this.cursos = new HashMap<>();
        this.notificarTareaCompletada(actualizable, Actualizable.Acción.BORRAR);
    }

    @Override
    public boolean isDatosGuardados() {
        return this.datosGuardados;
    }

    @Override
    public void setDatosGuardados(boolean nuevoEstado) {
        this.datosGuardados = nuevoEstado;
    }
}
