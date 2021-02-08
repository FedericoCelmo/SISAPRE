/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;


import Controlador.ControladorAgregarAlumno;
import Controlador.ControladorAgregarCarrera;
import Controlador.ControladorAgregarEquipo;
import Controlador.ControladorAgregarSalon;
import Controlador.ControladorBusquedaPrestamo;
import Controlador.ControladorComputadora;
import Controlador.ControladorEquipoInterno;
import Controlador.ControladorGestionAlumno;
import Controlador.ControladorGestionCarrera;
import Controlador.ControladorGestionEquipo;
import Controlador.ControladorGestionSalon;
import Controlador.ControladorGestionUsuario;
import Controlador.ControladorPorcentaje;
import Controlador.ControladorPrestamo;
import Controlador.ControladorReserva;
import Controlador.ControladorTablaPrestamos;
import Modelo.Alumno;
import Modelo.Carrera;
import Modelo.ConsultasAlumno;
import Modelo.ConsultasCarrera;
import Modelo.ConsultasEquipo;
import Modelo.ConsultasEquipoInterno;
import Modelo.ConsultasPrestamo;
import Modelo.ConsultasReserva;
import Modelo.ConsultasSalon;
import Modelo.ConsultasUsuario;
import Modelo.Equipo;
import Modelo.EquipoInterno;
import Modelo.Prestamo;
import Modelo.Reserva;
import Modelo.Salon;
import Modelo.Usuario;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico
 */
public class VistaPrincipalAdmin extends javax.swing.JFrame {
    String temp = "Administrador";
    Usuario mod;
    VistaGestionUsuario usr = new VistaGestionUsuario();
    private Dimension dim;//esta variable es para ajustar el tamaño de la pantalla segun su resolucion
    
    //objetos de las vista
    //para que cada vez que se abran no se genere otra, si no que muestre la misma que esta abierta
    public static VistaMapaComputadoras vistaMapaC;
    public static VistaCompuIndividual vistaInd;
    public static VistaGestionUsuario vistaU;
    public static VistaAcercaDe vistaAcerca = new VistaAcercaDe();
    public static VistaGuardarBD vistaGuardarBS;
    public static VistaRestaurarBD vistaRestaurarBD;
    public static VistaAgregarAlumno vistaA;
    public static VistaGestionSalon vistaGS;
    public static VistaAgregarCarrera vistaC;
    public static VistaAgregarEquipo vistaE;
    public static VistaAgregarSalon vistaS;
    public static VistaGestionCarrera vistaGC;
    public static VistaGestionEquipo vistaGE;
    public static VistaTablaPrestamos vistaTablaP; 
    public static VistaGestionAlumno vistaGA;
    public static VistaBusquedaPrestamo vistaBusquedaP;
    public static VistaPorcentajeOcupacion vistaPorcentaje;
    public static VistaConfiguracionEquipo vistaConfiEquipo;
    public static VistaReservaSalon vistaReserva;
    
    //instancias que ayudan a cerrar todas la ventanas que esten abiertas al cerrar una sesión
    VistaMapaComputadoras vistaMC = new VistaMapaComputadoras();
    VistaGestionAlumno vistaGAl = new  VistaGestionAlumno();
    VistaCompuIndividual vistaID = new VistaCompuIndividual();
    VistaGestionUsuario vistaGU = new VistaGestionUsuario();  
    VistaGuardarBD vistaGuardarBD = new VistaGuardarBD();
    VistaRestaurarBD vistaRestaurarBDA = new VistaRestaurarBD();
    VistaAgregarAlumno vistaAl = new VistaAgregarAlumno();
    VistaGestionSalon vistaGSl = new VistaGestionSalon();
    VistaAgregarCarrera vistaCr = new VistaAgregarCarrera();
    VistaAgregarEquipo vistaEq = new VistaAgregarEquipo();
    VistaAgregarSalon vistaSl = new VistaAgregarSalon();
    VistaGestionCarrera vistaGCr = new VistaGestionCarrera();
    VistaGestionEquipo vistaGEq = new VistaGestionEquipo();
    VistaTablaPrestamos vistaTablaPr = new VistaTablaPrestamos();     
    VistaBusquedaPrestamo vistaBusquedaPr = new VistaBusquedaPrestamo();
    VistaPorcentajeOcupacion vistaPorcentajeOcu = new VistaPorcentajeOcupacion();
    VistaConfiguracionEquipo vistaCE = new VistaConfiguracionEquipo();
    VistaReservaSalon vistaRes = new VistaReservaSalon();
    
    public VistaPrincipalAdmin() {
        //con esto se obtienes el tamano en en x y y del monitor
        dim=super.getToolkit().getScreenSize();
        super.setSize(dim);       
        initComponents();    
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
    
    
    public VistaPrincipalAdmin(Usuario mod){
        initComponents();
        this.mod = mod;
                
        String usuario = mod.getNick() + "/" + mod.getTipoUsuario();//asignacion para ver el usuario conectado       
        menuUsuario.setText(usuario);
        
        if (mod.getTipoUsuario().equals("Administrador")) {  
           temp = "Administrador";
        }
        else{
            if (mod.getTipoUsuario().equals("Becario")) {
                temp = "Becario";
                menuOpcionesAdm.setVisible(false);
                menuGestionAlumno.setVisible(false);
                btnGuardarBD1.setVisible(false);
                btnRestaurarBD.setVisible(false);
                menuGestionSalon.setVisible(false);
                menuGestionCarrera.setVisible(false);
                menuGestionEquipo.setVisible(false);
                btnEliminar.setVisible(false);
            }      
        }
    }
    
    //este metodo es para insertar el icono de la ventana
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/Sisapre001.png"));
        return retValue;
    }
                        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnGuardarBD1 = new javax.swing.JButton();
        btnRestaurarBD = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnMostrarLista = new javax.swing.JButton();
        btnReservarSalon = new javax.swing.JButton();
        btnPrestamoComp = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtIdEquipo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jDateFechaUso = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbxStatus = new javax.swing.JComboBox<>();
        txtFolio = new javax.swing.JTextField();
        txtMatricula = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        spinnerHora2 = new javax.swing.JSpinner();
        spinnerHora1 = new javax.swing.JSpinner();
        txtFechaPrestamo = new javax.swing.JTextField();
        txtHoraPrestamo = new javax.swing.JTextField();
        txtIdEquipoTemp = new javax.swing.JTextField();
        jDCFechaDevolucion = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        txtHora24 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePrestados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnDevolver = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableEquiposDisponibles = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        labelMsg = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableAtrasados = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        menuBarMain = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuUsuario = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuCerrarSesion = new javax.swing.JMenuItem();
        menuSalir = new javax.swing.JMenuItem();
        menuAlumnos = new javax.swing.JMenu();
        menuAgregarAlumno = new javax.swing.JMenuItem();
        menuGestionAlumno = new javax.swing.JMenuItem();
        menuCarreras = new javax.swing.JMenu();
        menuAgregarCarrera = new javax.swing.JMenuItem();
        menuGestionCarrera = new javax.swing.JMenuItem();
        menuEquipos = new javax.swing.JMenu();
        menuAgregarEquipo = new javax.swing.JMenuItem();
        menuGestionEquipo = new javax.swing.JMenuItem();
        menuSalas = new javax.swing.JMenu();
        menuAgregarSalon = new javax.swing.JMenuItem();
        menuGestionSalon = new javax.swing.JMenuItem();
        menuOpcionesAdm = new javax.swing.JMenu();
        menuGestionUsuario = new javax.swing.JMenuItem();
        menuGestionBD = new javax.swing.JMenu();
        menuRespaldoBD = new javax.swing.JMenuItem();
        menuRestBD = new javax.swing.JMenuItem();
        menuBusqueda = new javax.swing.JMenuItem();
        menuMostrarPorcentaje = new javax.swing.JMenuItem();
        menuConfiguracionEquipoInterno = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        menuAcercade = new javax.swing.JMenuItem();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISAPRE");
        setIconImage(getIconImage());

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnGuardarBD1.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnGuardarBD1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/botonGuardarBD.png"))); // NOI18N
        btnGuardarBD1.setText("Respaldar");
        btnGuardarBD1.setToolTipText("Crear respaldo de seguridad de la base de datos (F7)");
        btnGuardarBD1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnGuardarBD1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarBD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarBD1ActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGuardarBD1);

        btnRestaurarBD.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnRestaurarBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/botonRestaurarBD.png"))); // NOI18N
        btnRestaurarBD.setText("Restaurar");
        btnRestaurarBD.setToolTipText("Restaurar la base de datos (F8)");
        btnRestaurarBD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnRestaurarBD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRestaurarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestaurarBDActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRestaurarBD);

        btnLimpiar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LimpiarBig.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setToolTipText("Limpiar las cajas de texto");
        btnLimpiar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar.setFocusable(false);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnLimpiar);

        btnEliminar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/EliminarBig.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setToolTipText("Eliminar los datos del prestamo seleccionado");
        btnEliminar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setFocusable(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEliminar);

        btnMostrarLista.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnMostrarLista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ListarBig.png"))); // NOI18N
        btnMostrarLista.setText("Ver Lista");
        btnMostrarLista.setToolTipText("Mostrar lista de prestamos");
        btnMostrarLista.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnMostrarLista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMostrarLista.setFocusable(false);
        btnMostrarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarListaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMostrarLista);

        btnReservarSalon.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnReservarSalon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Salon.png"))); // NOI18N
        btnReservarSalon.setText("Reservaciones");
        btnReservarSalon.setToolTipText("Registrar reservacion de salón");
        btnReservarSalon.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnReservarSalon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReservarSalon.setFocusable(false);
        btnReservarSalon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarSalonActionPerformed(evt);
            }
        });
        jToolBar1.add(btnReservarSalon);

        btnPrestamoComp.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnPrestamoComp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Computadora.png"))); // NOI18N
        btnPrestamoComp.setText("Equipos Internos");
        btnPrestamoComp.setToolTipText("Registrar un turno interno");
        btnPrestamoComp.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnPrestamoComp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrestamoComp.setFocusable(false);
        btnPrestamoComp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrestamoCompActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrestamoComp);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel2.setText("Matrícula:");

        jLabel5.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel5.setText("ID Equipo:");

        txtIdEquipo.setToolTipText("Ingrese el id del equipo a registrar");
        txtIdEquipo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtIdEquipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdEquipoKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel7.setText("Fecha de uso:");

        jDateFechaUso.setToolTipText("Ingresar fecha");
        jDateFechaUso.setDateFormatString("yyyy/MM/dd");

        jLabel8.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel8.setText("Hora de uso:");

        jLabel9.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel9.setText("Hora de devolución:");

        jLabel10.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel10.setText("Estatus:");

        cbxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        txtFolio.setEditable(false);
        txtFolio.setEnabled(false);

        txtMatricula.setToolTipText("Ingrese la matricula del alumno");
        txtMatricula.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtMatricula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMatriculaKeyTyped(evt);
            }
        });

        txtNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtDescripcion.setEditable(false);
        txtDescripcion.setToolTipText("Descripción del equipo");

        jLabel3.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel3.setText("Nombre completo:");

        jLabel13.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel13.setText("Folio:");

        spinnerHora2.setModel(new javax.swing.SpinnerListModel(new String[] {"07:00:00", "07:15:00", "07:30:00", "07:45:00", "08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00", "10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00", "12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00", "14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00", "16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00", "18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00", "20:00:00"}));
        spinnerHora2.setToolTipText("Ingrese la hora final de uso");
        spinnerHora2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        spinnerHora1.setModel(new javax.swing.SpinnerListModel(new String[] {"07:00:00", "07:15:00", "07:30:00", "07:45:00", "08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00", "10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00", "12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00", "14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00", "16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00", "18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00", "20:00:00"}));
        spinnerHora1.setToolTipText("Ingrese la hora de uso");
        spinnerHora1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        txtFechaPrestamo.setEditable(false);
        txtFechaPrestamo.setEnabled(false);

        txtHoraPrestamo.setEditable(false);
        txtHoraPrestamo.setEnabled(false);

        txtIdEquipoTemp.setEditable(false);
        txtIdEquipoTemp.setEnabled(false);

        jDCFechaDevolucion.setDateFormatString("yyyy/MM/dd");

        jLabel11.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel11.setText("Fecha de devolución:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10)))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtFolio, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdEquipo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcion))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdEquipoTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(spinnerHora1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerHora2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addComponent(txtHoraPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFechaPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHora24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jDCFechaDevolucion, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                        .addComponent(jDateFechaUso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtNombre))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFolio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMatricula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtIdEquipoTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtIdEquipo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jDateFechaUso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDCFechaDevolucion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHora24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoraPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(spinnerHora1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(spinnerHora2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbxStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(89, 89, 89))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtDescripcion, txtFolio, txtIdEquipo, txtMatricula, txtNombre});

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablePrestados.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        tablePrestados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Folio", "Matricula", "ID Equipo", "Fecha Devolución", "Estatus"
            }
        ));
        tablePrestados.setToolTipText("Seleccione un dato de la lista para editarlo");
        tablePrestados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tablePrestados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePrestadosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablePrestados);
        if (tablePrestados.getColumnModel().getColumnCount() > 0) {
            tablePrestados.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablePrestados.getColumnModel().getColumn(3).setPreferredWidth(120);
        }

        jLabel1.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel1.setText("Equipos Externos Prestados y Reservados");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnActualizar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar Listas");
        btnActualizar.setToolTipText("Actualiza los datos de las tablas");
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDevolver.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnDevolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Devolver.png"))); // NOI18N
        btnDevolver.setText("Devolver");
        btnDevolver.setToolTipText("Realiza la devolución del prestamo seleccionado");
        btnDevolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnCancelar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancela el prestamo seleccionado");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tableEquiposDisponibles.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        tableEquiposDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Descripción", "Número de serie", "Status"
            }
        ));
        tableEquiposDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEquiposDisponiblesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableEquiposDisponibles);
        if (tableEquiposDisponibles.getColumnModel().getColumnCount() > 0) {
            tableEquiposDisponibles.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableEquiposDisponibles.getColumnModel().getColumn(1).setPreferredWidth(90);
            tableEquiposDisponibles.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableEquiposDisponibles.getColumnModel().getColumn(3).setPreferredWidth(40);
        }

        jLabel6.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel6.setText("Equipos Externos Disponibles ");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnModificar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Modificar.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setToolTipText("Modifica el prestamo seleccionado");
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCancelar, btnDevolver});

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnGuardar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Realizar.png"))); // NOI18N
        btnGuardar.setText("Realizar Prestamo");
        btnGuardar.setToolTipText("Registra los datos del prestamo");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        labelMsg.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        labelMsg.setForeground(new java.awt.Color(204, 0, 0));

        tableAtrasados.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        tableAtrasados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Folio", "Matricula", "ID Equipo", "Status"
            }
        ));
        tableAtrasados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAtrasadosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableAtrasados);

        jLabel4.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel4.setText("Prestamos Atrasados");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addComponent(labelMsg, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBarMain.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        menuBarMain.setForeground(new java.awt.Color(255, 255, 255));
        menuBarMain.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBarMain.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N

        menuArchivo.setText("Archivo");

        menuUsuario.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/avatar.png"))); // NOI18N
        menuUsuario.setToolTipText("Usuario conectado");
        menuUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuUsuarioActionPerformed(evt);
            }
        });
        menuArchivo.add(menuUsuario);
        menuArchivo.add(jSeparator1);

        menuCerrarSesion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        menuCerrarSesion.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/CerrarSesion.png"))); // NOI18N
        menuCerrarSesion.setText("Cerrar Sesión");
        menuCerrarSesion.setToolTipText("Cierra la sesión actual");
        menuCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCerrarSesionActionPerformed(evt);
            }
        });
        menuArchivo.add(menuCerrarSesion);

        menuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        menuSalir.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Salir.png"))); // NOI18N
        menuSalir.setText("Salir");
        menuSalir.setToolTipText("Salir del sistema");
        menuSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirActionPerformed(evt);
            }
        });
        menuArchivo.add(menuSalir);

        menuBarMain.add(menuArchivo);

        menuAlumnos.setText("Alumnos");

        menuAgregarAlumno.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        menuAgregarAlumno.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuAgregarAlumno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/plus.png"))); // NOI18N
        menuAgregarAlumno.setText("Nuevo Alumno");
        menuAgregarAlumno.setToolTipText("Registre un nuevo alumno");
        menuAgregarAlumno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuAgregarAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAgregarAlumnoActionPerformed(evt);
            }
        });
        menuAlumnos.add(menuAgregarAlumno);

        menuGestionAlumno.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        menuGestionAlumno.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuGestionAlumno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Editar.png"))); // NOI18N
        menuGestionAlumno.setText("Gestión de Alumnos");
        menuGestionAlumno.setToolTipText("Gestione los datos de los alumnos");
        menuGestionAlumno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuGestionAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGestionAlumnoActionPerformed(evt);
            }
        });
        menuAlumnos.add(menuGestionAlumno);

        menuBarMain.add(menuAlumnos);

        menuCarreras.setText("Carreras");
        menuCarreras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menuAgregarCarrera.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        menuAgregarCarrera.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuAgregarCarrera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/plus.png"))); // NOI18N
        menuAgregarCarrera.setText("Nueva Carrera");
        menuAgregarCarrera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuAgregarCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAgregarCarreraActionPerformed(evt);
            }
        });
        menuCarreras.add(menuAgregarCarrera);

        menuGestionCarrera.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        menuGestionCarrera.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuGestionCarrera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Editar.png"))); // NOI18N
        menuGestionCarrera.setText("Gestión de Carreras");
        menuGestionCarrera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuGestionCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGestionCarreraActionPerformed(evt);
            }
        });
        menuCarreras.add(menuGestionCarrera);

        menuBarMain.add(menuCarreras);

        menuEquipos.setText("Equipos");
        menuEquipos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menuAgregarEquipo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        menuAgregarEquipo.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuAgregarEquipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/plus.png"))); // NOI18N
        menuAgregarEquipo.setText("Nuevo Equipo");
        menuAgregarEquipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuAgregarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAgregarEquipoActionPerformed(evt);
            }
        });
        menuEquipos.add(menuAgregarEquipo);

        menuGestionEquipo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        menuGestionEquipo.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuGestionEquipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Editar.png"))); // NOI18N
        menuGestionEquipo.setText("Gestión de Equipos");
        menuGestionEquipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuGestionEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGestionEquipoActionPerformed(evt);
            }
        });
        menuEquipos.add(menuGestionEquipo);

        menuBarMain.add(menuEquipos);

        menuSalas.setText("Salones");
        menuSalas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menuAgregarSalon.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuAgregarSalon.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuAgregarSalon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/plus.png"))); // NOI18N
        menuAgregarSalon.setText("Nuevo Salón");
        menuAgregarSalon.setToolTipText("Registre un nuevo salón");
        menuAgregarSalon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuAgregarSalon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAgregarSalonActionPerformed(evt);
            }
        });
        menuSalas.add(menuAgregarSalon);

        menuGestionSalon.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        menuGestionSalon.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuGestionSalon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Editar.png"))); // NOI18N
        menuGestionSalon.setText("Gestión de Salones");
        menuGestionSalon.setToolTipText("Gestiones los datos de los salones");
        menuGestionSalon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuGestionSalon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGestionSalonActionPerformed(evt);
            }
        });
        menuSalas.add(menuGestionSalon);

        menuBarMain.add(menuSalas);

        menuOpcionesAdm.setText("Opciones de Administrador");
        menuOpcionesAdm.setToolTipText("");
        menuOpcionesAdm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menuGestionUsuario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        menuGestionUsuario.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuGestionUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Editar.png"))); // NOI18N
        menuGestionUsuario.setText("Gestión de Usuarios");
        menuGestionUsuario.setToolTipText("Gestione los datos de los usuarios del sistema");
        menuGestionUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuGestionUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGestionUsuarioActionPerformed(evt);
            }
        });
        menuOpcionesAdm.add(menuGestionUsuario);

        menuGestionBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BD.png"))); // NOI18N
        menuGestionBD.setText("Gestión de Base de Datos");
        menuGestionBD.setToolTipText("Gestione el respaldo de la BD");
        menuGestionBD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuGestionBD.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N

        menuRespaldoBD.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        menuRespaldoBD.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuRespaldoBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/GuardarBD.png"))); // NOI18N
        menuRespaldoBD.setText("Respaldar Base de datos");
        menuRespaldoBD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuRespaldoBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRespaldoBDActionPerformed(evt);
            }
        });
        menuGestionBD.add(menuRespaldoBD);

        menuRestBD.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        menuRestBD.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuRestBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/RestaurarBD.png"))); // NOI18N
        menuRestBD.setText("Restaurar Base de Datos");
        menuRestBD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuRestBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRestBDActionPerformed(evt);
            }
        });
        menuGestionBD.add(menuRestBD);

        menuOpcionesAdm.add(menuGestionBD);

        menuBusqueda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        menuBusqueda.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuBusqueda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BuscarSmall.png"))); // NOI18N
        menuBusqueda.setText("Búsqueda de Préstamos");
        menuBusqueda.setToolTipText("Busque los préstamos registrados");
        menuBusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBusquedaActionPerformed(evt);
            }
        });
        menuOpcionesAdm.add(menuBusqueda);

        menuMostrarPorcentaje.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        menuMostrarPorcentaje.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuMostrarPorcentaje.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Porcentaje.png"))); // NOI18N
        menuMostrarPorcentaje.setText("Mostrar Porcentaje de Ocupación");
        menuMostrarPorcentaje.setToolTipText("Muestra el porcentaje de ocupación de los equipos");
        menuMostrarPorcentaje.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuMostrarPorcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMostrarPorcentajeActionPerformed(evt);
            }
        });
        menuOpcionesAdm.add(menuMostrarPorcentaje);

        menuConfiguracionEquipoInterno.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        menuConfiguracionEquipoInterno.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuConfiguracionEquipoInterno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ConfiguracionCompu.png"))); // NOI18N
        menuConfiguracionEquipoInterno.setText("Asignación de Número de Inventario");
        menuConfiguracionEquipoInterno.setToolTipText("Asigna el número de inventario a los equipos internos");
        menuConfiguracionEquipoInterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConfiguracionEquipoInternoActionPerformed(evt);
            }
        });
        menuOpcionesAdm.add(menuConfiguracionEquipoInterno);

        menuBarMain.add(menuOpcionesAdm);

        menuAyuda.setText("Ayuda");

        menuAcercade.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        menuAcercade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/About.png"))); // NOI18N
        menuAcercade.setText("Acerca de");
        menuAcercade.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuAcercade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAcercadeActionPerformed(evt);
            }
        });
        menuAyuda.add(menuAcercade);

        menuBarMain.add(menuAyuda);

        setJMenuBar(menuBarMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCerrarSesionActionPerformed
        // TODO add your handling code here:
        
        int msg = JOptionPane.showConfirmDialog(null, "¿Realmente desea cerrar sesión?", "Confirmar cerrar sesión", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (msg == JOptionPane.YES_OPTION){
            VistaLogin vista = new VistaLogin();
            vista.setLocationRelativeTo(null);
            vista.setVisible(true);
            this.dispose();
            this.vistaAcerca.dispose();
            
            //sirven para cerrar todas las ventanas que esten abiertas
            this.vistaGAl.dispose();
            vistaGA = null;
            this.vistaAl.dispose();
            vistaA = null;
            this.vistaMC.dispose();
            vistaMapaC = null;
            this.vistaBusquedaPr.dispose();
            vistaBusquedaP = null;
            this.vistaCr.dispose();
            vistaC = null;
            this.vistaEq.dispose(); 
            vistaE = null;
            this.vistaGCr.dispose();
            vistaGC = null;
            this.vistaGEq.dispose();
            vistaGE = null;
            this.vistaGSl.dispose();
            vistaGS = null;
            this.vistaGuardarBD.dispose();
            vistaGuardarBS = null;
            this.vistaID.dispose();
            vistaInd = null;
            this.vistaRestaurarBDA.dispose();
            vistaRestaurarBD = null;
            this.vistaSl.dispose();
            vistaS = null;
            this.vistaTablaPr.dispose();
            vistaTablaP = null;
            this.vistaGU.dispose();
            vistaU = null;
            this.vistaPorcentajeOcu.dispose();
            vistaPorcentaje = null;
            this.vistaCE.dispose();
            vistaConfiEquipo = null;
            this.vistaRes.dispose();
            vistaReserva = null;         
        }
    }//GEN-LAST:event_menuCerrarSesionActionPerformed

    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
        // TODO add your handling code here:        
        int msg = JOptionPane.showConfirmDialog(null, "¿Realmente desea salir del sistema?", "Confirmar salida", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (msg == JOptionPane.YES_OPTION){
            System.exit(0);
        }       
    }//GEN-LAST:event_menuSalirActionPerformed

    private void menuGestionUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGestionUsuarioActionPerformed
       if (vistaU == null) {
            vistaU = new VistaGestionUsuario();
            vistaGU = vistaU;
            Usuario usuario = new Usuario();
            ConsultasUsuario conU = new ConsultasUsuario();        
            ControladorGestionUsuario ctrl = new ControladorGestionUsuario(usuario, conU, vistaU);
            ctrl.iniciar();
            vistaU.setVisible(true);
        }
    }//GEN-LAST:event_menuGestionUsuarioActionPerformed

    private void menuAcercadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAcercadeActionPerformed
        // TODO add your handling code here:
        //VistaAcercaDe vista = new VistaAcercaDe();
        vistaAcerca.setLocationRelativeTo(null);
        vistaAcerca.setVisible(true);
    }//GEN-LAST:event_menuAcercadeActionPerformed

    private void btnGuardarBD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarBD1ActionPerformed
       
        if (vistaGuardarBS == null) {
            vistaGuardarBS = new VistaGuardarBD();
            vistaGuardarBD = vistaGuardarBS;
            vistaGuardarBS.setLocationRelativeTo(null);
            vistaGuardarBS.setVisible(true);         
        }
    }//GEN-LAST:event_btnGuardarBD1ActionPerformed

    private void btnRestaurarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestaurarBDActionPerformed
       
        if (vistaRestaurarBD == null) {
            vistaRestaurarBD = new VistaRestaurarBD();
            vistaRestaurarBDA = vistaRestaurarBD;
            vistaRestaurarBD.setLocationRelativeTo(null);
            vistaRestaurarBD.setVisible(true);           
        }
        
    }//GEN-LAST:event_btnRestaurarBDActionPerformed

    private void menuGestionAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGestionAlumnoActionPerformed

        if (vistaGA == null) {
            vistaGA = new VistaGestionAlumno();
            vistaGAl = vistaGA;          
            Alumno alumno = new Alumno();
            ConsultasAlumno conA = new ConsultasAlumno();
            ControladorGestionAlumno ctrl = new ControladorGestionAlumno(alumno, conA, vistaGA);
            ctrl.iniciar();
            vistaGA.setVisible(true);
        }
    }//GEN-LAST:event_menuGestionAlumnoActionPerformed

    private void menuAgregarAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAgregarAlumnoActionPerformed
       
        if (vistaA == null) {
            vistaA = new VistaAgregarAlumno();
            vistaAl = vistaA;
            Alumno alumno = new Alumno();
            ConsultasAlumno conA = new ConsultasAlumno();       
            ControladorAgregarAlumno ctrl = new ControladorAgregarAlumno(alumno, conA, vistaA);
            ctrl.iniciar();
            vistaA.setVisible(true);
        }
    }//GEN-LAST:event_menuAgregarAlumnoActionPerformed

    private void menuGestionSalonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGestionSalonActionPerformed
        
        if (vistaGS == null) {
            vistaGS = new VistaGestionSalon();
            vistaGSl = vistaGS;
            Salon salon = new Salon();
            ConsultasSalon conS = new ConsultasSalon();          
            ControladorGestionSalon ctrl = new ControladorGestionSalon(salon, conS, vistaGS);
            ctrl.iniciar();
            vistaGS.setVisible(true);           
        }
    }//GEN-LAST:event_menuGestionSalonActionPerformed

    private void menuAgregarCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAgregarCarreraActionPerformed
        
        if (vistaC == null) {
            vistaC = new VistaAgregarCarrera();
            vistaCr = vistaC;
            Carrera carrera = new Carrera();
            ConsultasCarrera conC = new ConsultasCarrera();
            ControladorAgregarCarrera ctrl = new ControladorAgregarCarrera(carrera, conC, vistaC);
            ctrl.iniciar();
            vistaC.setVisible(true);
        }
    }//GEN-LAST:event_menuAgregarCarreraActionPerformed

    private void menuAgregarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAgregarEquipoActionPerformed
        
        if (vistaE == null) {
            vistaE = new VistaAgregarEquipo();
            vistaEq = vistaE;
            Equipo equipo = new Equipo();
            ConsultasEquipo conE = new ConsultasEquipo();           
            ControladorAgregarEquipo ctrl = new ControladorAgregarEquipo(equipo, conE, vistaE);
            ctrl.iniciar();
            vistaE.setVisible(true);           
        }
    }//GEN-LAST:event_menuAgregarEquipoActionPerformed

    private void menuAgregarSalonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAgregarSalonActionPerformed
        
        if (vistaS == null) {
            vistaS = new VistaAgregarSalon();
            vistaSl = vistaS;
            Salon salon = new Salon();
            ConsultasSalon conS = new ConsultasSalon();
            ControladorAgregarSalon ctrl = new ControladorAgregarSalon(salon, conS, vistaS);
            ctrl.iniciar();
            vistaS.setVisible(true);           
        }
    }//GEN-LAST:event_menuAgregarSalonActionPerformed

    private void menuGestionCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGestionCarreraActionPerformed
        
        if (vistaGC == null) {
            vistaGC = new VistaGestionCarrera();
            vistaGCr = vistaGC;
            Carrera carrera = new Carrera();
            ConsultasCarrera conC = new ConsultasCarrera();           
            ControladorGestionCarrera ctrl = new ControladorGestionCarrera(carrera, conC, vistaGC);
            ctrl.iniciar();
            vistaGC.setVisible(true);
        }
    }//GEN-LAST:event_menuGestionCarreraActionPerformed

    private void menuGestionEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGestionEquipoActionPerformed
        
        if (vistaGE == null) {
            vistaGE = new VistaGestionEquipo();
            vistaGEq = vistaGE;
            Equipo equipo = new Equipo();
            ConsultasEquipo conE = new ConsultasEquipo();
            //VistaGestionEquipo vistaE = new VistaGestionEquipo();
            ControladorGestionEquipo ctrl = new ControladorGestionEquipo(equipo, conE, vistaGE);
            ctrl.iniciar();
            vistaGE.setVisible(true);
        }
    }//GEN-LAST:event_menuGestionEquipoActionPerformed

    private void menuUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuUsuarioActionPerformed

    private void tablePrestadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePrestadosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablePrestadosMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
       
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tableAtrasadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAtrasadosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableAtrasadosMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnMostrarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarListaActionPerformed
       
        if (vistaTablaP == null) {
            vistaTablaP = new VistaTablaPrestamos();    
            vistaTablaPr = vistaTablaP;
            ControladorTablaPrestamos ctrl = new ControladorTablaPrestamos(vistaTablaP);
            ctrl.iniciar();
            vistaTablaP.setLocationRelativeTo(null);
            vistaTablaP.setVisible(true);
        }
    }//GEN-LAST:event_btnMostrarListaActionPerformed

    private void tableEquiposDisponiblesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEquiposDisponiblesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableEquiposDisponiblesMouseClicked

    private void btnPrestamoCompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrestamoCompActionPerformed
        // TODO add your handling code here:
        
        if (vistaMapaC == null) {
            vistaMapaC = new VistaMapaComputadoras();
            vistaInd = new VistaCompuIndividual();
            vistaMC = vistaMapaC;
            Prestamo prestamo = new Prestamo();
            Equipo equipo = new Equipo();
            ConsultasPrestamo conP = new ConsultasPrestamo();
            ConsultasEquipo conE = new ConsultasEquipo();
            ControladorComputadora ctrl = new ControladorComputadora(prestamo, conP, vistaMapaC, equipo, conE, vistaInd);
            ctrl.iniciar();
            vistaMapaC.setLocationRelativeTo(null);
            vistaMapaC.setVisible(true);
        }
    }//GEN-LAST:event_btnPrestamoCompActionPerformed

    private void menuBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBusquedaActionPerformed
        
        if (vistaBusquedaP == null) {
            vistaBusquedaP = new  VistaBusquedaPrestamo();   
            vistaBusquedaPr = vistaBusquedaP;
            Prestamo prestamo = new Prestamo();
            ConsultasPrestamo conP = new ConsultasPrestamo();
            ControladorBusquedaPrestamo ctrl = new ControladorBusquedaPrestamo(vistaBusquedaP, prestamo, conP);
            ctrl.iniciar();
            vistaBusquedaP.setLocationRelativeTo(null);
            vistaBusquedaP.setVisible(true);
        }
    }//GEN-LAST:event_menuBusquedaActionPerformed

    private void txtMatriculaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMatriculaKeyTyped
        char c = evt.getKeyChar();
        if((c <'0'|| c >'9') && (c !=' '))evt.consume(); //Limita los tipos de caracteres a ingresar
            
        int maxCaracteres = 10; //Limita el maximo de caracteres a ingresar
        if(txtMatricula.getText().length()>=maxCaracteres)evt.consume();
    }//GEN-LAST:event_txtMatriculaKeyTyped

    private void txtIdEquipoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdEquipoKeyTyped
        
       
    }//GEN-LAST:event_txtIdEquipoKeyTyped

    private void menuRespaldoBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRespaldoBDActionPerformed
        if (vistaGuardarBS == null) {
            vistaGuardarBS = new VistaGuardarBD();
            vistaGuardarBD = vistaGuardarBS;
            vistaGuardarBS.setLocationRelativeTo(null);
            vistaGuardarBS.setVisible(true);         
        }
    }//GEN-LAST:event_menuRespaldoBDActionPerformed

    private void menuRestBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRestBDActionPerformed
        if (vistaRestaurarBD == null) {
            vistaRestaurarBD = new VistaRestaurarBD();
            vistaRestaurarBDA = vistaRestaurarBD;
            vistaRestaurarBD.setLocationRelativeTo(null);
            vistaRestaurarBD.setVisible(true);           
        }
    }//GEN-LAST:event_menuRestBDActionPerformed

    private void menuMostrarPorcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMostrarPorcentajeActionPerformed
        if (vistaPorcentaje == null) {
            vistaPorcentaje = new VistaPorcentajeOcupacion();
            vistaPorcentajeOcu = vistaPorcentaje;  
            ControladorPorcentaje ctrl = new ControladorPorcentaje(vistaPorcentaje);
            ctrl.iniciar();
            vistaPorcentaje.setLocationRelativeTo(null);
            vistaPorcentaje.setVisible(true);
        }
    }//GEN-LAST:event_menuMostrarPorcentajeActionPerformed

    private void btnReservarSalonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarSalonActionPerformed
        if (vistaReserva == null) {
            vistaReserva = new VistaReservaSalon();
            vistaRes = vistaReserva;  
            Reserva reserva = new Reserva();
            ConsultasReserva conR = new ConsultasReserva();
            ControladorReserva ctrl = new ControladorReserva(reserva, conR, vistaReserva);
            ctrl.iniciar();
            vistaReserva.setLocationRelativeTo(null);
            vistaReserva.setVisible(true);
        }
    }//GEN-LAST:event_btnReservarSalonActionPerformed

    private void menuConfiguracionEquipoInternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConfiguracionEquipoInternoActionPerformed
        if (vistaConfiEquipo == null) {
            vistaConfiEquipo = new VistaConfiguracionEquipo();
            vistaCE = vistaConfiEquipo;
            EquipoInterno equipo = new EquipoInterno();
            ConsultasEquipoInterno conE = new ConsultasEquipoInterno();
            ControladorEquipoInterno ctrl = new ControladorEquipoInterno(equipo, conE, vistaConfiEquipo );
            ctrl.iniciar();
            vistaConfiEquipo.setLocationRelativeTo(null);
            vistaConfiEquipo.setVisible(true);
        }
    }//GEN-LAST:event_menuConfiguracionEquipoInternoActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnActualizar;
    public javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnDevolver;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarBD1;
    public javax.swing.JButton btnLimpiar;
    public javax.swing.JButton btnModificar;
    public javax.swing.JButton btnMostrarLista;
    public javax.swing.JButton btnPrestamoComp;
    public javax.swing.JButton btnReservarSalon;
    private javax.swing.JButton btnRestaurarBD;
    public javax.swing.JComboBox<String> cbxStatus;
    public com.toedter.calendar.JDateChooser jDCFechaDevolucion;
    public com.toedter.calendar.JDateChooser jDateFechaUso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    public javax.swing.JLabel labelMsg;
    private javax.swing.JMenuItem menuAcercade;
    private javax.swing.JMenuItem menuAgregarAlumno;
    private javax.swing.JMenuItem menuAgregarCarrera;
    private javax.swing.JMenuItem menuAgregarEquipo;
    private javax.swing.JMenuItem menuAgregarSalon;
    private javax.swing.JMenu menuAlumnos;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenuBar menuBarMain;
    private javax.swing.JMenuItem menuBusqueda;
    private javax.swing.JMenu menuCarreras;
    private javax.swing.JMenuItem menuCerrarSesion;
    private javax.swing.JMenuItem menuConfiguracionEquipoInterno;
    private javax.swing.JMenu menuEquipos;
    private javax.swing.JMenuItem menuGestionAlumno;
    private javax.swing.JMenu menuGestionBD;
    private javax.swing.JMenuItem menuGestionCarrera;
    private javax.swing.JMenuItem menuGestionEquipo;
    private javax.swing.JMenuItem menuGestionSalon;
    private javax.swing.JMenuItem menuGestionUsuario;
    private javax.swing.JMenuItem menuMostrarPorcentaje;
    private javax.swing.JMenu menuOpcionesAdm;
    private javax.swing.JMenuItem menuRespaldoBD;
    private javax.swing.JMenuItem menuRestBD;
    private javax.swing.JMenu menuSalas;
    private javax.swing.JMenuItem menuSalir;
    public javax.swing.JMenuItem menuUsuario;
    public javax.swing.JSpinner spinnerHora1;
    public javax.swing.JSpinner spinnerHora2;
    public javax.swing.JTable tableAtrasados;
    public javax.swing.JTable tableEquiposDisponibles;
    public javax.swing.JTable tablePrestados;
    public javax.swing.JTextField txtDescripcion;
    public javax.swing.JTextField txtFechaPrestamo;
    public javax.swing.JTextField txtFolio;
    public javax.swing.JTextField txtHora24;
    public javax.swing.JTextField txtHoraPrestamo;
    public javax.swing.JTextField txtIdEquipo;
    public javax.swing.JTextField txtIdEquipoTemp;
    public javax.swing.JTextField txtMatricula;
    public javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
