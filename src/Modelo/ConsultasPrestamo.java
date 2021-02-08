/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


/**
 *
 * @author Federico
 */
public class ConsultasPrestamo extends Conexion{
    //Con estos metodos se realizan las consultas a la base de datos
    public boolean registrar(Prestamo prestamo){        
        PreparedStatement ps = null;
        Connection con = getConnection();       
        String sql = "INSERT INTO prestamosalum(alumno_matricula, equipo_idEquipo, "
                + "fechaPrestamo, horaPrestamo, fechaUso, fechaDevolucion, horaIniUso, horaFinUso, status) VALUES(?,?,?,?,?,?,?,?,?)";        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, prestamo.getMatricula());
            ps.setInt(2, prestamo.getIdequipo());
            ps.setString(3, prestamo.getFechaPrestamo());
            ps.setString(4, prestamo.getHoraPrestamo());
            ps.setString(5, prestamo.getFechaUso());
            ps.setString(6, prestamo.getFechaDevolucion());
            ps.setString(7, prestamo.getHoraUso());
            ps.setString(8, prestamo.getHoraFinUso());
            ps.setString(9, prestamo.getStatus());
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
    public boolean modificar(Prestamo prestamo){
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "UPDATE prestamosalum SET alumno_matricula=?, equipo_idEquipo=?, fechaUso=?, fechaDevolucion=?, horaIniUso=?, horaFinUso=?, status=? WHERE folio=?";        
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, prestamo.getMatricula());
            ps.setInt(2, prestamo.getIdequipo());
            ps.setString(3, prestamo.getFechaUso());
            ps.setString(4, prestamo.getFechaDevolucion());
            ps.setString(5, prestamo.getHoraUso());
            ps.setString(6, prestamo.getHoraFinUso());
            ps.setString(7, prestamo.getStatus());
            ps.setInt(8, prestamo.getFolio());
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
    public boolean cancelar(Prestamo prestamo){
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
    
    public boolean devolverCompu(Prestamo prestamo){
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "UPDATE prestamosalum SET fechaDevolucion=?, horaFinUso=?, status=? WHERE folio=?";        
        try{
            ps = con.prepareStatement(sql);
            
            ps.setString(1, prestamo.getFechaDevolucion());
            ps.setString(2, prestamo.getHoraFinUso());
            ps.setString(3, prestamo.getStatus());
            ps.setInt(4, prestamo.getFolio());
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
    public boolean eliminar(Prestamo prestamo){
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "DELETE FROM prestamosalum WHERE folio=?";        
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, prestamo.getFolio());
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
    
    public boolean modificarStatusFechaUso(Prestamo prestamo, Equipo equipo){
        PreparedStatement ps = null;
        Connection con = getConnection();        
        String sql = "UPDATE prestamosalum, equipo SET prestamosalum.status=?, equipo.status=? WHERE prestamosalum.equipo_idEquipo=equipo.idEquipo AND prestamosalum.status='Reservado' AND prestamosalum.fechaUso <= NOW()";       
        try{
            ps = con.prepareStatement(sql);                     
            ps.setString(1, prestamo.getStatus());
            ps.setString(2, equipo.getStatus());
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
}
