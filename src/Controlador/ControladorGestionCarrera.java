/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Carrera;
import Modelo.Conexion;
import Modelo.ConsultasCarrera;
import Vista.VistaGestionCarrera;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Federico
 */
public class ControladorGestionCarrera extends Conexion implements ActionListener, KeyListener, MouseListener{
    
    private Carrera carrera;
    private ConsultasCarrera conC;
    private VistaGestionCarrera vistaCarrera;
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();// variable del modelo de la tabla
    
    //constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorGestionCarrera(Carrera carrera, ConsultasCarrera conC, VistaGestionCarrera vistaCarrera){
        this.carrera = carrera;
        this.conC = conC;
        this.vistaCarrera = vistaCarrera;
        this.vistaCarrera.btnGuardar.addActionListener(this);
        this.vistaCarrera.btnLimpiar.addActionListener(this);
        this.vistaCarrera.btnModificar.addActionListener(this);
        this.vistaCarrera.btnEliminar.addActionListener(this);
        this.vistaCarrera.btnActualizar.addActionListener(this);
        this.vistaCarrera.tableCarreras.addMouseListener(this);       
    }   
    //aqui se cargan todos los componentes que se iniciarian cuando se abra la vista
    public void iniciar(){
        vistaCarrera.setTitle("Gestión de Carreras");
        vistaCarrera.setLocationRelativeTo(null);
        vistaCarrera.btnEliminar.setEnabled(false);
        vistaCarrera.btnModificar.setEnabled(false);
        //nombre de las columnas
        modelo.addColumn("ID");//les asigno nombres a las columnas
        modelo.addColumn("Nombre de la Carrera");//les asigno nombres a las columnas
        modelo.addColumn("Facultad");//les asigno nombres a las columnas
        cargarTabla(vistaCarrera.tableCarreras);
    }    
    //metodo que sirve para cargar los datos de la tabla
    public void cargarTabla(JTable tableCarreras){        
        tableCarreras.setModel(modelo); 
        try{                      
            ps = null;
            rs = null;
            Conexion conn = new Conexion();
            Connection con = (Connection) conn.getConnection();            
            String sql = "SELECT idCarrera, nomCarrera, facultad FROM carrera";// consulta de los datos de la tabla a la base de datos
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();           
            int[] size = {50,80,80};//arreglo para asignar el ancho de las columnas
            //recorremos el arreglo y asignamos dichos valores
            for (int x = 0; x < cantidadColumnas; x++) {
                vistaCarrera.tableCarreras.getColumnModel().getColumn(x).setPreferredWidth(size[x]);            
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
    //metodo para vaciar los campos de texto 
    public void limpiar(){
        vistaCarrera.txtId.setText(null);
        vistaCarrera.txtNombre.setText(null);
        vistaCarrera.txtFacultad.setText(null);
        vistaCarrera.labelMsg.setText(null);
        vistaCarrera.btnEliminar.setEnabled(false);
        vistaCarrera.btnModificar.setEnabled(false);
        vistaCarrera.btnGuardar.setEnabled(true);
    }    
    //En este metodo se realizan las acciones de las consultas
    @Override
    public void actionPerformed(ActionEvent e) {
        //aqui se compara si se presiono el boton guardar
        if (e.getSource() == vistaCarrera.btnGuardar) { 
            //primero compara si estan vacios los campos de texto
            if (!vistaCarrera.txtId.getText().equals("") && !vistaCarrera.txtNombre.equals("") && 
                !vistaCarrera.txtFacultad.equals("")) {                   
                carrera.setIdCarrera(vistaCarrera.txtId.getText());
                carrera.setNombreCarrera(vistaCarrera.txtNombre.getText());
                carrera.setFacultad(vistaCarrera.txtFacultad.getText());
                //De ser valida la accion, se guarda el objeto y se muestra el mensaje
                if (conC.registrar(carrera)) {
                    JOptionPane.showMessageDialog(null, "Carrera agregada correctamente");
                    limpiar();
                    reiniciar();
                    cargarTabla(vistaCarrera.tableCarreras);
                } //De lo contrario se muestra el mensaje de error
                else {
                    JOptionPane.showMessageDialog(null, "No se ha agregado la carrera");
                    limpiar();
                }
            }
            //mandara un mensaje al usuario si los campos estan vacios
            else {
                vistaCarrera.labelMsg.setText("No se aceptan campos vacíos");
            }
        }       
        //aqui se compara si se presiono el boton modificar
        if (e.getSource() == vistaCarrera.btnModificar) {  
            //primero compara si estan vacios los campos de texto
            if (!vistaCarrera.txtId.getText().equals("") && !vistaCarrera.txtNombre.equals("") && 
                !vistaCarrera.txtFacultad.equals("")) {            
                carrera.setIdCarrera(vistaCarrera.txtId.getText());
                carrera.setNombreCarrera(vistaCarrera.txtNombre.getText());
                carrera.setFacultad(vistaCarrera.txtFacultad.getText());
                if (conC.modificar(carrera)) {
                    JOptionPane.showMessageDialog(null, "Carrera modificada correctamente");
                    limpiar();
                    reiniciar();
                    cargarTabla(vistaCarrera.tableCarreras);
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha modificado la carrera");
                    limpiar();
                }
            }
            else {
                //mandara un mensaje al usuario si los campos estan vacios
                vistaCarrera.labelMsg.setText("No se ha modificado nada, por favor seleccione un dato de la tabla");
            }
        }
        //aqui se compara si se presiono el boton eliminar
        if (e.getSource() == vistaCarrera.btnEliminar) {
            //primero compara si estan vacios los campos de texto
            if (!vistaCarrera.txtId.getText().equals("") && !vistaCarrera.txtNombre.equals("") && 
                !vistaCarrera.txtFacultad.equals("")) {
                int msg = JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar la carrera seleccionada?", "Confirmar eliminar carrera", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);       
                if (msg == JOptionPane.YES_OPTION) {
                    carrera.setIdCarrera(vistaCarrera.txtId.getText());
                    if (conC.eliminar(carrera)) {
                        JOptionPane.showMessageDialog(null, "Carrera eliminada correctamente");
                        limpiar();
                        reiniciar();
                        cargarTabla(vistaCarrera.tableCarreras);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha podido eliminar la carrera");
                        limpiar();
                    }
                }             
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaCarrera.labelMsg.setText("No se ha eliminado nada, por favor seleccione un dato de la tabla");
            }
        }       
        //aqui se compara si se presiono el boton limpiar
        if(e.getSource() == vistaCarrera.btnLimpiar){
            limpiar();       
        }
        //aqui se compara si se presiono el boton actualizar
        if (e.getSource() == vistaCarrera.btnActualizar) {
            reiniciar();
            cargarTabla(vistaCarrera.tableCarreras);  
        }
    }
    
    //evento para pasar los datos de la tabla a los campos de los textos
    @Override
    public void mouseClicked(MouseEvent e){
        vistaCarrera.btnEliminar.setEnabled(true);
        vistaCarrera.btnModificar.setEnabled(true);
        vistaCarrera.btnGuardar.setEnabled(false);       
        ps = null;
        rs = null;
        try{
            Conexion ObjCon = new Conexion();
            com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) ObjCon.getConnection();
            int fila = vistaCarrera.tableCarreras.getSelectedRow();
            String id = vistaCarrera.tableCarreras.getValueAt(fila, 0).toString();           
            ps = conn.prepareStatement("SELECT idCarrera, nomCarrera, facultad FROM carrera WHERE idCarrera=?");
            ps.setString(1, id);
            rs = ps.executeQuery();           
            while(rs.next()){
                vistaCarrera.txtId.setText(rs.getString("idCarrera"));
                vistaCarrera.txtNombre.setText(rs.getString("nomCarrera"));
                vistaCarrera.txtFacultad.setText(rs.getString("facultad"));
            }
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);      
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
}
