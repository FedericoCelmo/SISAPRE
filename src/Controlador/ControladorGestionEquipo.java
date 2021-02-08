/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasEquipo;
import Modelo.Equipo;
import Vista.VistaGestionEquipo;
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
public class ControladorGestionEquipo extends Conexion implements ActionListener, KeyListener, MouseListener {
    
    private Equipo equipo;
    private ConsultasEquipo conE;
    private VistaGestionEquipo vistaEquipo;
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();// variable del modelo de la tabla
    
    //constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorGestionEquipo(Equipo equipo, ConsultasEquipo conE, VistaGestionEquipo vistaEquipo){
        this.equipo = equipo;
        this.conE = conE;
        this.vistaEquipo = vistaEquipo;
        this.vistaEquipo.btnGuardar.addActionListener(this);
        this.vistaEquipo.btnLimpiar.addActionListener(this);
        this.vistaEquipo.btnModificar.addActionListener(this);
        this.vistaEquipo.btnEliminar.addActionListener(this);
        this.vistaEquipo.btnActualizar.addActionListener(this);
        this.vistaEquipo.tableEquipos.addMouseListener(this);
        this.vistaEquipo.txtBuscar.addKeyListener(this);
    }    
    //aqui se cargan todos los componentes que se iniciarian cuando se abra la vista
    public void iniciar(){
        vistaEquipo.setTitle("Gestión de Equipos");
        vistaEquipo.setLocationRelativeTo(null);
        vistaEquipo.btnEliminar.setEnabled(false);
        vistaEquipo.btnModificar.setEnabled(false);
        //nombre de las columnas
        modelo.addColumn("ID");//les asigno nombres a las columnas
        modelo.addColumn("Descripcion");//les asigno nombres a las columnas
        modelo.addColumn("Numero de Serie");//les asigno nombres a las columnas
        modelo.addColumn("Número Inventario");//les asigno nombres a las columnas
        modelo.addColumn("Ubicación");//les asigno nombres a las columnas
        modelo.addColumn("Estatus");//les asigno nombres a las columnas
        modelo.addColumn("Tipo");//les asigno nombres a las columnas
        cargarTabla(vistaEquipo.tableEquipos);
    }     
    //metodo que sirve para cargar los datos de la tabla
    public void cargarTabla(JTable tableEquipos){        
        tableEquipos.setModel(modelo); 
        try{                      
            ps = null;
            rs = null;
            Conexion conn = new Conexion();
            Connection con = (Connection) conn.getConnection();          
            String sql = "SELECT idEquipo, descripcion, numSerie, numInv, sala_idSala, status, tipo FROM equipo";// consulta de los datos de la tabla a la base de datos
            //String sql = "SELECT * FROM equipo, sala WHERE idEquipo=sala_idEquipo AND idEquipo=idSala";// consulta de los datos de la tabla a la base de datos
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();           
            int[] size = {30,120,95,100,60,80,50};//arreglo para asignar el ancho de las columnas
            //recorremos el arreglo y asignamos dichos valores
            for (int x = 0; x < cantidadColumnas; x++) {
                vistaEquipo.tableEquipos.getColumnModel().getColumn(x).setPreferredWidth(size[x]);            
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
        vistaEquipo.txtId.setText(null);
        vistaEquipo.txtDescripcion.setText(null);
        vistaEquipo.txtNumSerie.setText(null);
        vistaEquipo.txtCantidad.setText(null);
        vistaEquipo.txtBuscar.setText(null);
        vistaEquipo.labelMsg.setText(null);
        vistaEquipo.cbxSala.setSelectedIndex(0);
        vistaEquipo.cbxStatus.setSelectedIndex(0);
        vistaEquipo.cbxTipo.setSelectedIndex(0);
        vistaEquipo.btnEliminar.setEnabled(false);
        vistaEquipo.btnModificar.setEnabled(false);
        vistaEquipo.btnGuardar.setEnabled(true);
    }      
    //En este metodo se realizan las acciones de las consultas
    @Override
    public void actionPerformed(ActionEvent e) {
        Connection con = null;
        //aqui se compara si se presiono el boton guardar
        if (e.getSource() == vistaEquipo.btnGuardar) { 
            //primero compara si estan vacios los campos de texto
            if (!vistaEquipo.txtDescripcion.getText().equals("") && !vistaEquipo.txtNumSerie.equals("") && !vistaEquipo.txtCantidad.equals("")) {                
                //consulta que sirve para cotejar el nombre del salon con su id             
                String sql = "SELECT idSala, nomSala FROM sala WHERE nomSala=?";       
                try {
                    con = (Connection) cc.getConnection();
                    ps = con.prepareStatement(sql);
                    ps.setString(1, vistaEquipo.cbxSala.getSelectedItem().toString());
                    rs = ps.executeQuery();
                    String idSala = "";                   
                    if (rs.next()) {
                        //variable local para guardar el id y despues verificar que el nombre concuerda con ese id
                        idSala = rs.getString("idSala");                                              
                        if (rs.getString("idSala").equals(rs.getString(1))) {                          
                            equipo.setDescripcion(vistaEquipo.txtDescripcion.getText());
                            equipo.setNumSerie(vistaEquipo.txtNumSerie.getText());
                            equipo.setNumInventario(Integer.parseInt(vistaEquipo.txtCantidad.getText()));
                            equipo.setIdSala(rs.getString(1));
                            equipo.setStatus(vistaEquipo.cbxStatus.getSelectedItem().toString());
                            equipo.setTipo(vistaEquipo.cbxTipo.getSelectedItem().toString());
                            //De ser valida la accion, se guarda el objeto y se muestra el mensaje
                            if (conE.registrar(equipo)) {
                                JOptionPane.showMessageDialog(null, "Equipo agregado correctamente");
                                limpiar();
                                reiniciar();
                                cargarTabla(vistaEquipo.tableEquipos);
                            } //De lo contrario se muestra el mensaje de error
                            else {
                                JOptionPane.showMessageDialog(null, "No se ha agregado el equipo");
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
            else{
                vistaEquipo.labelMsg.setText("No se aceptan campos vacíos");
            }
        }        
        //aqui se compara si se presiono el boton modificar
        if (e.getSource() == vistaEquipo.btnModificar) {  
            //primero compara si estan vacios los campos de texto
            if (!vistaEquipo.txtDescripcion.equals("") && !vistaEquipo.txtNumSerie.equals("") && !vistaEquipo.txtCantidad.equals("")) {               
                //consulta que sirve para cotejar el nombre del salon con su id             
                String sql = "SELECT idSala, nomSala FROM sala WHERE nomSala=?";       
                try {
                    con = (Connection) cc.getConnection();
                    ps = con.prepareStatement(sql);
                    ps.setString(1, vistaEquipo.cbxSala.getSelectedItem().toString());
                    rs = ps.executeQuery();
                    String idSala = "";                   
                    if (rs.next()) {
                        //variable local para guradar el id y despues verificar que el nombre concuerda con ese id
                        idSala= rs.getString("idSala");                                                
                        if (rs.getString("idSala").equals(rs.getString(1))) {     
                            equipo.setId(Integer.parseInt(vistaEquipo.txtId.getText()));
                            equipo.setDescripcion(vistaEquipo.txtDescripcion.getText());
                            equipo.setNumSerie(vistaEquipo.txtNumSerie.getText());
                            equipo.setNumInventario(Integer.parseInt(vistaEquipo.txtCantidad.getText()));
                            equipo.setIdSala(rs.getString(1));
                            equipo.setStatus(vistaEquipo.cbxStatus.getSelectedItem().toString());
                            equipo.setTipo(vistaEquipo.cbxTipo.getSelectedItem().toString());
                            //De ser valida la accion, se guarda el objeto y se muestra el mensaje
                            if (conE.modificar(equipo)) {
                                JOptionPane.showMessageDialog(null, "Datos del equipo modificado correctamente");
                                limpiar();
                                reiniciar();
                                cargarTabla(vistaEquipo.tableEquipos);
                            } //De lo contrario se muestra el mensaje de error
                            else {
                                JOptionPane.showMessageDialog(null, "No se ha modificado los datos del equipo");
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
            vistaEquipo.labelMsg.setText("No se ha modificado nada, por favor seleccione un dato de la tabla");
            }
        }
        //aqui se compara si se presiono el boton eliminar
        if (e.getSource() == vistaEquipo.btnEliminar) {
            //primero compara si estan vacios los campos de texto
            if (!vistaEquipo.txtId.equals("")) {
                int msg = JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar el equipo seleccionado?", "Confirmar eliminar equipo", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);       
                if (msg == JOptionPane.YES_OPTION) {
                    equipo.setId(Integer.parseInt(vistaEquipo.txtId.getText()));
                    if (conE.eliminar(equipo)) {
                        JOptionPane.showMessageDialog(null, "Equipo eliminado correctamente");
                        limpiar();
                        reiniciar();
                        cargarTabla(vistaEquipo.tableEquipos);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha podido eliminar el equipo");
                        limpiar();
                    }
                }               
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaEquipo.labelMsg.setText("No se ha eliminado nada, por favor seleccione un dato de la tabla");
            }
        }       
        //aqui se compara si se presiono el boton limpiar
        if(e.getSource() == vistaEquipo.btnLimpiar){
            limpiar();       
        }
        //aqui se compara si se presiono el boton actualizar
        if (e.getSource() == vistaEquipo.btnActualizar) {
            reiniciar();
            cargarTabla(vistaEquipo.tableEquipos);  
        }
    }
    
    //evento para buscar datos de la base de datos por medio de la caja de texto "buscar"
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vistaEquipo.txtBuscar) {
            Connection con = null;           
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"ID", "Descripcion", "Numero de serie", "Número Inventario", "ID Salón", "Nombre", "Status"});                       
            String temp = "SELECT * FROM equipo, sala WHERE equipo.descripcion LIKE ? AND equipo.sala_idSala=idSala";        
            try {               
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, "%" + vistaEquipo.txtBuscar.getText() + "%");
                rs = ps.executeQuery();                      
                while (rs.next()) {                  
                    modelo.addRow(new Object[]{rs.getString("idEquipo"), rs.getString("descripcion"), rs.getString("numSerie"), rs.getString("numInv"), 
                        rs.getString("sala_idSala"), rs.getString("nomSala"), rs.getString("status"), rs.getString("tipo")});
                }
                vistaEquipo.tableEquipos.setModel(modelo);

            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
    }
    
    //evento para pasar los datos de la tabla a los campos de los textos
    @Override
    public void mouseClicked(MouseEvent e){
        vistaEquipo.btnEliminar.setEnabled(true);
        vistaEquipo.btnModificar.setEnabled(true);
        vistaEquipo.btnGuardar.setEnabled(false);
        ps = null;
        rs = null;
        try{
            Conexion ObjCon = new Conexion();
            com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) ObjCon.getConnection();
            int fila = vistaEquipo.tableEquipos.getSelectedRow();
            String id = vistaEquipo.tableEquipos.getValueAt(fila, 0).toString();            
            //ps = conn.prepareStatement("SELECT idEquipo, descripcion, numSerie, numInv, sala_idSala, status, tipo FROM equipo WHERE idEquipo=?");
            ps = conn.prepareStatement("SELECT idEquipo, descripcion, numSerie, numInv, sala_idSala, status, tipo, idSala, nomSala FROM equipo, sala WHERE idEquipo=? "
                    + "AND sala.idSala=sala_idSala");
            ps.setString(1, id);
            rs = ps.executeQuery();            
            while(rs.next()){
                vistaEquipo.txtId.setText(rs.getString("idEquipo"));
                vistaEquipo.txtDescripcion.setText(rs.getString("descripcion"));
                vistaEquipo.txtNumSerie.setText(rs.getString("numSerie"));
                vistaEquipo.txtCantidad.setText(rs.getString("numInv"));
                vistaEquipo.cbxSala.setSelectedItem(rs.getString("nomSala"));
                vistaEquipo.cbxStatus.setSelectedItem(rs.getString("status"));
                vistaEquipo.cbxTipo.setSelectedItem(rs.getString("tipo"));
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
