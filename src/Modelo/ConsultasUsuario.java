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
public class ConsultasUsuario extends Conexion{
    //Con estos metodos se realizan las consultas a la base de datos   
    //metodo para registrar un usuario en la base de datos
    public boolean registrar(Usuario usuario){
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "INSERT INTO usuario(nick, password, tipousuario)VALUES(?,?,?)";        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getNick());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getTipoUsuario());
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
    //metodo de consulta para modificar un usuario
    public boolean modificar(Usuario usuario){
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "UPDATE usuario SET nick=?, password=?, tipousuario=? WHERE idusuario=?";       
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getNick());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getTipoUsuario());
            ps.setInt(4, usuario.getId());
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
    //metodo para consultar los datos del usuario para poder eliminar
    public boolean eliminar(Usuario usuario){
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "DELETE FROM usuario WHERE idusuario=?";       
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, usuario.getId());
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
    //metodo para consultar los datos del usuario para que pueda iniciar sesion
    public boolean login(Usuario usuario){    
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();        
        String sql = "SELECT idusuario, nick, password, tipousuario FROM usuario WHERE nick=?";        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getNick());           
            rs = ps.executeQuery();           
            if (rs.next()) {
                if (usuario.getPassword().equals(rs.getString(3))) {
                    usuario.setId(rs.getInt(1));
                    usuario.setNick(rs.getString(2));
                    usuario.setTipoUsuario(rs.getString(4));
                    return true;
                }
                else{
                    return false;
                }                
            }           
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
        return false;
    }    
}
