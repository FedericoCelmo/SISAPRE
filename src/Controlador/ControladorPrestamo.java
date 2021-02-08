/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasEquipo;
import Modelo.ConsultasPrestamo;
import Modelo.Equipo;
import Modelo.Prestamo;
import Vista.VistaPrincipalAdmin;
import com.mxrck.autocompleter.TextAutoCompleter;
import com.mysql.jdbc.Connection;
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
//import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Federico
 */
public class ControladorPrestamo extends Conexion implements ActionListener, MouseListener, KeyListener {
    
    private Prestamo prestamo;
    private ConsultasPrestamo conP;
    private VistaPrincipalAdmin vistaPrestamo;
    private Equipo equipo;
    private ConsultasEquipo conE;
    ControladorReserva ctrl;
    Conexion conn = new Conexion();
    Connection con = (Connection) conn.getConnection();  
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();// variable del modelo de las tablas
    
    //constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorPrestamo(Prestamo prestamo, ConsultasPrestamo conP, VistaPrincipalAdmin vistaPrestamo, Equipo equipo,ConsultasEquipo conE) {
        this.prestamo = prestamo;
        this.conP = conP;
        this.vistaPrestamo = vistaPrestamo;
        this.equipo = equipo;
        this.conE = conE;
        //se le añaden todos los componentes de la vista
        //botones
        //tablas que implementaran el mouseClicked
        //cajas de texto
        this.vistaPrestamo.btnGuardar.addActionListener(this);
        this.vistaPrestamo.btnLimpiar.addActionListener(this);
        this.vistaPrestamo.btnModificar.addActionListener(this);
        this.vistaPrestamo.btnEliminar.addActionListener(this);
        this.vistaPrestamo.btnActualizar.addActionListener(this);
        this.vistaPrestamo.btnDevolver.addActionListener(this);
        this.vistaPrestamo.btnCancelar.addActionListener(this);
        this.vistaPrestamo.tablePrestados.addMouseListener(this);    
        this.vistaPrestamo.txtMatricula.addKeyListener(this);
        this.vistaPrestamo.txtIdEquipo.addKeyListener(this);
        this.vistaPrestamo.txtNombre.addKeyListener(this);
       
    }
    
    //aqui se cargan todos los componentes que se iniciarian cuando se abra la vista
    public void iniciar(){
        fechaSistema();//carga la hora del sistema    
        vistaPrestamo.setLocationRelativeTo(null);
        vistaPrestamo.cbxStatus.removeAllItems();//remueve los valores por defecto del combobox del status    
        vistaPrestamo.cbxStatus.addItem("Prestado");//asigna los valores al combobox  
        vistaPrestamo.cbxStatus.addItem("Reservado");//asigna los valores al combobox  
        //variable que no necesitan que estar visibles ya que solo se usan internamente por el 
        vistaPrestamo.txtFechaPrestamo.setVisible(false);
        vistaPrestamo.txtHoraPrestamo.setVisible(false);
        vistaPrestamo.txtHora24.setVisible(false);//esta variable se usa para comparar la hora del sistema en formato de 24hrs
        vistaPrestamo.txtIdEquipoTemp.setVisible(false);
        verificarFechaDeUso();
        //carga el contenido de las tablas
        cargarTablaPrestados(vistaPrestamo.tablePrestados);
        cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);
        cargarTablaAtrasados(vistaPrestamo.tableAtrasados);        
        autocompletarMatricula();  
        autocompletarNombre();     
        vistaPrestamo.btnModificar.setEnabled(false);
        vistaPrestamo.btnDevolver.setEnabled(false);
        vistaPrestamo.btnCancelar.setEnabled(false);
        actualizacionDeEstatusAutomatico();
        actualizacionDeTablasAutomatico();
        
        //ctrl.actualizacionDeEstatusAutomatico();
    }
    
    //metodo que sirve para cargar los datos de la tabla de prestados
    public void cargarTablaPrestados(JTable tablePrestados) {
        ps = null;
        rs = null;
        //Conexion conn = new Conexion();
        tablePrestados.setModel(modelo);
        try {
            //DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"Folio", "Matrícula", "IdEquipo", "Fecha Uso", "Fecha Devolución", "Estatus"});//asigna los titulos de las columnas
            try {
                //Connection con = (Connection) conn.getConnection();
                //se realiza la consulta a la BD
                //ps = con.prepareStatement("SELECT * FROM prestamosalum, equipo WHERE equipo.idEquipo = prestamosalum.equipo_idEquipo AND equipo.tipo='Externo' AND (prestamosalum.status='Prestado' OR prestamosalum.status='Reservado')");
                ps = con.prepareStatement("SELECT folio, alumno_matricula, equipo_idEquipo, DATE_FORMAT(fechaUso, '%d/%b/%y-%H:%i') AS fUso, DATE_FORMAT(fechaDevolucion, '%d/%b/%y-%H:%i') AS fDev, prestamosalum.status, tipo FROM prestamosalum, equipo "
                        + "WHERE equipo.idEquipo = prestamosalum.equipo_idEquipo AND equipo.tipo='Externo' AND (prestamosalum.status='Prestado' OR prestamosalum.status='Reservado')");
                rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        //asigna los valores obtenidos a la tabla
                        modelo.addRow(new Object[]{rs.getString("folio"), rs.getString("alumno_matricula"), rs.getString("equipo_idEquipo"), rs.getString("fUso"), rs.getString("fDev"), rs.getString("prestamosalum.status")});
                    }
                    vistaPrestamo.tablePrestados.setModel(modelo);
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

    //metodo que sirve para cargar los datos de la tabla de prestamos atrasados
    public void cargarTablaAtrasados(JTable tableAtrasados) {
        ps = null;
        rs = null;
        tableAtrasados.setModel(modelo);
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"Folio", "Matrícula", "IdEquipo", "Fecha Devolución"});//asigna los titulos de las columnas
            try {
                ps = con.prepareStatement("SELECT folio, alumno_matricula, equipo_idEquipo, DATE_FORMAT(fechaDevolucion, '%d/%b/%y-%H:%i') AS fDev, tipo FROM prestamosalum, equipo "
                        + "WHERE equipo.idEquipo = prestamosalum.equipo_idEquipo AND equipo.tipo='Externo' AND fechaDevolucion <= NOW() AND prestamosalum.status = 'Prestado'");
                rs = ps.executeQuery();

                try {
                    while (rs.next()) {
                        //asigna los valores obtenidos a la tabla
                        modelo.addRow(new Object[]{rs.getString("folio"), rs.getString("alumno_matricula"), rs.getString("equipo_idEquipo"),
                            rs.getString("fDev")});
                    }
                    vistaPrestamo.tableAtrasados.setModel(modelo);
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
    
    //metodo que sirve para cargar los datos de la tabla de equipos disponibles
    public void cargarTablaEquiposDisponibles(JTable tableEquipos){
        ps = null;
        rs = null;
        //Conexion conn = new Conexion();
        tableEquipos.setModel(modelo);
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"ID", "Descripción", "Número de Inventario", "Estatus"});//asigna los titulos de las columnas
            try {
                //Connection con = (Connection) conn.getConnection();
                //se realiza la consulta a la BD
                ps = con.prepareStatement("SELECT * FROM equipo WHERE tipo='Externo' AND status LIKE 'Disponible%'");
                rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        //asigna los valores obtenidos a la tabla
                        modelo.addRow(new Object[]{rs.getString("idEquipo"), rs.getString("descripcion"), rs.getString("numInv"), rs.getString("status")});
                    }
                    vistaPrestamo.tableEquiposDisponibles.setModel(modelo);
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
       
    //reinicia los datos de las tablas
    public void reiniciar() {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
    }
    
    //metodo que obtiene la hora del sistema y se lo asigna a las caja de texto para guardarlo a la BD
    public void fechaSistema(){
        Calendar c;
        c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR), m = 1+ (c.get(Calendar.MONTH)), d = c.get(Calendar.DATE);
        vistaPrestamo.txtFechaPrestamo.setText(y+"/"+m+"/"+d);       
        int h = c.get(Calendar.HOUR_OF_DAY), mm = c.get(Calendar.MINUTE), s = c.get(Calendar.SECOND);
        int HH = c.get(Calendar.HOUR_OF_DAY), MM = c.get(Calendar.MINUTE), SS = c.get(Calendar.SECOND);
        vistaPrestamo.txtHoraPrestamo.setText(h+":"+mm+":"+s);
        vistaPrestamo.txtHora24.setText(HH+":"+MM+":"+SS);
        vistaPrestamo.jDateFechaUso.setCalendar(c);
        vistaPrestamo.jDCFechaDevolucion.setCalendar(c);
    }
       
    @Override//En este metodo se realizan las acciones de las consultas
    public void actionPerformed(ActionEvent e){
        
        //compara si se ha seleccionado el boton guardar
        if (e.getSource() == vistaPrestamo.btnGuardar) {             
            //compara si estan vacios los campos de texto
            if (!vistaPrestamo.txtMatricula.getText().equals("") && !vistaPrestamo.txtIdEquipo.equals("")) {                                        
                Connection con = null;
                String temp = "SELECT * FROM equipo WHERE idEquipo=?";//consulta para verificar el id del equipo
                //variables temporales de fechas para verificar que las ingresadas sean correctas
                String fecha1 = ((JTextField)vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText()+" "+vistaPrestamo.spinnerHora1.getValue().toString();
                String fecha2 = ((JTextField)vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText()+" "+vistaPrestamo.spinnerHora2.getValue().toString();
                SimpleDateFormat format = new SimpleDateFormat ("yyyy/mm/dd HH:mm:ss");
                try{
                    //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
                    Date fechaIni = format.parse(fecha1);
                    Date fechaFinal = format.parse(fecha2);
                    //compara si la fecha de uso es menor que la de devolucion
                    if (fechaIni.before(fechaFinal)) {  
                        //variable local para obtener el string del combobox
                        String statusTemp = vistaPrestamo.cbxStatus.getSelectedItem().toString();
                        if (statusTemp.equals("Prestado")) {
                            System.out.println(statusTemp);
                            try {
                                con = (Connection) cc.getConnection();
                                ps = con.prepareStatement(temp);
                                ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                rs = ps.executeQuery();
                                //itera el resultado y verifica el status del equipo
                                if (rs.next()) {
                                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                                    String tem = rs.getString("status");//variable local del status del equipo                   
                                    //compara si el equipo se encuentra disponible con la variable local
                                    if (tem.equals("Disponible")) {
                                        System.out.println(tem);
                                        String temp1 = "SELECT * FROM prestamosalum WHERE equipo_idEquipo=? AND status = 'Reservado'";//consulta para verificar si el equipo esta reservado
                                        String statusEquipo;
                                        try {
                                            ps = con.prepareStatement(temp1);
                                            ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                            rs = ps.executeQuery();
                                            //si existen registros de que el equipo esta reservado, se consultara su disponibilidad de fechas
                                            if (rs.next()) {
                                               
                                                String tem1 = rs.getString("status");//variable local del status del equipo 
                                                //consulta para verificar disponibilidad de fechas del equipo
                                                String temp2 = "SELECT fechaUso, fechaDevolucion, equipo_idEquipo, status FROM prestamosalum WHERE equipo_idEquipo=? AND status = 'Reservado' AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                                                + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
                                                try {
                                                    ps = con.prepareStatement(temp2);
                                                    ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                                    ps.setString(2, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                    ps.setString(3, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                    ps.setString(4, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                    ps.setString(5, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                    ps.setString(6, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                    ps.setString(7, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                    rs = ps.executeQuery();
                                                    //si existen registros de esas fechas, no se registra el equipo
                                                    if (rs.next()) {
                                                        vistaPrestamo.labelMsg.setText("El equipo esta reservado en esas fechas");  
                                                    }
                                                    //si no hay registros de reservaciones en esas fechas se registra el prestamo del equipo
                                                    else{
                                                        prestamo.setMatricula(vistaPrestamo.txtMatricula.getText());
                                                        prestamo.setIdequipo(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
                                                        prestamo.setFechaPrestamo(vistaPrestamo.txtFechaPrestamo.getText() + " " + vistaPrestamo.txtHoraPrestamo.getText());
                                                        prestamo.setHoraPrestamo(vistaPrestamo.txtHoraPrestamo.getText());
                                                        prestamo.setFechaUso(((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                        prestamo.setFechaDevolucion(((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                        prestamo.setHoraUso(vistaPrestamo.spinnerHora1.getValue().toString());
                                                        prestamo.setHoraFinUso(vistaPrestamo.spinnerHora2.getValue().toString());
                                                        prestamo.setStatus(vistaPrestamo.cbxStatus.getSelectedItem().toString());
                                                        //si no ha habido nigun error, se realizara el prestamo
                                                        if (conP.registrar(prestamo)) {
                                                            JOptionPane.showMessageDialog(null, "Préstamo registrado exitosamente");
                                                            modificarStatusEquipoNoDisponible();//modifica el status del equipo
                                                            limpiar();//limpia los campos de texto
                                                            reiniciar();//reinicia las tablas
                                                            fechaSistema();//actualiza la hora del sistema
                                                            cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados
                                                            cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                                                            cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                                                        } //De lo contrario se muestra el mensaje de error
                                                        else {
                                                            JOptionPane.showMessageDialog(null, "No se ha realizado el préstamo\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                                                    + "La matrícula no existe\nEl ID del equipo no existe");
                                                            //limpiar();//limpia las cajas de los textos
                                                        }
                                                    }
                                                } catch (Exception exc) {
                                                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                                }
                                            }
                                            //si el equipo no esta reservado hara el registro del prestamo
                                            else{                                              
                                                prestamo.setMatricula(vistaPrestamo.txtMatricula.getText());
                                                prestamo.setIdequipo(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
                                                prestamo.setFechaPrestamo(vistaPrestamo.txtFechaPrestamo.getText() + " " + vistaPrestamo.txtHoraPrestamo.getText());
                                                prestamo.setHoraPrestamo(vistaPrestamo.txtHoraPrestamo.getText());
                                                prestamo.setFechaUso(((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                prestamo.setFechaDevolucion(((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                prestamo.setHoraUso(vistaPrestamo.spinnerHora1.getValue().toString());
                                                prestamo.setHoraFinUso(vistaPrestamo.spinnerHora2.getValue().toString());
                                                prestamo.setStatus(vistaPrestamo.cbxStatus.getSelectedItem().toString());
                                                //si no ha habido nigun error, se realizara el prestamo
                                                if (conP.registrar(prestamo)) {
                                                    JOptionPane.showMessageDialog(null, "Préstamo registrado exitosamente");                                            
                                                    modificarStatusEquipoNoDisponible();//modifica el status del equipo
                                                    limpiar();//limpia los campos de texto
                                                    reiniciar();//reinicia las tablas
                                                    fechaSistema();//actualiza la hora del sistema
                                                    cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados
                                                    cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                                                    cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                                                } //De lo contrario se muestra el mensaje de error
                                                else {
                                                    JOptionPane.showMessageDialog(null, "No se ha realizado el préstamo\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                                            + "La matrícula no existe\nEl ID del equipo no existe");
                                                    //limpiar();//limpia las cajas de los textos
                                                }                           
                                            }
                                            
                                        } catch (Exception exc) {
                                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    else{
                                        vistaPrestamo.labelMsg.setText("Equipo no disponible, seleccione otro");               
                                    }
                                }                               
                            } catch (Exception exc) {
                                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (statusTemp.equals("Reservado")) {
                            System.out.println(statusTemp);
                            try {
                                con = (Connection) cc.getConnection();
                                ps = con.prepareStatement(temp);
                                ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                rs = ps.executeQuery();
                                //itera el resultado y verifica el status del equipo
                                if (rs.next()) {
                                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                                    String tem = rs.getString("status");//variable local del status del equipo                   
                                    //compara si el equipo se encuentra disponible con la variable local
                                    if (tem.equals("Disponible")) {
                                        System.out.println(tem);
                                        String temp1 = "SELECT * FROM prestamosalum WHERE equipo_idEquipo=? AND status = 'Reservado'";//consulta para verificar si el equipo esta reservado
                                        String statusEquipo;
                                        try {
                                            ps = con.prepareStatement(temp1);
                                            ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                            rs = ps.executeQuery();
                                            //si existen registros de que el equipo esta reservado, se consultara su disponibilidad de fechas
                                            if (rs.next()) {
                                               
                                                String tem1 = rs.getString("status");//variable local del status del equipo 
                                                //consulta para verificar disponibilidad de fechas del equipo
                                                String temp2 = "SELECT fechaUso, fechaDevolucion, equipo_idEquipo, status FROM prestamosalum WHERE equipo_idEquipo=? AND status = 'Reservado' AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                                                + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
                                                try {
                                                    ps = con.prepareStatement(temp2);
                                                    ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                                    ps.setString(2, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                    ps.setString(3, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                    ps.setString(4, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                    ps.setString(5, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                    ps.setString(6, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                    ps.setString(7, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                    rs = ps.executeQuery();
                                                    //si existen registros de esas fechas, no se registra el equipo
                                                    if (rs.next()) {
                                                        vistaPrestamo.labelMsg.setText("El equipo esta reservado en esas fechas");  
                                                    }
                                                    //si no hay registros de reservaciones en esas fechas se registra el prestamo del equipo
                                                    else{
                                                        prestamo.setMatricula(vistaPrestamo.txtMatricula.getText());
                                                        prestamo.setIdequipo(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
                                                        prestamo.setFechaPrestamo(vistaPrestamo.txtFechaPrestamo.getText() + " " + vistaPrestamo.txtHoraPrestamo.getText());
                                                        prestamo.setHoraPrestamo(vistaPrestamo.txtHoraPrestamo.getText());
                                                        prestamo.setFechaUso(((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                        prestamo.setFechaDevolucion(((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                        prestamo.setHoraUso(vistaPrestamo.spinnerHora1.getValue().toString());
                                                        prestamo.setHoraFinUso(vistaPrestamo.spinnerHora2.getValue().toString());
                                                        prestamo.setStatus(vistaPrestamo.cbxStatus.getSelectedItem().toString());
                                                        //si no ha habido nigun error, se realizara el prestamo
                                                        if (conP.registrar(prestamo)) {
                                                            JOptionPane.showMessageDialog(null, "Equipo reservado exitosamente");
                                                            //modificarStatusEquipoNoDisponible();//modifica el status del equipo
                                                            limpiar();//limpia los campos de texto
                                                            reiniciar();//reinicia las tablas
                                                            fechaSistema();//actualiza la hora del sistema
                                                            cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados
                                                            cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                                                            cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                                                        } //De lo contrario se muestra el mensaje de error
                                                        else {
                                                            JOptionPane.showMessageDialog(null, "No se ha realizado el préstamo\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                                                    + "La matrícula no existe\nEl ID del equipo no existe");
                                                            //limpiar();//limpia las cajas de los textos
                                                        }
                                                    }
                                                } catch (Exception exc) {
                                                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                                }
                                            }
                                            //si el equipo no esta reservado hara el registro del prestamo
                                            else{                                              
                                                prestamo.setMatricula(vistaPrestamo.txtMatricula.getText());
                                                prestamo.setIdequipo(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
                                                prestamo.setFechaPrestamo(vistaPrestamo.txtFechaPrestamo.getText() + " " + vistaPrestamo.txtHoraPrestamo.getText());
                                                prestamo.setHoraPrestamo(vistaPrestamo.txtHoraPrestamo.getText());
                                                prestamo.setFechaUso(((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                prestamo.setFechaDevolucion(((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                prestamo.setHoraUso(vistaPrestamo.spinnerHora1.getValue().toString());
                                                prestamo.setHoraFinUso(vistaPrestamo.spinnerHora2.getValue().toString());
                                                prestamo.setStatus(vistaPrestamo.cbxStatus.getSelectedItem().toString());
                                                //si no ha habido nigun error, se realizara el prestamo
                                                if (conP.registrar(prestamo)) {
                                                    JOptionPane.showMessageDialog(null, "Equipo reservado exitosamente");
                                                    //modificarStatusEquipoNoDisponible();//modifica el status del equipo
                                                    limpiar();//limpia los campos de texto
                                                    reiniciar();//reinicia las tablas
                                                    fechaSistema();//actualiza la hora del sistema
                                                    cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados
                                                    cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                                                    cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                                                } //De lo contrario se muestra el mensaje de error
                                                else {
                                                    JOptionPane.showMessageDialog(null, "No se ha realizado el préstamo\nLas razones pueden ser:\nLos campos estan vacíos\n"
                                                            + "La matrícula no existe\nEl ID del equipo no existe");
                                                    //limpiar();//limpia las cajas de los textos
                                                }                           
                                            }                                           
                                        } catch (Exception exc) {
                                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    else{
                                        vistaPrestamo.labelMsg.setText("Equipo no disponible, seleccione otro");               
                                    }
                                }                               
                            } catch (Exception exc) {
                                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                            }                           
                        }
                    }    
                    else{
                        System.out.println("Las fecha de uso no debe de ser mayor que la de devolución");
                        vistaPrestamo.labelMsg.setText("La fecha y hora de uso no deben de ser mayor que la fecha y hora de devolución");   
                    }
                }
                catch(ParseException ex){
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + ex.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                }
            } //mandara un mensaje al usuario si los campos estan vacios
            else {
                vistaPrestamo.labelMsg.setText("No se aceptan campos vacíos");
            }
        }      
        //aqui se compara si se presiono el boton modificar
        if (e.getSource() == vistaPrestamo.btnModificar) {  
            //primero compara si estan vacios los campos de texto            
            if (!vistaPrestamo.txtFolio.getText().equals("")) {    
                //variables temporales de fechas para verificar que las ingresadas sean correctas
                String fecha1 = ((JTextField)vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText()+" "+vistaPrestamo.spinnerHora1.getValue().toString();
                String fecha2 = ((JTextField)vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText()+" "+vistaPrestamo.spinnerHora2.getValue().toString();
                SimpleDateFormat format = new SimpleDateFormat ("yyyy/mm/dd HH:mm:ss");  
                try{
                    //se le dan formato a las variables temporales de las fechas para que puedan ser comparadas
                    Date fechaIni = format.parse(fecha1);
                    Date fechaFinal = format.parse(fecha2);
                    //compara si la fecha de uso es menor que la de devolucion
                    if (fechaIni.before(fechaFinal)) {                                               
                        //variable local para obtener el string del combobox
                        String statusTemp = vistaPrestamo.cbxStatus.getSelectedItem().toString();
                        if (statusTemp.equals("Prestado")) {
                            //compara si es el mismo id seleccionado del equipo 
                            //si es igual, modificara el status del equipo a "Disponible" para que se pueda modificar
                            if (vistaPrestamo.txtIdEquipo.getText().equals(vistaPrestamo.txtIdEquipoTemp.getText())) {
                                modificarStatusEquipoDisponible();//actualiza el status del equipo
                                Connection con = null;
                                String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
                                try {
                                    con = (Connection) cc.getConnection();
                                    ps = con.prepareStatement(temp);
                                    ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                    rs = ps.executeQuery();
                                    //itera el resultado
                                    if (rs.next()) {
                                        //si el id es correcto, consulta su status y se lo asigna a una variable local
                                        String tem = rs.getString("status");//variable local del status del equipo                   
                                        //compara si el equipo se encuentra disponible con la variable local
                                        if (tem.equals("Disponible")) {                                       
                                            String temp1 = "SELECT * FROM prestamosalum WHERE equipo_idEquipo=? AND status = 'Reservado'";//consulta para verificar si el equipo esta reservado
                                            try {
                                                ps = con.prepareStatement(temp1);
                                                ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                                rs = ps.executeQuery();
                                                //si existen registros de que el equipo esta reservado, se consultara su disponibilidad de fechas
                                                if (rs.next()) {
                                                    String tem1 = rs.getString("status");//variable local del status del equipo 
                                                    //consulta para verificar disponibilidad de fechas del equipo
                                                    String temp2 = "SELECT fechaUso, fechaDevolucion, equipo_idEquipo, status FROM prestamosalum WHERE equipo_idEquipo=? AND status = 'Reservado' AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                                                            + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
                                                    try {
                                                        ps = con.prepareStatement(temp2);
                                                        ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                                        ps.setString(2, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                        ps.setString(3, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                        ps.setString(4, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                        ps.setString(5, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                        ps.setString(6, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                        ps.setString(7, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                        rs = ps.executeQuery();
                                                        //si existen registros de esas fechas, no se registra el equipo
                                                        if (rs.next()) {
                                                            vistaPrestamo.labelMsg.setText("El equipo esta reservado en esas fechas");
                                                        }
                                                        else{
                                                            prestamo.setFolio(Integer.parseInt(vistaPrestamo.txtFolio.getText()));
                                                            prestamo.setMatricula(vistaPrestamo.txtMatricula.getText());
                                                            prestamo.setIdequipo(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
                                                            prestamo.setFechaUso(((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                            prestamo.setFechaDevolucion(((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                            prestamo.setHoraUso(vistaPrestamo.spinnerHora1.getValue().toString());
                                                            prestamo.setHoraFinUso(vistaPrestamo.spinnerHora2.getValue().toString());
                                                            prestamo.setStatus(vistaPrestamo.cbxStatus.getSelectedItem().toString());
                                                            //si no ha habido nigun error, se modificara el prestamo
                                                            if (conP.modificar(prestamo)) {
                                                                JOptionPane.showMessageDialog(null, "Modificación exitosa");
                                                                modificarStatusEquipoNoDisponible();//modifica el status del equipo
                                                                limpiar();//limpia los campos de texto
                                                                reiniciar();//reinicia las tablas
                                                                fechaSistema();//actualiza la hora del sistema
                                                                cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados
                                                                cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                                                                cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                                                            } //De lo contrario se muestra el mensaje de error
                                                            else {
                                                                JOptionPane.showMessageDialog(null, "Modificación errónea\n Verifique que la fecha de uso o fecha de devolución no este vacía");
                                                                //limpiar();//limpia las cajas de los textos
                                                            }                                        
                                                        }
                                                    } catch (Exception exc) {
                                                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                                //si el equipo no esta reservado hara el registro del prestamo
                                                else{
                                                    prestamo.setFolio(Integer.parseInt(vistaPrestamo.txtFolio.getText()));
                                                    prestamo.setMatricula(vistaPrestamo.txtMatricula.getText());
                                                    prestamo.setIdequipo(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
                                                    prestamo.setFechaUso(((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                    prestamo.setFechaDevolucion(((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                    prestamo.setHoraUso(vistaPrestamo.spinnerHora1.getValue().toString());
                                                    prestamo.setHoraFinUso(vistaPrestamo.spinnerHora2.getValue().toString());
                                                    prestamo.setStatus(vistaPrestamo.cbxStatus.getSelectedItem().toString());
                                                    //si no ha habido nigun error, se modificara el prestamo
                                                    if (conP.modificar(prestamo)) {
                                                        JOptionPane.showMessageDialog(null, "Modificación exitosa");
                                                        modificarStatusEquipoNoDisponible();//modifica el status del equipo
                                                        limpiar();//limpia los campos de texto
                                                        reiniciar();//reinicia las tablas
                                                        fechaSistema();//actualiza la hora del sistema
                                                        cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados
                                                        cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                                                        cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                                                    } //De lo contrario se muestra el mensaje de error
                                                    else {
                                                        JOptionPane.showMessageDialog(null, "Modificación errónea\n Verifique que la fecha de uso o fecha de devolución no este vacía");
                                                        //limpiar();//limpia las cajas de los textos
                                                    }
                                                }
                                            } catch (Exception exc) {
                                                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                            }
                                        }
                                        else{
                                            vistaPrestamo.labelMsg.setText("Equipo no disponible, seleccione otro");                                  
                                        }
                                    }
                                } catch (Exception exc) {
                                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                }                               
                            }
                            else{
                                modificarStatusEquipoDisponibleTemp();
                                Connection con = null;
                                String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
                                try {
                                    con = (Connection) cc.getConnection();
                                    ps = con.prepareStatement(temp);
                                    ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                    rs = ps.executeQuery();
                                    //itera el resultado
                                    if (rs.next()) {
                                        //si el id es correcto, consulta su status y se lo asigna a una variable local
                                        String tem = rs.getString("status");//variable local del status del equipo                   
                                        //compara si el equipo se encuentra disponible con la variable local
                                        if (tem.equals("Disponible")) {                                       
                                            String temp1 = "SELECT * FROM prestamosalum WHERE equipo_idEquipo=? AND status = 'Reservado'";//consulta para verificar si el equipo esta reservado
                                            try {
                                                ps = con.prepareStatement(temp1);
                                                ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                                rs = ps.executeQuery();
                                                //si existen registros de que el equipo esta reservado, se consultara su disponibilidad de fechas
                                                if (rs.next()) {
                                                    String tem1 = rs.getString("status");//variable local del status del equipo 
                                                    //consulta para verificar disponibilidad de fechas del equipo
                                                    String temp2 = "SELECT fechaUso, fechaDevolucion, equipo_idEquipo, status FROM prestamosalum WHERE equipo_idEquipo=? AND status = 'Reservado' AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                                                            + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
                                                    try {
                                                        ps = con.prepareStatement(temp2);
                                                        ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                                        ps.setString(2, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                        ps.setString(3, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                        ps.setString(4, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                        ps.setString(5, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                        ps.setString(6, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                        ps.setString(7, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                        rs = ps.executeQuery();
                                                        //si existen registros de esas fechas, no se registra el equipo
                                                        if (rs.next()) {
                                                            vistaPrestamo.labelMsg.setText("El equipo esta reservado en esas fechas");
                                                        }
                                                        else{
                                                            prestamo.setFolio(Integer.parseInt(vistaPrestamo.txtFolio.getText()));
                                                            prestamo.setMatricula(vistaPrestamo.txtMatricula.getText());
                                                            prestamo.setIdequipo(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
                                                            prestamo.setFechaUso(((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                            prestamo.setFechaDevolucion(((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                            prestamo.setHoraUso(vistaPrestamo.spinnerHora1.getValue().toString());
                                                            prestamo.setHoraFinUso(vistaPrestamo.spinnerHora2.getValue().toString());
                                                            prestamo.setStatus(vistaPrestamo.cbxStatus.getSelectedItem().toString());
                                                            //si no ha habido nigun error, se modificara el prestamo
                                                            if (conP.modificar(prestamo)) {
                                                                JOptionPane.showMessageDialog(null, "Modificación exitosa");
                                                                modificarStatusEquipoNoDisponible();//modifica el status del equipo
                                                                limpiar();//limpia los campos de texto
                                                                reiniciar();//reinicia las tablas
                                                                fechaSistema();//actualiza la hora del sistema
                                                                cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados
                                                                cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                                                                cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                                                            } //De lo contrario se muestra el mensaje de error
                                                            else {
                                                                JOptionPane.showMessageDialog(null, "Modificación errónea\n Verifique que la fecha de uso o fecha de devolución no este vacía");
                                                                //limpiar();//limpia las cajas de los textos
                                                            }                                        
                                                        }
                                                    } catch (Exception exc) {
                                                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                                //si el equipo no esta reservado hara el registro del prestamo
                                                else{
                                                    prestamo.setFolio(Integer.parseInt(vistaPrestamo.txtFolio.getText()));
                                                    prestamo.setMatricula(vistaPrestamo.txtMatricula.getText());
                                                    prestamo.setIdequipo(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
                                                    prestamo.setFechaUso(((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                    prestamo.setFechaDevolucion(((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                    prestamo.setHoraUso(vistaPrestamo.spinnerHora1.getValue().toString());
                                                    prestamo.setHoraFinUso(vistaPrestamo.spinnerHora2.getValue().toString());
                                                    prestamo.setStatus(vistaPrestamo.cbxStatus.getSelectedItem().toString());
                                                    //si no ha habido nigun error, se modificara el prestamo
                                                    if (conP.modificar(prestamo)) {
                                                        JOptionPane.showMessageDialog(null, "Modificación exitosa");
                                                        modificarStatusEquipoNoDisponible();//modifica el status del equipo
                                                        limpiar();//limpia los campos de texto
                                                        reiniciar();//reinicia las tablas
                                                        fechaSistema();//actualiza la hora del sistema
                                                        cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados
                                                        cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                                                        cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                                                    } //De lo contrario se muestra el mensaje de error
                                                    else {
                                                        JOptionPane.showMessageDialog(null, "Modificación errónea\n Verifique que la fecha de uso o fecha de devolución no este vacía");
                                                        //limpiar();//limpia las cajas de los textos
                                                    }
                                                }
                                            } catch (Exception exc) {
                                                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                            }
                                        }
                                        else{
                                            vistaPrestamo.labelMsg.setText("Equipo no disponible, seleccione otro");                                  
                                        }
                                    }
                                } catch (Exception exc) {
                                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                }    
                            
                            }
                        }
                        if (statusTemp.equals("Reservado")) {                                                       
                            cancelarReservacion();
                            Connection con = null;
                            String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
                            try {
                                con = (Connection) cc.getConnection();
                                ps = con.prepareStatement(temp);
                                ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                rs = ps.executeQuery();
                                //itera el resultado
                                if (rs.next()) {
                                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                                    String tem = rs.getString("status");//variable local del status del equipo                   
                                    //compara si el equipo se encuentra disponible con la variable local
                                    if (tem.equals("Disponible")) {
                                        String temp1 = "SELECT * FROM prestamosalum WHERE equipo_idEquipo=? AND status = 'Reservado'";//consulta para verificar si el equipo esta reservado
                                        try {
                                            ps = con.prepareStatement(temp1);
                                            ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                            rs = ps.executeQuery();
                                            //si existen registros de que el equipo esta reservado, se consultara su disponibilidad de fechas
                                            if (rs.next()) {
                                                String tem1 = rs.getString("status");//variable local del status del equipo 
                                                //consulta para verificar disponibilidad de fechas del equipo
                                                String temp2 = "SELECT fechaUso, fechaDevolucion, equipo_idEquipo, status FROM prestamosalum WHERE equipo_idEquipo=? AND status = 'Reservado' AND ((? BETWEEN fechaUso AND fechaDevolucion) "
                                                        + "OR (? BETWEEN fechaUso AND fechaDevolucion) OR (fechaUso <= ? AND fechaDevolucion >= ?) OR (fechaUso >= ? AND fechaDevolucion <= ?))";
                                                try {
                                                    ps = con.prepareStatement(temp2);
                                                    ps.setString(1, vistaPrestamo.txtIdEquipo.getText());
                                                    ps.setString(2, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                    ps.setString(3, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                    ps.setString(4, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                    ps.setString(5, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                    ps.setString(6, ((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                    ps.setString(7, ((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                    rs = ps.executeQuery();
                                                    //si existen registros de esas fechas, no se registra el equipo
                                                    if (rs.next()) {
                                                        vistaPrestamo.labelMsg.setText("El equipo esta reservado en esas fechas");
                                                    } else {
                                                        prestamo.setFolio(Integer.parseInt(vistaPrestamo.txtFolio.getText()));
                                                        prestamo.setMatricula(vistaPrestamo.txtMatricula.getText());
                                                        prestamo.setIdequipo(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
                                                        prestamo.setFechaUso(((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                        prestamo.setFechaDevolucion(((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                        prestamo.setHoraUso(vistaPrestamo.spinnerHora1.getValue().toString());
                                                        prestamo.setHoraFinUso(vistaPrestamo.spinnerHora2.getValue().toString());
                                                        prestamo.setStatus(vistaPrestamo.cbxStatus.getSelectedItem().toString());
                                                        //si no ha habido nigun error, se modificara el prestamo
                                                        if (conP.modificar(prestamo)) {
                                                            JOptionPane.showMessageDialog(null, "Modificación exitosa");
                                                            //modificarStatusEquipoNoDisponible();//modifica el status del equipo
                                                            limpiar();//limpia los campos de texto
                                                            reiniciar();//reinicia las tablas
                                                            fechaSistema();//actualiza la hora del sistema
                                                            cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados
                                                            cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                                                            cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                                                        } //De lo contrario se muestra el mensaje de error
                                                        else {
                                                            JOptionPane.showMessageDialog(null, "Modificación errónea\n Verifique que la fecha de uso o fecha de devolución no este vacía");
                                                            //limpiar();//limpia las cajas de los textos
                                                        }
                                                    }
                                                } catch (Exception exc) {
                                                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                                }
                                            } //si el equipo no esta reservado hara el registro del prestamo
                                            else {
                                                prestamo.setFolio(Integer.parseInt(vistaPrestamo.txtFolio.getText()));
                                                prestamo.setMatricula(vistaPrestamo.txtMatricula.getText());
                                                prestamo.setIdequipo(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
                                                prestamo.setFechaUso(((JTextField) vistaPrestamo.jDateFechaUso.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora1.getValue().toString());
                                                prestamo.setFechaDevolucion(((JTextField) vistaPrestamo.jDCFechaDevolucion.getDateEditor().getUiComponent()).getText() + " " + vistaPrestamo.spinnerHora2.getValue().toString());
                                                prestamo.setHoraUso(vistaPrestamo.spinnerHora1.getValue().toString());
                                                prestamo.setHoraFinUso(vistaPrestamo.spinnerHora2.getValue().toString());
                                                prestamo.setStatus(vistaPrestamo.cbxStatus.getSelectedItem().toString());
                                                //si no ha habido nigun error, se modificara el prestamo
                                                if (conP.modificar(prestamo)) {
                                                    JOptionPane.showMessageDialog(null, "Modificación exitosa");
                                                    //modificarStatusEquipoNoDisponible();//modifica el status del equipo
                                                    limpiar();//limpia los campos de texto
                                                    reiniciar();//reinicia las tablas
                                                    fechaSistema();//actualiza la hora del sistema
                                                    cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados
                                                    cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                                                    cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                                                } //De lo contrario se muestra el mensaje de error
                                                else {
                                                    JOptionPane.showMessageDialog(null, "Modificación errónea\n Verifique que la fecha de uso o fecha de devolución no este vacía");
                                                    //limpiar();//limpia las cajas de los textos
                                                }
                                            }
                                        } catch (Exception exc) {
                                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                }
                            } catch (Exception exc) {
                                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    else{
                        vistaPrestamo.labelMsg.setText("La fecha y hora de uso no deben de ser mayor que la fecha y hora de devolución");
                    }
                }catch(ParseException ex){
                    Logger.getLogger(ControladorReserva.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + ex.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
            //mandara un mensaje al usuario si los campos estan vacios
            vistaPrestamo.labelMsg.setText("No se ha modificado nada, por favor seleccione un dato de la tabla");
            }
        }
        // se verifica si se presiono el boton devolver
        if (e.getSource() == vistaPrestamo.btnDevolver) {  
            //primero compara si estan vacios el campo de texto del folio
            if (!vistaPrestamo.txtFolio.getText().equals("")) {
                prestamo.setFolio(Integer.parseInt(vistaPrestamo.txtFolio.getText()));
                String status = "Devuelto";
                prestamo.setStatus(status);//modifica el status del prestamo              
                //si no ocurrio ningun error, se procedera a modificar los datos
                if (conP.devolver(prestamo)) {
                    JOptionPane.showMessageDialog(null, "Devolución exitosa");   
                    modificarStatusEquipoDisponible();//actualiza el status del equipo
                    limpiar();//limpia los campos de texto
                    reiniciar();//reinicia las tablas
                    fechaSistema();//actualiza la hora del sistema
                    cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados
                    cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                    cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                } else {
                    JOptionPane.showMessageDialog(null, "Devolución errónea");                   
                    limpiar();//limpia las cajas de los textos
                }                              
            }
            else {
                //mandara un mensaje al usuario si los campos estan vacios
                vistaPrestamo.labelMsg.setText("No se ha devuelto, por favor seleccione un dato de la tabla");
            }
        }
        // se verifica si se presiono el boton cancelar
        if (e.getSource() == vistaPrestamo.btnCancelar) {  
            //primero compara si estan vacios el campo de texto del folio
            if (!vistaPrestamo.txtFolio.getText().equals("")) {
                prestamo.setFolio(Integer.parseInt(vistaPrestamo.txtFolio.getText()));
                String status = "Cancelado";
                prestamo.setStatus(status);//actualiza el status del prestamo
                //si no ocurrio ningun error, se procedera a modificar los datos
                if (conP.devolver(prestamo)) {
                    JOptionPane.showMessageDialog(null, "Cancelación exitosa"); 
                    modificarStatusEquipoDisponible();//actualiza el status del equipo
                    limpiar();//limpia los campos de texto
                    reiniciar();//reinicia las tablas
                    fechaSistema();//actualiza la hora del sistema
                    cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados 
                    cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                    cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                } else {
                    JOptionPane.showMessageDialog(null, "Cancelación errónea");                   
                    limpiar();//limpia las cajas de los textos
                }                               
            }
            else {
                //mandara un mensaje al usuario si los campos estan vacios
                vistaPrestamo.labelMsg.setText("No se ha cancelado, por favor seleccione un dato de la tabla");
            }
        }
        //aqui se compara si se presiono el boton eliminar
        if (e.getSource() == vistaPrestamo.btnEliminar) {             
            //primero compara si estan vacios los campos de texto
            if (!vistaPrestamo.txtFolio.getText().equals("")) {
                int msg = JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar el préstamo seleccionado?", "Confirmar eliminar préstamo", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);       
                if (msg == JOptionPane.YES_OPTION) {
                    prestamo.setFolio(Integer.parseInt(vistaPrestamo.txtFolio.getText()));
                    if (conP.eliminar(prestamo)) {
                        JOptionPane.showMessageDialog(null, "Eliminación exitosa");
                        modificarStatusEquipoDisponible();
                        limpiar();//limpia las cajas de los textos
                        reiniciar();//reinicia las tablas
                        fechaSistema();//actualiza la hora del sistema
                        cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados 
                        cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                        cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
                    } else {
                        JOptionPane.showMessageDialog(null, "Eliminación errónea");
                        limpiar();//limpia las cajas de los textos
                    }
                }                
            } else {
                //mandara un mensaje al usuario si los campos estan vacios
                vistaPrestamo.labelMsg.setText("No se ha eliminado nada, por favor seleccione un dato de la tabla");
            }        
        }            
        //aqui se compara si se presiono el boton limpiar
        if (e.getSource() == vistaPrestamo.btnLimpiar) {
            limpiar();//limpia las cajas de los textos
        }
        //aqui se compara si se presiono el boton actualizar
        if (e.getSource() == vistaPrestamo.btnActualizar) {
            reiniciar();//reinicia las tablas
            fechaSistema();//actualiza la hora del sistema
            verificarFechaDeUso();
            cargarTablaPrestados(vistaPrestamo.tablePrestados);//actualiza los datos de la tabla de prestados  
            cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);//actualiza los datos de la tabla de equipos disponibles
            cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
        }
     
    }    
    //Modifica el status "No disponible" del equipo cuando se realiza un prestamo exitoso      
    public void modificarStatusEquipoNoDisponible(){        
        String statusE = "No Disponible";//variable local para asignar el status
        try {
            equipo.setId(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
            equipo.setStatus(statusE);//actualiza el status del equipo
            if (conE.establecerStatus(equipo)) {
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }
    }
   
    //Modifica el status "Disponible" del equipo cuando se realiza una devolucion o cancelacion exitosa     
    public void modificarStatusEquipoDisponible(){        
        String statusE = "Disponible";//variable local para asignar el status
        try {
            equipo.setId(Integer.parseInt(vistaPrestamo.txtIdEquipo.getText()));
            equipo.setStatus(statusE);//actualiza el status del equipo           
            if (conE.establecerStatus(equipo)) {
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }
    }    
    //este metodo sirve para cambiar el status de un equipo cuando se requiera modificar un prestamo y este se quiera cambiar de equipo
    //para no afectar el status
    public void modificarStatusEquipoDisponibleTemp(){       
        String statusE = "Disponible";//variable local para asignar el status
        try {
            equipo.setId(Integer.parseInt(vistaPrestamo.txtIdEquipoTemp.getText()));
            equipo.setStatus(statusE);//actualiza el status del equipo           
            if (conE.establecerStatus(equipo)) {
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }
   }    
    //metodo que sirve para actualizar el estatus de las reservas de los equipos cuando empieze la hora de utilizacion
    public void verificarFechaDeUso(){       
        prestamo.setStatus("Prestado");
        equipo.setStatus("No Disponible");
        if (conP.modificarStatusFechaUso(prestamo, equipo)) {
            //modificarStatusEquipoNoDisponible();
        } else {
            System.out.println("No hay registros actuales de reservaciones");
        }
    }   
    //metodo para cancelar la reservacion del equipo cuando se quiera modificar
    public void cancelarReservacion(){
        prestamo.setFolio(Integer.parseInt(vistaPrestamo.txtFolio.getText()));
        String status = "Cancelado";
        prestamo.setStatus(status);//actualiza el status del prestamo
        //si no ocurrio ningun error, se procedera a modificar los datos
        if (conP.devolver(prestamo)) {
            
        } else {           
        }  
    }
    
    //metodo para ejecutar las actualizaciones automaticas de los estatus de las reservas de los equipos
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
                    cargarTablaPrestados(vistaPrestamo.tablePrestados);
                    cargarTablaEquiposDisponibles(vistaPrestamo.tableEquiposDisponibles);
                    cargarTablaAtrasados(vistaPrestamo.tableAtrasados);
                    System.out.println("Función Ejecutada Actualizar Tablas");
                }
            });
            timer.start();

        }catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }         
    }   
    //metodo para autocompletar el campo de la matricula del prestamo
    public void autocompletarMatricula(){
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaPrestamo.txtMatricula);
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
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaPrestamo.txtNombre);
        Connection con = null;       
        //string de la consulta
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
    //metodo para vaciar los campos de texto 
    public void limpiar(){
        vistaPrestamo.txtFolio.setText(null);
        vistaPrestamo.txtMatricula.setText(null);
        vistaPrestamo.txtNombre.setText(null);               
        vistaPrestamo.txtIdEquipo.setText(null);
        vistaPrestamo.txtDescripcion.setText(null);       
        vistaPrestamo.jDateFechaUso.setDate(null); 
        vistaPrestamo.jDCFechaDevolucion.setDate(null);          
        vistaPrestamo.cbxStatus.setSelectedIndex(0);        
        vistaPrestamo.labelMsg.setText(null);       
        fechaSistema();//actualiza la fecha del sistema
        vistaPrestamo.btnModificar.setEnabled(false);
        vistaPrestamo.btnDevolver.setEnabled(false);
        vistaPrestamo.btnCancelar.setEnabled(false);
        vistaPrestamo.btnGuardar.setEnabled(true);
    }
    
    //evento para pasar los datos de la tabla de equipos prestados a los campos de los textos
    @Override
    public void mouseClicked(MouseEvent e){
        vistaPrestamo.btnModificar.setEnabled(true);
        vistaPrestamo.btnDevolver.setEnabled(true);
        vistaPrestamo.btnCancelar.setEnabled(true);
        vistaPrestamo.btnGuardar.setEnabled(false);
        ps = null;
        rs = null;
        try{
            //creamos el formato en el que deseamos mostrar la fecha
            SimpleDateFormat format = new SimpleDateFormat ("yyyy/MM/dd");
            Date fechaIni = null;//creamos una variable tipo Date y la ponemos NULL
            Date fechaFin = null;//creamos una variable tipo Date y la ponemos NULL
            Conexion ObjCon = new Conexion();
            com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) ObjCon.getConnection();
            int fila = vistaPrestamo.tablePrestados.getSelectedRow();//obtiene el numero de filas de la tabla           
            String id = vistaPrestamo.tablePrestados.getValueAt(fila, 0).toString();//obtiene el valor 0 de la fila de la tabla           
            String temp = "SELECT folio, alumno_matricula, equipo_idEquipo, alumno.nombre, alumno.apellidoP, alumno.apellidoM, equipo.descripcion, DATE_FORMAT(prestamosalum.fechaUso, '%Y/%m/%d') AS fUso, DATE_FORMAT(prestamosalum.fechaDevolucion, '%Y/%m/%d') AS fDev,"
                    + "DATE_FORMAT(prestamosalum.fechaUso, '%H:%i:%s') AS hUso, DATE_FORMAT(prestamosalum.fechaDevolucion, '%H:%i:%s') AS hDev, status FROM prestamosalum, alumno, equipo "
                    + "WHERE folio=? AND alumno.matricula = prestamosalum.alumno_matricula AND equipo.idEquipo = prestamosalum.equipo_idEquipo";
            //realiza la consulta a la base de datos
            String temp2 = "SELECT folio, alumno_matricula, equipo_idEquipo, status, DATE_FORMAT(prestamosalum.fechaUso, '%Y/%m/%d') AS fUso, DATE_FORMAT(prestamosalum.fechaDevolucion, '%Y/%m/%d') AS fDev,"
                    + "DATE_FORMAT(prestamosalum.fechaUso, '%H:%i:%s') AS hUso, DATE_FORMAT(prestamosalum.fechaDevolucion, '%H:%i:%s') AS hDev, nombre, apellidoP, apellidoM FROM prestamosalum, alumno "
                    + "WHERE folio=? AND matricula = prestamosalum.alumno_matricula";
            //ps = con.prepareStatement("SELECT folio, alumno_matricula, equipo_idEquipo, alumno.nombre, alumno.apellidoP, alumno.apellidoM, equipo.descripcion, DATE_FORMAT(prestamosalum.fechaUso, '%Y/%m/%d') AS fUso, DATE_FORMAT(prestamosalum.fechaDevolucion, '%Y/%m/%d') AS fDev,"
            //        + "DATE_FORMAT(prestamosalum.fechaUso, '%H:%i:%s') AS hUso, DATE_FORMAT(prestamosalum.fechaDevolucion, '%H:%i:%s') AS hDev, status FROM prestamosalum, alumno, equipo "
            //        + "WHERE folio=? AND alumno.matricula = prestamosalum.alumno_matricula AND equipo.idEquipo = prestamosalum.equipo_idEquipo");
            ps = conn.prepareStatement(temp2);
            ps.setString(1, id);           
            rs = ps.executeQuery();                 
            //itera los resultados que coincidieron con la consulta
            while(rs.next()){          
                //asigna los valores que coincidieron
                vistaPrestamo.txtFolio.setText(rs.getString("folio"));
                vistaPrestamo.txtMatricula.setText(rs.getString("alumno_matricula"));          
                vistaPrestamo.txtIdEquipo.setText(rs.getString("equipo_idEquipo")); 
                vistaPrestamo.txtIdEquipoTemp.setText(rs.getString("equipo_idEquipo"));
                vistaPrestamo.txtNombre.setText(rs.getString("alumno.nombre") + " " + rs.getString("alumno.apellidoP") +" "+ rs.getString("alumno.apellidoM"));               
                //vistaPrestamo.txtDescripcion.setText(rs.getString("equipo.descripcion"));              
                //vistaR.txtFechaTemp.setText(rs.getString("fechaUso"));                
                //vistaR.txtFechaTemp2.setText(rs.getString("fechaDevolucion"));              
                String fecha1 = rs.getString("fUso");//obtenemos la fecha 
                String fecha2 = rs.getString("fDev");//obtenemos la fecha 
                System.out.println(fecha1 + " " + fecha2);                                       
                fechaIni = format.parse(fecha1);//Parseamos de String a Date usando el formato
                fechaFin = format.parse(fecha2);//Parseamos de String a Date usando el formato
                vistaPrestamo.jDateFechaUso.setDate(fechaIni);//Seteamos o mostramos la fecha en el JDateChooser
                vistaPrestamo.jDCFechaDevolucion.setDate(fechaFin);//Seteamos o mostramos la fecha en el JDateChooser
                vistaPrestamo.spinnerHora1.setValue(rs.getString("hUso"));               
                vistaPrestamo.spinnerHora2.setValue(rs.getString("hDev"));                
                vistaPrestamo.cbxStatus.setSelectedItem(rs.getString("prestamosalum.status"));                  
            }           
        }
        catch(Exception exc){  
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }      
    //evento para buscar datos de la base de datos por medio de los textField
    @Override
    public void keyReleased(KeyEvent e) {
        //compara si fue la caja de texto de la matricula
        if (e.getSource() == vistaPrestamo.txtMatricula) {
            Connection con = null;
            fechaSistema();//reinicia la hora del sistema cada vez que se haga esta accion
            //string de la consulta
            String temp = "SELECT * FROM alumno, carrera WHERE alumno.matricula LIKE ? AND alumno.carrera_idCarrera=idCarrera";
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, vistaPrestamo.txtMatricula.getText());
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    vistaPrestamo.txtNombre.setText(rs.getString("nombre") + " " + rs.getString("apellidoP") + " " + rs.getString("apellidoM"));                  
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }                    
        //compara si fue la caja de texto del nombre
        if (e.getSource() == vistaPrestamo.txtNombre) {           
            Connection con = null;
            fechaSistema();//reinicia la hora del sistema cada vez que se haga esta accion          
            String temp = "SELECT * FROM alumno, carrera WHERE alumno.nombre LIKE ? AND alumno.carrera_idCarrera=idCarrera";
            try {                
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                //ps.setString(1,vistaPrestamo.txtNombre.getText());
                ps.setString(1,vistaPrestamo.txtNombre.getText());
                rs = ps.executeQuery();
                
                while (rs.next()) {                  
                    vistaPrestamo.txtMatricula.setText(rs.getString("matricula"));
               }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
        //compara si fue la caja de texto del Id del equipo
        if (e.getSource() == vistaPrestamo.txtIdEquipo) {
            Connection con = null;
            fechaSistema();//reinicia la hora del sistema cada vez que se haga esta accion
            //string de la consulta             
            String temp = "SELECT * FROM equipo WHERE idEquipo LIKE ?";
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, "%" + vistaPrestamo.txtIdEquipo.getText() + "%");
                rs = ps.executeQuery();                      
                rs = ps.executeQuery();
                while (rs.next()) {
                    vistaPrestamo.txtDescripcion.setText(rs.getString("descripcion"));
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
        //compara si fue la caja de texto de la descripcion del equipo
        if (e.getSource() == vistaPrestamo.txtDescripcion) {
            Connection con = null;
            fechaSistema();//reinicia la hora del sistema cada vez que se haga esta accion
            //string de la consulta
            String temp = "SELECT * FROM equipo, sala WHERE equipo.descripcion LIKE ? AND equipo.sala_idSala=idSala";
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, "%" + vistaPrestamo.txtDescripcion.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {                   
                    vistaPrestamo.txtIdEquipo.setText(rs.getString("idEquipo"));
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
    }
 
    /*
    estos metodos no son utilizados   
    */
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
