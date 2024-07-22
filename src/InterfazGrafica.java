import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class InterfazGrafica extends JFrame {

    private JTextArea textArea; //prescindir segun conveniencia
    private JTextField txtNombre, txtApellido, txtFechaNacimiento, txtDocumento; //alta alumnos
    private List<Alumno> listaAlumnos = new ArrayList<>(); //alta alumnos
    private JTextField txtNombreMateria, txtCodigo, txtCuatrimestre, txtTipo, txtCargaHoraria; //alta materias
    private JCheckBox chkPromocionable; //alta materias
    private JList<Materia> listCorrelativas; //alta materias
    private DefaultListModel<Materia> listModelCorrelativas; //alta materias
    private List<Materia> listaMaterias;  // Lista global de materias
    private JTextField txtNombreCarrera, txtDuracion, txtOptativasParaGraduarse; //alta carreras
    private List<Carrera> listaCarreras = new ArrayList<>(); //alta carreras
    private JComboBox<String> cmbPlanEstudio; //alta carreras
    private JComboBox<String> comboBoxAlumnos; //inscripcion a carreras
    private JComboBox<String> comboBoxCarreras; //inscripcion a carreras
    private JComboBox<String> comboBoxMaterias; //inscripcion a materias
    private JComboBox<String> comboBoxAlumnosNotas; //notas
    private JComboBox<String> comboBoxMateriasNotas; //notas
    private JTextField txtNotaParcial, txtNotaFinal; //notas

    public InterfazGrafica() {
        setTitle("Gestión de Alumnos y Carreras");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea(); //prescindir segun conveniencia
        JScrollPane scrollPane = new JScrollPane(textArea); //permitir desplazamiento si el contenido es demasiado grande
        listaMaterias = new ArrayList<>(); //inicializamos las listas 3 listas
        listaAlumnos = new ArrayList<>();
        listaCarreras = new ArrayList<>();

        //añadir las pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Alta de Alumnos", createAltaAlumnosPanel());
        tabbedPane.addTab("Alta de Carreras", createAltaCarrerasPanel());
        tabbedPane.addTab("Alta de Materias", createAltaMateriasPanel());
        tabbedPane.addTab("Inscripcion a Carrera", createInscripcionCarreraPanel());
        tabbedPane.addTab("Inscripcion a Materia", createInscripcionMateriaPanel());
        tabbedPane.addTab("Materias Cursables", createMateriasCursablesPanel());
        tabbedPane.addTab("Verificar Graduacion", createVerificarGraduacionPanel());
        tabbedPane.addTab("Agregar Notas", createAgregarNotasPanel());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabbedPane, BorderLayout.CENTER); //ubicar el tabbedPane en el centro, usando borderlayout
    }
//---PESTAÑA ALTA DE ALUMNOS---
    private JPanel createAltaAlumnosPanel() {
        JPanel altaAlumnosPanel = new JPanel();
        altaAlumnosPanel.setLayout(new GridLayout(5, 2));

        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblApellido = new JLabel("Apellido:");
        JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
        JLabel lblDocumento = new JLabel("Documento:");

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtFechaNacimiento = new JTextField();
        txtDocumento = new JTextField();

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarAlumno();
                } catch (CamposObligatoriosException ex) {
                    JOptionPane.showMessageDialog(InterfazGrafica.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        altaAlumnosPanel.add(lblNombre);
        altaAlumnosPanel.add(txtNombre);
        altaAlumnosPanel.add(lblApellido);
        altaAlumnosPanel.add(txtApellido);
        altaAlumnosPanel.add(lblFechaNacimiento);
        altaAlumnosPanel.add(txtFechaNacimiento);
        altaAlumnosPanel.add(lblDocumento);
        altaAlumnosPanel.add(txtDocumento);
        altaAlumnosPanel.add(new JLabel()); //espacio en blanco
        altaAlumnosPanel.add(btnGuardar);

        return altaAlumnosPanel;
    }
    private void guardarAlumno() throws CamposObligatoriosException {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String fechaNacimiento = txtFechaNacimiento.getText();
        String documento = txtDocumento.getText();

        if (nombre.isEmpty() || apellido.isEmpty() || fechaNacimiento.isEmpty() || documento.isEmpty()) {
            throw new CamposObligatoriosException("Todos los campos son obligatorios.");
        }

        Alumno nuevoAlumno = new Alumno(nombre, apellido, fechaNacimiento, documento);
        listaAlumnos.add(nuevoAlumno);  //agregar el nuevo alumno a la lista
        actualizarComboBoxAlumnos();  //actualizar el ComboBox de alumnos
        textArea.append("Alumno creado: " + nuevoAlumno.getNombre() + " " + nuevoAlumno.getApellido() + "\n");

        //limpiar los campos despues de guardar el alumno
        txtNombre.setText("");
        txtApellido.setText("");
        txtFechaNacimiento.setText("");
        txtDocumento.setText("");

        JOptionPane.showMessageDialog(this, "Alumno guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    private void actualizarComboBoxAlumnos() {
        comboBoxAlumnos.removeAllItems();  //limpiar el ComboBox
        for (Alumno alumno : listaAlumnos) {
            comboBoxAlumnos.addItem(alumno.getNombreCompleto());
        }
    }
    class CamposObligatoriosException extends Exception {
        public CamposObligatoriosException(String mensaje) {
            super(mensaje);
        }
    }

//---ALTA DE CARRERAS---
    private JPanel createAltaCarrerasPanel() {
        JPanel altaCarrerasPanel = new JPanel();
        altaCarrerasPanel.setLayout(new GridLayout(6, 2));

        JLabel lblNombreCarrera = new JLabel("Nombre de la Carrera:");
        JLabel lblDuracion = new JLabel("Duración (cuatrimestres):");
        JLabel lblOptativasParaGraduarse = new JLabel("Optativas para Graduarse:");
        JLabel lblPlanEstudio = new JLabel("Plan de Estudio:");

        txtNombreCarrera = new JTextField();
        txtDuracion = new JTextField();
        txtOptativasParaGraduarse = new JTextField();
        String[] planesEstudio = {"Plan A", "Plan B", "Plan C", "Plan D", "Plan E"};
        cmbPlanEstudio = new JComboBox<>(planesEstudio);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarCarrera();
            }
        });

        altaCarrerasPanel.add(lblNombreCarrera);
        altaCarrerasPanel.add(txtNombreCarrera);
        altaCarrerasPanel.add(lblDuracion);
        altaCarrerasPanel.add(txtDuracion);
        altaCarrerasPanel.add(lblOptativasParaGraduarse);
        altaCarrerasPanel.add(txtOptativasParaGraduarse);
        altaCarrerasPanel.add(lblPlanEstudio);
        altaCarrerasPanel.add(cmbPlanEstudio);
        altaCarrerasPanel.add(new JLabel()); //espacio en blanco
        altaCarrerasPanel.add(btnGuardar);

        return altaCarrerasPanel;
    }

    private void guardarCarrera() {
        try {
            String nombre = txtNombreCarrera.getText();
            int duracion = Integer.parseInt(txtDuracion.getText());
            int optativasParaGraduarse = Integer.parseInt(txtOptativasParaGraduarse.getText());
            String planEstudio = (String) cmbPlanEstudio.getSelectedItem();

            if (!nombre.isEmpty() && !txtDuracion.getText().isEmpty() && !txtOptativasParaGraduarse.getText().isEmpty()) {

                Carrera nuevaCarrera = new Carrera(nombre, duracion, optativasParaGraduarse, new ArrayList<>(), obtenerPlanEstudio(planEstudio));
                listaCarreras.add(nuevaCarrera);  //agregar la nueva carrera a la lista
                actualizarComboBoxCarreras();  //actualizar el ComboBox de carreras
                textArea.append("Carrera creada: " + nuevaCarrera.getNombre() + "\n");

                //limpiar los campos despues de guardar la carrera
                txtNombreCarrera.setText("");
                txtDuracion.setText("");
                txtOptativasParaGraduarse.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void actualizarComboBoxCarreras() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Carrera carrera : listaCarreras) {
            model.addElement(carrera.getNombre());
        }
        comboBoxCarreras.setModel(model);
    }

    private PlanEstudio obtenerPlanEstudio(String planEstudio) {
        switch (planEstudio) {
            case "Plan A":
                return new PlanA();
            case "Plan B":
                return new PlanB();
            case "Plan C":
                return new PlanC();
            case "Plan D":
                return new PlanD();
            case "Plan E":
                return new PlanE();
            default:
                throw new IllegalArgumentException("Plan de estudio no válido");
        }
    }
//---ALTA DE MATERIAS---
    private JPanel createAltaMateriasPanel() {
        JPanel altaMateriasPanel = new JPanel();
        altaMateriasPanel.setLayout(new GridLayout(8, 2));

        JLabel lblNombreMateria = new JLabel("Nombre de la Materia:");
        JLabel lblCodigo = new JLabel("Código:");
        JLabel lblCuatrimestre = new JLabel("Cuatrimestre:");
        JLabel lblTipo = new JLabel("Tipo (obligatoria u optativa):");
        JLabel lblPromocionable = new JLabel("¿Promocionable?");
        JLabel lblCargaHoraria = new JLabel("Carga Horaria:");
        JLabel lblCorrelativas = new JLabel("Materias Correlativas:");

        txtNombreMateria = new JTextField();
        txtCodigo = new JTextField();
        txtCuatrimestre = new JTextField();
        txtTipo = new JTextField();
        chkPromocionable = new JCheckBox();
        txtCargaHoraria = new JTextField();
        listModelCorrelativas = new DefaultListModel<>();
        listCorrelativas = new JList<>(listModelCorrelativas);
        listCorrelativas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane listScrollPane = new JScrollPane(listCorrelativas);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarMateria();
            }
        });

        altaMateriasPanel.add(lblNombreMateria);
        altaMateriasPanel.add(txtNombreMateria);
        altaMateriasPanel.add(lblCodigo);
        altaMateriasPanel.add(txtCodigo);
        altaMateriasPanel.add(lblCuatrimestre);
        altaMateriasPanel.add(txtCuatrimestre);
        altaMateriasPanel.add(lblTipo);
        altaMateriasPanel.add(txtTipo);
        altaMateriasPanel.add(lblPromocionable);
        altaMateriasPanel.add(chkPromocionable);
        altaMateriasPanel.add(lblCargaHoraria);
        altaMateriasPanel.add(txtCargaHoraria);
        altaMateriasPanel.add(lblCorrelativas);
        altaMateriasPanel.add(listScrollPane);
        altaMateriasPanel.add(new JLabel()); //espacio en blanco
        altaMateriasPanel.add(btnGuardar);

        return altaMateriasPanel;
    }

    private void guardarMateria() {
        try {
            String nombre = txtNombreMateria.getText();
            String codigo = txtCodigo.getText();
            int cuatrimestre = Integer.parseInt(txtCuatrimestre.getText());
            String tipo = txtTipo.getText();
            boolean promocionable = chkPromocionable.isSelected();
            int cargaHoraria = Integer.parseInt(txtCargaHoraria.getText());

            List<Materia> correlativas = listCorrelativas.getSelectedValuesList();

            if (!nombre.isEmpty() && !codigo.isEmpty() && !txtCuatrimestre.getText().isEmpty() &&
                    !tipo.isEmpty() && !txtCargaHoraria.getText().isEmpty()) {

                Materia nuevaMateria = new Materia(nombre, codigo, cuatrimestre, tipo, promocionable, cargaHoraria, correlativas);

                //agregar la nueva materia a la lista
                listaMaterias.add(nuevaMateria);

                //limpia y actualizar la lista de correlativas
                listModelCorrelativas.clear();
                for (Materia materia : listaMaterias) {
                    listModelCorrelativas.addElement(materia);
                }

                textArea.append("Materia creada: " + nuevaMateria.getNombre() + "\n");

                //limpiar los campos despues de guardar la materia
                txtNombreMateria.setText("");
                txtCodigo.setText("");
                txtCuatrimestre.setText("");
                txtTipo.setText("");
                chkPromocionable.setSelected(false);
                txtCargaHoraria.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
//---INSCRIPCION A CARRERA---
    private JPanel createInscripcionCarreraPanel() {
        JPanel inscripcionCarreraPanel = new JPanel();
        inscripcionCarreraPanel.setLayout(new GridLayout(5, 2));

        JLabel lblAlumno = new JLabel("Alumno:");
        JLabel lblCarrera = new JLabel("Carrera:");

        comboBoxAlumnos = new JComboBox<>();
        comboBoxCarreras = new JComboBox<>();

        JButton btnInscribir = new JButton("Inscribir");
        btnInscribir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inscribirAlumnoEnCarrera();
            }
        });

        inscripcionCarreraPanel.add(lblAlumno);
        inscripcionCarreraPanel.add(comboBoxAlumnos);
        inscripcionCarreraPanel.add(lblCarrera);
        inscripcionCarreraPanel.add(comboBoxCarreras);
        inscripcionCarreraPanel.add(new JLabel()); //espacio en blanco
        inscripcionCarreraPanel.add(btnInscribir);

        return inscripcionCarreraPanel;
    }
    private void inscribirAlumnoEnCarrera() {
        try {
            //obtener el alumno y la carrera seleccionados de los ComboBox
            Alumno alumnoSeleccionado = (Alumno) comboBoxAlumnos.getSelectedItem();
            Carrera carreraSeleccionada = (Carrera) comboBoxCarreras.getSelectedItem();

            if (alumnoSeleccionado != null && carreraSeleccionada != null) {

                alumnoSeleccionado.setCarreraInscrita(carreraSeleccionada); //inscribir el alumno en la carrera
                JOptionPane.showMessageDialog(this, "Inscripción exitosa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un alumno y una carrera", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al realizar la inscripción", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//---INSCRIPCION A MATERIA---
    private JPanel createInscripcionMateriaPanel() {
        JPanel inscripcionMateriaPanel = new JPanel();
        inscripcionMateriaPanel.setLayout(new GridLayout(5, 2));

        JLabel lblAlumno = new JLabel("Alumno:");
        JLabel lblMateria = new JLabel("Materia:");

        comboBoxAlumnos = new JComboBox<>();
        comboBoxMaterias = new JComboBox<>();

        JButton btnInscribir = new JButton("Inscribir");
        btnInscribir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inscribirAlumnoEnMateria();
            }
        });

        inscripcionMateriaPanel.add(lblAlumno);
        inscripcionMateriaPanel.add(comboBoxAlumnos);
        inscripcionMateriaPanel.add(lblMateria);
        inscripcionMateriaPanel.add(comboBoxMaterias);
        inscripcionMateriaPanel.add(new JLabel()); //espacio en blanco
        inscripcionMateriaPanel.add(btnInscribir);

        return inscripcionMateriaPanel;
    }
    private void inscribirAlumnoEnMateria() {
        //obtener el alumno y la materia seleccionados de los ComboBox
        Alumno alumnoSeleccionado = listaAlumnos.get(comboBoxAlumnos.getSelectedIndex());
        Materia materiaSeleccionada = listaMaterias.get(comboBoxMaterias.getSelectedIndex());

        try {
            //obtener la lista de materias cursables para el alumno
            Carrera carreraInscrita = alumnoSeleccionado.getCarreraInscrita();
            List<Materia> materiasCursables = carreraInscrita.obtenerMateriasCursables(alumnoSeleccionado);

            //verificar si la materia seleccionada está en la lista de cursables
            if (materiasCursables.contains(materiaSeleccionada)) {

                alumnoSeleccionado.inscribirMateria(materiaSeleccionada); //inscribir el alumno en la materia
                JOptionPane.showMessageDialog(this, "Inscripción exitosa.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No cumple con los requisitos para cursar la materia.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener las materias cursables.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//---MOSTRAR MATERIAS CURSABLES---
    private JPanel createMateriasCursablesPanel() {
        JPanel materiasCursablesPanel = new JPanel();
        materiasCursablesPanel.setLayout(new GridLayout(3, 2));

        JLabel lblAlumno = new JLabel("Alumno:");
        comboBoxAlumnos = new JComboBox<>();
        JButton btnMostrarMateriasCursables = new JButton("Mostrar Materias Cursables");
        JTextArea textAreaMateriasCursables = new JTextArea();
        textAreaMateriasCursables.setEditable(false);

        btnMostrarMateriasCursables.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMateriasCursables();
            }
        });

        materiasCursablesPanel.add(lblAlumno);
        materiasCursablesPanel.add(comboBoxAlumnos);
        materiasCursablesPanel.add(new JLabel()); //espacio en blanco, es una cuestion de diseño del panel
        materiasCursablesPanel.add(btnMostrarMateriasCursables);
        materiasCursablesPanel.add(new JLabel()); //espacio en blanco
        materiasCursablesPanel.add(new JScrollPane(textAreaMateriasCursables));

        return materiasCursablesPanel;
    }
    private void mostrarMateriasCursables() {
        Alumno alumnoSeleccionado = (Alumno) comboBoxAlumnos.getSelectedItem();

        if (alumnoSeleccionado != null) {
            Carrera carreraInscrita = alumnoSeleccionado.getCarreraInscrita();

            if (carreraInscrita != null) {

                List<Materia> materiasCursables = carreraInscrita.obtenerMateriasCursables(alumnoSeleccionado);

                //mostrar materias cursables en el JTextArea
                JTextArea textAreaMateriasCursables = new JTextArea();
                textAreaMateriasCursables.setEditable(false);

                for (Materia materia : materiasCursables) {
                    textAreaMateriasCursables.append(materia.getNombre() + "\n");
                }

                JOptionPane.showMessageDialog(this, new JScrollPane(textAreaMateriasCursables), "Materias Cursables", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "El alumno no está inscrito en ninguna carrera.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//---VERIFICAR GRADUACION---
    private JPanel createVerificarGraduacionPanel() {
        JPanel verificarGraduacionPanel = new JPanel();
        verificarGraduacionPanel.setLayout(new BorderLayout());

        JLabel lblAlumno = new JLabel("Selecciona un alumno:");
        JComboBox<Alumno> comboBoxAlumnos = new JComboBox<>();
        JButton btnVerificarGraduacion = new JButton("Verificar Graduación");

        //obtener la lista de alumnos y cargarla en el ComboBox
        DefaultComboBoxModel<Alumno> comboBoxModel = new DefaultComboBoxModel<>(listaAlumnos.toArray(new Alumno[0]));
        for (Alumno alumno : listaAlumnos) {
            comboBoxAlumnos.addItem(alumno);
        }

        btnVerificarGraduacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Alumno alumnoSeleccionado = (Alumno) comboBoxAlumnos.getSelectedItem();
                    if (alumnoSeleccionado != null) {
                        verificarGraduacion(alumnoSeleccionado);
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, selecciona un alumno.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ClassCastException ex) {
                    JOptionPane.showMessageDialog(null, "Error al obtener el alumno seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Aquí puedes imprimir el stack trace o realizar otra acción
                }
            }
        });

        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(lblAlumno);
        panelSuperior.add(comboBoxAlumnos);

        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.add(btnVerificarGraduacion);

        verificarGraduacionPanel.add(panelSuperior, BorderLayout.NORTH);
        verificarGraduacionPanel.add(panelInferior, BorderLayout.CENTER);

        return verificarGraduacionPanel;
    }

    private void verificarGraduacion(Alumno alumno) {
        Carrera carrera = alumno.getCarreraInscrita();
        if (carrera != null) {
            carrera.verificarGraduacion(alumno);
        }
    }

//---AGREGAR NOTAS---
    private JPanel createAgregarNotasPanel() {
        JPanel agregarNotasPanel = new JPanel();
        agregarNotasPanel.setLayout(new GridLayout(5, 2));

        JLabel lblAlumno = new JLabel("Alumno:");
        JLabel lblMateria = new JLabel("Materia:");
        JLabel lblNotaParcial = new JLabel("Nota Parcial:");
        JLabel lblNotaFinal = new JLabel("Nota Final:");

        comboBoxAlumnosNotas = new JComboBox<>();
        comboBoxMateriasNotas = new JComboBox<>();
        txtNotaParcial = new JTextField();
        txtNotaFinal = new JTextField();

        JButton btnAgregarNotas = new JButton("Agregar Notas");
        btnAgregarNotas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarNotas();
            }
        });

        agregarNotasPanel.add(lblAlumno);
        agregarNotasPanel.add(comboBoxAlumnosNotas);
        agregarNotasPanel.add(lblMateria);
        agregarNotasPanel.add(comboBoxMateriasNotas);
        agregarNotasPanel.add(lblNotaParcial);
        agregarNotasPanel.add(txtNotaParcial);
        agregarNotasPanel.add(lblNotaFinal);
        agregarNotasPanel.add(txtNotaFinal);
        agregarNotasPanel.add(new JLabel()); //espacio en blanco
        agregarNotasPanel.add(btnAgregarNotas);

        return agregarNotasPanel;
    }

    private void agregarNotas() {
        try {
            //obtener alumno, materia y notas de los ComboBox
            Alumno alumnoSeleccionado = listaAlumnos.get(comboBoxAlumnosNotas.getSelectedIndex());
            Materia materiaSeleccionada = listaMaterias.get(comboBoxMateriasNotas.getSelectedIndex());
            String notaParcial = txtNotaParcial.getText().trim();
            String notaFinal = txtNotaFinal.getText().trim();

            //verificar que se haya ingresado al menos una nota
            if (notaParcial.isEmpty() && notaFinal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese al menos una nota.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //nota parcial
            if (!notaParcial.isEmpty()) {
                double notaParcialValue = Double.parseDouble(notaParcial);
                alumnoSeleccionado.registrarNotaParcial(materiaSeleccionada, notaParcialValue);
            }

            //nota final
            if (!notaFinal.isEmpty()) {
                double notaFinalValue = Double.parseDouble(notaFinal);
                alumnoSeleccionado.registrarNotaFinal(materiaSeleccionada, notaFinalValue);
            }

            //limpiar los campos despues de agregar las notas
            txtNotaParcial.setText("");
            txtNotaFinal.setText("");

            JOptionPane.showMessageDialog(this, "Notas agregadas correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos para las notas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
