/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Alumno;
import Modelo.Conexion;
import Modelo.ConsultasAlumno;
import Vista.VistaAgregarAlumno;
import Vista.VistaGestionAlumno;
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
public class ControladorAgregarAlumno extends Conexion implements ActionListener{
    
    private Alumno alumno;
    private ConsultasAlumno conA;
    private VistaAgregarAlumno vistaAlumno;
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    
    //constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorAgregarAlumno(Alumno alumno, ConsultasAlumno conA, VistaAgregarAlumno vistaAlumno){
        this.alumno = alumno;
        this.conA = conA;
        this.vistaAlumno = vistaAlumno;
        this.vistaAlumno.btnGuardar.addActionListener(this);
        this.vistaAlumno.btnCancelar.addActionListener(this);
        
    }
    //inicia los componentes de la vista
    public void iniciar(){
        vistaAlumno.setTitle("Nuevo Alumno");
        vistaAlumno.setLocationRelativeTo(null);
    }        
    @Override//En este metodo se realizan las acciones de las consultas
    public void actionPerformed(ActionEvent e){
        Connection con = null;
        //aqui se compara si se presiono el boton guardar
        if (e.getSource() == vistaAlumno.btnGuardar) {  
            //primero compara si estan vacios los campos de texto
            if (!vistaAlumno.txtMatricula.getText().equals("") && !vistaAlumno.txtNombre.equals("") && 
                !vistaAlumno.txtApellidoP.equals("") && !vistaAlumno.txtApellidoM.equals("") && !vistaAlumno.cbxCarrera.equals("")) {
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
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaAlumno.labelMsg.setText("No se aceptan campos vacíos");
            }
        }               
    }    
    //metodo para limpiar las caja de texto
    public void limpiar(){
        vistaAlumno.txtMatricula.setText(null);
        vistaAlumno.txtNombre.setText(null);
        vistaAlumno.txtApellidoP.setText(null);
        vistaAlumno.txtApellidoM.setText(null);
        vistaAlumno.labelMsg.setText(null);
        vistaAlumno.cbxCarrera.setSelectedIndex(0);
    }
}
