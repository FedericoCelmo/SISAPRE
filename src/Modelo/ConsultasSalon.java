/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico
 */
public class ConsultasSalon extends Conexion{
    
    public boolean registrar(Salon salon){       
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "INSERT INTO Sala(nomSala, capacidad)VALUES(?,?)";       
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, salon.getNombreSala());
            ps.setInt(2, salon.getCapacidad());           
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
    
    public boolean modificar(Salon salon){
        PreparedStatement ps = null;
        Connection con = getConnection();
        
        String sql = "UPDATE sala SET nomSala=?, capacidad=? WHERE idSala=?";
        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, salon.getNombreSala());
            ps.setInt(2, salon.getCapacidad());
            ps.setInt(3, salon.getId());
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
    
    public boolean eliminar(Salon salon){
        PreparedStatement ps = null;
        Connection con = getConnection();      
        String sql = "DELETE FROM sala WHERE idSala=?";        
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, salon.getId());
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
