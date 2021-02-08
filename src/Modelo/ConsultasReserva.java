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
public class ConsultasReserva extends Conexion {
    
    //Con estos metodos se realizan las consultas a la base de datos
    public boolean registrar(Reserva reserva){       
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "INSERT INTO prestamossalon(alumno_matricula, sala_idSala, "
                + "fechaPrestamo, fechaUso, fechaDevolucion, observacion, status) VALUES(?,?,?,?,?,?,?)";       
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, reserva.getMatricula());
            ps.setInt(2, reserva.getIdSala());
            ps.setString(3, reserva.getFechaPrestamo());     
            ps.setString(4, reserva.getFechaUso());
            ps.setString(5, reserva.getFechaDevolucion());           
            ps.setString(6, reserva.getObservacion());
            ps.setString(7, reserva.getStatus());
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
    //metodo para actualizar un dato de la tabla de prestamos
    public boolean modificar(Reserva reserva){
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "UPDATE prestamossalon SET alumno_matricula=?, sala_idSala=?, fechaUso=?, fechaDevolucion=?, observacion=?, status=? WHERE folio=?";       
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, reserva.getMatricula());
            ps.setInt(2, reserva.getIdSala());          
            ps.setString(3, reserva.getFechaUso());
            ps.setString(4, reserva.getFechaDevolucion()); 
            ps.setString(5, reserva.getObservacion());
            ps.setString(6, reserva.getStatus());
            ps.setInt(7, reserva.getFolio());
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
    //metodo para actualizar el status del prestamo "devolver"
    public boolean devolver(Prestamo prestamo){
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "UPDATE prestamosalum SET status=? WHERE folio=?";        
        try{
            ps = con.prepareStatement(sql);           
            ps.setString(1, prestamo.getStatus());
            ps.setInt(2, prestamo.getFolio());
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
    //metodo para actualizar el status del prestamo "cancelar"
    public boolean cancelar(Reserva reserva){
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "UPDATE prestamossalon SET status=? WHERE folio=?";        
        try{
            ps = con.prepareStatement(sql);            
            ps.setString(1, reserva.getStatus());
            ps.setInt(2, reserva.getFolio());
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
    
    public boolean modificarStatusFechaUso(Reserva reserva){
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "UPDATE prestamossalon SET status=? WHERE status='Reservado' AND fechaUso <= NOW()";        
        try{
            ps = con.prepareStatement(sql);                     
            ps.setString(1, reserva.getStatus());
            //ps.setInt(2, reserva.getFolio());
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
    
    public boolean modificarStatusFechaFinUso(Reserva reserva){
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "UPDATE prestamossalon SET status=? WHERE status='En Uso' AND fechaDevolucion <= NOW()";       
        try{
            ps = con.prepareStatement(sql);                     
            ps.setString(1, reserva.getStatus());
            //ps.setInt(2, reserva.getFolio());
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
    
    public boolean establecerStatus(Reserva reserva){
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "UPDATE prestamossalon SET status=? WHERE folio=?";       
        try{
            ps = con.prepareStatement(sql);                     
            ps.setString(1, reserva.getStatus());
            ps.setInt(2, reserva.getFolio());
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
    
    //metodo para eliminar un dato de la tabla de prestamos
    public boolean eliminar(Reserva reserva){
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "DELETE FROM prestamossalon WHERE folio=?";        
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, reserva.getFolio());
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
