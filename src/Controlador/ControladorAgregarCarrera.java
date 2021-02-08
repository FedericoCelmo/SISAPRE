/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Carrera;
import Modelo.ConsultasCarrera;
import Vista.VistaAgregarCarrera;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico
 */
public class ControladorAgregarCarrera implements ActionListener{
    
    private Carrera carrera;
    private ConsultasCarrera conC;
    private VistaAgregarCarrera vistaCarrera;
    
    //constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorAgregarCarrera(Carrera carrera, ConsultasCarrera conC, VistaAgregarCarrera vistaCarrera) {
        this.carrera = carrera;
        this.conC = conC;
        this.vistaCarrera = vistaCarrera;
        this.vistaCarrera.btnGuardar.addActionListener(this);
        this.vistaCarrera.btnCancelar.addActionListener(this);
    }   
    //inicia los componentes de la vista
    public void iniciar(){
        vistaCarrera.setTitle("Nueva Carrera");
        vistaCarrera.setLocationRelativeTo(null);
    }   
    //metodo para limpiar las caja de texto
    public void limpiar(){
        vistaCarrera.txtId.setText(null);
        vistaCarrera.txtNombre.setText(null);
        vistaCarrera.txtFacultad.setText(null);
        vistaCarrera.labelMsg.setText(null);
    }    
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
                } //De lo contrario se muestra el mensaje de error
                else {
                    JOptionPane.showMessageDialog(null, "No se ha agregado la carrera");
                    limpiar();
                }
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaCarrera.labelMsg.setText("No se aceptan campos vacíos");
            }
        }       
    }
}
