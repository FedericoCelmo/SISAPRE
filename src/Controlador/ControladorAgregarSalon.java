/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasSalon;
import Modelo.Salon;
import Vista.VistaAgregarSalon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico
 */
public class ControladorAgregarSalon implements ActionListener{
    private Salon salon;
    private ConsultasSalon conS;
    private VistaAgregarSalon vistaSalon;
    
    //Constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorAgregarSalon(Salon salon, ConsultasSalon conS, VistaAgregarSalon vistaSalon){
        this.salon = salon;
        this.conS = conS;
        this.vistaSalon = vistaSalon;
        this.vistaSalon.btnGuardar.addActionListener(this);
        this.vistaSalon.btnCancelar.addActionListener(this);
    }   
    //inicia los componentes de la vista
    public void iniciar(){
        vistaSalon.setTitle("Nuevo Salón");
        vistaSalon.setLocationRelativeTo(null);
    }
    //metodo para limpiar las caja de texto
    public void limpiar(){
        vistaSalon.txtNombre.setText(null);
        vistaSalon.txtCapacidad.setText(null);
        vistaSalon.labelMsg.setText(null);
    }    
    //En este metodo se realizan las acciones de las consultas
    @Override
    public void actionPerformed(ActionEvent e) {
        //aqui se compara si se presiono el boton guardar
        if (e.getSource() == vistaSalon.btnGuardar) {  
            //primero compara si estan vacios los campos de texto
            if (!vistaSalon.txtNombre.getText().equals("") && !vistaSalon.txtCapacidad.equals("")) {
                salon.setNombreSala(vistaSalon.txtNombre.getText());
                salon.setCapacidad(Integer.parseInt(vistaSalon.txtCapacidad.getText()));
                //De ser valida la accion, se guarda el objeto y se muestra el mensaje
                if (conS.registrar(salon)) {
                    JOptionPane.showMessageDialog(null, "Salón agregado correctamente");
                    limpiar();
                } //De lo contrario se muestra el mensaje de error
                else {
                    JOptionPane.showMessageDialog(null, "No se ha agregado el salon");
                    limpiar();
                }
            }
            else{
                //mandara un mensaje al usuario si los campos estan vacios
                vistaSalon.labelMsg.setText("No se aceptan campos vacíos");
            }
        }
    }
}
