/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico
 */
public class ConsultasCarrera extends Conexion{
     //Con estos metodos se realizan las consultas a la base de datos
    public boolean registrar (Carrera carrera){        
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "INSERT INTO carrera(idCarrera, nomCarrera, facultad)VALUES(?,?,?)";       
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, carrera.getIdCarrera());
            ps.setString(2, carrera.getNombreCarrera());
            ps.setString(3, carrera.getFacultad());
            ps.execute();
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        finally{
            try{
                con.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
    }
    
    public boolean modificar(Carrera carrera){
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "UPDATE carrera SET nomCarrera=?, facultad=? WHERE idCarrera=?";        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, carrera.getNombreCarrera());
            ps.setString(2, carrera.getFacultad());
            ps.setString(3, carrera.getIdCarrera());
            ps.execute();
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        finally{
            try{
                con.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }       
        }
    }
    
    public boolean eliminar(Carrera carrera){
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "DELETE FROM carrera WHERE idCarrera=?";        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, carrera.getIdCarrera());
            ps.execute();
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        finally{
            try{
                con.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }       
        }
    }
}
