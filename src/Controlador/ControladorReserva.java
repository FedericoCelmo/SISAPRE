/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasReserva;
import Modelo.Reserva;
import Vista.VistaPrincipalAdmin;
import Vista.VistaReservaSalon;
import com.mxrck.autocompleter.TextAutoCompleter;
import com.mysql.jdbc.Connection;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Federico
 */
public class ControladorReserva extends Conexion implements ActionListener, MouseListener, KeyListener {
    
    private Reserva reserva;
    private ConsultasReserva conR;
    private VistaReservaSalon vistaR;
    //public static VistaPrincipalAdmin vistaAdmin;
    Conexion conn = new Conexion();
    Connection con = (Connection) conn.getConnection();  
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();// variable del modelo de las tablas
    String horaIni = "07:00:00";
    String horaFin = "20:00:00";
    /*Variables para la barra de progreso*/   
    int lunes = 0;
    int martes = 0;
    int miercoles = 0;
    int jueves = 0;
    int viernes = 0;
        
    public ControladorReserva(Reserva reserva, ConsultasReserva conR, VistaReservaSalon vistaR) {
        this.reserva = reserva;
        this.conR = conR;
        this.vistaR = vistaR;
        this.vistaR.btnGuardar.addActionListener(this);
        this.vistaR.btnLimpiar.addActionListener(this);
        this.vistaR.btnModificar.addActionListener(this);
        this.vistaR.btnEliminar.addActionListener(this);
        this.vistaR.btnActualizar.addActionListener(this);      
        this.vistaR.btnCancelar.addActionListener(this);
        this.vistaR.tableReservas.addMouseListener(this); 
        this.vistaR.txtMatricula.addKeyListener(this);
        this.vistaR.txtNombre.addKeyListener(this);
        this.vistaR.txtIdSala.addKeyListener(this);
        this.vistaR.checkBoxLunes.addActionListener(this);
        this.vistaR.checkBoxMartes.addActionListener(this);
        this.vistaR.checkBoxMiercoles.addActionListener(this);
        this.vistaR.checkBoxJueves.addActionListener(this);
        this.vistaR.checkBoxViernes.addActionListener(this);
        this.vistaR.radioButtonDefecto.addActionListener(this);
        this.vistaR.radioButtonTodos.addActionListener(this);
        this.vistaR.radioButtonPersonalizado.addActionListener(this);
        this.vistaR.btnBuscar.addActionListener(this);
    }
    
    //aqui se cargan todos los componentes que se iniciarian cuando se abra la vista
    public void iniciar(){
        fechaSistema();//carga la hora del sistema    
        vistaR.setLocationRelativeTo(null);      
        //variable que no necesitan que estar visibles ya que solo se usan internamente por el 
        vistaR.txtFechaPrestamo.setVisible(false);
        vistaR.txtHoraPrestamo.setVisible(false);
        vistaR.txtHora24.setVisible(false);//esta variable se usa para comparar la hora del sistema en formato de 24hrs
        verificarFechaDeUso();
        verificarFechaFinUso();
        //carga el contenido de las tablas
        cargarTablaReservados(vistaR.tableReservas);
        cargarTablaSalones(vistaR.tableSalones);
        cargarTablaSalonesEnUso(vistaR.tableSalonesEnUso);
        autocompletarMatricula();  
        autocompletarNombre();     
        actualizacionDeEstatusAutomatico();
        actualizacionDeTablasAutomatico();
        vistaR.btnModificar.setEnabled(false);       
        vistaR.btnEliminar.setEnabled(false);
        vistaR.btnCancelar.setEnabled(false);
        vistaR.radioButtonDefecto.setSelected(true);
        vistaR.checkBoxLunes.setEnabled(false);
        vistaR.checkBoxMartes.setEnabled(false);
        vistaR.checkBoxMiercoles.setEnabled(false);
        vistaR.checkBoxJueves.setEnabled(false);
        vistaR.checkBoxViernes.setEnabled(false);
        vistaR.btnEliminar.setVisible(false);
        
    }
    
    //metodo para vaciar los campos de texto 
    public void limpiar(){
        vistaR.txtFolio.setText(null);
        vistaR.txtMatricula.setText(null);
        vistaR.txtNombre.setText(null);                
        vistaR.txtIdSala.setText(null);
        vistaR.txtDescripcion.setText(null);       
        vistaR.jDateFechaUso.setDate(null); 
        vistaR.jDCFechaDevolucion.setDate(null);
        vistaR.txtObservacion.setText(null);                
        vistaR.labelMsg.setText(null);       
        fechaSistema();//actualiza la fecha del sistema
        vistaR.btnModificar.setEnabled(false);
        vistaR.btnCancelar.setEnabled(false);
        vistaR.btnEliminar.setEnabled(false);
        vistaR.btnGuardar.setEnabled(true);
        vistaR.radioButtonDefecto.setSelected(true);
    }   
    //metodo que sirve para cargar los datos de la tabla de salones reservados
    public void cargarTablaReservados(JTable tableReservados) {
        ps = null;
        rs = null;        
        tableReservados.setModel(modelo);
        try {
            //DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"Folio", "ID Salón", "Fecha Uso", "Fecha Desocupación", "Estatus"});//asigna los titulos de las columnas
            try {
                //Connection con = (Connection) conn.getConnection();
                //se realiza la consulta a la BD
                ps = con.prepareStatement("SELECT folio, sala_idSala, DATE_FORMAT(fechaUso, '%d/%b/%y-%H:%i') AS fUso, DATE_FORMAT(fechaDevolucion, '%d/%b/%y-%H:%i') AS fDev, status FROM prestamossalon WHERE prestamossalon.status LIKE 'Reservado%' ORDER BY fechaUso ASC");
                rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        //asigna los valores obtenidos a la tabla
                        modelo.addRow(new Object[]{rs.getString("folio"), rs.getString("sala_idSala"), rs.getString("fUso"),
                            rs.getString("fDev"), rs.getString("status")});
                    }
                    vistaR.tableReservas.setModel(modelo);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }
    //metodo que sirve para cargar los datos de la tabla de salones disponibles
    public void cargarTablaSalones(JTable tableSalones) {
        ps = null;
        rs = null;
        //Conexion conn = new Conexion();
        tableSalones.setModel(modelo);
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Capacidad"});//asigna los titulos de las columnas
            try {
                //Connection con = (Connection) conn.getConnection();
                //se realiza la consulta a la BD
                ps = con.prepareStatement("SELECT * FROM sala");
                rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        //asigna los valores obtenidos a la tabla
                        modelo.addRow(new Object[]{rs.getString("idSala"), rs.getString("nomSala"), rs.getString("capacidad")});
                    }
                    vistaR.tableSalones.setModel(modelo);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }
    //metodo que sirve para cargar los datos de la tabla de salones en uso
    public void cargarTablaSalonesEnUso(JTable tableSalonesUso) {
        ps = null;
        rs = null;
        //Conexion conn = new Conexion();
        tableSalonesUso.setModel(modelo);
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"Folio", "ID Salón", "Fecha Uso", "Fecha Desocupación", "Estatus"});//asigna los titulos de las columnas
            try {
                //Connection con = (Connection) conn.getConnection();
                //se realiza la consulta a la BD
                ps = con.prepareStatement("SELECT folio, sala_idSala, DATE_FORMAT(fechaUso, '%d/%b/%y-%H:%i') AS fUso, DATE_FORMAT(fechaDevolucion, '%d/%b/%y-%H:%i') AS fDev, status FROM prestamossalon WHERE status='En Uso'");
                rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        //asigna los valores obtenidos a la tabla
                        modelo.addRow(new Object[]{rs.getString("folio"), rs.getString("sala_idSala"), rs.getString("fUso"), rs.getString("fDev"), rs.getString("status")});
                    }
                    vistaR.tableSalonesEnUso.setModel(modelo);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }   
    //metodo que obtiene la hora del sistema y se lo asigna a las caja de texto para guardarlo a la BD
    public void fechaSistema(){
        Calendar c;
        c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR), m = 1+ (c.get(Calendar.MONTH)), d = c.get(Calendar.DATE);
        vistaR.txtFechaPrestamo.setText(y+"/"+m+"/"+d);       
        int h = c.get(Calendar.HOUR_OF_DAY), mm = c.get(Calendar.MINUTE), s = c.get(Calendar.SECOND);
         int HH = c.get(Calendar.HOUR_OF_DAY), MM = c.get(Calendar.MINUTE), SS = c.get(Calendar.SECOND);
        vistaR.txtHoraPrestamo.setText(h+":"+mm+":"+s);
        vistaR.txtHora24.setText(HH+":"+MM+":"+SS);
        vistaR.jDateFechaUso.setCalendar(c);
        vistaR.jDCFechaDevolucion.setCalendar(c);
    }    
     //reinicia los datos de las tablas
    public void reiniciar() {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
    }    
    //metodo para autocompletar el campo de la matricula del prestamo
    public void autocompletarMatricula(){
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaR.txtMatricula);
        Connection con = null;        
        //string de la consulta
        String temp = "SELECT matricula, nombre, apellidoP, apellidoM FROM alumno";
        try {
            con = (Connection) cc.getConnection();
            ps = con.prepareStatement(temp);
            //ps.setString(1,vistaPrestamo.txtMatricula.getText());
            rs = ps.executeQuery();
            while (rs.next()) {                
                textAuto.addItem(rs.getString("matricula"));                             
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }    
    //metodo para autocompletar el campo de l nombre del prestamo
    public void autocompletarNombre(){
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaR.txtNombre);
        Connection con = null;       
        //string de la consulta
        //String temp = "SELECT nombre, apellidoP, apellidoM FROM alumno";
        String temp = "SELECT nombre, apellidoP, apellidoM FROM alumno";
        try {
            con = (Connection) cc.getConnection();
            ps = con.prepareStatement(temp);           
            rs = ps.executeQuery();
            while (rs.next()) {               
                textAuto.addItem(rs.getString("nombre"));                            
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }    
    //metodo que sirve para buscar las reservas en los rangos de fechas
    public void buscarReservas(JTable tablePrestamos){
        ps = null;
        rs = null;       
        tablePrestamos.setModel(modelo);                 
        /*String temp = "SELECT * FROM prestamossalon WHERE status = 'Reservado' AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                                    + "OR (? BETWEEN fechaUso AND fechaDevolucion)) ORDER BY fechaPrestamo ASC";*/
        String temp = "SELECT folio, sala_idSala, DATE_FORMAT(fechaUso, '%d/%b/%y-%H:%i') AS fUso, DATE_FORMAT(fechaDevolucion, '%d/%b/%y-%H:%i') AS fDev, status FROM prestamossalon WHERE status = 'Reservado' AND ((fechaUso BETWEEN ? AND ?) "
                                    + "OR (fechaDevolucion BETWEEN ? AND ?)) ORDER BY fechaPrestamo ASC";
        try {
            //DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"Folio", "ID Salón", "Fecha Uso", "Fecha Desocupación", "Estatus"});//asigna los titulos de las columnas
            con = (Connection) getConnection();
            ps = con.prepareStatement(temp);
            ps.setString(1, ((JTextField) vistaR.jDCFechaInicial.getDateEditor().getUiComponent()).getText() + " " + horaIni);
            ps.setString(2, ((JTextField) vistaR.jDCFechaFinal.getDateEditor().getUiComponent()).getText() + " " + horaFin);
            ps.setString(3, ((JTextField) vistaR.jDCFechaInicial.getDateEditor().getUiComponent()).getText() + " " + horaIni);
            ps.setString(4, ((JTextField) vistaR.jDCFechaFinal.getDateEditor().getUiComponent()).getText() + " " + horaFin);
            rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getString("folio"), rs.getString("sala_idSala"),
                    rs.getString("fUso"), rs.getString("fDev"), rs.getString("status")});
            }
            vistaR.tableReservas.setModel(modelo);
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }    
    @Override
    public void actionPerformed(ActionEvent e) {
        Connection con = null;
        int contadorDias = 0;
        if (vistaR.radioButtonPersonalizado.isSelected()) {
            
            vistaR.checkBoxLunes.setEnabled(true);
            vistaR.checkBoxMartes.setEnabled(true);
            vistaR.checkBoxMiercoles.setEnabled(true);
            vistaR.checkBoxJueves.setEnabled(true);
            vistaR.checkBoxViernes.setEnabled(true);
            //vistaR.checkBoxLunes.setSelected(true);
        }
        if (vistaR.radioButtonDefecto.isSelected()) {
            vistaR.checkBoxLunes.setEnabled(false);
            vistaR.checkBoxMartes.setEnabled(false);
            vistaR.checkBoxMiercoles.setEnabled(false);
            vistaR.checkBoxJueves.setEnabled(false);
            vistaR.checkBoxViernes.setEnabled(false);
            vistaR.checkBoxLunes.setSelected(false);
            vistaR.checkBoxMartes.setSelected(false);
            vistaR.checkBoxMiercoles.setSelected(false);
            vistaR.checkBoxJueves.setSelected(false);
            vistaR.checkBoxViernes.setSelected(false);
        }
        if (vistaR.radioButtonTodos.isSelected()) {
            vistaR.checkBoxLunes.setEnabled(false);
            vistaR.checkBoxMartes.setEnabled(false);
            vistaR.checkBoxMiercoles.setEnabled(false);
            vistaR.checkBoxJueves.setEnabled(false);
            vistaR.checkBoxViernes.setEnabled(false);
            vistaR.checkBoxLunes.setSelected(false);
            vistaR.checkBoxMartes.setSelected(false);
            vistaR.checkBoxMiercoles.setSelected(false);
            vistaR.checkBoxJueves.setSelected(false);
            vistaR.checkBoxViernes.setSelected(false);
        }              
        //variables temporales de fechas para verificar que las ingresadas sean correctas
        String fecha1 = ((JTextField) vistaR.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora1.getValue().toString();
        String fecha2 = ((JTextField) vistaR.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora2.getValue().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
                
        try {
            //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
            Date fechaIni = format.parse(fecha1);
            Date fechaFinal = format.parse(fecha2);
            String temp = "SELECT fechaUso, fechaDevolucion, sala_idSala, status FROM prestamossalon WHERE sala_idSala=? AND ((status = 'Reservado') OR (status = 'En Uso')) AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
            if (e.getSource() == vistaR.btnBuscar) {
                reiniciar();
                buscarReservas(vistaR.tableReservas);
            }         
            if (e.getSource() == vistaR.btnGuardar) {
                //verifica que no haya campos vacíos
                if (!vistaR.txtMatricula.getText().equals("") && !vistaR.txtIdSala.equals("")) {
                    //si el usuario oprime el radio boton por defecto
                    if (vistaR.radioButtonDefecto.isSelected()) {
                        //compara si la fecha de uso es menor que la de devolucion
                        if (fechaIni.before(fechaFinal)) {
                            //invocamos a la funcion que nos registrará el dia y la hora ingresada
                            registrarDefecto(vistaR.jDateFechaUso, vistaR.jDCFechaDevolucion);
                          
                        }//si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
                        else {
                            System.out.println("Las fecha de uso no debe de ser mayor que la de devolucion");
                            vistaR.labelMsg.setText("La fecha y hora de uso no deben de ser mayor que la fecha y hora de desocupación");
                        }
                    }
                    //si el usuario oprime el radio boton todos los dias
                    if (vistaR.radioButtonTodos.isSelected()) {
                        //compara si la fecha de uso es menor que la de devolucion
                        if (fechaIni.before(fechaFinal)){
                            //invocamos a la funcion que nos registrará los dias ingresados
                            registrarTodosLosDias(vistaR.jDateFechaUso, vistaR.jDCFechaDevolucion);
                        }//si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
                        else{
                            System.out.println("Las fecha de uso no debe de ser mayor que la de devolucion");
                            vistaR.labelMsg.setText("La fecha y hora de uso no deben de ser mayor que la fecha y hora de desocupación");
                        }                       
                    }
                    //si el usuario oprime el radio boton personalizado
                    if (vistaR.radioButtonPersonalizado.isSelected()) {
                        //si marca Lunes
                        if (vistaR.checkBoxLunes.isSelected()) {

                            //compara si la fecha de uso es menor que la de devolucion
                            if (fechaIni.before(fechaFinal)) {                                                              
                                 //invoco a la funcion que resgitrara los lunes
                                registrarLunes(vistaR.jDateFechaUso, vistaR.jDCFechaDevolucion); 
                                contadorDias++;
                            } //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
                            else {
                                System.out.println("Las fecha de uso no debe de ser mayor que la de devolucion");
                                vistaR.labelMsg.setText("La fecha y hora de uso no deben de ser mayor que la fecha y hora de devolución");
                            }
                        }
                        //si marca Martes
                        if (vistaR.checkBoxMartes.isSelected()) {
                            //compara si la fecha de uso es menor que la de devolucion
                            if (fechaIni.before(fechaFinal)) {
                                //invoco a la funcion que resgitrara los martes
                                registrarMartes(vistaR.jDateFechaUso, vistaR.jDCFechaDevolucion);
                                contadorDias++;
                                   
                            } //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
                            else {
                                System.out.println("Las fecha de uso no debe de ser mayor que la de devolucion");
                                vistaR.labelMsg.setText("La fecha y hora de uso no deben de ser mayor que la fecha y hora de devolución");
                            }
                        }
                        //si marca Miercoles
                        if (vistaR.checkBoxMiercoles.isSelected()) {
                            //compara si la fecha de uso es menor que la de devolucion
                            if (fechaIni.before(fechaFinal)) {
                                //invoco a la funcion que resgitrara los miercoles
                                registrarMiercoles(vistaR.jDateFechaUso, vistaR.jDCFechaDevolucion);  
                                contadorDias++;
                                
                            } //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
                            else {
                                System.out.println("Las fecha de uso no debe de ser mayor que la de devolucion");
                                vistaR.labelMsg.setText("La fecha y hora de uso no deben de ser mayor que la fecha y hora de devolución");
                            }
                        }
                        //si marca Jueves
                        if (vistaR.checkBoxJueves.isSelected()) {
                            //compara si la fecha de uso es menor que la de devolucion
                            if (fechaIni.before(fechaFinal)) {
                                //invoco a la funcion que resgitrara los jueves
                                registrarJueves(vistaR.jDateFechaUso, vistaR.jDCFechaDevolucion);
                                contadorDias++;                               
                            } //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
                            else {
                                System.out.println("Las fecha de uso no debe de ser mayor que la de devolucion");
                                vistaR.labelMsg.setText("La fecha y hora de uso no deben de ser mayor que la fecha y hora de devolución");
                            }
                        }
                        //si marca Viernes
                        if (vistaR.checkBoxViernes.isSelected()) {
                            //compara si la fecha de uso es menor que la de devolucion
                            if (fechaIni.before(fechaFinal)) {
                                //invoco a la funcion que resgitrara los viernes
                                registrarViernes(vistaR.jDateFechaUso, vistaR.jDCFechaDevolucion);
                                contadorDias++;
                                
                            } //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
                            else {
                                System.out.println("Las fecha de uso no debe de ser mayor que la de devolucion");
                                vistaR.labelMsg.setText("La fecha y hora de uso no deben de ser mayor que la fecha y hora de devolución");
                            }
                        }
                        System.out.println("Los dias seleccionados son: " + contadorDias);
                        
                        while(contadorDias!=0){
                            limpiar();
                            break;
                        }                                                
                    }
                } else {
                    vistaR.labelMsg.setText("No se aceptan campos vacíos");
                }
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
        if (e.getSource() == vistaR.btnModificar){             
           try{
                //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
                Date fechaIni = format.parse(fecha1);
                Date fechaFinal = format.parse(fecha2);     
                if (!vistaR.txtMatricula.getText().equals("") && !vistaR.txtIdSala.equals("")) {
                    //si el usuario oprime el radio boton por defecto
                    if (vistaR.radioButtonDefecto.isSelected()) {
                        //compara si la fecha de uso es menor que la de devolucion
                        if (fechaIni.before(fechaFinal)) {                            
                            //invocamos a la funcion que nos registrará el dia y la hora ingresada
                            modificarDefecto(vistaR.jDateFechaUso, vistaR.jDCFechaDevolucion);
                        }//si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
                        else {
                            System.out.println("Las fecha de uso no debe de ser mayor que la de devolución");
                            vistaR.labelMsg.setText("La fecha y hora de uso no deben de ser mayor que la fecha y hora de desocupación");
                        }
                    }
                }
                else{
                    vistaR.labelMsg.setText("No se aceptan campos vacíos");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + ex.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
        if (e.getSource() == vistaR.btnEliminar){  
            //primero compara si estan vacios los campos de texto
            if (!vistaR.txtFolio.getText().equals("")) {
                int msg = JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar la reserva seleccionada?", "Confirmar eliminar reserva", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);       
               
                if (msg == JOptionPane.YES_OPTION) {
                    reserva.setFolio(Integer.parseInt(vistaR.txtFolio.getText()));
                    if (conR.eliminar(reserva)) {
                        JOptionPane.showMessageDialog(null, "Eliminación exitosa");
                        limpiar();//limpia las cajas de los textos
                        reiniciar();//reinicia las tablas
                        fechaSistema();//actualiza la hora del sistema
                        cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados 

                    } else {
                        JOptionPane.showMessageDialog(null, "Eliminación errónea");
                        limpiar();//limpia las cajas de los textos
                    }
                }                            
            } else {
                //mandara un mensaje al usuario si los campos estan vacios
                vistaR.labelMsg.setText("No se ha eliminado nada, por favor seleccione un dato de la tabla");
            }   
        }       
        if (e.getSource() == vistaR.btnCancelar){  
            //primero compara si estan vacios el campo de texto del folio
            if (!vistaR.txtFolio.getText().equals("")) {
                reserva.setFolio(Integer.parseInt(vistaR.txtFolio.getText()));
                String status = "Cancelado";
                reserva.setStatus(status);//actualiza el status del prestamo
                //si no ocurrio ningun error, se procedera a modificar los datos
                if (conR.cancelar(reserva)) {
                    JOptionPane.showMessageDialog(null, "Cancelación exitosa");                   
                    limpiar();//limpia los campos de texto
                    reiniciar();//reinicia las tablas
                    fechaSistema();//actualiza la hora del sistema
                    cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados                                        
                } else {
                    JOptionPane.showMessageDialog(null, "Cancelación errónea");                   
                    limpiar();//limpia las cajas de los textos
                }                               
            }
            else {
                //mandara un mensaje al usuario si los campos estan vacios
                vistaR.labelMsg.setText("No se ha cancelado, por favor seleccione un dato de la tabla");
            }
        }
        if (e.getSource() == vistaR.btnLimpiar){  
            reiniciar();//reinicia las tablas
            limpiar();//limpia las cajas de los textos
            cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados    
        }
        if (e.getSource() == vistaR.btnActualizar){  
            reiniciar();//reinicia las tablas
            fechaSistema();//actualiza la hora del sistema
            verificarFechaDeUso();
            verificarFechaFinUso();
            cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados           
            cargarTablaSalonesEnUso(vistaR.tableSalonesEnUso);
            limpiar();//limpia las cajas de los textos
        }     
    }     
    //metodo que sirve para actualizar el estatus de las preservas cuando empieze la hora de utilizacion
    public void verificarFechaDeUso(){        
        reserva.setStatus("En Uso");
        if (conR.modificarStatusFechaUso(reserva)) {
            //System.out.println("Reserva en uso");
        } else {
            System.out.println("No hay registros actuales de reservaciones");
        }     
    }    
    //metodo que sirve para actualizar el estatus de las preservas cuando termine la hora de utilizacion
    public void verificarFechaFinUso(){              
        reserva.setStatus("Cancelado");
        if (conR.modificarStatusFechaFinUso(reserva)) {
            //System.out.println("Reserva Cancelada");
        } else {
            System.out.println("No hay registros actuales de reservaciones");
        }  
    }    
    //metodo que sirve para liberar la reserva seleccionada
    public void modificarStatusReservaCancelado(){
        String statusR = "Cancelado";//variable local para asignar el status
        try {
            reserva.setFolio(Integer.parseInt(vistaR.txtFolio.getText()));
            reserva.setStatus(statusR);//actualiza el status del equipo           
            if (conR.establecerStatus(reserva)) {
            } 
        } catch (Exception e) {
            System.out.println("Error al modificar el status del equipo");
        }
    }   
    //metodo para ejecutar las actualizaciones automaticas de los estatus de las reservas
    public void actualizacionDeEstatusAutomatico(){
        //1000 milisegundos = 1 segundo
        //60000 milisegundos = 1 minuto
        //3600000 milisegundos = 1 hora       
        try {
            //va ejecutar la tarea cada 10 minutos
            Timer timer = new Timer(600000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    verificarFechaDeUso();
                    System.out.println("Función Ejecutada Actualizar estatus");
                }
            });
            timer.start();
        }catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }          
    }    
    //metodo para ejecutar las actualizaciones automaticas de los estatus de las tablas
    public void actualizacionDeTablasAutomatico(){
        //1000 milisegundos = 1 segundo
        //60000 milisegundos = 1 minuto
        //3600000 milisegundos = 1 hora       
        try {
            //va ejecutar la tarea cada 11 minutos
            Timer timer = new Timer(700000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reiniciar();//reinicia las tablas                                    
                    cargarTablaReservados(vistaR.tableReservas);
                    cargarTablaSalonesEnUso(vistaR.tableSalonesEnUso);
                    System.out.println("Función Ejecutada Actualizar Tablas");
                }
            });
            timer.start();
        }catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }  
    }
    
    //funcion que registra solamente el dia ingresado
    public int registrarDefecto(JDateChooser fechaIni, JDateChooser fechaFin) throws ParseException{
        int dias = 0;//variable para contar los dias transcurridos
        boolean verificar = true;//variable para indicar si de las fechas ingresadas alguna esta ocupada
        boolean indicadorFecha = true;//variable para saber si la fecha de inicio es mayor que la de devolucion
        String fechaOcupada = " ";//variable para asignar la primera fecha que esta ocupada       
        //variables temporales de fechas que el usuario ingresara  para verificar que las ingresadas sean correctas
        String fecha1 = ((JTextField) vistaR.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora1.getValue().toString();
        String fecha2 = ((JTextField) vistaR.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora2.getValue().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");            
        //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
        Date fechaIni1 = format.parse(fecha1);
        Date fechaFinal1 = format.parse(fecha2);
        //cadena de instruccion para verificar disponibilidad de la fecha ingresada
        String temp = "SELECT fechaUso, fechaDevolucion, sala_idSala, status FROM prestamossalon WHERE sala_idSala=? AND ((status = 'Reservado') OR (status = 'En Uso')) AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
        //compara si la fecha de uso es menor que la de devolucion
        if (fechaIni1.before(fechaFinal1)) {
            Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
            Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos           
            if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {
                //iniciamos el ciclo para contar los dias
                while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                    //verificamos que la fecha no sea sabado ni domingo
                    if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                        dias++;//sumamos los dias  
                        Date date = fecha_Inicio.getTime();
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                        String date1 = format1.format(date);//variable para guardar la fecha en turno
                        System.out.println(date1);                      
                        try {
                            con = (Connection) cc.getConnection();
                            ps = con.prepareStatement(temp);
                            ps.setString(1, vistaR.txtIdSala.getText());
                            ps.setString(2, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(3, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(4, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(5, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(6, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(7, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            rs = ps.executeQuery();
                            //itera el resultado
                            if (rs.next()) {
                                //vistaR.labelMsg.setText("Las fechas ingresadas ya están ocupadas");
                                System.out.println("Ocupado: " + date1);
                                fechaOcupada = date1;
                                verificar = false;
                                break;
                            } else {
                                System.out.println("Disponible: " + date1);
                            }
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    fecha_Inicio.add(Calendar.DATE, 1);
                }               
            }
        //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
        } else {
            vistaR.labelMsg.setText("La fecha inicial no debe de ser mayor que la de devolución");
        }
        System.out.println("Los dias son: " + dias);    
        if (dias==1) {
            //verifica que el usuario solo haya ingresado un dia y no varios
            if (verificar==true) {
                //compara si la fecha de uso es menor que la de devolucion
                if (fechaIni1.before(fechaFinal1)) {
                    Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
                    Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos
                    if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {
                        while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                            //verificamos que la fecha no sea sabado ni domingo
                            if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                                //dias++;//sumamos los dias
                                Date date = fecha_Inicio.getTime();
                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                                String date1 = format1.format(date);//la convertimos a string
                                System.out.println(date1);
                                reserva.setMatricula(vistaR.txtMatricula.getText());
                                reserva.setIdSala(Integer.parseInt(vistaR.txtIdSala.getText()));
                                reserva.setFechaPrestamo(vistaR.txtFechaPrestamo.getText() + " " + vistaR.txtHoraPrestamo.getText());
                                reserva.setFechaUso(date1 + " " + vistaR.spinnerHora1.getValue().toString());
                                reserva.setFechaDevolucion(date1 + " " + vistaR.spinnerHora2.getValue().toString());
                                reserva.setObservacion(vistaR.txtObservacion.getText());
                                reserva.setStatus("Reservado");
                                //si no ha habido nigun error, se realizara el prestamo
                                if (conR.registrar(reserva)) {
                                    reiniciar();//reinicia las tablas
                                    fechaSistema();//actualiza la hora del sistema
                                    cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados

                                } //De lo contrario se muestra el mensaje de error
                                else {
                                    JOptionPane.showMessageDialog(null, "No se ha registrado las reservas\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                            + "La matrícula no existe\nEl ID del salón no existe");
                                }
                            }
                            fecha_Inicio.add(Calendar.DATE, 1);
                        }
                        limpiar();//limpia los campos de texto
                        JOptionPane.showMessageDialog(null, "Reservaciones registradas exitosamente");

                    }
                    //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
                } else {
                    vistaR.labelMsg.setText("La fecha inicial no debe de ser mayor que la de devolución");
                }
            }
            else{
                System.out.println("Fechas Ocupadas");
                vistaR.labelMsg.setText("La fecha que ha ingresado, esta ocupada: " + fechaOcupada + " "+ vistaR.spinnerHora1.getValue().toString()
            + "-" + vistaR.spinnerHora2.getValue().toString());
            }           
            System.out.println("Fechas Disponibles");            
        }
        else{            
            vistaR.labelMsg.setText("Por favor, seleccione solamente un día");            
        }
        System.out.println("Los dias trancurridos son: " + dias);
        return dias;  
    }
    
    //funcion que registra los dias habiles disponibles
    public int registrarTodosLosDias(JDateChooser fechaIni, JDateChooser fechaFin) throws ParseException{
        int dias = 0;//variable para contar los dias transcurridos
        boolean verificar = true;//variable para indicar si de las fechas ingresadas alguna esta ocupada
        boolean indicadorFecha = true;//variable para saber si la fecha de inicio es mayor que la de devolucion
        String fechaOcupada = " ";//variable para asignar la primera fecha que esta ocupada        
        //variables temporales de fechas que el usuario ingresara  para verificar que las ingresadas sean correctas
        String fecha1 = ((JTextField) vistaR.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora1.getValue().toString();
        String fecha2 = ((JTextField) vistaR.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora2.getValue().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");            
        //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
        Date fechaIni1 = format.parse(fecha1);
        Date fechaFinal1 = format.parse(fecha2);
        //cadena de instruccion para verificar disponibilidad de la fecha ingresada
         String temp = "SELECT fechaUso, fechaDevolucion, sala_idSala, status FROM prestamossalon WHERE sala_idSala=? AND ((status = 'Reservado') OR (status = 'En Uso')) AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
        //compara si la fecha de uso es menor que la de devolucion
        try {
            Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
            Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos            
            if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {
                //iniciamos el ciclo para contar los dias
                while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                    //verificamos que la fecha no sea sabado ni domingo
                    if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                        dias++;//sumamos los dias  
                        Date date = fecha_Inicio.getTime();
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                        String date1 = format1.format(date);//variable para guardar la fecha en turno
                        System.out.println(date1);                      
                            try {
                                con = (Connection) cc.getConnection();
                                ps = con.prepareStatement(temp);
                                ps.setString(1, vistaR.txtIdSala.getText());
                                ps.setString(2, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                                ps.setString(3, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                                ps.setString(4, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                                ps.setString(5, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                                ps.setString(6, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                                ps.setString(7, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                                rs = ps.executeQuery();
                                //itera el resultado
                                if (rs.next()) {
                                    //vistaR.labelMsg.setText("Las fechas ingresadas ya están ocupadas");
                                    System.out.println("Ocupado: " + date1);
                                    fechaOcupada = date1;
                                    verificar = false;
                                    break;
                                } else {
                                    System.out.println("Disponible: " + date1);
                                }
                            } catch (Exception exc) {
                                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                            }                                            
                    }
                    fecha_Inicio.add(Calendar.DATE, 1);
                }               
            }
        //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
        } catch(Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);;     
        }
        if (verificar==true) {
            //compara si la fecha de uso es menor que la de devolucion
            //if (indicadorFecha==true) {
                Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
                Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos
                if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {                 
                    while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                        //verificamos que la fecha no sea sabado ni domingo
                        if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {                           
                            Date date = fecha_Inicio.getTime();
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                            String date1 = format1.format(date);//la convertimos a string
                            System.out.println(date1);
                            reserva.setMatricula(vistaR.txtMatricula.getText());
                            reserva.setIdSala(Integer.parseInt(vistaR.txtIdSala.getText()));
                            reserva.setFechaPrestamo(vistaR.txtFechaPrestamo.getText() + " " + vistaR.txtHoraPrestamo.getText());
                            reserva.setFechaUso(date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            reserva.setFechaDevolucion(date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            reserva.setObservacion(vistaR.txtObservacion.getText());
                            reserva.setStatus("Reservado");
                            //si no ha habido nigun error, se realizara el prestamo
                            if (conR.registrar(reserva)) {
                                reiniciar();//reinicia las tablas
                                fechaSistema();//actualiza la hora del sistema
                                cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados

                            } //De lo contrario se muestra el mensaje de error
                            else {
                                JOptionPane.showMessageDialog(null, "No se ha registrado las reservas\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                        + "La matrícula no existe\nEl ID del salón no existe");
                            }
                        }
                        fecha_Inicio.add(Calendar.DATE, 1);
                    }
                    JOptionPane.showMessageDialog(null, "Reservaciones registradas exitosamente");                   
                }       
            limpiar();//limpia los campos de texto
            System.out.println("Fechas Disponibles");           
        }
        else{
            System.out.println("Fechas Ocupadas");
            JOptionPane.showMessageDialog(null, "¡No se han registrado las reservas!\nUna o varias de las fechas ingresadas están ocupadas\nUna de las fechas ocupadas es: " + fechaOcupada + " "+ vistaR.spinnerHora1.getValue().toString()
            + "-" + vistaR.spinnerHora2.getValue().toString());
        }
        System.out.println("Los días trancurridos son: " + dias);
        return dias;  
    }
           
    //Con este metodo calculamos los dias transcurridos dadas dos fechas, una inicial y una final
    //solo cuenta los lunes
    public int registrarLunes(JDateChooser fechaIni, JDateChooser fechaFin) throws ParseException{
        int dias = 0;//variable para contar los dias transcurridos
        boolean verificar = true;//variable para indicar si de las fechas ingresadas alguna esta ocupada
        boolean indicadorFecha = true;//variable para saber si la fecha de inicio es mayor que la de devolucion
        String fechaOcupada = " ";//variable para asignar la primera fecha que esta ocupada       
        //variables temporales de fechas que el usuario ingresara  para verificar que las ingresadas sean correctas
        String fecha1 = ((JTextField) vistaR.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora1.getValue().toString();
        String fecha2 = ((JTextField) vistaR.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora2.getValue().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");             
        //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
        Date fechaIni1 = format.parse(fecha1);
        Date fechaFinal1 = format.parse(fecha2);
        //cadena de instruccion para verificar disponibilidad de la fecha ingresada
         String temp = "SELECT fechaUso, fechaDevolucion, sala_idSala, status FROM prestamossalon WHERE sala_idSala=? AND ((status = 'Reservado') OR (status = 'En Uso')) AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
        //compara si la fecha de uso es menor que la de devolucion
        try {
            Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
            Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos           
            if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {
                //iniciamos el ciclo para contar los dias
                while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                    //verificamos que la fecha no sea sabado ni domingo
                    if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY
                        && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                        dias++;//sumamos los dias  
                        Date date = fecha_Inicio.getTime();
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                        String date1 = format1.format(date);//variable para guardar la fecha en turno
                        System.out.println(date1);                       
                        try {
                            con = (Connection) cc.getConnection();
                            ps = con.prepareStatement(temp);
                            ps.setString(1, vistaR.txtIdSala.getText());
                            ps.setString(2, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(3, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(4, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(5, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(6, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(7, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            rs = ps.executeQuery();
                            //itera el resultado
                            if (rs.next()) {                               
                                System.out.println("Ocupado: " + date1);
                                fechaOcupada = date1;
                                verificar = false;
                                break;
                            } else {
                                System.out.println("Disponible: " + date1);
                            }
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    fecha_Inicio.add(Calendar.DATE, 1);
                }               
            }
        //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
        } catch(Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);         
        }
        if (verificar==true) {         
                Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
                Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos
                if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {                 
                    while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                        //verificamos que la fecha no sea sabado ni domingo
                        if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY
                        && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {                          
                            Date date = fecha_Inicio.getTime();
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                            String date1 = format1.format(date);//la convertimos a string
                            System.out.println(date1);
                            reserva.setMatricula(vistaR.txtMatricula.getText());
                            reserva.setIdSala(Integer.parseInt(vistaR.txtIdSala.getText()));
                            reserva.setFechaPrestamo(vistaR.txtFechaPrestamo.getText() + " " + vistaR.txtHoraPrestamo.getText());
                            reserva.setFechaUso(date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            reserva.setFechaDevolucion(date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            reserva.setObservacion(vistaR.txtObservacion.getText());
                            reserva.setStatus("Reservado");
                            int lunes = 1;
                            //si no ha habido nigun error, se realizara el prestamo
                            if (conR.registrar(reserva)) {
                                reiniciar();//reinicia las tablas
                                //fechaSistema();//actualiza la hora del sistema
                                cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados
                            } //De lo contrario se muestra el mensaje de error
                            else {
                                JOptionPane.showMessageDialog(null, "No se ha registrado la(s) reserva(s)\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                        + "La matrícula no existe\nEl ID del salón no existe");
                            }
                        }
                        fecha_Inicio.add(Calendar.DATE, 1);
                    }                   
                    JOptionPane.showMessageDialog(null, "Las reservaciones de los lunes han sido registradas exitosamente");                   
                }                           
            System.out.println("Fechas Disponibles");           
        }
        else{
            System.out.println("Fechas Ocupadas");
            JOptionPane.showMessageDialog(null, "¡No se han registrado las reservas!\nUna o varias de las fechas ingresadas están ocupadas\nUna de las fechas ocupadas es: " + fechaOcupada + " "+ vistaR.spinnerHora1.getValue().toString()
            + "-" + vistaR.spinnerHora2.getValue().toString());
        }
        System.out.println("Los dias trancurridos son: " + dias);
        return dias;     
    }
    
    //Con este metodo calculamos los dias transcurridos dadas dos fechas, una inicial y una final
    //solo cuenta los martes
    public int registrarMartes(JDateChooser fechaIni, JDateChooser fechaFin) throws ParseException{
        int dias = 0;//variable para contar los dias transcurridos
        boolean verificar = true;//variable para indicar si de las fechas ingresadas alguna esta ocupada
        boolean indicadorFecha = true;//variable para saber si la fecha de inicio es mayor que la de devolucion
        String fechaOcupada = " ";//variable para asignar la primera fecha que esta ocupada        
        //variables temporales de fechas que el usuario ingresara  para verificar que las ingresadas sean correctas
        String fecha1 = ((JTextField) vistaR.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora1.getValue().toString();
        String fecha2 = ((JTextField) vistaR.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora2.getValue().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");            
        //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
        Date fechaIni1 = format.parse(fecha1);
        Date fechaFinal1 = format.parse(fecha2);
        //cadena de instruccion para verificar disponibilidad de la fecha ingresada
         String temp = "SELECT fechaUso, fechaDevolucion, sala_idSala, status FROM prestamossalon WHERE sala_idSala=? AND ((status = 'Reservado') OR (status = 'En Uso')) AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
        //compara si la fecha de uso es menor que la de devolucion
        try {
            Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
            Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos           
            if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {
                //iniciamos el ciclo para contar los dias
                while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                    //verificamos que la fecha no sea sabado ni domingo
                    if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
                        && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                        dias++;//sumamos los dias  
                        Date date = fecha_Inicio.getTime();
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                        String date1 = format1.format(date);//variable para guardar la fecha en turno
                        System.out.println(date1);                        
                        try {
                            con = (Connection) cc.getConnection();
                            ps = con.prepareStatement(temp);
                            ps.setString(1, vistaR.txtIdSala.getText());
                            ps.setString(2, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(3, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(4, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(5, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(6, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(7, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            rs = ps.executeQuery();
                            //itera el resultado
                            if (rs.next()) {
                                //vistaR.labelMsg.setText("Las fechas ingresadas ya están ocupadas");
                                System.out.println("Ocupado: " + date1);
                                fechaOcupada = date1;
                                verificar = false;
                                break;
                            } else {
                                System.out.println("Disponible: " + date1);
                            }
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    fecha_Inicio.add(Calendar.DATE, 1);
                }               
            }
        //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
        } catch(Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }       
        if (verificar==true) {         
                Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
                Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos
                if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {                 
                    while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                        //verificamos que la fecha no sea sabado ni domingo
                        if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
                        && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {                          
                            Date date = fecha_Inicio.getTime();
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                            String date1 = format1.format(date);//la convertimos a string
                            System.out.println(date1);
                            reserva.setMatricula(vistaR.txtMatricula.getText());
                            reserva.setIdSala(Integer.parseInt(vistaR.txtIdSala.getText()));
                            reserva.setFechaPrestamo(vistaR.txtFechaPrestamo.getText() + " " + vistaR.txtHoraPrestamo.getText());
                            reserva.setFechaUso(date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            reserva.setFechaDevolucion(date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            reserva.setObservacion(vistaR.txtObservacion.getText());
                            reserva.setStatus("Reservado");
                            int martes = 1;
                            //si no ha habido nigun error, se realizara el prestamo
                            if (conR.registrar(reserva)) {
                                reiniciar();//reinicia las tablas                             
                                cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados
                            }//De lo contrario se muestra el mensaje de error
                            else {
                                JOptionPane.showMessageDialog(null, "No se ha registrado la(s) reserva(s)\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                        + "La matrícula no existe\nEl ID del salón no existe");
                            }
                        }
                        fecha_Inicio.add(Calendar.DATE, 1);
                    }                   
                    JOptionPane.showMessageDialog(null, "Las reservaciones de los martes han sido registradas exitosamente");                   
                }                
            System.out.println("Fechas Disponibles");           
        }
        else{
            System.out.println("Fechas Ocupadas");
            JOptionPane.showMessageDialog(null, "¡No se han registrado las reservas!\nUna o varias de las fechas ingresadas están ocupadas\nUna de las fechas ocupadas es: " + fechaOcupada + " "+ vistaR.spinnerHora1.getValue().toString()
            + "-" + vistaR.spinnerHora2.getValue().toString());
        }
        System.out.println("Los dias trancurridos son: " + dias);
        return dias;   
    }
    
    //Con este metodo calculamos los dias transcurridos dadas dos fechas, una inicial y una final
    //solo cuenta los miercoles
    public int registrarMiercoles(JDateChooser fechaIni, JDateChooser fechaFin) throws ParseException{
        int dias = 0;//variable para contar los dias transcurridos
        boolean verificar = true;//variable para indicar si de las fechas ingresadas alguna esta ocupada
        boolean indicadorFecha = true;//variable para saber si la fecha de inicio es mayor que la de devolucion
        String fechaOcupada = " ";//variable para asignar la primera fecha que esta ocupada        
        //variables temporales de fechas que el usuario ingresara  para verificar que las ingresadas sean correctas
        String fecha1 = ((JTextField) vistaR.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora1.getValue().toString();
        String fecha2 = ((JTextField) vistaR.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora2.getValue().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");            
        //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
        Date fechaIni1 = format.parse(fecha1);
        Date fechaFinal1 = format.parse(fecha2);
        //cadena de instruccion para verificar disponibilidad de la fecha ingresada
        String temp = "SELECT fechaUso, fechaDevolucion, sala_idSala, status FROM prestamossalon WHERE sala_idSala=? AND ((status = 'Reservado') OR (status = 'En Uso')) AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
        //compara si la fecha de uso es menor que la de devolucion
        try {
            Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
            Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos            
            if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {
                //iniciamos el ciclo para contar los dias
                while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                    //verificamos que la fecha no sea sabado ni domingo
                    if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
                        && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                        dias++;//sumamos los dias  
                        Date date = fecha_Inicio.getTime();
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                        String date1 = format1.format(date);//variable para guardar la fecha en turno
                        System.out.println(date1);                       
                        try {
                            con = (Connection) cc.getConnection();
                            ps = con.prepareStatement(temp);
                            ps.setString(1, vistaR.txtIdSala.getText());
                            ps.setString(2, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(3, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(4, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(5, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(6, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(7, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            rs = ps.executeQuery();
                            //itera el resultado
                            if (rs.next()) {
                                //vistaR.labelMsg.setText("Las fechas ingresadas ya están ocupadas");
                                System.out.println("Ocupado: " + date1);
                                fechaOcupada = date1;
                                verificar = false;
                                break;
                            } else {
                                System.out.println("Disponible: " + date1);
                            }
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    fecha_Inicio.add(Calendar.DATE, 1);
                }               
            }
        //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
        } catch(Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }
        if (verificar==true) {         
                Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
                Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos
                if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {                 
                    while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                        //verificamos que la fecha no sea sabado ni domingo
                        if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
                        && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {                         
                            Date date = fecha_Inicio.getTime();
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                            String date1 = format1.format(date);//la convertimos a string
                            System.out.println(date1);
                            reserva.setMatricula(vistaR.txtMatricula.getText());
                            reserva.setIdSala(Integer.parseInt(vistaR.txtIdSala.getText()));
                            reserva.setFechaPrestamo(vistaR.txtFechaPrestamo.getText() + " " + vistaR.txtHoraPrestamo.getText());
                            reserva.setFechaUso(date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            reserva.setFechaDevolucion(date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            reserva.setObservacion(vistaR.txtObservacion.getText());
                            reserva.setStatus("Reservado");
                            int miercoles = 1;
                            //si no ha habido nigun error, se realizara el prestamo
                            if (conR.registrar(reserva)) {
                                reiniciar();//reinicia las tablas                               
                                cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados
                            }//De lo contrario se muestra el mensaje de error
                            else {
                                JOptionPane.showMessageDialog(null, "No se ha registrado la(s) reserva(s)\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                        + "La matrícula no existe\nEl ID del salón no existe");
                            }
                        }
                        fecha_Inicio.add(Calendar.DATE, 1);
                    }                   
                    JOptionPane.showMessageDialog(null, "Las reservaciónes de los miércoles han sido registradas exitosamente");                   
                }                
            System.out.println("Fechas Disponibles");           
        }
        else{
            System.out.println("Fechas Ocupadas");
            JOptionPane.showMessageDialog(null, "¡No se han registrado las reservas!\nUna o varias de las fechas ingresadas están ocupadas\nUna de las fechas ocupadas es: " + fechaOcupada + " "+ vistaR.spinnerHora1.getValue().toString()
            + "-" + vistaR.spinnerHora2.getValue().toString());
        }
        System.out.println("Los dias trancurridos son: " + dias);
        return dias;     
    }
    
    //Con este metodo calculamos los dias transcurridos dadas dos fechas, una inicial y una final
    //solo cuenta los jueves
    public int registrarJueves(JDateChooser fechaIni, JDateChooser fechaFin) throws ParseException{
        int dias = 0;//variable para contar los dias transcurridos
        boolean verificar = true;//variable para indicar si de las fechas ingresadas alguna esta ocupada
        boolean indicadorFecha = true;//variable para saber si la fecha de inicio es mayor que la de devolucion
        String fechaOcupada = " ";//variable para asignar la primera fecha que esta ocupada       
        //variables temporales de fechas que el usuario ingresara  para verificar que las ingresadas sean correctas
        String fecha1 = ((JTextField) vistaR.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora1.getValue().toString();
        String fecha2 = ((JTextField) vistaR.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora2.getValue().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");             
        //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
        Date fechaIni1 = format.parse(fecha1);
        Date fechaFinal1 = format.parse(fecha2);
        //cadena de instruccion para verificar disponibilidad de la fecha ingresada
         String temp = "SELECT fechaUso, fechaDevolucion, sala_idSala, status FROM prestamossalon WHERE sala_idSala=? AND ((status = 'Reservado') OR (status = 'En Uso')) AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
        //compara si la fecha de uso es menor que la de devolucion
        try {
            Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
            Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos           
            if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {
                //iniciamos el ciclo para contar los dias
                while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                    //verificamos que la fecha no sea sabado ni domingo
                    if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
                        && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                        dias++;//sumamos los dias  
                        Date date = fecha_Inicio.getTime();
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                        String date1 = format1.format(date);//variable para guardar la fecha en turno
                        System.out.println(date1);                       
                        try {
                            con = (Connection) cc.getConnection();
                            ps = con.prepareStatement(temp);
                            ps.setString(1, vistaR.txtIdSala.getText());
                            ps.setString(2, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(3, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(4, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(5, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(6, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(7, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            rs = ps.executeQuery();
                            //itera el resultado
                            if (rs.next()) {                              
                                System.out.println("Ocupado: " + date1);
                                fechaOcupada = date1;
                                verificar = false;
                                break;
                            } else {
                                System.out.println("Disponible: " + date1);
                            }
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    fecha_Inicio.add(Calendar.DATE, 1);
                }               
            }
        //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
        } catch(Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }
        if (verificar==true) {         
                Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
                Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos
                if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {                 
                    while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                        //verificamos que la fecha no sea sabado ni domingo
                        if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
                        && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {                            
                            Date date = fecha_Inicio.getTime();
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                            String date1 = format1.format(date);//la convertimos a string
                            System.out.println(date1);
                            reserva.setMatricula(vistaR.txtMatricula.getText());
                            reserva.setIdSala(Integer.parseInt(vistaR.txtIdSala.getText()));
                            reserva.setFechaPrestamo(vistaR.txtFechaPrestamo.getText() + " " + vistaR.txtHoraPrestamo.getText());
                            reserva.setFechaUso(date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            reserva.setFechaDevolucion(date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            reserva.setObservacion(vistaR.txtObservacion.getText());
                            reserva.setStatus("Reservado");
                            int jueves = 1;
                            //si no ha habido nigun error, se realizara el prestamo
                            if (conR.registrar(reserva)) {
                                reiniciar();//reinicia las tablas                                
                                cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados
                            }//De lo contrario se muestra el mensaje de error
                            else {
                                JOptionPane.showMessageDialog(null, "No se ha registrado la(s) reserva(s)\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                        + "La matrícula no existe\nEl ID del salón no existe");
                            }
                        }
                        fecha_Inicio.add(Calendar.DATE, 1);
                    }                   
                    JOptionPane.showMessageDialog(null, "Las reservaciones de los jueves han sido registradas exitosamente");                   
                }                
            System.out.println("Fechas Disponibles");           
        }
        else{
            System.out.println("Fechas Ocupadas");
            JOptionPane.showMessageDialog(null, "¡No se han registrado las reservas!\nUna o varias de las fechas ingresadas están ocupadas\nUna de las fechas ocupadas es: " + fechaOcupada + " "+ vistaR.spinnerHora1.getValue().toString()
            + "-" + vistaR.spinnerHora2.getValue().toString());
        }
        System.out.println("Los dias trancurridos son: " + dias);
        return dias;   
    }
    
    //Con este metodo calculamos los dias transcurridos dadas dos fechas, una inicial y una final
    //solo cuenta los viernes
    public int registrarViernes(JDateChooser fechaIni, JDateChooser fechaFin) throws ParseException{
        int dias = 0;//variable para contar los dias transcurridos
        boolean verificar = true;//variable para indicar si de las fechas ingresadas alguna esta ocupada
        boolean indicadorFecha = true;//variable para saber si la fecha de inicio es mayor que la de devolucion
        String fechaOcupada = " ";//variable para asignar la primera fecha que esta ocupada       
        //variables temporales de fechas que el usuario ingresara  para verificar que las ingresadas sean correctas
        String fecha1 = ((JTextField) vistaR.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora1.getValue().toString();
        String fecha2 = ((JTextField) vistaR.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora2.getValue().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");            
        //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
        Date fechaIni1 = format.parse(fecha1);
        Date fechaFinal1 = format.parse(fecha2);
        //cadena de instruccion para verificar disponibilidad de la fecha ingresada
        String temp = "SELECT fechaUso, fechaDevolucion, sala_idSala, status FROM prestamossalon WHERE sala_idSala=? AND ((status = 'Reservado') OR (status = 'En Uso')) AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
        //compara si la fecha de uso es menor que la de devolucion
        try {
            Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
            Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos           
            if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {
                //iniciamos el ciclo para contar los dias
                while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                    //verificamos que la fecha no sea sabado ni domingo
                    if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
                        && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY) {
                        dias++;//sumamos los dias  
                        Date date = fecha_Inicio.getTime();
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                        String date1 = format1.format(date);//variable para guardar la fecha en turno
                        System.out.println(date1);                       
                        try {
                            con = (Connection) cc.getConnection();
                            ps = con.prepareStatement(temp);
                            ps.setString(1, vistaR.txtIdSala.getText());
                            ps.setString(2, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(3, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(4, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(5, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(6, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(7, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            rs = ps.executeQuery();
                            //itera el resultado
                            if (rs.next()) {                               
                                System.out.println("Ocupado: " + date1);
                                fechaOcupada = date1;
                                verificar = false;
                                break;
                            } else {
                                System.out.println("Disponible: " + date1);
                            }
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    fecha_Inicio.add(Calendar.DATE, 1);
                }               
            }
        //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
        } catch(Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }
        if (verificar==true) {         
                Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
                Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos
                if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {                 
                    while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                        //verificamos que la fecha no sea sabado ni domingo
                        if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
                        && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY) {
                            Date date = fecha_Inicio.getTime();
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                            String date1 = format1.format(date);//la convertimos a string
                            System.out.println(date1);
                            reserva.setMatricula(vistaR.txtMatricula.getText());
                            reserva.setIdSala(Integer.parseInt(vistaR.txtIdSala.getText()));
                            reserva.setFechaPrestamo(vistaR.txtFechaPrestamo.getText() + " " + vistaR.txtHoraPrestamo.getText());
                            reserva.setFechaUso(date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            reserva.setFechaDevolucion(date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            reserva.setObservacion(vistaR.txtObservacion.getText());
                            reserva.setStatus("Reservado");
                            int viernes = 1;
                            //si no ha habido nigun error, se realizara el prestamo
                            if (conR.registrar(reserva)) {
                                reiniciar();//reinicia las tablas
                                cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados
                            }//De lo contrario se muestra el mensaje de error
                            else {
                                JOptionPane.showMessageDialog(null, "No se ha registrado la(s) reserva(s)\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                        + "La matrícula no existe\nEl ID del salón no existe");
                            }
                        }
                        fecha_Inicio.add(Calendar.DATE, 1);
                    }                   
                    JOptionPane.showMessageDialog(null, "Las reservaciones de los viernes han sido registradas exitosamente");                   
                }                
            System.out.println("Fechas Disponibles");           
        }
        else{
            System.out.println("Fechas Ocupadas");
            JOptionPane.showMessageDialog(null, "¡No se han registrado las reservas!\nUna o varias de las fechas ingresadas están ocupadas\nUna de las fechas ocupadas es: " + fechaOcupada + " "+ vistaR.spinnerHora1.getValue().toString()
            + "-" + vistaR.spinnerHora2.getValue().toString());
        }
        System.out.println("Los dias trancurridos son: " + dias);
        return dias;    
    }
    
    //funcion que registra solamente el dia ingresado
    public int modificarDefecto(JDateChooser fechaIni, JDateChooser fechaFin) throws ParseException{
        int dias = 0;//variable para contar los dias transcurridos
        boolean verificar = true;//variable para indicar si de las fechas ingresadas alguna esta ocupada
        boolean indicadorFecha = true;//variable para saber si la fecha de inicio es mayor que la de devolucion
        String fechaOcupada = " ";//variable para asignar la primera fecha que esta ocupada       
        //variables temporales de fechas que el usuario ingresara  para verificar que las ingresadas sean correctas
        String fecha1 = ((JTextField) vistaR.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora1.getValue().toString();
        String fecha2 = ((JTextField) vistaR.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaR.spinnerHora2.getValue().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");           
        //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
        Date fechaIni1 = format.parse(fecha1);
        Date fechaFinal1 = format.parse(fecha2);
        //cadena de instruccion para verificar disponibilidad de la fecha ingresada
        String temp = "SELECT fechaUso, fechaDevolucion, sala_idSala, status FROM prestamossalon WHERE sala_idSala=? AND ((status = 'Reservado') OR (status = 'En Uso')) AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
        //compara si la fecha de uso es menor que la de devolucion
        if (fechaIni1.before(fechaFinal1)) {
            Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
            Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos           
            if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {
                //iniciamos el ciclo para contar los dias
                while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                    //verificamos que la fecha no sea sabado ni domingo
                    if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                        dias++;//sumamos los dias  
                        Date date = fecha_Inicio.getTime();
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                        String date1 = format1.format(date);//variable para guardar la fecha en turno
                        System.out.println(date1);                     
                        try {
                            con = (Connection) cc.getConnection();
                            ps = con.prepareStatement(temp);
                            ps.setString(1, vistaR.txtIdSala.getText());
                            ps.setString(2, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(3, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(4, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(5, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            ps.setString(6, date1 + " " + vistaR.spinnerHora1.getValue().toString());
                            ps.setString(7, date1 + " " + vistaR.spinnerHora2.getValue().toString());
                            rs = ps.executeQuery();
                            //itera el resultado
                            if (rs.next()) {
                                System.out.println("Ocupado: " + date1);
                                fechaOcupada = date1;
                                verificar = false;
                                break;
                            } else {
                                System.out.println("Disponible: " + date1);
                            }
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    fecha_Inicio.add(Calendar.DATE, 1);
                }               
            }
        //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
        } else {
            vistaR.labelMsg.setText("La fecha inicial no debe de ser mayor que la de devolución");
        }
        System.out.println("Los dias son: " + dias);
        if (dias==1) {
            //verifica que el usuario solo haya ingresado un dia y no varios
            if (verificar==true) {
                //compara si la fecha de uso es menor que la de devolucion
                if (fechaIni1.before(fechaFinal1)) {
                    Calendar fecha_Inicio = fechaIni.getCalendar();//variable de fecha inicial y la obtenemos
                    Calendar fecha_Final = fechaFin.getCalendar();//variable de fecha final y la obtenemos
                    if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {
                        while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                            //verificamos que la fecha no sea sabado ni domingo
                            if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {                              
                                //modificamos es estatus de la reserva
                                //modificarStatusReservaCancelado();
                                Date date = fecha_Inicio.getTime();
                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");//le damos formato a la fecha
                                String date1 = format1.format(date);//la convertimos a string
                                System.out.println(date1);
                                reserva.setFolio(Integer.parseInt(vistaR.txtFolio.getText()));
                                reserva.setMatricula(vistaR.txtMatricula.getText());
                                reserva.setIdSala(Integer.parseInt(vistaR.txtIdSala.getText()));                                
                                reserva.setFechaUso(date1 + " " + vistaR.spinnerHora1.getValue().toString());
                                reserva.setFechaDevolucion(date1 + " " + vistaR.spinnerHora2.getValue().toString());
                                reserva.setObservacion(vistaR.txtObservacion.getText());
                                reserva.setStatus("Reservado");
                                //si no ha habido nigun error, se realizara el prestamo
                                if (conR.modificar(reserva)) {                                    
                                    reiniciar();//reinicia las tablas
                                    fechaSistema();//actualiza la hora del sistema
                                    cargarTablaReservados(vistaR.tableReservas);//actualiza los datos de la tabla de prestados
                                } //De lo contrario se muestra el mensaje de error
                                else {
                                    JOptionPane.showMessageDialog(null, "No se ha modificado las reservas\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                            + "La matrícula no existe\nEl ID del salón no existe");
                                }
                            }
                            fecha_Inicio.add(Calendar.DATE, 1);
                        }
                        limpiar();//limpia los campos de texto
                        JOptionPane.showMessageDialog(null, "Reservación modificada exitosamente");
                    }
                    //si la fecha de uso es mayor que la de devolucion, mandara un msj al usuario y la reservacion no se registrara
                } else {
                    vistaR.labelMsg.setText("La fecha inicial no debe de ser mayor que la de devolución");
                }
            }
            else{
                System.out.println("Fechas Ocupadas");
                vistaR.labelMsg.setText("La fecha que ha ingresado, esta ocupada: " + fechaOcupada + " "+ vistaR.spinnerHora1.getValue().toString()
            + "-" + vistaR.spinnerHora2.getValue().toString());
            }            
            System.out.println("Fechas Disponibles");         
        }
        else{            
            vistaR.labelMsg.setText("Por favor, seleccione solamente un dia");            
        }
        System.out.println("Los dias trancurridos son: " + dias);
        return dias;  
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        vistaR.btnModificar.setEnabled(true);
        vistaR.btnCancelar.setEnabled(true);
        vistaR.btnEliminar.setEnabled(true);
        vistaR.btnGuardar.setEnabled(false);
        ps = null;
        rs = null;
        try {
            //creamos el formato en el que deseamos mostrar la fecha
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date fechaIni = null;//creamos una variable tipo Date y la ponemos NULL
            Date fechaFin = null;//creamos una variable tipo Date y la ponemos NULL
            Conexion ObjCon = new Conexion();
            com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) ObjCon.getConnection();
            int fila = vistaR.tableReservas.getSelectedRow();//obtiene el numero de filas de la tabla           
            String id = vistaR.tableReservas.getValueAt(fila, 0).toString();//obtiene el valor 0 de la fila de la tabla                       
            //realiza la consulta a la base de datos
            ps = conn.prepareStatement("SELECT folio, alumno_matricula, sala_idSala, alumno.nombre, alumno.apellidoP, alumno.apellidoM, sala.nomSala, DATE_FORMAT(prestamossalon.fechaUso, '%Y/%m/%d') AS fUso, DATE_FORMAT(prestamossalon.fechaDevolucion, '%Y/%m/%d') AS fDev,"
                    + "DATE_FORMAT(prestamossalon.fechaUso, '%H:%i:%s') AS hUso, DATE_FORMAT(prestamossalon.fechaDevolucion, '%H:%i:%s') AS hDev, observacion FROM prestamossalon, alumno, sala "
                    + "WHERE folio=? AND alumno.matricula = prestamossalon.alumno_matricula AND sala.idSala = prestamossalon.sala_idSala");
            ps.setString(1, id);
            rs = ps.executeQuery();
            //itera los resultados que coincidieron con la consulta
            while (rs.next()) {
                //asigna los valores que coincidieron
                vistaR.txtFolio.setText(rs.getString("folio"));
                vistaR.txtMatricula.setText(rs.getString("alumno_matricula"));
                vistaR.txtIdSala.setText(rs.getString("sala_idSala"));
                vistaR.txtNombre.setText(rs.getString("alumno.nombre") + " " + rs.getString("alumno.apellidoP") + " " + rs.getString("alumno.apellidoM"));
                vistaR.txtDescripcion.setText(rs.getString("sala.nomSala"));
                String fecha1 = rs.getString("fUso");//obtenemos la fecha 
                String fecha2 = rs.getString("fDev");//obtenemos la fecha 
                System.out.println(fecha1 + " " + fecha2);
                fechaIni = format.parse(fecha1);//Parseamos de String a Date usando el formato
                fechaFin = format.parse(fecha2);//Parseamos de String a Date usando el formato
                vistaR.jDateFechaUso.setDate(fechaIni);//Seteamos o mostramos la fecha en el JDateChooser
                vistaR.jDCFechaDevolucion.setDate(fechaFin);//Seteamos o mostramos la fecha en el JDateChooser
                vistaR.spinnerHora1.setValue(rs.getString("hUso"));
                vistaR.spinnerHora2.setValue(rs.getString("hDev"));
                vistaR.txtObservacion.setText(rs.getString("observacion"));
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //compara si fue la caja de texto de la matricula
        if (e.getSource() == vistaR.txtMatricula) {
            Connection con = null;
            fechaSistema();//reinicia la hora del sistema cada vez que se haga esta accion
            //string de la consulta
            String temp = "SELECT * FROM alumno, carrera WHERE alumno.matricula LIKE ? AND alumno.carrera_idCarrera=idCarrera";
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, vistaR.txtMatricula.getText());
                rs = ps.executeQuery();
                while (rs.next()) {
                    vistaR.txtNombre.setText(rs.getString("nombre") + " " + rs.getString("apellidoP") + " " + rs.getString("apellidoM"));
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
            }
        }
        //compara si fue la caja de texto del nombre
        if (e.getSource() == vistaR.txtNombre) {

            Connection con = null;
            fechaSistema();//reinicia la hora del sistema cada vez que se haga esta accion          
            String temp = "SELECT * FROM alumno, carrera WHERE alumno.nombre LIKE ? AND alumno.carrera_idCarrera=idCarrera";
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                //ps.setString(1,vistaPrestamo.txtNombre.getText());
                ps.setString(1, vistaR.txtNombre.getText());
                rs = ps.executeQuery();

                while (rs.next()) {
                    vistaR.txtMatricula.setText(rs.getString("matricula"));
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
            }
        }
        //compara si fue la caja de texto del nombre
        if (e.getSource() == vistaR.txtIdSala) {
            Connection con = null;
            fechaSistema();//reinicia la hora del sistema cada vez que se haga esta accion
            //string de la consulta
            String temp = "SELECT * FROM sala WHERE sala.idSala LIKE ?";

            try {

                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, "%" + vistaR.txtIdSala.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    vistaR.txtDescripcion.setText(rs.getString("nomSala"));

                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
   
    @Override
    public void mousePressed(MouseEvent e) {
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    
    
}
