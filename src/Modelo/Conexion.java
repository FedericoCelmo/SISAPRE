/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico
 */
public class Conexion {
    
    public static final String URL = "jdbc:mysql://localhost:3306/prestamos";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    
    PreparedStatement ps;
    ResultSet rs;
    int aux = 1;//esta variable sirve para comprobar el estado de conexion de la base de datos
    Connection con = null;

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }
    
    public  Connection getConnection(){
               
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //JOptionPane.showMessageDialog(null, "Coneccion Exitosa");
            aux = 1;
            System.out.println("Conexión Exitosa!");        
        }
        catch(Exception e){
            System.out.println(e);
            aux = 0;
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);      
        }
        return con;   
    }
}
