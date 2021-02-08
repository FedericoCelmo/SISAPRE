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
public class ConsultasEquipoInterno extends Conexion{
    
    //Con estos metodos se realizan las consultas a la base de datos
    public boolean registrar(EquipoInterno equipo){       
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "INSERT INTO equipointerno(numInv)VALUES(?)";        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, equipo.getNumInventario());
           
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
    
    public boolean modificar(EquipoInterno equipo){
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "UPDATE equipointerno SET numInv=? WHERE idEquipo=?";      
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, equipo.getNumInventario());           
            ps.setInt(2, equipo.getId());
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
    
    public boolean establecerStatus(Equipo equipo){
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "UPDATE equipo SET status=? WHERE idEquipo=?";       
        try{
            ps = con.prepareStatement(sql);            
            ps.setString(1, equipo.getStatus());
            ps.setInt(2, equipo.getId());
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
