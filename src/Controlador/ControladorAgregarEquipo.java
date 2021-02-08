/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasEquipo;
import Modelo.Equipo;
import Vista.VistaAgregarEquipo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico
 */
public class ControladorAgregarEquipo extends Conexion implements ActionListener{
    private Equipo equipo;
    private ConsultasEquipo conE;
    private VistaAgregarEquipo vistaEquipo;
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    
    //Constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorAgregarEquipo(Equipo equipo, ConsultasEquipo conE, VistaAgregarEquipo vistaEquipo){
        this.equipo = equipo;
        this.conE = conE;
        this.vistaEquipo = vistaEquipo;
        this.vistaEquipo.btnGuardar.addActionListener(this);
        this.vistaEquipo.btnCancelar.addActionListener(this);
    }   
    //inicia los componentes de la vista
    public void iniciar(){
        vistaEquipo.setTitle("Nuevo Equipo");
        vistaEquipo.setLocationRelativeTo(null);
    }    
    //metodo para limpiar las caja de texto
    public void limpiar(){
        vistaEquipo.txtDescripcion.setText(null);
        vistaEquipo.txtNumSerie.setText(null);
        vistaEquipo.txtCantidad.setText(null);
        vistaEquipo.labelMsg.setText(null);
        vistaEquipo.cbxSala.setSelectedIndex(0);
        vistaEquipo.cbxStatus.setSelectedIndex(0);
        vistaEquipo.cbxTipo.setSelectedIndex(0);
    }    
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
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaEquipo.labelMsg.setText("No se aceptan campos vacíos");
            }
        }
    }
}
