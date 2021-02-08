/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasPrestamo;
import Modelo.Prestamo;
import Vista.VistaBusquedaPrestamo;
import com.mxrck.autocompleter.TextAutoCompleter;
import com.mysql.jdbc.Connection;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author Federico
 */
public class ControladorBusquedaPrestamo extends Conexion implements ActionListener, MouseListener, KeyListener{
    
    private VistaBusquedaPrestamo vistaB;
    private Prestamo prestamo;
    private ConsultasPrestamo conP;
    Conexion conn = new Conexion();
    Connection con = (Connection) conn.getConnection();  
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();// variable del modelo de las tablas    
    String cabecera = "Reporte general de Préstamos";//cabecera de la pagina a imprimir
    String horaIni = "07:00:00";
    String horaFin = "20:00:00";
    
    //constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorBusquedaPrestamo(VistaBusquedaPrestamo vistaB, Prestamo prestamo, ConsultasPrestamo conP) {
        this.vistaB = vistaB;
        this.prestamo = prestamo;
        this.conP = conP;
        this.vistaB.txtMatricula.addKeyListener(this);
        this.vistaB.txtNombre.addKeyListener(this);
        this.vistaB.txtIdEquipo.addKeyListener(this);
        this.vistaB.txtDescripcion.addKeyListener(this);
        this.vistaB.txtIdSala.addKeyListener(this);
        this.vistaB.txtNombreSala.addKeyListener(this);           
        this.vistaB.btnBuscar.addActionListener(this);
        this.vistaB.btnImprimir.addActionListener(this);
        this.vistaB.btnLimpiar.addActionListener(this);        
        this.vistaB.btnExportar.addActionListener(this);
        this.vistaB.tablePrestamos.addMouseListener(this);       
        this.vistaB.jRadioBuscarAlumno.addActionListener(this);
        this.vistaB.jRadioBEquipo.addActionListener(this);
        this.vistaB.jRadioSalon.addActionListener(this);           
    }

    //aqui se cargan todos los componentes que se iniciarian cuando se abra la vista
    public void iniciar(){
        fechaSistema();//carga la hora del sistema
        limpiar();
        reiniciar();
        vistaB.btnBuscar.setEnabled(true);  
        vistaB.setLocationRelativeTo(null);
        //deshabilitar textfields, labels de alumno
        vistaB.txtMatricula.setEnabled(false);
        vistaB.txtNombre.setEnabled(false);      
        vistaB.labelMatricula.setEnabled(false);
        vistaB.labelNombre.setEnabled(false);       
        //deshabilitar textfields, labels del equipo
        vistaB.txtIdEquipo.setEnabled(false);
        vistaB.txtDescripcion.setEnabled(false);       
        vistaB.labelIDEquipo.setEnabled(false);
        vistaB.labelDescripcion.setEnabled(false);     
        //deshabilitar textfields, labels, btn del salón
        vistaB.txtIdSala.setEnabled(false);
        vistaB.txtNombreSala.setEnabled(false);      
        vistaB.labelIdSala.setEnabled(false);
        vistaB.labelNombreSala.setEnabled(false);       
        //deshabilitar textfields, labels, btn, jdChooser del rango de fechas
        vistaB.jDCFechaFinal.setEnabled(true);
        vistaB.jDCFechaInicial.setEnabled(true);
        vistaB.labelFechaInicial.setEnabled(true);
        vistaB.labelFechaFinal.setEnabled(true);
        vistaB.labelEntre.setEnabled(true);
        vistaB.labelFecha.setEnabled(true);
        vistaB.txtFolio.setVisible(false);//sirve para guardar temporalmente el folio del prestamo para poder realizar la eliminacion
        cargarTablaPrestamos(vistaB.tablePrestamos);
        autocompletarMatricula();
        autocompletarNombre();
        autocompletarNumeroInventario();
        autocompletarDescripcion();
        autocompletarIdSalon();
        autocompletarNombreSalon();
    }   
    //reinicia los datos de las tablas
    public void reiniciar() {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
    }   
    //metodo que obtiene la hora del sistema y se lo asigna a las caja de texto para guardarlo a la BD
    public void fechaSistema(){
        Calendar c;
        c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR), m = 1+ (c.get(Calendar.MONTH)), d = c.get(Calendar.DATE);                
        int h = c.get(Calendar.HOUR), mm = c.get(Calendar.MINUTE), s = c.get(Calendar.SECOND);
        int HH = c.get(Calendar.HOUR_OF_DAY), MM = c.get(Calendar.MINUTE), SS = c.get(Calendar.SECOND);       
        vistaB.jDCFechaInicial.setCalendar(c);
        vistaB.jDCFechaFinal.setCalendar(c);
    }    
    //metodo para vaciar los campos de texto 
    public void limpiar(){
        reiniciar();    
        fechaSistema();//actualiza la fecha del sistema
        vistaB.txtMatricula.setText(null);
        vistaB.txtNombre.setText(null);
        vistaB.txtIdEquipo.setText(null);
        vistaB.txtDescripcion.setText(null);
        vistaB.txtIdSala.setText(null);
        vistaB.txtNombreSala.setText(null);       
    }    
    //metodo que sirve para cargar los datos de la tabla de prestamos
    public void cargarTablaPrestamos(JTable tablePrestamos){
        ps = null;
        rs = null;
        //Conexion conn = new Conexion();
        tablePrestamos.setModel(modelo);
        try {            
            modelo.setColumnIdentifiers(new Object[]{"Folio", "Matricula", "IdEquipo", "Fecha Préstamo", "Fecha Uso", "Fecha Devolución", "Estatus"});//asigna los titulos de las columnas
            try {
                //Connection con = (Connection) conn.getConnection();
                //se realiza la consulta a la BD
                ps = con.prepareStatement("SELECT * FROM prestamosalum");
                rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        //asigna los valores obtenidos a la tabla
                        modelo.addRow(new Object[]{rs.getString("folio"), rs.getString("alumno_matricula"), rs.getString("equipo_idEquipo"), rs.getString("fechaPrestamo"), rs.getString("fechaUso"), rs.getString("fechaDevolucion"), rs.getString("status")});
                    }
                    vistaB.tablePrestamos.setModel(modelo);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }   
    //metodo que sirve para buscar los prestamos en los rangos de fecha
    public void buscarPrestamos(JTable tablePrestamos){
        ps = null;
        rs = null;
        //Conexion conn = new Conexion();
        tablePrestamos.setModel(modelo);                 
        String temp = "SELECT * FROM prestamosalum, equipo WHERE equipo_idEquipo=equipo.idEquipo AND fechaPrestamo BETWEEN ? AND ? ORDER BY fechaPrestamo ASC";
        try {           
            modelo.setColumnIdentifiers(new Object[]{"Matricula", "Número Inv.", "Equipos", "Fecha Préstamo", "Hora Uso", "Hora Fin", "Estatus"});//asigna los titulos de las columnas
            con = (Connection) getConnection();
            ps = con.prepareStatement(temp);
            ps.setString(1, ((JTextField) vistaB.jDCFechaInicial.getDateEditor().getUiComponent()).getText() + " " + horaIni);
            ps.setString(2, ((JTextField) vistaB.jDCFechaFinal.getDateEditor().getUiComponent()).getText() + " " + horaFin);
            rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getString("alumno_matricula"), rs.getString("numInv"),
                    rs.getString("descripcion"), rs.getString("fechaPrestamo"), rs.getString("horaIniUso"), rs.getString("horaFinUso"),
                    rs.getString("status")});
            }
            vistaB.tablePrestamos.setModel(modelo);
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }  
    //metodo para imprimir los elementos de la lista
    public void imprimirTabla(JTable jTable, String cabecera,String piePagina, boolean showPrintDialog) {
       boolean fitWidth = true;
        boolean interactive = true;
        //Definimos el modo de impresión
        JTable.PrintMode modo = fitWidth ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;
        try {
            // mprimo la tabla          
            boolean complete = vistaB.tablePrestamos.print(modo,
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
    //metodo para exportar los datos de la tabla a excell
    public void exportarExcel(JTable t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro = new HSSFWorkbook();
                FileOutputStream archivo = new FileOutputStream(archivoXLS);
                Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                hoja.setDisplayGridlines(false);
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(f);
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (f == 0) {
                            celda.setCellValue(t.getColumnName(c));
                        }
                    }
                }
                int filaInicio = 1;
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(filaInicio);
                    filaInicio++;
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (t.getValueAt(f, c) instanceof Double) {
                            celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                        } else if (t.getValueAt(f, c) instanceof Float) {
                            celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                        } else {
                            celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                        }
                    }
                }
                libro.write(archivo);
                archivo.close();
                int msg = JOptionPane.showConfirmDialog(null, "Datos exportados correctamente,\n¿Desea abrir el archivo?", "Confirmar", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);       
                if (msg == JOptionPane.YES_OPTION) {
                    Desktop.getDesktop().open(archivoXLS);               
                }
                
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                throw e;              
            }
        }
    }   
    @Override
    public void actionPerformed(ActionEvent e) {
        if (vistaB.jRadioBuscarAlumno.isSelected()) {
            cabecera = "Reporte de préstamos por datos del alumno\n";//cabecera de la pagina a imprimir
            //habilita alumno
            vistaB.txtMatricula.setEnabled(true);
            vistaB.txtNombre.setEnabled(true);           
            vistaB.labelMatricula.setEnabled(true);
            vistaB.labelNombre.setEnabled(true);                    
            //deshabilita equipo
            vistaB.txtIdEquipo.setEnabled(false);
            vistaB.txtDescripcion.setEnabled(false);          
            vistaB.labelIDEquipo.setEnabled(false);
            vistaB.labelDescripcion.setEnabled(false);         
            //deshabilita salones
            vistaB.txtIdSala.setEnabled(false);
            vistaB.txtNombreSala.setEnabled(false);          
            vistaB.labelIdSala.setEnabled(false);
            vistaB.labelNombreSala.setEnabled(false);         
            //deshabilita rangos
            //vistaB.btnBuscarFecha.setEnabled(false);          
            vistaB.jDCFechaFinal.setEnabled(true);
            vistaB.jDCFechaInicial.setEnabled(true);
            vistaB.labelFechaInicial.setEnabled(true);
            vistaB.labelFechaFinal.setEnabled(true);
            vistaB.labelEntre.setEnabled(true);
            vistaB.labelFecha.setEnabled(true);         
        }
        if (vistaB.jRadioBEquipo.isSelected()) {
            cabecera = "Reporte de préstamos por datos del equipo\n";//cabecera de la pagina a imprimir
            //habilita equipo
            vistaB.txtIdEquipo.setEnabled(true);
            vistaB.txtDescripcion.setEnabled(true);           
            vistaB.labelIDEquipo.setEnabled(true);
            vistaB.labelDescripcion.setEnabled(true);           
            //deshabilita alumno
            vistaB.txtMatricula.setEnabled(false);
            vistaB.txtNombre.setEnabled(false);           
            vistaB.labelMatricula.setEnabled(false);
            vistaB.labelNombre.setEnabled(false);           
            //deshabilita salones
            vistaB.txtIdSala.setEnabled(false);
            vistaB.txtNombreSala.setEnabled(false);           
            vistaB.labelIdSala.setEnabled(false);
            vistaB.labelNombreSala.setEnabled(false);          
            //deshabilita rangos
           //vistaB.btnBuscarFecha.setEnabled(false);          
            vistaB.jDCFechaFinal.setEnabled(true);
            vistaB.jDCFechaInicial.setEnabled(true);
            vistaB.labelFechaInicial.setEnabled(true);
            vistaB.labelFechaFinal.setEnabled(true);
            vistaB.labelEntre.setEnabled(true);
            vistaB.labelFecha.setEnabled(true);       
        }
        if (vistaB.jRadioSalon.isSelected()) {
            cabecera = "Reporte de préstamos por datos del salón\n";//cabecera de la pagina a imprimir
            //habilita salones
            vistaB.txtIdSala.setEnabled(true);
            vistaB.txtNombreSala.setEnabled(true);           
            vistaB.labelIdSala.setEnabled(true);
            vistaB.labelNombreSala.setEnabled(true);         
            //deshabilita equipo
            vistaB.txtIdEquipo.setEnabled(false);
            vistaB.txtDescripcion.setEnabled(false);    
            vistaB.labelIDEquipo.setEnabled(false);
            vistaB.labelDescripcion.setEnabled(false);          
            //deshabilita alumno
            vistaB.txtMatricula.setEnabled(false);
            vistaB.txtNombre.setEnabled(false);
            vistaB.labelMatricula.setEnabled(false);
            vistaB.labelNombre.setEnabled(false);           
            //deshabilita rangos
            //vistaB.btnBuscarFecha.setEnabled(false);          
            vistaB.jDCFechaFinal.setEnabled(true);
            vistaB.jDCFechaInicial.setEnabled(true);
            vistaB.labelFechaInicial.setEnabled(true);
            vistaB.labelFechaFinal.setEnabled(true);
            vistaB.labelEntre.setEnabled(true);
            vistaB.labelFecha.setEnabled(true);
        }
        
        //aqui se compara si se presiono el boton buscar
        if (e.getSource() == vistaB.btnBuscar) {
            reiniciar();
            buscarPrestamos(vistaB.tablePrestamos);
        }                       
        //aqui se compara si se presiono el boton limpiar
        if (e.getSource() == vistaB.btnLimpiar) {
            limpiar();//limpia las cajas de los textos
            cargarTablaPrestamos(vistaB.tablePrestamos);
        }        
        //aqui se compara si se presiono el boton imprimir
        if (e.getSource() == vistaB.btnImprimir) {
            Date date = new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");//formato de la fecha de la cabecera de la impresion           
            try {               
                String piePagina = "SISAPRE/UADY-UMT\n" + "                                               " + "Fecha: " + hourdateFormat.format(date);
                imprimirTabla(vistaB.tablePrestamos, cabecera, piePagina, true);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }      
        }
         //aqui se compara si se presiono el boton exportar a excell
        if (e.getSource() == vistaB.btnExportar) {
            try {              
                exportarExcel(vistaB.tablePrestamos);
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }   
        }       
    }    
    //metodo para autocompletar el campo de la matricula del prestamo
    public void autocompletarMatricula(){
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaB.txtMatricula);
        Connection con = null;        
        //string de la consulta
        String temp = "SELECT matricula, nombre, apellidoP, apellidoM FROM alumno";
        try {
            con = (Connection) cc.getConnection();
            ps = con.prepareStatement(temp);
            //ps.setString(1,vistaPrestamo.txtMatricula.getText());
            rs = ps.executeQuery();
            while (rs.next()) {                
                textAuto.addItem(rs.getString("matricula"));                             
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }   
    //metodo para autocompletar el campo del nombre del prestamo
    public void autocompletarNombre(){
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaB.txtNombre);
        Connection con = null;       
        //string de la consulta
        String temp = "SELECT nombre, apellidoP, apellidoM FROM alumno";
        try {
            con = (Connection) cc.getConnection();
            ps = con.prepareStatement(temp);           
            rs = ps.executeQuery();
            while (rs.next()) {               
                textAuto.addItem(rs.getString("nombre"));                            
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }   
    //metodo para autocompletar el campo de numero de inventario
    public void autocompletarNumeroInventario(){
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaB.txtIdEquipo);
        Connection con = null;       
        //string de la consulta
        String temp = "SELECT numInv FROM equipo";
        try {
            con = (Connection) cc.getConnection();
            ps = con.prepareStatement(temp);           
            rs = ps.executeQuery();
            while (rs.next()) {               
                textAuto.addItem(rs.getString("numInv"));                            
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }    
    //metodo para autocompletar el campo de descripcion del equipo
    public void autocompletarDescripcion(){
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaB.txtDescripcion);
        Connection con = null;       
        //string de la consulta
        String temp = "SELECT descripcion FROM equipo";
        try {
            con = (Connection) cc.getConnection();
            ps = con.prepareStatement(temp);           
            rs = ps.executeQuery();
            while (rs.next()) {               
                textAuto.addItem(rs.getString("descripcion"));                            
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }   
    //metodo para autocompletar el campo del id de la sala
    public void autocompletarIdSalon(){
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaB.txtIdSala);
        Connection con = null;       
        //string de la consulta
        String temp = "SELECT idSala FROM sala";
        try {
            con = (Connection) cc.getConnection();
            ps = con.prepareStatement(temp);           
            rs = ps.executeQuery();
            while (rs.next()) {               
                textAuto.addItem(rs.getString("idSala"));                            
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }   
    //metodo para autocompletar el campo del nombre del salon
    public void autocompletarNombreSalon(){
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaB.txtNombreSala);
        Connection con = null;       
        //string de la consulta
        String temp = "SELECT nomSala FROM sala";
        try {
            con = (Connection) cc.getConnection();
            ps = con.prepareStatement(temp);           
            rs = ps.executeQuery();
            while (rs.next()) {               
                textAuto.addItem(rs.getString("nomSala"));                            
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }    
    @Override
    public void mouseClicked(MouseEvent e) {        
        ps = null;
        rs = null;
        try{
            Conexion ObjCon = new Conexion();
            com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) ObjCon.getConnection();
            int fila = vistaB.tablePrestamos.getSelectedRow();//obtiene el numero de filas de la tabla           
            String id = vistaB.tablePrestamos.getValueAt(fila, 0).toString();//obtiene el valor 0 de la fila de la tabla                     
            //realiza la consulta a la base de datos
            ps = conn.prepareStatement("SELECT * FROM prestamosalum, alumno, equipo WHERE folio=? AND alumno.matricula = prestamosalum.alumno_matricula"
                    + " AND equipo.idEquipo = prestamosalum.equipo_idEquipo");
            ps.setString(1, id);       
            rs = ps.executeQuery();                
            //itera los resultados que coincidieron con la consulta
            while(rs.next()){
                //asigna los valores que coincidieron
                vistaB.txtFolio.setText(rs.getString("folio"));               
            }
        }
        catch(Exception exc){     
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }
    }    
    //eventos para buscar datos de la base de datos por medio de los textField
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vistaB.txtMatricula) {
            Connection con = null;
            DefaultTableModel modelo = new DefaultTableModel();           
            modelo.setColumnIdentifiers(new Object[]{ "Matricula", "Nombre(s)", "Apellido P", "Apellido M", "Número Inv.", "Equipos", "Fecha Préstamo", "Hora Uso", "Hora Fin", "Estatus"});           
            String temp = "SELECT * FROM alumno, prestamosalum, equipo WHERE alumno.matricula LIKE ? "
                    + "AND alumno.matricula=prestamosalum.alumno_matricula "
                    + "AND equipo.idEquipo=prestamosalum.equipo_idEquipo AND fechaPrestamo BETWEEN '" 
                        +((JTextField) vistaB.jDCFechaInicial.getDateEditor().getUiComponent()).getText()+" "+horaIni
                        + "' AND '" + ((JTextField) vistaB.jDCFechaFinal.getDateEditor().getUiComponent()).getText()+" " +horaFin+ "'ORDER BY fechaPrestamo ASC";
            try {
                con = (Connection) getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, "%" + vistaB.txtMatricula.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    modelo.addRow(new Object[]{rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidoP"), rs.getString("apellidoM"), rs.getString("numInv"),
                        rs.getString("descripcion"), rs.getString("fechaPrestamo"), rs.getString("horaIniUso"), rs.getString("horaFinUso"),
                        rs.getString("status")});
                }
                vistaB.tablePrestamos.setModel(modelo);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
        if (e.getSource() == vistaB.txtNombre) {
            Connection con = null;
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"Matricula", "Nombre(s)", "Apellido P", "Apellido M", "Número Inv.", 
                "Equipos Prestados", "Fecha Préstamo", "Hora Uso", "Hora Fin", "Estatus"});
            String temp = "SELECT * FROM alumno, prestamosalum, equipo WHERE alumno.nombre LIKE ? "
                    + "AND alumno.matricula=prestamosalum.alumno_matricula AND equipo.idEquipo=prestamosalum.equipo_idEquipo AND fechaPrestamo BETWEEN '" 
                        +((JTextField) vistaB.jDCFechaInicial.getDateEditor().getUiComponent()).getText()+" "+horaIni
                        + "' AND '" + ((JTextField) vistaB.jDCFechaFinal.getDateEditor().getUiComponent()).getText()+" " +horaFin+ "'ORDER BY fechaPrestamo ASC";
            try {
                con = (Connection) getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, "%" + vistaB.txtNombre.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    modelo.addRow(new Object[]{rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidoP"), rs.getString("apellidoM"),  rs.getString("numInv"),
                        rs.getString("descripcion"), rs.getString("fechaPrestamo"), rs.getString("horaIniUso"), rs.getString("horaFinUso"),
                        rs.getString("status")});
                }
                vistaB.tablePrestamos.setModel(modelo);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
        if (e.getSource() == vistaB.txtIdEquipo) {
            Connection con = null;
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"Matricula", "Nombre(s)", "Apellido P", "Apellido M", "Número Inv.", 
                "Equipos", "Fecha Préstamo", "Hora Uso", "Hora Fin", "Estatus"});
            String temp = "SELECT * FROM alumno, prestamosalum, equipo WHERE equipo.numInv LIKE ? "
                    + "AND alumno.matricula=prestamosalum.alumno_matricula AND equipo.idEquipo=prestamosalum.equipo_idEquipo AND fechaPrestamo BETWEEN '" 
                        +((JTextField) vistaB.jDCFechaInicial.getDateEditor().getUiComponent()).getText()+" "+horaIni
                        + "' AND '" + ((JTextField) vistaB.jDCFechaFinal.getDateEditor().getUiComponent()).getText()+" " +horaFin+ "'ORDER BY fechaPrestamo ASC";
            try {
                con = (Connection) getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, "%" + vistaB.txtIdEquipo.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    modelo.addRow(new Object[]{rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidoP"), 
                        rs.getString("apellidoM"), rs.getString("numInv"), rs.getString("descripcion"), rs.getString("fechaPrestamo"), rs.getString("horaIniUso"), rs.getString("horaFinUso"),
                        rs.getString("status")});
                }
                vistaB.tablePrestamos.setModel(modelo);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
        if (e.getSource() == vistaB.txtDescripcion) {
            Connection con = null;
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"Matricula", "Nombre(s)", "Apellido P", "Apellido M", "Número Inv.", 
                "Equipos", "Fecha Préstamo", "Hora Uso", "Hora Fin", "Estatus"});
            String temp = "SELECT * FROM alumno, prestamosalum, equipo WHERE equipo.descripcion LIKE ? "
                    + "AND alumno.matricula=prestamosalum.alumno_matricula AND equipo.idEquipo=prestamosalum.equipo_idEquipo AND fechaPrestamo BETWEEN '" 
                        +((JTextField) vistaB.jDCFechaInicial.getDateEditor().getUiComponent()).getText()+" "+horaIni
                        + "' AND '" + ((JTextField) vistaB.jDCFechaFinal.getDateEditor().getUiComponent()).getText()+" " +horaFin+ "'ORDER BY fechaPrestamo ASC";
            try {
                con = (Connection) getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, "%" + vistaB.txtDescripcion.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    modelo.addRow(new Object[]{rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidoP"), 
                        rs.getString("apellidoM"), rs.getString("numInv"), rs.getString("descripcion"), rs.getString("fechaPrestamo"), rs.getString("horaIniUso"), rs.getString("horaFinUso"),
                        rs.getString("status")});
                }
                vistaB.tablePrestamos.setModel(modelo);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }          
        }
        if (e.getSource() == vistaB.txtIdSala) {
            Connection con = null;
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"Id Sala","Matricula", "Nombre(s)", "Apellido P", "Apellido M", "Número Inv.", 
                "Equipos", "Fecha Préstamo", "Hora Uso", "Hora Fin", "Estatus"});
            String temp = "SELECT * FROM alumno, prestamosalum, equipo, sala WHERE sala.idSala LIKE ? "
                    + "AND alumno.matricula=prestamosalum.alumno_matricula AND equipo.idEquipo=prestamosalum.equipo_idEquipo "
                    + "AND sala.idSala=equipo.sala_idSala AND fechaPrestamo BETWEEN '" 
                        +((JTextField) vistaB.jDCFechaInicial.getDateEditor().getUiComponent()).getText()+" "+horaIni
                        + "' AND '" + ((JTextField) vistaB.jDCFechaFinal.getDateEditor().getUiComponent()).getText()+" " +horaFin+ "'ORDER BY fechaPrestamo ASC";
            try {
                con = (Connection) getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, "%" + vistaB.txtIdSala.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    modelo.addRow(new Object[]{rs.getString("idSala"), rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidoP"), 
                        rs.getString("apellidoM"), rs.getString("numInv"), rs.getString("descripcion"), rs.getString("fechaPrestamo"), rs.getString("horaIniUso"), rs.getString("horaFinUso"),
                        rs.getString("status")});
                }
                vistaB.tablePrestamos.setModel(modelo);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
        if (e.getSource() == vistaB.txtNombreSala) {
            Connection con = null;
            DefaultTableModel modelo = new DefaultTableModel();           
            modelo.setColumnIdentifiers(new Object[]{"Salón","Matricula", "Nombre(s)", "Apellido P", "Apellido M", "Número Inv.", 
                "Equipos", "Fecha Préstamo", "Hora Uso", "Hora Fin", "Estatus"});
            String temp = "SELECT * FROM alumno, prestamosalum, equipo, sala WHERE sala.nomSala LIKE ? "
                    + "AND alumno.matricula=prestamosalum.alumno_matricula AND equipo.idEquipo=prestamosalum.equipo_idEquipo "
                    + "AND sala.idSala=equipo.sala_idSala AND fechaPrestamo BETWEEN '" 
                        +((JTextField) vistaB.jDCFechaInicial.getDateEditor().getUiComponent()).getText()+" "+horaIni
                        + "' AND '" + ((JTextField) vistaB.jDCFechaFinal.getDateEditor().getUiComponent()).getText()+" " +horaFin+ "'ORDER BY fechaPrestamo ASC";
            try {
                con = (Connection) getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, "%" + vistaB.txtNombreSala.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    modelo.addRow(new Object[]{rs.getString("nomSala"), rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidoP"), 
                        rs.getString("apellidoM"), rs.getString("numInv"), rs.getString("descripcion"), rs.getString("fechaPrestamo"), rs.getString("horaIniUso"), rs.getString("horaFinUso"),
                        rs.getString("status")});
                }
                vistaB.tablePrestamos.setModel(modelo);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {    
    }
    @Override
    public void mouseReleased(MouseEvent e) {        
    }
    @Override
    public void mouseEntered(MouseEvent e) {       
    }
    @Override
    public void mouseExited(MouseEvent e) {       
    }
    @Override
    public void keyTyped(KeyEvent e) {       
    }
    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    
}
