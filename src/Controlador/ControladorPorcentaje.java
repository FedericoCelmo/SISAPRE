/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Vista.VistaPorcentajeOcupacion;
import com.mysql.jdbc.Connection;
import com.toedter.calendar.JDateChooser;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Federico
 */
public class ControladorPorcentaje  extends Conexion implements ActionListener{
    
    private VistaPorcentajeOcupacion vistaPor;
    Conexion conn = new Conexion();
    Connection con = (Connection) conn.getConnection(); 
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();// variable del modelo de la tabla
    String cabecera = "Reporte general de Préstamos";//cabecera de la pagina a imprimir
    String horaIni = "07:00:00";
    String horaFin = "20:00:00";

    public ControladorPorcentaje(VistaPorcentajeOcupacion vistaPor) {
        this.vistaPor = vistaPor;
        this.vistaPor.btnImprimir.addActionListener(this);
        this.vistaPor.btnExportar.addActionListener(this);
        this.vistaPor.btnMostrarE.addActionListener(this);
        this.vistaPor.btnMostrarS.addActionListener(this);
        this.vistaPor.jRadioBtnEquipo.addActionListener(this);
        this.vistaPor.jRadioBtnGeneralEquipo.addActionListener(this);
        this.vistaPor.jRadioBtnPorSalon.addActionListener(this);
        this.vistaPor.jRadioBtnSalon.addActionListener(this);
        this.vistaPor.jRadioBtnSalonGeneral.addActionListener(this);
        this.vistaPor.jRadioLicEquipo.addActionListener(this);
        this.vistaPor.cbxCarrera.addActionListener(this);
        this.vistaPor.cbxSala.addActionListener(this);
    }    
    //aqui se cargan todos los componentes que se iniciarian cuando se abra la vista
    public void iniciar(){
        fechaSistema();//carga la hora del sistema
        reiniciar();       
        vistaPor.setLocationRelativeTo(null);
        vistaPor.btnMostrarE.setEnabled(false);
        vistaPor.btnMostrarS.setEnabled(false);
        vistaPor.jRadioBtnGeneralEquipo.setEnabled(false);
        vistaPor.jRadioBtnSalonGeneral.setEnabled(false);
        vistaPor.jRadioLicEquipo.setEnabled(false);
        vistaPor.jRadioBtnPorSalon.setEnabled(false);
        vistaPor.cbxCarrera.setEnabled(false);
        vistaPor.cbxSala.setEnabled(false);            
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
        vistaPor.jDCFechaInicial.setCalendar(c);
        vistaPor.jDCFechaFinal.setCalendar(c);
    }    
    public void imprimirTabla(JTable jTable, String cabecera,String piePagina, boolean showPrintDialog) {
        boolean fitWidth = true;
        boolean interactive = true;
        //Definimos el modo de impresión
        JTable.PrintMode modo = fitWidth ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;
        try {
            // mprimo la tabla          
            boolean complete = vistaPor.tablePorcentaje.print(modo,
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
    //metodo para pasar los datos de la tabla a excell
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
        float hrsDisponibles;
        if (vistaPor.jRadioBtnEquipo.isSelected()) {        
            vistaPor.jRadioBtnGeneralEquipo.setEnabled(true);
            vistaPor.jRadioLicEquipo.setEnabled(true);
            vistaPor.jRadioBtnSalonGeneral.setEnabled(false);
            vistaPor.jRadioBtnPorSalon.setEnabled(false);           
            if (vistaPor.jRadioBtnGeneralEquipo.isSelected()) {
                cabecera = "Reporte general de % de uso de equipos\n";//cabecera de la pagina a imprimir
                vistaPor.btnMostrarE.setEnabled(true);
                vistaPor.btnMostrarS.setEnabled(false);
                vistaPor.cbxCarrera.setEnabled(false);
                vistaPor.cbxSala.setEnabled(false);
            }
            if (vistaPor.jRadioLicEquipo.isSelected()) {
                cabecera = "Reporte de % de uso de equipos por licenciatura\n";//cabecera de la pagina a imprimir
                vistaPor.btnMostrarE.setEnabled(true);
                vistaPor.btnMostrarS.setEnabled(false);
                vistaPor.cbxCarrera.setEnabled(true);
                vistaPor.cbxSala.setEnabled(false);
            }               
            if (e.getSource() == vistaPor.btnMostrarE) {              
                if (vistaPor.jRadioBtnGeneralEquipo.isSelected()) {
                    System.out.println("Equipo General Seleccionado");
                    int diasTranscurridos = calcularDiasTranscurridos(vistaPor.jDCFechaInicial, vistaPor.jDCFechaFinal);
                    hrsDisponibles = diasTranscurridos * 13;//se calcula el total de las horas diarias disponibles
                    System.out.println("Horas totales: " + hrsDisponibles);
                    String d = Integer.toString(diasTranscurridos);
                    String h = Float.toString(hrsDisponibles);
                    vistaPor.txtDias.setText(d);
                    vistaPor.txtHoras.setText(h);
                    Connection con = null;
                    DefaultTableModel modelo = new DefaultTableModel();
                    modelo.setColumnIdentifiers(new Object[]{"ID Equipo", "Descripción", "Número Inventario", "Horas Usadas", "Porcentaje"});
                    //TIME_TO_SEC
                    //consulta para obtener el porcentanje de uso de los equipos internos de manera general
                    //formula usada=hrs usadas * 100/total de hrs disponibles(dias transcurridos*13)
                    String temp = "SELECT p.equipo_idEquipo AS 'id equipo', e.descripcion AS nombre, e.numInv AS numero, ROUND(SUM(EXTRACT(HOUR FROM TIMEDIFF(p.fechaDevolucion, p.fechaUso))),2) AS horas,"
                            + "SUM(EXTRACT(HOUR FROM TIMEDIFF(p.fechaDevolucion, p.fechaUso))*100/"+hrsDisponibles+") AS total FROM prestamosalum p, equipo e " +
                                    "WHERE p.equipo_idEquipo = e.idEquipo AND e.tipo = 'Interno' AND p.status = 'Devuelto' AND p.fechaPrestamo BETWEEN ? AND ? "
                            + " GROUP BY p.equipo_idEquipo ORDER BY p.equipo_idEquipo ASC";
                    String temp1 = "SELECT p.equipo_idEquipo AS 'id equipo', e.descripcion AS nombre, e.numInv AS numero, ROUND(SUM(TIME_TO_SEC(TIMEDIFF(p.fechaDevolucion, p.fechaUso))/3600),2) AS horas,"
                            + "ROUND(SUM(TIME_TO_SEC(TIMEDIFF(p.fechaDevolucion, p.fechaUso))*100/"+hrsDisponibles+")/3600,2) AS total FROM prestamosalum p, equipo e " +
                                    "WHERE p.equipo_idEquipo = e.idEquipo AND e.tipo = 'Interno' AND p.status = 'Devuelto' AND p.fechaUso BETWEEN ? AND ? "
                            + " GROUP BY p.equipo_idEquipo ORDER BY p.equipo_idEquipo ASC";                    
                    try {
                        con = (Connection) getConnection();
                        ps = con.prepareStatement(temp1);
                        ps.setString(1, ((JTextField) vistaPor.jDCFechaInicial.getDateEditor().getUiComponent()).getText() + " " + horaIni);
                        ps.setString(2, ((JTextField) vistaPor.jDCFechaFinal.getDateEditor().getUiComponent()).getText() + " " + horaFin);
                        rs = ps.executeQuery();
                        while (rs.next()) {                           
                            modelo.addRow(new Object[]{rs.getString("id equipo"), rs.getString("nombre"), rs.getString("numero"),
                            rs.getString("horas"), rs.getString("total")+" "+"%"});
                        }
                        vistaPor.tablePorcentaje.setModel(modelo);
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                    }
                }
                if (vistaPor.jRadioLicEquipo.isSelected()) {
                    System.out.println("Equipo licenciatura Seleccionado");
                    int diasTranscurridos = calcularDiasTranscurridos(vistaPor.jDCFechaInicial, vistaPor.jDCFechaFinal);
                    hrsDisponibles = diasTranscurridos * 13;//se calcula el total de las horas diarias disponibles
                    System.out.println("Horas totales: " + hrsDisponibles);
                    String d = Integer.toString(diasTranscurridos);
                    String h = Float.toString(hrsDisponibles);
                    vistaPor.txtDias.setText(d);
                    vistaPor.txtHoras.setText(h);
                    Connection con = null;
                    DefaultTableModel modelo = new DefaultTableModel();
                    modelo.setColumnIdentifiers(new Object[]{"ID Equipo", "Descripción", "Número Inventario", "Horas Usadas", "Porcentaje"});
                    //consulta para obtener el porcentanje de uso de los equipos internos por licenciatura
                    //formula usada=hrs usadas * 100/total de hrs disponibles(dias transcurridos*13)
                    String temp = "SELECT p.equipo_idEquipo AS 'id equipo', e.descripcion AS nombre, e.numInv AS numero, SUM(EXTRACT(HOUR FROM TIMEDIFF(p.fechaDevolucion, p.fechaUso))) AS horas,"
                            + "SUM(EXTRACT(HOUR FROM TIMEDIFF(p.fechaDevolucion, p.fechaUso))*100/"+hrsDisponibles+") AS total FROM prestamosalum p, equipo e, alumno a, carrera c " +
                                    "WHERE p.equipo_idEquipo = e.idEquipo AND e.tipo = 'Interno' AND p.status = 'Devuelto' AND fechaPrestamo BETWEEN ? AND ? "
                            + "AND p.alumno_matricula = a.matricula AND a.carrera_idCarrera = c.idCarrera AND ? = c.nomCarrera GROUP BY p.equipo_idEquipo ORDER BY p.equipo_idEquipo ASC";
                    String temp1 = "SELECT p.equipo_idEquipo AS 'id equipo', e.descripcion AS nombre, e.numInv AS numero, ROUND(SUM(TIME_TO_SEC(TIMEDIFF(p.fechaDevolucion, p.fechaUso))/3600),2) AS horas,"
                            + "ROUND(SUM(TIME_TO_SEC(TIMEDIFF(p.fechaDevolucion, p.fechaUso))*100/"+hrsDisponibles+")/3600,2) AS total FROM prestamosalum p, equipo e, alumno a, carrera c " +
                                    "WHERE p.equipo_idEquipo = e.idEquipo AND e.tipo = 'Interno' AND p.status = 'Devuelto' AND p.fechaUso BETWEEN ? AND ? "
                            + "AND p.alumno_matricula = a.matricula AND a.carrera_idCarrera = c.idCarrera AND ? = c.nomCarrera GROUP BY p.equipo_idEquipo ORDER BY p.equipo_idEquipo ASC";
                    try {
                        con = (Connection) getConnection();
                        ps = con.prepareStatement(temp1);
                        ps.setString(1, ((JTextField) vistaPor.jDCFechaInicial.getDateEditor().getUiComponent()).getText() + " " + horaIni);
                        ps.setString(2, ((JTextField) vistaPor.jDCFechaFinal.getDateEditor().getUiComponent()).getText() + " " + horaFin);
                        ps.setString(3, vistaPor.cbxCarrera.getSelectedItem().toString());
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            modelo.addRow(new Object[]{rs.getString("id equipo"), rs.getString("nombre"), rs.getString("numero"),
                            rs.getString("horas"), rs.getString("total")+" "+"%"});
                        }
                        vistaPor.tablePorcentaje.setModel(modelo);
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                    }
                }
            }
        }
        if (vistaPor.jRadioBtnSalon.isSelected()) {            
            vistaPor.jRadioBtnGeneralEquipo.setEnabled(false);
            vistaPor.jRadioLicEquipo.setEnabled(false);    
            vistaPor.jRadioBtnSalonGeneral.setEnabled(true);
            vistaPor.jRadioBtnPorSalon.setEnabled(true);
            vistaPor.btnMostrarE.setEnabled(false);
            vistaPor.cbxCarrera.setEnabled(false);           
            if (vistaPor.jRadioBtnSalonGeneral.isSelected()) {
                cabecera = "Reporte general de % de ocupacion de salones\n";//cabecera de la pagina a imprimir
                vistaPor.btnMostrarE.setEnabled(false);
                vistaPor.btnMostrarS.setEnabled(true);
                vistaPor.cbxCarrera.setEnabled(false);
                vistaPor.cbxSala.setEnabled(false);
            }
            if (vistaPor.jRadioBtnPorSalon.isSelected()) {
               cabecera = "Reporte de % de ocupación de salones por licenciatura\n";//cabecera de la pagina a imprimir
                vistaPor.btnMostrarE.setEnabled(false);
                vistaPor.btnMostrarS.setEnabled(true);
                vistaPor.cbxCarrera.setEnabled(false);
                vistaPor.cbxSala.setEnabled(true);
            }   
            if (e.getSource() == vistaPor.btnMostrarS) {
                if (vistaPor.jRadioBtnSalonGeneral.isSelected()) {
                    System.out.println("Salon General Seleccionado");
                    int diasTranscurridos = calcularDiasTranscurridos(vistaPor.jDCFechaInicial, vistaPor.jDCFechaFinal);
                    hrsDisponibles = diasTranscurridos * 13;//se calcula el total de las horas diarias disponibles
                    System.out.println("Horas totales: " + hrsDisponibles);
                    String d = Integer.toString(diasTranscurridos);
                    String h = Float.toString(hrsDisponibles);
                    vistaPor.txtDias.setText(d);
                    vistaPor.txtHoras.setText(h);
                    Connection con = null;
                    DefaultTableModel modelo = new DefaultTableModel();
                    modelo.setColumnIdentifiers(new Object[]{"ID Sala", "Nombre", "Horas Usadas", "Porcentaje"});
                    //consulta para obtener el porcentanje de uso de los salones de manera general
                    //formula usada=hrs usadas * 100/total de hrs disponibles(dias transcurridos*13)
                    String temp = "SELECT p.sala_idSala AS 'id sala', s.nomSala AS nombre, SUM(EXTRACT(HOUR FROM TIMEDIFF(p.fechaDevolucion, p.fechaUso))) AS horas,"
                            + "SUM(EXTRACT(HOUR FROM TIMEDIFF(p.fechaDevolucion, p.fechaUso))*100/"+hrsDisponibles+") AS total FROM prestamossalon p, sala s " +
                                    "WHERE p.sala_idSala = s.idSala AND p.fechaUso BETWEEN ? AND ? GROUP BY p.sala_idSala ORDER BY p.sala_idSala ASC"; 
                    String temp1 = "SELECT p.sala_idSala AS 'id sala', s.nomSala AS nombre, ROUND(SUM(TIME_TO_SEC(TIMEDIFF(p.fechaDevolucion, p.fechaUso))/3600),2) AS horas,"
                            + "ROUND(SUM(TIME_TO_SEC(TIMEDIFF(p.fechaDevolucion, p.fechaUso))*100/"+hrsDisponibles+")/3600,2) AS total FROM prestamossalon p, sala s " +
                                    "WHERE p.sala_idSala = s.idSala AND p.fechaUso BETWEEN ? AND ? GROUP BY p.sala_idSala ORDER BY p.sala_idSala ASC"; 
                    try {
                        con = (Connection) getConnection();
                        ps = con.prepareStatement(temp1);
                        ps.setString(1, ((JTextField) vistaPor.jDCFechaInicial.getDateEditor().getUiComponent()).getText() + " " + horaIni);
                        ps.setString(2, ((JTextField) vistaPor.jDCFechaFinal.getDateEditor().getUiComponent()).getText() + " " + horaFin);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            modelo.addRow(new Object[]{rs.getString("id sala"), rs.getString("nombre"),
                            rs.getString("horas"), rs.getString("total")+" "+"%"});
                        }
                        vistaPor.tablePorcentaje.setModel(modelo);
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                    }
                }
                if (vistaPor.jRadioBtnPorSalon.isSelected()) {
                    System.out.println("Salon Seleccionado");
                    int diasTranscurridos = calcularDiasTranscurridos(vistaPor.jDCFechaInicial, vistaPor.jDCFechaFinal);
                    hrsDisponibles = diasTranscurridos * 13;//se calcula el total de las horas diarias disponibles
                    System.out.println("Horas totales: " + hrsDisponibles);
                    String d = Integer.toString(diasTranscurridos);
                    String h = Float.toString(hrsDisponibles);
                    vistaPor.txtDias.setText(d);
                    vistaPor.txtHoras.setText(h);
                    Connection con = null;
                    DefaultTableModel modelo = new DefaultTableModel();
                    modelo.setColumnIdentifiers(new Object[]{"ID Sala", "Nombre", "Horas Usadas", "Porcentaje"});
                    //consulta para obtener el porcentanje de uso de cada salon disponible
                    //formula usada=hrs usadas * 100/total de hrs disponibles(dias transcurridos*13)
                    String temp = "SELECT p.sala_idSala AS 'id sala', s.nomSala AS nombre, SUM(EXTRACT(HOUR FROM TIMEDIFF(p.fechaDevolucion, p.fechaUso))) AS horas,"
                            + "SUM(EXTRACT(HOUR FROM TIMEDIFF(p.fechaDevolucion, p.fechaUso))*100/"+hrsDisponibles+") AS total FROM prestamossalon p, sala s " +
                                    "WHERE p.sala_idSala = s.idSala AND s.nomSala = ? AND p.fechaPrestamo BETWEEN ? AND ? GROUP BY 'id sala' ORDER BY p.sala_idSala ASC";
                    String temp1 = "SELECT p.sala_idSala AS 'id sala', s.nomSala AS nombre, ROUND(SUM(TIME_TO_SEC(TIMEDIFF(p.fechaDevolucion, p.fechaUso))/3600),2) AS horas,"
                            + "ROUND(SUM(TIME_TO_SEC(TIMEDIFF(p.fechaDevolucion, p.fechaUso))*100/"+hrsDisponibles+")/3600,2) AS total FROM prestamossalon p, sala s " +
                                    "WHERE p.sala_idSala = s.idSala AND s.nomSala = ? AND p.fechaUso BETWEEN ? AND ? GROUP BY 'id sala' ORDER BY p.sala_idSala ASC";                    
                    try {
                        con = (Connection) getConnection();
                        ps = con.prepareStatement(temp1);
                        ps.setString(1, vistaPor.cbxSala.getSelectedItem().toString());
                        ps.setString(2, ((JTextField) vistaPor.jDCFechaInicial.getDateEditor().getUiComponent()).getText() + " " + horaIni);
                        ps.setString(3, ((JTextField) vistaPor.jDCFechaFinal.getDateEditor().getUiComponent()).getText() + " " + horaFin);
                        
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            modelo.addRow(new Object[]{rs.getString("id sala"), rs.getString("nombre"),
                            rs.getString("horas"), rs.getString("total")+" "+"%"});
                        }
                        vistaPor.tablePorcentaje.setModel(modelo);
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
                    }
                }
            }
        } 
        //aqui se compara si se presiono el boton imprimir
        if (e.getSource() == vistaPor.btnImprimir) {
            Date date = new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");//formato de la fecha de la cabecera de la impresion           
            try {               
                String piePagina = "SISAPRE/UADY-UMT\n" + "                                               " + "Fecha: " + hourdateFormat.format(date);
                imprimirTabla(vistaPor.tablePorcentaje, cabecera, piePagina, true);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }      
        }
         //aqui se compara si se presiono el boton exportar a excell
        if (e.getSource() == vistaPor.btnExportar) {
            try {              
                exportarExcel(vistaPor.tablePorcentaje);
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }   
        }       
    }   
    //Con este metodo calculamos los dias transcurridos dadas dos fechas, una inicial y una final
    //no cuenta los sabados ni domingos
    public int calcularDiasTranscurridos(JDateChooser fechaIni, JDateChooser fechaFin){
        int dias = 0;
        if ((fechaIni.getDate() != null) && (fechaFin.getDate() != null)) {
            Calendar fecha_Inicio = fechaIni.getCalendar();
            Calendar fecha_Final = fechaFin.getCalendar();       
            while (fecha_Inicio.before(fecha_Final) || fecha_Inicio.equals(fecha_Final)) {
                if (fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && fecha_Inicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    dias++;//sumamos los dias
                }
                fecha_Inicio.add(Calendar.DATE, 1);               
            }
            System.out.println("Los dias trancurridos son: " + dias);
        }
        return dias;    
    }    
}
