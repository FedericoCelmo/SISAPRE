/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasSalon;
import Modelo.Salon;
import Vista.VistaGestionSalon;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class ControladorGestionSalon implements ActionListener, MouseListener{
    
    private Salon salon;
    private ConsultasSalon conS;
    private VistaGestionSalon vistaSalon;
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();
    
    //constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorGestionSalon(Salon salon, ConsultasSalon conS, VistaGestionSalon vistaSalon) {
        this.salon = salon;
        this.conS = conS;
        this.vistaSalon = vistaSalon;
        this.vistaSalon.btnGuardar.addActionListener(this);
        this.vistaSalon.btnActualizar.addActionListener(this);
        this.vistaSalon.btnLimpiar.addActionListener(this);
        this.vistaSalon.btnModificar.addActionListener(this);
        this.vistaSalon.btnEliminar.addActionListener(this);
        this.vistaSalon.tableSalon.addMouseListener(this);
    }    
     //aqui se cargan todos los componentes que se iniciarian cuando se abra la vista
    public void iniciar(){
        vistaSalon.setTitle("Gestión de Salones");
        vistaSalon.setLocationRelativeTo(null);
        //nombre de las columnas
        modelo.addColumn("ID");//les asigno nombres a las columnas
        modelo.addColumn("Nombre");//les asigno nombres a las columnas
        modelo.addColumn("Capacidad");//les asigno nombres a las columnas
        cargarTabla(vistaSalon.tableSalon); 
        vistaSalon.btnEliminar.setEnabled(false);
        vistaSalon.btnModificar.setEnabled(false);
    }    
    //metodo que sirve para cargar los datos de la tabla
    public void cargarTabla(JTable tableSalon){        
        tableSalon.setModel(modelo);      
        try{                       
            ps = null;
            rs = null;
            Conexion conn = new Conexion();
            Connection con = (Connection) conn.getConnection();
            String sql = "SELECT idSala, nomSala, capacidad FROM sala";// consulta de los datos de la tabla a la base de datos
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            int[] size = {30, 100, 50};//arreglo para asignar el ancho de las columnas
            //recorremos el arreglo y asignamos dichos valores
            for (int x = 0; x < cantidadColumnas; x++) {
                vistaSalon.tableSalon.getColumnModel().getColumn(x).setPreferredWidth(size[x]);
            }
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modelo.addRow(filas);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }   
    //metodo para reinicar los datos de la tabla y evitar que se dupliquen los datos
    public void reiniciar() {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
    } 
    @Override
    public void actionPerformed(ActionEvent e){       
        if (e.getSource() == vistaSalon.btnGuardar) {    
            //primero compara si estan vacios los campos de texto
            if (!vistaSalon.txtNombre.getText().equals("") && !vistaSalon.txtCapacidad.equals("")) {                
                salon.setNombreSala(vistaSalon.txtNombre.getText());
                salon.setCapacidad(Integer.parseInt(vistaSalon.txtCapacidad.getText()));
                //De ser valida la accion, se guarda el objeto y se muestra el mensaje
                if (conS.registrar(salon)) {
                    JOptionPane.showMessageDialog(null, "Salón agregado correctamente");
                    limpiar();
                    reiniciar();
                    cargarTabla(vistaSalon.tableSalon); 
                } //De lo contrario se muestra el mensaje de error
                else {
                    JOptionPane.showMessageDialog(null, "No se ha agregado el salón");
                    limpiar();
                }
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaSalon.labelMsg.setText("No se aceptan campos vacíos");
            }
        }
        if (e.getSource() == vistaSalon.btnModificar) {
            //primero compara si estan vacios los campos de texto
            if (!vistaSalon.txtId.getText().equals("") && !vistaSalon.txtNombre.getText().equals("") && !vistaSalon.txtCapacidad.equals("")) {
                salon.setId(Integer.parseInt(vistaSalon.txtId.getText()));
                salon.setNombreSala(vistaSalon.txtNombre.getText());
                salon.setCapacidad(Integer.parseInt(vistaSalon.txtCapacidad.getText()));                
                if (conS.modificar(salon)) {
                    JOptionPane.showMessageDialog(null, "Salón modificado correctamente");
                    limpiar();
                    reiniciar();
                    cargarTabla(vistaSalon.tableSalon); 
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha modificado el salón");
                    limpiar();
                }
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaSalon.labelMsg.setText("Para continuar, seleccione un dato de la tabla");
            }
        }       
        if (e.getSource() == vistaSalon.btnEliminar) {
            //primero compara si estan vacios los campos de texto
            if (!vistaSalon.txtId.getText().equals("")) {
                int msg = JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar el salón seleccionado?", "Confirmar eliminar salón", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);       
                if (msg == JOptionPane.YES_OPTION) {
                    salon.setId(Integer.parseInt(vistaSalon.txtId.getText()));
                    if (conS.eliminar(salon)) {
                        JOptionPane.showMessageDialog(null, "Salón eliminado correctamente");
                        limpiar();
                        reiniciar();
                        cargarTabla(vistaSalon.tableSalon);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha podido eliminar el salón");
                        limpiar();                      
                    }
                }               
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaSalon.labelMsg.setText("Para continuar, seleccione un dato de la tabla");
            }
        }
        //aqui se compara si se presiono el boton limpiar
        if(e.getSource() == vistaSalon.btnLimpiar){
            limpiar();       
        }
        //aqui se compara si se presiono el boton actualizar
        if(e.getSource() == vistaSalon.btnActualizar){
            reiniciar();
            cargarTabla(vistaSalon.tableSalon);       
        }   
    }
    
    //evento para pasar los datos de la tabla a los campos de texto
    @Override
    public void mouseClicked(MouseEvent e){
        vistaSalon.btnEliminar.setEnabled(true);
        vistaSalon.btnModificar.setEnabled(true);
        vistaSalon.btnGuardar.setEnabled(false);
        ps = null;
        rs = null;
        try {
            Conexion ObjCon = new Conexion();
            Connection conn = (Connection) ObjCon.getConnection();
            int fila = vistaSalon.tableSalon.getSelectedRow();
            String id = vistaSalon.tableSalon.getValueAt(fila, 0).toString();

            ps = conn.prepareStatement("SELECT idSala, nomSala, capacidad FROM sala WHERE idSala=?");
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                vistaSalon.txtId.setText(rs.getString("idSala"));
                vistaSalon.txtNombre.setText(rs.getString("nomSala"));
                vistaSalon.txtCapacidad.setText(rs.getString("capacidad"));
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }     
    }
    
    //metodo para vaciar los campos de texto
    public void limpiar(){
        vistaSalon.txtId.setText(null);
        vistaSalon.txtNombre.setText(null);
        vistaSalon.txtCapacidad.setText(null);
        vistaSalon.labelMsg.setText(null);
        vistaSalon.btnEliminar.setEnabled(false);
        vistaSalon.btnModificar.setEnabled(false);
        vistaSalon.btnGuardar.setEnabled(true);
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
