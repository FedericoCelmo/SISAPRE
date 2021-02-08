/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasUsuario;
import Modelo.Hash;
import Modelo.Usuario;
import Vista.VistaGestionUsuario;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import static java.util.Objects.hash;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Federico
 */
public class ControladorGestionUsuario implements ActionListener, MouseListener {
    
    private Usuario usuario;
    private ConsultasUsuario conU;
    private VistaGestionUsuario vistaUsuario;
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();// variable del modelo de la tabla
    
    //constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorGestionUsuario(Usuario usuario, ConsultasUsuario conU, VistaGestionUsuario vistaUsuario){
        this.usuario = usuario;
        this.conU = conU;
        this.vistaUsuario = vistaUsuario;
        this.vistaUsuario.btnGuardar.addActionListener(this);
        this.vistaUsuario.btnLimpiar.addActionListener(this);
        this.vistaUsuario.btnModificar.addActionListener(this);
        this.vistaUsuario.btnEliminar.addActionListener(this);
        this.vistaUsuario.btnCargar.addActionListener(this);
        this.vistaUsuario.jTableUsuario.addMouseListener(this);        
    }       
    //aqui se cargan todos los componentes que se iniciarian cuando se abra la vista
    public void iniciar(){
        vistaUsuario.setTitle("Gestión de Usuarios");
        vistaUsuario.setLocationRelativeTo(null);
        modelo.addColumn("ID");//les asigno nombres a las columnas
        modelo.addColumn("Nick");//les asigno nombres a las columnas
        modelo.addColumn("Password");//les asigno nombres a las columnas
        modelo.addColumn("Tipo de Usuario");//les asigno nombres a las columnas
        cargarTabla(vistaUsuario.jTableUsuario);
        vistaUsuario.btnEliminar.setEnabled(false);
        vistaUsuario.btnModificar.setEnabled(false);        
    }
    
    //metodo que sirve para cargar los datos de la tabla
    public void cargarTabla(JTable jTableUsuario){       
        jTableUsuario.setModel(modelo); 
        try{                      
            ps = null;
            rs = null;
            Conexion conn = new Conexion();
            Connection con = (Connection) conn.getConnection();           
            String sql = "SELECT idusuario, nick, password, tipousuario FROM usuario";// consulta de los datos de la tabla a la base de datos
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();           
            int[] size = {40,80,80,80,40,100,100};//arreglo para asignar el ancho de las columnas
            //recorremos el arreglo y asignamos dichos valores
            for (int x = 0; x < cantidadColumnas; x++) {
                vistaUsuario.jTableUsuario.getColumnModel().getColumn(x).setPreferredWidth(size[x]);            
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
        String pass = new String(vistaUsuario.txtPassword.getText()); 
        String nuevopass = Hash.sha1(pass);
        //aqui se compara si se presiono el boton guardar
        if (e.getSource() == vistaUsuario.btnGuardar) { 
            //primero compara si estan vacios los campos de texto
            if (!vistaUsuario.txtNick.getText().equals("") && !vistaUsuario.txtPassword.equals("") && !vistaUsuario.cbxTipo.equals("")) {
                usuario.setNick(vistaUsuario.txtNick.getText());
                usuario.setPassword(nuevopass);
                usuario.setTipoUsuario(vistaUsuario.cbxTipo.getSelectedItem().toString());
                //De ser valida la accion, se guarda el objeto y se muestra el mensaje
                if (conU.registrar(usuario)) {
                    JOptionPane.showMessageDialog(null, "Usuario agregado correctamente");
                    limpiar();
                    reiniciar();
                    cargarTabla(vistaUsuario.jTableUsuario); 
                } //De lo contrario se muestra el mensaje de error
                else {
                    JOptionPane.showMessageDialog(null, "No se ha agregado el usuario");
                    limpiar();
                }
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaUsuario.labelMsg.setText("No se aceptan campos vacíos");
            }
        }
        if (e.getSource() == vistaUsuario.btnModificar) {
            //primero compara si estan vacios los campos de texto
            if (!vistaUsuario.txtNick.getText().equals("") && !vistaUsuario.txtPassword.equals("") && !vistaUsuario.cbxTipo.equals("")) {
                usuario.setId(Integer.parseInt(vistaUsuario.txtId.getText()));
                usuario.setNick(vistaUsuario.txtNick.getText());
                usuario.setPassword(nuevopass);
                usuario.setTipoUsuario(vistaUsuario.cbxTipo.getSelectedItem().toString());
                if (conU.modificar(usuario)) {
                    JOptionPane.showMessageDialog(null, "Usuario modificado correctamente");
                    limpiar();
                    reiniciar();
                    cargarTabla(vistaUsuario.jTableUsuario); 
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha modificado el usuario");
                    limpiar();
                }
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaUsuario.labelMsg.setText("Para continuar, seleccione un dato de la lista");
            }
        }       
        if (e.getSource() == vistaUsuario.btnEliminar) {
            //primero compara si estan vacios los campos de texto
            if (!vistaUsuario.txtNick.getText().equals("") && !vistaUsuario.txtPassword.equals("") && !vistaUsuario.cbxTipo.equals("")) {
                int msg = JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar el usuario seleccionado?", "Confirmar eliminar usuario", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);       
                if (msg == JOptionPane.YES_OPTION) {
                    usuario.setId(Integer.parseInt(vistaUsuario.txtId.getText()));
                    if (conU.eliminar(usuario)) {
                        JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
                        limpiar();
                        reiniciar();
                        cargarTabla(vistaUsuario.jTableUsuario);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha podido eliminar el usuario");
                        limpiar();
                    }
                }              
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaUsuario.labelMsg.setText("Para continuar, seleccione un dato de la tabla");
            }
        }
        //aqui se compara si se presiono el boton limpiar
        if(e.getSource() == vistaUsuario.btnLimpiar){
            limpiar();       
        }
        //aqui se compara si se presiono el boton actualizar
        if (e.getSource() == vistaUsuario.btnCargar) {
            reiniciar();
            cargarTabla(vistaUsuario.jTableUsuario);  
        }
    }
    //metodo para vaciar los campos de texto 
    public void limpiar(){
        vistaUsuario.txtId.setText(null);
        vistaUsuario.txtNick.setText(null);
        vistaUsuario.txtPassword.setText(null);
        vistaUsuario.labelMsg.setText(null);
        vistaUsuario.btnEliminar.setEnabled(false);
        vistaUsuario.btnModificar.setEnabled(false);
        vistaUsuario.btnGuardar.setEnabled(true);
    }
    
    //evento para pasar los datos de la tabla a los campos de los textos
    @Override
    public void mouseClicked(MouseEvent e) {
        vistaUsuario.btnEliminar.setEnabled(true);
        vistaUsuario.btnModificar.setEnabled(true);
        vistaUsuario.btnGuardar.setEnabled(false);
        ps = null;
        rs = null;      
        try{
            Conexion ObjCon = new Conexion();
            com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) ObjCon.getConnection();
            int fila = vistaUsuario.jTableUsuario.getSelectedRow();
            String id = vistaUsuario.jTableUsuario.getValueAt(fila, 0).toString();           
            ps = conn.prepareStatement("SELECT idusuario, nick, tipousuario FROM usuario WHERE idusuario=?");
            ps.setString(1, id);
            rs = ps.executeQuery();            
            while(rs.next()){
                
                vistaUsuario.txtId.setText(rs.getString("idusuario"));
                vistaUsuario.txtNick.setText(rs.getString("nick"));              
                vistaUsuario.cbxTipo.setSelectedItem(rs.getString("tipousuario"));              
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
