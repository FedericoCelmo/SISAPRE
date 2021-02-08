/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico
 */
public class ConsultasEquipo extends Conexion{   
    //Con estos metodos se realizan las consultas a la base de datos
    public boolean registrar(Equipo equipo){        
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "INSERT INTO equipo(descripcion, numSerie, numInv, sala_idSala, status, tipo)VALUES(?,?,?,?,?,?)";        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, equipo.getDescripcion());
            ps.setString(2, equipo.getNumSerie());
            ps.setInt(3, equipo.getNumInventario());
            ps.setString(4, equipo.getIdSala());
            ps.setString(5, equipo.getStatus());
            ps.setString(6, equipo.getTipo());
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
    
    public boolean modificar(Equipo equipo){
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "UPDATE equipo SET descripcion=?, numSerie=?, numInv=?, sala_idSala=?, status=?, tipo=? WHERE idEquipo=?";      
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, equipo.getDescripcion());
            ps.setString(2, equipo.getNumSerie());
            ps.setInt(3, equipo.getNumInventario());
            ps.setString(4, equipo.getIdSala());
            ps.setString(5, equipo.getStatus());
            ps.setString(6, equipo.getTipo());
            ps.setInt(7, equipo.getId());
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
    
    public boolean eliminar(Equipo equipo){
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "DELETE FROM equipo WHERE idEquipo=?";        
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, equipo.getId());
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

