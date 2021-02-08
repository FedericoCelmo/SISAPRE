/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Alumno;
import Modelo.Conexion;
import Modelo.ConsultasAlumno;
import Vista.VistaGestionAlumno;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

/**
 *
 * @author Federico
 * 
 */
public class ControladorGestionAlumno implements ActionListener, KeyListener, MouseListener{  
    private Alumno alumno;
    private ConsultasAlumno conA;
    private VistaGestionAlumno vistaAlumno;
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();// variable del modelo de la tabla
    
    //constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorGestionAlumno(Alumno alumno, ConsultasAlumno conA, VistaGestionAlumno vistaAlumno){
        this.alumno = alumno;
        this.conA = conA;
        this.vistaAlumno = vistaAlumno;
        this.vistaAlumno.btnGuardar.addActionListener(this);
        this.vistaAlumno.btnLimpiar.addActionListener(this);
        this.vistaAlumno.btnModificar.addActionListener(this);
        this.vistaAlumno.btnEliminar.addActionListener(this);
        this.vistaAlumno.btnActualizar.addActionListener(this);
        this.vistaAlumno.tableAlumnos.addMouseListener(this);
        this.vistaAlumno.btnImprimir.addActionListener(this);
        this.vistaAlumno.txtBuscar.addKeyListener(this);       
    }    
    //aqui se cargan todos los componentes que se iniciarian cuando se abra la vista
    public void iniciar(){
        vistaAlumno.setTitle("Gestión de Alumnos");
        vistaAlumno.setLocationRelativeTo(null);
        //nombre de las columnas
        modelo.addColumn("Matricula");//les asigno nombres a las columnas
        modelo.addColumn("Nombre(s)");//les asigno nombres a las columnas
        modelo.addColumn("Apellido Paterno");//les asigno nombres a las columnas
        modelo.addColumn("Apellido Materno");//les asigno nombres a las columnas
        modelo.addColumn("ID Carrera");//les asigno nombres a las columnas
        cargarTabla(vistaAlumno.tableAlumnos);
        vistaAlumno.btnEliminar.setEnabled(false);
        vistaAlumno.btnModificar.setEnabled(false);
        vistaAlumno.btnImprimir.setVisible(false);
    }     
    //metodo que sirve para cargar los datos de la tabla
    public void cargarTabla(JTable tableAlumnos){    
        tableAlumnos.setModel(modelo); 
        try{                      
            ps = null;
            rs = null;
            Conexion conn = new Conexion();
            Connection con = (Connection) conn.getConnection();          
            String sql = "SELECT matricula, nombre, apellidoP, apellidoM, carrera_idCarrera FROM alumno";// consulta de los datos de la tabla a la base de datos           
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();           
            int[] size = {40,80,80,80,40,100,100};//arreglo para asignar el ancho de las columnas
            //recorremos el arreglo y asignamos dichos valores
            for (int x = 0; x < cantidadColumnas; x++) {
                vistaAlumno.tableAlumnos.getColumnModel().getColumn(x).setPreferredWidth(size[x]);            
            }                    
            while(rs.next()){
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i+1);                   
                }
                modelo.addRow(filas);
            }            
        }
        catch(Exception e){   
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }    
    //metodo para reinicar los datos de la tabla y evitar que se dupliquen estos mismos
    public void reiniciar() {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
    }   
    @Override//En este metodo se realizan las acciones de las consultas
    public void actionPerformed(ActionEvent e){
        Connection con = null;
        //aqui se compara si se presiono el boton guardar
        if (e.getSource() == vistaAlumno.btnGuardar) { 
            //primero compara si estan vacios los campos de texto
            if (!vistaAlumno.txtMatricula.getText().equals("") && !vistaAlumno.txtNombre.equals("") && 
                !vistaAlumno.txtApellidoP.equals("") && !vistaAlumno.txtApellidoM.equals("")) {
                //consulta que sirve para cotejar el nombre de la carrera con su id  
                String sql = "SELECT idCarrera, nomCarrera FROM carrera WHERE nomCarrera=?";                   
                try {
                    con = (Connection) cc.getConnection();
                    ps = con.prepareStatement(sql);
                    ps.setString(1, vistaAlumno.cbxCarrera.getSelectedItem().toString());
                    rs = ps.executeQuery();
                    String idCarrera = "";                   
                    if (rs.next()) {
                        //variable local para guardar el id y despues verificar que el nombre concuerda con ese id
                        idCarrera = rs.getString("idCarrera");                                              
                        if (rs.getString("idCarrera").equals(rs.getString(1))) {                                                                              
                            alumno.setMatricula(vistaAlumno.txtMatricula.getText());
                            alumno.setNombre(vistaAlumno.txtNombre.getText());
                            alumno.setApellidoPat(vistaAlumno.txtApellidoP.getText());
                            alumno.setApellidoMat(vistaAlumno.txtApellidoM.getText());
                            alumno.setCarrera(rs.getString(1));
                            //De ser valida la accion, se guarda el objeto y se muestra el mensaje
                            if (conA.registrar(alumno)) {
                                JOptionPane.showMessageDialog(null, "Alumno agregado correctamente");
                                limpiar();
                                reiniciar();
                                cargarTabla(vistaAlumno.tableAlumnos);
                                limpiar();
                            } //De lo contrario se muestra el mensaje de error
                            else {
                                JOptionPane.showMessageDialog(null, "No se ha agregado el alumno");
                                limpiar();
                            }                                                     
                        }
                    }
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                }
            }
            //mandara un mensaje al usuario si los campos estan vacios
            else {
                vistaAlumno.labelMsg.setText("No se aceptan campos vacíos");
            }
        }        
        //aqui se compara si se presiono el boton modificar
        if (e.getSource() == vistaAlumno.btnModificar) {  
            //primero compara si estan vacios los campos de texto
            if (!vistaAlumno.txtMatricula.getText().equals("") && !vistaAlumno.txtNombre.equals("") && 
                !vistaAlumno.txtApellidoP.equals("") && !vistaAlumno.txtApellidoM.equals("")) {    
                //consulta que sirve para cotejar el nombre de la carrera con su id  
                String sql = "SELECT idCarrera, nomCarrera FROM carrera WHERE nomCarrera=?";                   
                try {
                    con = (Connection) cc.getConnection();
                    ps = con.prepareStatement(sql);
                    ps.setString(1, vistaAlumno.cbxCarrera.getSelectedItem().toString());
                    rs = ps.executeQuery();
                    String idCarrera = "";                   
                    if (rs.next()) {
                        //variable local para guardar el id y despues verificar que el nombre concuerda con ese id
                        idCarrera = rs.getString("idCarrera");                                              
                        if (rs.getString("idCarrera").equals(rs.getString(1))) {                               
                            alumno.setMatricula(vistaAlumno.txtMatricula.getText());
                            alumno.setNombre(vistaAlumno.txtNombre.getText());
                            alumno.setApellidoPat(vistaAlumno.txtApellidoP.getText());
                            alumno.setApellidoMat(vistaAlumno.txtApellidoM.getText());
                            alumno.setCarrera(rs.getString(1));
                            //De ser valida la accion, se guarda el objeto y se muestra el mensaje
                            if (conA.modificar(alumno)) {
                                JOptionPane.showMessageDialog(null, "Datos del alumno modificado correctamente");
                                limpiar();
                                reiniciar();
                                cargarTabla(vistaAlumno.tableAlumnos);
                                limpiar();
                            } //De lo contrario se muestra el mensaje de error
                            else {
                                JOptionPane.showMessageDialog(null, "No se ha modificado los datos del alumno");
                                limpiar();
                            }                                                     
                        }
                    }
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                }              
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaAlumno.labelMsg.setText("No se ha modificado nada, por favor seleccione un dato de la tabla");
            }
        }
        //aqui se compara si se presiono el boton eliminar
        if (e.getSource() == vistaAlumno.btnEliminar) {
            //primero compara si estan vacios los campos de texto
            if (!vistaAlumno.txtMatricula.getText().equals("")) {               
                int msg = JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar el alumno seleccionado?", "Confirmar eliminar alumno", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);       
                if (msg == JOptionPane.YES_OPTION) {
                    alumno.setMatricula(vistaAlumno.txtMatricula.getText());
                    if (conA.eliminar(alumno)) {
                        JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente");
                        limpiar();
                        reiniciar();
                        cargarTabla(vistaAlumno.tableAlumnos);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha podido eliminar el alumno");
                        limpiar();
                    }
                }              
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaAlumno.labelMsg.setText("No se ha eliminado nada, por favor seleccione un dato de la tabla");
            }
        }
        //aqui se compara si se presiono el boton limpiar
        if(e.getSource() == vistaAlumno.btnImprimir){
            Date date = new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");//formato de la fecha de la cabecera de la impresion           
            try{
                String cabecera = "Reporte de Lista de Alumnos\n";
                String piePagina = "SISAPRE/UADY-UMT\n"+"                                               "+"Fecha: " + hourdateFormat.format(date);
                imprimirTabla(vistaAlumno.tableAlumnos, cabecera, piePagina, true);                
            }
            catch(Exception exc){
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }               
        }
        //aqui se compara si se presiono el boton limpiar
        if(e.getSource() == vistaAlumno.btnLimpiar){
            limpiar();       
        }
        //aqui se compara si se presiono el boton actualizar
        if (e.getSource() == vistaAlumno.btnActualizar) {
            reiniciar();
            cargarTabla(vistaAlumno.tableAlumnos);  
        }       
    }      
    //metodo para vaciar los campos de texto 
    public void limpiar(){
        vistaAlumno.txtMatricula.setText(null);
        vistaAlumno.txtNombre.setText(null);
        vistaAlumno.txtApellidoP.setText(null);
        vistaAlumno.txtApellidoM.setText(null);
        vistaAlumno.txtBuscar.setText(null);
        vistaAlumno.cbxCarrera.setSelectedIndex(0);
        vistaAlumno.labelMsg.setText(null);
        vistaAlumno.btnEliminar.setEnabled(false);
        vistaAlumno.btnModificar.setEnabled(false);
        vistaAlumno.btnGuardar.setEnabled(true);
    }    
    //este metodo es para imprimir el contenido actual de la tabla
    public void imprimirTabla(JTable jTable, String cabecera,String piePagina, boolean showPrintDialog) {
        boolean fitWidth = true;
        boolean interactive = true;
        //Definimos el modo de impresión
        JTable.PrintMode modo = fitWidth ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;
        try {
            // mprimo la tabla          
            boolean complete = vistaAlumno.tableAlumnos.print(modo,
                    new MessageFormat(cabecera),
                    new MessageFormat(piePagina),showPrintDialog,null,
                    interactive);
            if (complete) {
                // Mostramos el mensaje de impresión existosa
                JOptionPane.showMessageDialog(jTable,
                        "Impresión completa",
                        "Resultado de la impresión",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Mostramos un mensaje indicando que la impresión fue cancelada                 
                JOptionPane.showMessageDialog(jTable,
                        "Impresión cancelada",
                        "Resultado de la impresión",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(jTable,
                    "Fallo de impresión: " + pe.getMessage(),
                    "Resultado de la impresión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    //evento para buscar datos de la base de datos por medio de la caja de texto "buscar"
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vistaAlumno.txtBuscar) {
            Connection con = null;          
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"Matricula", "Nombre(s)", "Apellido P", "Apellido M", "ID Carrera", "Carrera", "Facultad"});                     
            String temp = "SELECT * FROM alumno, carrera WHERE alumno.nombre LIKE ? AND alumno.carrera_idCarrera=idCarrera";        
            try {                
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, "%" + vistaAlumno.txtBuscar.getText() + "%");
                rs = ps.executeQuery();                      
                while (rs.next()) {                  
                    modelo.addRow(new Object[]{rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidoP"), rs.getString("apellidoM"), 
                        rs.getString("carrera_idCarrera"), rs.getString("nomCarrera"), rs.getString("facultad")});
                }
                vistaAlumno.tableAlumnos.setModel(modelo);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
    }  
    //evento para pasar los datos de la tabla a los campos de los textos
    @Override
    public void mouseClicked(MouseEvent e){
        vistaAlumno.btnEliminar.setEnabled(true);
        vistaAlumno.btnModificar.setEnabled(true);
        vistaAlumno.btnGuardar.setEnabled(false);           
        ps = null;
        rs = null;
        try{
            Conexion ObjCon = new Conexion();
            com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) ObjCon.getConnection();
            int fila = vistaAlumno.tableAlumnos.getSelectedRow();
            String id = vistaAlumno.tableAlumnos.getValueAt(fila, 0).toString();           
            //ps = conn.prepareStatement("SELECT matricula, nombre, apellidoP, apellidoM, carrera_idCarrera FROM alumno WHERE matricula=?");
            ps = conn.prepareStatement("SELECT matricula, nombre, apellidoP, apellidoM, carrera_idCarrera, idCarrera, nomCarrera FROM alumno, carrera WHERE matricula=? "
                    + "AND carrera.idCarrera=carrera_idCarrera");
            ps.setString(1, id);
            rs = ps.executeQuery();           
            while(rs.next()){
                vistaAlumno.txtMatricula.setText(rs.getString("matricula"));
                vistaAlumno.txtNombre.setText(rs.getString("nombre"));
                vistaAlumno.txtApellidoP.setText(rs.getString("apellidoP"));
                vistaAlumno.txtApellidoM.setText(rs.getString("apellidoM"));
                vistaAlumno.cbxCarrera.setSelectedItem(rs.getString("nomCarrera"));
            }
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);       
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
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

   
}
