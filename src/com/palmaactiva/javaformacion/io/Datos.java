package com.palmaactiva.javaformacion.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class Datos implements ProveedorDatos {

    public static final String EXTENSION = "jfd";
    public static final String CARPETA = "JavaFormacion";
    private Map<Long, Alumno> alumnos;
    private Map<Long, Profesor> docentes;
    private Map<Long, Curso> cursos;

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

    private void notificarTareaCompletada(Actualizable actualizable) {
        if (actualizable != null) {
            actualizable.tareaCompletada();
        }
    }

    private void notificarErrorTarea(Actualizable actualizable, Exception ex) {
        if (actualizable != null) {
            actualizable.errorTarea(ex);
        }
    }

    @Override
    public void guardarDatos(Actualizable actualizable) {
        try {
            this.guardarAlumnos();
            this.guardarDocentes();
            this.guardarCursos();
            actualizable.tareaCompletada();
        } catch (Exception ex) {
            this.notificarErrorTarea(actualizable, ex);
        }
    }

    protected void guardarAlumnos() throws Exception {
        this.guardarDatosEnArchivo(getArchivoDatosAlumnos(), this.getAlumnos());
    }

    protected void guardarDocentes() throws Exception {
        this.guardarDatosEnArchivo(getArchivoDatosDocentes(), this.getDocentes());
    }

    protected void guardarCursos() throws Exception {
        this.guardarDatosEnArchivo(getArchivoDatosCursos(), this.getCursos());
    }

    protected void guardarDatosEnArchivo(String rutaArchivo, Collection<? extends Tabulable> datos) throws Exception {
        try {
            comprobarDirectorioDatos();
            try (ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
                escritor.writeObject(datos);
            } catch (FileNotFoundException fnfEx) {
                System.out.println("Error Guardando Datos, el archivo no existe: " + fnfEx.getLocalizedMessage());
                throw fnfEx;
            }
        } catch (IOException ioEx) {
            System.out.println("Error creando archivo/carpeta datos: " + ioEx.getLocalizedMessage());
            throw ioEx;
        }
    }

    protected void exportarDatosEnArchivo(String rutaArchivo, Collection<? extends Tabulable> datos) throws Exception {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (OutputStreamWriter escritor = new OutputStreamWriter(new FileOutputStream(rutaArchivo))) {
                gson.toJson(datos, escritor);
            } catch (JsonIOException jsonEx) {
                System.out.println("Error codificando JSON: " + jsonEx.getLocalizedMessage());
                throw jsonEx;
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

    @Override
    public void cargarDatos(Actualizable progresable) {

//        this.cargarAlumnos();
//        this.cargarDocentes();
//        this.cargarCursos();
        progresable.tareaCompletada();
    }

//    protected void cargarAlumnos() {
//        String rutaArchivo = getArchivoDatosAlumnos();
//        try {
//            Alumno[] alumnos = this.cargarDatos(rutaArchivo, Alumno[].class);
//            if (alumnos != null) {
//                for (Alumno alumno : alumnos) {
//                    this.añadirAlumno(alumno);
//                }
//            }
//        } catch (IOException ioEx) {
//            System.out.println("No se pudieron cargar los alumnos");
//        }
//    }
//
//    protected void cargarDocentes() {
//        String rutaArchivo = getArchivoDatosDocentes();
//        try {
//            Profesor[] profresores = this.cargarDatos(rutaArchivo, Profesor[].class);
//            if (profresores != null) {
//                for (Profesor profesor : profresores) {
//                    this.añadirProfesor(profesor);
//                }
//            }
//        } catch (IOException ioEx) {
//            System.out.println("No se pudieron cargar los profesores");
//        }
//    }
//
//    protected void cargarCursos() {
//        String rutaArchivo = getArchivoDatosDocentes();
//        try {
//            Curso[] cursos = this.cargarDatos(rutaArchivo, Curso[].class);
//            if (cursos != null) {
//                for (Curso curso : cursos) {
//                    this.añadirCurso(curso);
//                }
//            }
//        } catch (IOException ioEx) {
//            System.out.println("No se pudieron cargar los cursos");
//        }
//    }
    private Tabulable[] cargarDatos(String rutaArchivo) throws ClassNotFoundException, IOException {
        if (existeArchivo(rutaArchivo)) {
            try (ObjectInputStream lector = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
                return (Tabulable[]) lector.readObject();
            } catch (ClassNotFoundException cnfEx) {
                System.out.println("Error deserializando clase: " + cnfEx.getLocalizedMessage());
                throw cnfEx;
            }
        }
        return null;
    }

    private <T extends Tabulable> T[] importarDatos(String rutaArchivo, Class<T[]> claseDatos) throws IOException {
        if (existeArchivo(rutaArchivo)) {
            Gson gson = new GsonBuilder().create();
            try (InputStreamReader lector = new InputStreamReader(new FileInputStream(rutaArchivo))) {
                T[] datos = gson.fromJson(lector, claseDatos);
                return datos;
            } catch (JsonIOException jsonEx) {
                System.out.println("Error codificando JSON: " + jsonEx.getLocalizedMessage());
                throw jsonEx;
            }
        }
        return null;
    }

    private boolean existeArchivo(String rutaArchivo) {
        return Files.exists(Paths.get(rutaArchivo));
    }

    protected String getArchivoDatosAlumnos() {
        return getDatosDir() + "/alumnos." + EXTENSION;
    }

    protected String getArchivoDatosDocentes() {
        return getDatosDir() + "/docentes." + EXTENSION;
    }

    protected String getArchivoDatosCursos() {
        return getDatosDir() + "/cursos." + EXTENSION;
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

    @Override
    public void añadirTabulable(Tabulable nuevaInstancia, Actualizable ventana) {
        if (nuevaInstancia instanceof Alumno) {
            this.añadirAlumno((Alumno) nuevaInstancia);
        } else if (nuevaInstancia instanceof Profesor) {
            this.añadirProfesor((Profesor) nuevaInstancia);
        } else if (nuevaInstancia instanceof Curso) {
            this.añadirCurso((Curso) nuevaInstancia);
        }
        if (ventana != null) {
            ventana.actualizarDatos();
        }
    }

    protected void añadirAlumno(Alumno nuevoAlumno) {
        this.alumnos.put(nuevoAlumno.getNumeroDNIComputado(), nuevoAlumno);
    }

    protected void añadirProfesor(Profesor nuevoProfesor) {
        this.docentes.put(nuevoProfesor.getNumeroDNIComputado(), nuevoProfesor);
    }

    protected void añadirCurso(Curso curso) {
        this.cursos.put((long) curso.getCodigo(), curso);
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
}
