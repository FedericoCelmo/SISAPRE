/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controlador.ControladorGestionUsuario;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.VistaGestionUsuario;
import Vista.VistaBienvenida;
import Vista.VistaLogin;
import javax.swing.UIManager;

/**
 *
 * @author Federico
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        /*VistaLogin vista = new VistaLogin();
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);*/
      
        VistaBienvenida vista = new VistaBienvenida();
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        
    }
    
}
