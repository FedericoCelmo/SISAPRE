/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Vista.VistaPrincipalAdmin;
import Vista.VistaTablaPrestamos;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Federico
 */
public class ControladorTablaPrestamos extends Conexion implements ActionListener{
    
    private VistaTablaPrestamos vista;   
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();// variable del modelo de las tablas

    public ControladorTablaPrestamos(VistaTablaPrestamos vista) {
        this.vista = vista;
        //se le añaden todos los componentes de la vista
        //botones
        //tablas que implementaran el mouseClicked
        //cajas de texto
        this.vista.btnImprimir.addActionListener(this);       
    }
    public void iniciar(){
        //vista.setTitle("Gestión de Alumnos");
        vista.setLocationRelativeTo(null);
        //nombre de las columnas
        modelo.addColumn("Folio");
        modelo.addColumn("Matricula");//les asigno nombres a las columnas        
        modelo.addColumn("ID Equipo");//les asigno nombres a las columnas
        modelo.addColumn("Fecha Préstamo"); 
        modelo.addColumn("Fecha Uso");
        modelo.addColumn("Fecha Devolución");       
        modelo.addColumn("Estatus");
        cargarTabla(vista.tableListaP);
        vista.btnImprimir.setVisible(false);
        
    }    
    public void imprimirTabla(JTable jTable, String cabecera,String piePagina, boolean showPrintDialog) {
       boolean fitWidth = true;
        boolean interactive = true;
        //Definimos el modo de impresión
        JTable.PrintMode modo = fitWidth ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;
        try {
            // mprimo la tabla          
            boolean complete = vista.tableListaP.print(modo,
                    new MessageFormat(cabecera),
                    new MessageFormat(piePagina),showPrintDialog,null,
                    interactive);
            if (complete) {
                // Mostramos el mensaje de impresión existosa
                JOptionPane.showMessageDialog(jTable,
                        "Impresión completa",
                        "Resultado de la impresión",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Mostramos un mensaje indicando que la impresión fue cancelada                 
                
                JOptionPane.showMessageDialog(jTable,
                        "Impresión cancelada",
                        "Resultado de la impresión",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(jTable,
                    "Fallo de impresión: " + pe.getMessage(),
                    "Resultado de la impresión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }    
    public void reiniciar() {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
    }    
    //metodo que sirve para cargar los datos de la tabla
    public void cargarTabla(JTable tableAlumnos){       
        tableAlumnos.setModel(modelo); 
        try{                      
            ps = null;
            rs = null;
            Conexion conn = new Conexion();
            Connection con = (Connection) conn.getConnection();           
            String sql = "SELECT folio, alumno_matricula, equipo_idEquipo, "
                + "fechaPrestamo, fechaUso, fechaDevolucion, status FROM prestamosalum";// consulta de los datos de la tabla a la base de datos
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();            
            int[] size = {20,40,30,80,80,80,50};//arreglo para asignar el ancho de las columnas
            //recorremos el arreglo y asignamos dichos valores
            for (int x = 0; x < cantidadColumnas; x++) {
                vista.tableListaP.getColumnModel().getColumn(x).setPreferredWidth(size[x]);         
            }                    
            while(rs.next()){
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i+1);                   
                }
                modelo.addRow(filas);
            }            
        }
        catch(Exception e){   
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }    
    }   
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == vista.btnImprimir) {
            Date date = new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");//formato de la fecha de la cabecera de la impresion           
            try {
                //vistaAlumno.tableAlumnos.print();               

                String cabecera = "Reporte de Lista de Prestamos\n";
                String piePagina = "SISAPRE/UADY-UMT\n" + "                                               " + "Fecha: " + hourdateFormat.format(date);
                imprimirTabla(vista.tableListaP, cabecera, piePagina, true);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }      
        }   
    }  
}
