/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico
 */
public class ConsultasAlumno extends Conexion {   
    //Con estos metodos se realizan las consultas a la base de datos
    public boolean registrar(Alumno alumno){        
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "INSERT INTO alumno(matricula, nombre, apellidoP, apellidoM, carrera_idCarrera)VALUES(?,?,?,?,?)";       
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, alumno.getMatricula());
            ps.setString(2, alumno.getNombre());
            ps.setString(3, alumno.getApellidoPat());
            ps.setString(4, alumno.getApellidoMat());
            ps.setString(5, alumno.getCarrera());
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
    
    public boolean modificar(Alumno alumno){
        PreparedStatement ps = null;
        Connection con = getConnection();   
        String sql = "UPDATE alumno SET nombre=?, apellidoP=?, apellidoM=?, carrera_idCarrera=? WHERE matricula=?";        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellidoPat());
            ps.setString(3, alumno.getApellidoMat());
            ps.setString(4, alumno.getCarrera());
            ps.setString(5, alumno.getMatricula());
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
    
    public boolean eliminar(Alumno alumno){
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "DELETE FROM alumno WHERE matricula=?";       
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, alumno.getMatricula());
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
