/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasEquipo;
import Modelo.ConsultasEquipoInterno;
import Modelo.Equipo;
import Modelo.EquipoInterno;
import Vista.VistaConfiguracionEquipo;
import Vista.VistaGestionEquipo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ControladorEquipoInterno extends Conexion implements ActionListener, MouseListener{
    
    private EquipoInterno equipo;
    private ConsultasEquipoInterno conE;
    private VistaConfiguracionEquipo vistaE;
    Conexion conn = new Conexion();
    Connection con = (Connection) conn.getConnection();  
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();// variable del modelo de la tabla
    
    //constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorEquipoInterno(EquipoInterno equipo, ConsultasEquipoInterno conE, VistaConfiguracionEquipo vistaE){
        this.equipo = equipo;
        this.conE = conE;
        this.vistaE = vistaE;
        this.vistaE.btnGuardar.addActionListener(this);
        this.vistaE.btnLimpiar.addActionListener(this);
        this.vistaE.btnModificar.addActionListener(this);        
        this.vistaE.btnActualizar.addActionListener(this);
        this.vistaE.tableEquipos.addMouseListener(this);        
    }    
     //aqui se cargan todos los componentes que se iniciarian cuando se abra la vista
    public void iniciar(){
        //vistaE.setTitle("Gestión de Equipos");
        vistaE.setLocationRelativeTo(null);     
        vistaE.btnModificar.setEnabled(false);
        //nombre de las columnas
        modelo.addColumn("No. Computadora");//les asigno nombres a las columnas
        modelo.addColumn("Número de Inventario");//les asigno nombres a las columnas       
        cargarTabla(vistaE.tableEquipos);
    }      
    //metodo que sirve para cargar los datos de la tabla
    public void cargarTabla(JTable tableEquipos){        
        tableEquipos.setModel(modelo); 
        try{                      
            ps = null;
            rs = null;
            Conexion conn = new Conexion();
            Connection con = (Connection) conn.getConnection();          
            String sql = "SELECT idEquipo, numInv FROM equipointerno";// consulta de los datos de la tabla a la base de datos
            //String sql = "SELECT * FROM equipo, sala WHERE idEquipo=sala_idEquipo AND idEquipo=idSala";// consulta de los datos de la tabla a la base de datos
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();           
            int[] size = {30,120};//arreglo para asignar el ancho de las columnas
            //recorremos el arreglo y asignamos dichos valores
            for (int x = 0; x < cantidadColumnas; x++) {
                vistaE.tableEquipos.getColumnModel().getColumn(x).setPreferredWidth(size[x]);            
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
        vistaE.txtIdEquipo.setText(null);   
        vistaE.txtNumInv.setText(null);    
        vistaE.labelMsg.setText(null);
        vistaE.btnModificar.setEnabled(false);
        vistaE.btnGuardar.setEnabled(true);
    }   
     //En este metodo se realizan las acciones de las consultas
    @Override
    public void actionPerformed(ActionEvent e) {
        //aqui se compara si se presiono el boton guardar
        if (e.getSource() == vistaE.btnGuardar) { 
            Connection con = null;
            //consulta para obtener el numero de datos de la tabla
            String temp = "SELECT COUNT(*) AS max FROM equipointerno";//consulta para verificar el id del equipo
            //primero compara si estan vacios los campos de texto
            if (!vistaE.txtNumInv.getText().equals("")) {                           
                try {
                    con = (Connection) cc.getConnection();
                    ps = con.prepareStatement(temp);
                    rs = ps.executeQuery();
                    //itera el resultado
                    if (rs.next()) {
                        //asigna el numero total de datos una variable entera
                        int m = Integer.parseInt(rs.getString("max"));
                        //si ya hay 30 registros
                        if (m<30) {                            
                            equipo.setNumInventario(vistaE.txtNumInv.getText());
                            //De ser valida la accion, se guarda el objeto y se muestra el mensaje
                            if (conE.registrar(equipo)) {
                                JOptionPane.showMessageDialog(null, "Asignación correcta de número de inventario");
                                limpiar();
                                reiniciar();
                                cargarTabla(vistaE.tableEquipos);
                            } //De lo contrario se muestra el mensaje de error
                            else {
                                JOptionPane.showMessageDialog(null, "No se ha asignado el número de inventario del equipo");
                                limpiar();
                            }
                        }
                        else{
                            vistaE.labelMsg.setText("No se permiten mas de 30 registros");
                        }
                    }
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                }
            }
            //mandara un mensaje al usuario si los campos estan vacios
            else{
                vistaE.labelMsg.setText("No se aceptan campos vacíos");
            }
        }        
        //aqui se compara si se presiono el boton modificar
        if (e.getSource() == vistaE.btnModificar) {  
            //primero compara si estan vacios los campos de texto
            if (!vistaE.txtNumInv.getText().equals("")) {               
                equipo.setId(Integer.parseInt(vistaE.txtIdEquipo.getText()));
                equipo.setNumInventario(vistaE.txtNumInv.getText());
                if (conE.modificar(equipo)) {
                    JOptionPane.showMessageDialog(null, "Equipo modificado correctamente");
                    limpiar();
                    reiniciar();
                    cargarTabla(vistaE.tableEquipos);
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha modificado el equipo");
                    limpiar();
                }
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaE.labelMsg.setText("No se ha modificado nada, por favor seleccione un dato de la tabla");
            }
        }       
        //aqui se compara si se presiono el boton limpiar
        if(e.getSource() == vistaE.btnLimpiar){
            limpiar();       
        }
        //aqui se compara si se presiono el boton actualizar
        if (e.getSource() == vistaE.btnActualizar) {
            reiniciar();
            cargarTabla(vistaE.tableEquipos);  
        }
    }    
    //evento para pasar los datos de la tabla a los campos de los textos
    @Override
    public void mouseClicked(MouseEvent e){     
        vistaE.btnModificar.setEnabled(true);
        vistaE.btnGuardar.setEnabled(false);
        ps = null;
        rs = null;
        try{
            Conexion ObjCon = new Conexion();
            com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) ObjCon.getConnection();
            int fila = vistaE.tableEquipos.getSelectedRow();
            String id = vistaE.tableEquipos.getValueAt(fila, 0).toString();           
            ps = conn.prepareStatement("SELECT idEquipo, numInv FROM equipointerno WHERE idEquipo=?");
            ps.setString(1, id);
            rs = ps.executeQuery();           
            while(rs.next()){
                vistaE.txtIdEquipo.setText(rs.getString("idEquipo"));
                vistaE.txtNumInv.setText(rs.getString("numInv"));               
            }
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);     
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
}
