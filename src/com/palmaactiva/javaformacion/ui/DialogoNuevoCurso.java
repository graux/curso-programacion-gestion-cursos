/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palmaactiva.javaformacion.ui;

import com.palmaactiva.javaformacion.io.ProveedorDatos;
import com.palmaactiva.javaformacion.lib.Alumno;
import com.palmaactiva.javaformacion.lib.Curso;
import com.palmaactiva.javaformacion.lib.Persona;
import com.palmaactiva.javaformacion.lib.Profesor;
import com.palmaactiva.javaformacion.ui.validacion.ValidadorNuevaPersona;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author Fran Grau <fran@kydemy.com>
 */
public class DialogoNuevoCurso extends javax.swing.JDialog {

    private VentanaPrincipal ventanaPrincipal;
    private InputVerifier validadorFormulario;
    private ArrayList<Profesor> docentes;
    private Curso cursoAEditar;
    private ArrayList<Curso.CategoríaCurso> categorias;
    private ArrayList<Alumno> alumnos;
    private DefaultListModel<Alumno> modeloListaAlumnosNoMatriculados;
    private DefaultListModel<Alumno> modeloListaAlumnosMatriculados;
    private ProveedorDatos proveedorDatos;

    /**
     * Creates new form DialogoNuevoAlumno
     */
    public <T extends Persona> DialogoNuevoCurso(java.awt.Frame parent, boolean modal, ProveedorDatos datos) {
        super(parent, modal);
        this.proveedorDatos = datos;
        initComponents();
        this.ventanaPrincipal = (VentanaPrincipal) parent;
        this.validadorFormulario = new ValidadorNuevaPersona();
        this.docentes = new ArrayList(datos.getDocentes());
        Collections.sort(this.docentes);
        ComboBoxModel comboModel = new DefaultComboBoxModel(this.docentes.toArray());
        ComboProfesores.setModel(comboModel);

        this.categorias = new ArrayList(Arrays.asList(Curso.CategoríaCurso.values()));
        Collections.sort(this.categorias);
        ComboBoxModel comboModelCats = new DefaultComboBoxModel(this.categorias.toArray());
        ComboCategoria.setModel(comboModelCats);

        this.alumnos = new ArrayList<>(datos.getAlumnos());
        Collections.sort(this.alumnos);
        this.modeloListaAlumnosNoMatriculados = new DefaultListModel();
        this.modeloListaAlumnosNoMatriculados.addAll(this.alumnos);
        this.ListaNoMatriculados.setModel(this.modeloListaAlumnosNoMatriculados);

        this.modeloListaAlumnosMatriculados = new DefaultListModel();
        this.modeloListaAlumnosMatriculados.clear();
        this.ListaMatriculados.setModel(this.modeloListaAlumnosMatriculados);

        this.pack();
    }

    public DialogoNuevoCurso(java.awt.Frame parent, boolean modal, Curso cursoAEditar, ProveedorDatos datos) {
        this(parent, modal, datos);
        this.CampoIdentificador.setText(Integer.toString(cursoAEditar.getCodigo()));
        this.CampoNombre.setText(cursoAEditar.getNombre());
        this.ComboCategoria.setSelectedItem(cursoAEditar.getCategoria());
        this.ComboProfesores.setSelectedItem(cursoAEditar.getDocente());

        for (Alumno alumno : cursoAEditar.getAlumnos()) {
            this.matricularAlumno(alumno);
        }

        this.cursoAEditar = cursoAEditar;
        this.ButtonCrear.setText("GUARDAR");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GrupoDNINIE = new javax.swing.ButtonGroup();
        EtiquetaIdentificador = new javax.swing.JLabel();
        EtiquetaNombre = new javax.swing.JLabel();
        ButtonCancelar = new javax.swing.JButton();
        ButtonCrear = new javax.swing.JButton();
        CampoNombre = new javax.swing.JTextField();
        CampoIdentificador = new javax.swing.JFormattedTextField(NumberFormat.getInstance(new Locale("es","ES")));
        EtiquetaProfesor = new javax.swing.JLabel();
        ComboProfesores = new javax.swing.JComboBox<>();
        EtiquetaCategoria = new javax.swing.JLabel();
        ComboCategoria = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListaNoMatriculados = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        ListaMatriculados = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BotonMatricular = new javax.swing.JButton();
        BotonDesmatricular = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear nuevo Curso");

        EtiquetaIdentificador.setText("Identificador:");
        EtiquetaIdentificador.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N

        EtiquetaNombre.setText("Nombre:");
        EtiquetaNombre.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N

        ButtonCancelar.setText("Cancelar");
        ButtonCancelar.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        ButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCancelarActionPerformed(evt);
            }
        });

        ButtonCrear.setText("CREAR");
        ButtonCrear.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        ButtonCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCrearActionPerformed(evt);
            }
        });

        CampoNombre.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        CampoNombre.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        CampoNombre.setInputVerifier(getValidadorFormularion());

        CampoIdentificador.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N

        EtiquetaProfesor.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        EtiquetaProfesor.setText("Docente:");

        ComboProfesores.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N

        EtiquetaCategoria.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
        EtiquetaCategoria.setText("Categoría:");

        ComboCategoria.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N

        ListaNoMatriculados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ListaNoMatriculados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ListaMousePressed(evt);
            }
        });
        ListaNoMatriculados.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ListaValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(ListaNoMatriculados);

        ListaMatriculados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ListaMatriculados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ListaMousePressed(evt);
            }
        });
        ListaMatriculados.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ListaValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(ListaMatriculados);

        jLabel1.setText("Alumnos disponibles:");

        jLabel2.setText("Alumnos Matriculados:");

        BotonMatricular.setText(">");
        BotonMatricular.setEnabled(false);
        BotonMatricular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonMatricularActionPerformed(evt);
            }
        });

        BotonDesmatricular.setText("<");
        BotonDesmatricular.setEnabled(false);
        BotonDesmatricular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonDesmatricularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EtiquetaNombre)
                            .addComponent(EtiquetaIdentificador)
                            .addComponent(EtiquetaProfesor)
                            .addComponent(EtiquetaCategoria))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CampoNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(CampoIdentificador)
                            .addComponent(ComboProfesores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                                .addComponent(ButtonCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BotonMatricular)
                                    .addComponent(BotonDesmatricular))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EtiquetaIdentificador)
                    .addComponent(CampoIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EtiquetaNombre)
                    .addComponent(CampoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EtiquetaProfesor)
                    .addComponent(ComboProfesores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EtiquetaCategoria)
                    .addComponent(ComboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(BotonMatricular)
                        .addGap(18, 18, 18)
                        .addComponent(BotonDesmatricular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonCrear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_ButtonCancelarActionPerformed

    private void ButtonCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCrearActionPerformed
        try {
            if (this.cursoAEditar == null) {
                this.crearNuevoCurso();
            } else {
                this.guardarCurso();
            }
            this.proveedorDatos.setDatosGuardados(false);
            this.ventanaPrincipal.actualizarDatos();
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos y asegurate que los formatos son correctos.", "Error creando entidad", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ButtonCrearActionPerformed

    private void ListaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ListaValueChanged
        JList<Alumno> lista = (JList<Alumno>) evt.getSource();
        JButton botonAsociado = lista == this.ListaMatriculados ? BotonDesmatricular : BotonMatricular;
        botonAsociado.setEnabled(lista.getSelectedValue() != null);
    }//GEN-LAST:event_ListaValueChanged

    private void BotonMatricularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonMatricularActionPerformed
        this.matricularAlumno(ListaNoMatriculados.getSelectedValue());
    }//GEN-LAST:event_BotonMatricularActionPerformed

    private void BotonDesmatricularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonDesmatricularActionPerformed
        this.desmatricularAlumno(ListaMatriculados.getSelectedValue());
    }//GEN-LAST:event_BotonDesmatricularActionPerformed

    private void ListaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaMousePressed
        JList<Alumno> lista = (JList<Alumno>) evt.getSource();
        Alumno alumnoSeleccionado = lista.getSelectedValue();
        if (evt.getClickCount() == 2 && alumnoSeleccionado != null) {
            if (lista == ListaMatriculados) {
                this.desmatricularAlumno(alumnoSeleccionado);
            } else {
                this.matricularAlumno(alumnoSeleccionado);
            }
        }
    }//GEN-LAST:event_ListaMousePressed

    private void desmatricularAlumno(Alumno alumno) {
        this.modeloListaAlumnosMatriculados.removeElement(alumno);
        this.modeloListaAlumnosNoMatriculados.addElement(alumno);
    }

    private void matricularAlumno(Alumno alumno) {
        this.modeloListaAlumnosNoMatriculados.removeElement(alumno);
        this.modeloListaAlumnosMatriculados.addElement(alumno);
    }

    private void crearNuevoCurso() {
        Curso nuevoCurso = crearCurso();
        this.ventanaPrincipal.añadirTabulable(nuevoCurso);
    }

    private void guardarCurso() {
        this.cursoAEditar.setNombre(this.CampoNombre.getText().trim());
        this.cursoAEditar.setCodigo(this.getCodigoCurso());
        this.cursoAEditar.setCategoria((Curso.CategoríaCurso) this.ComboCategoria.getSelectedItem());
        this.cursoAEditar.setDocente((Profesor) this.ComboProfesores.getSelectedItem());
        for (Alumno alumno : this.cursoAEditar.getAlumnos()) {
            alumno.removeCurso(this.cursoAEditar);
        }
        this.cursoAEditar.clearAlumnos();
        for (int indice = 0; indice < this.modeloListaAlumnosMatriculados.size(); indice++) {
            this.cursoAEditar.addAlumno(this.modeloListaAlumnosMatriculados.getElementAt(indice));
        }
    }

    private int getCodigoCurso() {
        return Integer.parseInt(this.CampoIdentificador.getText().trim().replace(".", ""));
    }

    protected InputVerifier getValidadorFormularion() {
        return this.validadorFormulario;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonDesmatricular;
    private javax.swing.JButton BotonMatricular;
    private javax.swing.JButton ButtonCancelar;
    private javax.swing.JButton ButtonCrear;
    private javax.swing.JFormattedTextField CampoIdentificador;
    private javax.swing.JTextField CampoNombre;
    private javax.swing.JComboBox<Curso.CategoríaCurso> ComboCategoria;
    private javax.swing.JComboBox<Profesor> ComboProfesores;
    private javax.swing.JLabel EtiquetaCategoria;
    private javax.swing.JLabel EtiquetaIdentificador;
    private javax.swing.JLabel EtiquetaNombre;
    private javax.swing.JLabel EtiquetaProfesor;
    private javax.swing.ButtonGroup GrupoDNINIE;
    private javax.swing.JList<Alumno> ListaMatriculados;
    private javax.swing.JList<Alumno> ListaNoMatriculados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables

    private Curso crearCurso() {
        String nombre = this.CampoNombre.getText().trim();
        int codigo = this.getCodigoCurso();
        Profesor selectedProfe = (Profesor) this.ComboProfesores.getSelectedItem();
        Curso.CategoríaCurso selectedCat = (Curso.CategoríaCurso) this.ComboCategoria.getSelectedItem();
        Curso nuevoCurso = new Curso(codigo, nombre, selectedCat, selectedProfe);
        for (int indice = 0; indice < this.modeloListaAlumnosMatriculados.size(); indice++) {
            nuevoCurso.addAlumno(this.modeloListaAlumnosMatriculados.getElementAt(indice));
        }
        return nuevoCurso;
    }
}
