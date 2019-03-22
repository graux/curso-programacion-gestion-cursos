package com.palmaactiva.javaformacion.lib;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Fran Grau <fran@kydemy.com>
 */
public class Persona implements Tabulable, Serializable, Comparable<Persona> {

    public static final long serialVersionUID = 900L;

    private static final char[] LETRAS_DNI = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
    private static final DecimalFormat formatoDNI = (DecimalFormat) NumberFormat.getInstance(new Locale("es", "ES"));

    public static String[] getColumnas() {
        return new String[]{
            "Nombre",
            "Apellidos",
            "DNI/NIE",
            "Edad"
        };
    }

    public static int[] getAnchoColumnas() {
        return new int[]{
            160,
            160,
            100,
            80
        };
    }

    public static long computarDNI(int numDNI, char letraNIE) {
        if (letraNIE == Character.UNASSIGNED) {
            return numDNI;
        }
        String numDniStr = String.valueOf(numDNI);
        switch (letraNIE) {
            case 'X':
                numDniStr = '0' + numDniStr;
                break;
            case 'Y':
                numDniStr = '1' + numDniStr;
                break;
            case 'Z':
                numDniStr = '2' + numDniStr;
                break;
        }
        return Long.parseLong(numDniStr);
    }

    protected String nombre;
    protected String apellidos;
    protected int numeroDNI;
    protected char letraNIE;
    protected Date fechaNacimiento;
    protected transient List<Curso> cursos;

    public Persona(String nombre, String apellidos, Date fechaNacimiento, int numDni, char letraNIE) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroDNI = numDni;
        this.letraNIE = letraNIE;
        this.cursos = new ArrayList<>(8);
    }

    public Persona(String nombre, String apellidos, Date fechaNacimiento, int numDni) {
        this(nombre, apellidos, fechaNacimiento, numDni, (char) Character.UNASSIGNED);
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getNumeroDNI() {
        return numeroDNI;
    }

    public int getLetraNIE() {
        return letraNIE;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setNumeroDNI(int numeroDNI) {
        this.numeroDNI = numeroDNI;
    }

    public void setLetraNIE(char letraNIE) {
        this.letraNIE = letraNIE;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public long getNumeroDNIComputado() {
        return computarDNI(this.numeroDNI, this.letraNIE);
    }

    public List<Curso> getCursos() {
        return this.cursos;
    }

    public void clearCursos() {
        this.cursos.clear();
    }

    public void addCurso(Curso nuevoCurso) {
        if (this.cursos == null) {
            this.cursos = new ArrayList<>(16);
        }
        if (!this.cursos.contains(nuevoCurso)) {
            this.cursos.add(nuevoCurso);
        }
    }

    public void removeCurso(Curso curso) {
        if (this.cursos.contains(curso)) {
            this.cursos.remove(curso);
        }
    }

    @Override
    public Object[] getValores() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaNacimientoLocal = LocalDate.ofInstant(this.fechaNacimiento.toInstant(), ZoneId.systemDefault());
        Period periodoEdad = Period.between(fechaNacimientoLocal, hoy);
        return new Object[]{
            this.nombre,
            this.apellidos,
            this.calcularDNIoNIE(),
            periodoEdad.getYears()
        };
    }

    private String calcularDNIoNIE() {
        long numDNI = computarDNI(this.numeroDNI, this.letraNIE);
        int indiceLetra = (int) (numDNI % LETRAS_DNI.length);
        String identificador = formatoDNI.format(this.numeroDNI) + "-" + LETRAS_DNI[indiceLetra];
        if (this.letraNIE != Character.UNASSIGNED) {
            identificador = this.letraNIE + " " + identificador;
        }
        return identificador;
    }

    public String getNombreCompleto() {
        return this.nombre + " " + this.apellidos;
    }

    public boolean esNIE() {
        return this.letraNIE != Character.UNASSIGNED;
    }

    @Override
    public String toString() {
        return String.format("%-14s", this.nombre)
                + String.format("%-14s", this.apellidos)
                + String.format("%15s", this.calcularDNIoNIE());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Persona otraPersona = (Persona) obj;
        if (this.numeroDNI != otraPersona.numeroDNI) {
            return false;
        }
        if (this.letraNIE != otraPersona.letraNIE) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Persona otraPersona) {
        return this.nombre.compareTo(otraPersona.nombre);
    }
}
