/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConsultasEquipo;
import Modelo.ConsultasPrestamo;
import Modelo.Equipo;
import Modelo.Prestamo;
import Vista.VistaCompuIndividual;
import Vista.VistaMapaComputadoras;
import com.mxrck.autocompleter.TextAutoCompleter;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Federico
 */
public class ControladorComputadora extends Conexion implements ActionListener, KeyListener, MouseListener{
    
    private Prestamo prestamo;
    private ConsultasPrestamo conP;
    private VistaMapaComputadoras vistaComp;
    private Equipo equipo;
    private ConsultasEquipo conE;
    VistaCompuIndividual vistaInd;
    boolean indicadoraColor = false;  
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    DefaultTableModel modelo = new DefaultTableModel();// variable del modelo de las tablas
    
    //constructor que le añado todas las interacciones con la que el usuario interactuara
    public ControladorComputadora(Prestamo prestamo, ConsultasPrestamo conP, VistaMapaComputadoras vistaComp, 
            Equipo equipo, ConsultasEquipo conE, VistaCompuIndividual vistaInd) {
        this.prestamo = prestamo;
        this.conP = conP;
        this.vistaComp = vistaComp;
        this.equipo = equipo;
        this.conE = conE;
        this.vistaInd = vistaInd;
        //se le añaden todos los componentes de la vista
        //botones
        //tablas que implementaran el mouseClicked
        //cajas de texto que tengan algun comportamiento
        vistaInd.btnGuardar.addActionListener(this);       
        this.vistaComp.btnDevolver.addActionListener(this);
        this.vistaComp.btnCancelar.addActionListener(this);
        this.vistaComp.tablePrestados.addMouseListener(this);
        this.vistaInd.txtMatricula.addKeyListener(this);       
        this.vistaInd.txtNombre.addKeyListener(this);
        this.vistaComp.btnLimpiar.addActionListener(this);
        this.vistaComp.btnActualizar.addActionListener(this);  
        this.vistaComp.btnComp1.addActionListener(this);
        this.vistaComp.btnComp2.addActionListener(this);
        this.vistaComp.btnComp3.addActionListener(this);
        this.vistaComp.btnComp4.addActionListener(this);
        this.vistaComp.btnComp5.addActionListener(this);
        this.vistaComp.btnComp6.addActionListener(this);
        this.vistaComp.btnComp7.addActionListener(this);
        this.vistaComp.btnComp8.addActionListener(this);
        this.vistaComp.btnComp9.addActionListener(this);
        this.vistaComp.btnComp10.addActionListener(this);
        this.vistaComp.btnComp11.addActionListener(this);
        this.vistaComp.btnComp12.addActionListener(this);
        this.vistaComp.btnComp13.addActionListener(this);
        this.vistaComp.btnComp14.addActionListener(this);
        this.vistaComp.btnComp15.addActionListener(this);
        this.vistaComp.btnComp16.addActionListener(this);
        this.vistaComp.btnComp17.addActionListener(this);
        this.vistaComp.btnComp18.addActionListener(this);
        this.vistaComp.btnComp19.addActionListener(this);
        this.vistaComp.btnComp20.addActionListener(this);
        this.vistaComp.btnComp21.addActionListener(this);
        this.vistaComp.btnComp22.addActionListener(this);
        this.vistaComp.btnComp23.addActionListener(this);
        this.vistaComp.btnComp24.addActionListener(this);
        this.vistaComp.btnComp25.addActionListener(this);
        this.vistaComp.btnComp26.addActionListener(this);
        this.vistaComp.btnComp27.addActionListener(this);
        this.vistaComp.btnComp28.addActionListener(this);
        this.vistaComp.btnComp29.addActionListener(this);
        this.vistaComp.btnComp30.addActionListener(this); 
    }
    
    //aqui se cargan todos los componentes que se iniciarian cuando se abra la vista
    public void iniciar(){             
        vistaComp.setLocationRelativeTo(null); 
        cargarTablaPrestados(vistaComp.tablePrestados);
        consultarStatusEquipo();
        fechaSistema();//carga la hora del sistema
        autocompletarMatricula();  
        autocompletarNombre(); 
        //estos campos de texto no estan visibles para el usuario
        //ya que solo se usa para hacer algunas operaciones del sistema
        vistaComp.txtFecha.setVisible(false);//sirve guardar la fecha del sistema en esta vista
        vistaComp.txtHora.setVisible(false);//sirve guardar la hora del sistema en esta vista
        vistaComp.txtFolio.setVisible(false);//sirve para guardar temporalmente el folio del prestamo para poder realizar su devolucion o eliminacion
        vistaComp.txtIdEquipo.setVisible(false);//sirve para guardar el id del equipo que se va a usar en las compus internas
        vistaInd.txtFecha.setVisible(false);//sirve para asignar las fechas a los prestamos de los equipos internos
        vistaInd.txtHora.setVisible(false);//sirve para asignar las horas a los prestamos de los equipos internos
        //estas declaraciones son para establecer el tamaño de las columnas de la tabla de equipos prestados
        TableColumnModel columnModel = vistaComp.tablePrestados.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(70);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(110);
        columnModel.getColumn(3).setPreferredWidth(40);      
        vistaComp.btnDevolver.setEnabled(false);
        vistaComp.btnCancelar.setEnabled(false);         
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
        vistaInd.txtFecha.setText(y+"/"+m+"/"+d);
        vistaComp.txtFecha.setText(y+"/"+m+"/"+d);      
        int h = c.get(Calendar.HOUR_OF_DAY), mm = c.get(Calendar.MINUTE), s = c.get(Calendar.SECOND);
        vistaInd.txtHora.setText(h+":"+mm+":"+s);
        vistaComp.txtHora.setText(h+":"+mm+":"+s);
    }
    
    //metodo para autocompletar el campo de la matricula del prestamo
    public void autocompletarMatricula(){
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaInd.txtMatricula);
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
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error para autocompletar la matrícula", "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //metodo para autocompletar el campo de l nombre del prestamo
    public void autocompletarNombre(){
        TextAutoCompleter textAuto = new TextAutoCompleter(vistaInd.txtNombre);
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
       
    //metodo que sirve para cargar los datos de la tabla de prestados
    public void cargarTablaPrestados(JTable tablePrestados){           
        ps = null;
        rs = null;
        Conexion conn = new Conexion();
        tablePrestados.setModel(modelo);
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"Folio", "Matricula", "Nombre(s)", "ID Equipo", "Estatus"});//asigna los titulos de las columnas
            try {
                Connection con = (Connection) conn.getConnection();
                //se realiza la consulta a la BD
                ps = con.prepareStatement("SELECT * FROM prestamosalum, alumno, equipo WHERE alumno.matricula = prestamosalum.alumno_matricula AND "
                        + "equipo.idEquipo = prestamosalum.equipo_idEquipo AND equipo.tipo='Interno' AND prestamosalum.status LIKE 'Prestado%'");
                rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        //asigna los valores obtenidos a la tabla
                        modelo.addRow(new Object[]{rs.getString("folio"),rs.getString("alumno_matricula"), rs.getString("nombre"), rs.getString("equipo_idEquipo"), rs.getString("status")});
                    }
                    vistaComp.tablePrestados.setModel(modelo);
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
    
    //metodo que sirve para asignar el id del equipo con el numero de inventario
    public void verificarIdEquipo(){
        Connection con = null;  
        //se realizan las consultas a las dos tablas
        //consulta si el numero de la computadora esta ligado al id del equipo y que a su ves el numero de inventario sea igual a ese id
        String temp1 = "SELECT * FROM equipo, equipointerno WHERE equipointerno.idEquipo=? AND equipointerno.numInv=equipo.numInv";
        try {
            con = (Connection) cc.getConnection();
            ps = con.prepareStatement(temp1);
            ps.setString(1, vistaInd.txtIdEquipo.getText());
            rs = ps.executeQuery();
            String numInv = "";
            //itera el resultado
            if (rs.next()) {   
                //asigna el id del equipo para poder realizar el prestamo
                numInv = rs.getString("equipo.idEquipo");
                vistaInd.txtIdEquipoTemp.setText(numInv);
        }
            System.out.println(numInv);                       
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override//En este metodo se realizan las acciones de las consultas
    public void actionPerformed(ActionEvent e){
        Connection con = null;          
        //compara si se ha seleccionado el boton de la computadora 1
        if (e.getSource().equals(vistaComp.btnComp1)) {  
            fechaSistema();//actualiza la hora del sistema
            //vistaInd.txtIdEquipoTemp.setText("");
            vistaInd.labelMsg2.setText(null);                    
            vistaInd.txtIdEquipo.setText("1");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);  
            vistaInd.setVisible(true);
            vistaInd.labelMsg.setText("Computadora N. 1");            
        }           
        //compara si se ha seleccionado el boton de la computadora 2
        if (e.getSource().equals(vistaComp.btnComp2)){  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);            
            vistaInd.labelMsg.setText("Computadora N. 2");
            vistaInd.txtIdEquipo.setText("2");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        }          
        //compara si se ha seleccionado el boton de la computadora 3
        if (e.getSource() == vistaComp.btnComp3) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 3");
            vistaInd.txtIdEquipo.setText("3");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        }   
        //compara si se ha seleccionado el boton de la computadora 4
        if (e.getSource() == vistaComp.btnComp4) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 4");
            vistaInd.txtIdEquipo.setText("4");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        }   
        //compara si se ha seleccionado el boton de la computadora 5
        if (e.getSource() == vistaComp.btnComp5) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);        
            vistaInd.labelMsg.setText("Computadora N. 5");
            vistaInd.txtIdEquipo.setText("5");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        }          
        //compara si se ha seleccionado el boton de la computadora 6
        if (e.getSource() == vistaComp.btnComp6) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);          
            vistaInd.labelMsg.setText("Computadora N. 6");
            vistaInd.txtIdEquipo.setText("6");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 7
        if (e.getSource() == vistaComp.btnComp7) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 7");
            vistaInd.txtIdEquipo.setText("7");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 8
        if (e.getSource() == vistaComp.btnComp8) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 8");
            vistaInd.txtIdEquipo.setText("8");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
            
        } 
        //compara si se ha seleccionado el boton de la computadora 9
        if (e.getSource() == vistaComp.btnComp9) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 9");
            vistaInd.txtIdEquipo.setText("9");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 10
        if (e.getSource() == vistaComp.btnComp10) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);         
            vistaInd.labelMsg.setText("Computadora N. 10");
            vistaInd.txtIdEquipo.setText("10");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 11
        if (e.getSource() == vistaComp.btnComp11) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 11");
            vistaInd.txtIdEquipo.setText("11");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 12
        if (e.getSource() == vistaComp.btnComp12) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);         
            vistaInd.labelMsg.setText("Computadora N. 12");
            vistaInd.txtIdEquipo.setText("12");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 13
        if (e.getSource() == vistaComp.btnComp13) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);          
            vistaInd.labelMsg.setText("Computadora N. 13");
            vistaInd.txtIdEquipo.setText("13");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 14
        if (e.getSource() == vistaComp.btnComp14) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 14");
            vistaInd.txtIdEquipo.setText("14");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 15
        if (e.getSource() == vistaComp.btnComp15) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);          
            vistaInd.labelMsg.setText("Computadora N. 15");
            vistaInd.txtIdEquipo.setText("15");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 16
        if (e.getSource() == vistaComp.btnComp16) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);            
            vistaInd.labelMsg.setText("Computadora N. 16");
            vistaInd.txtIdEquipo.setText("16");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 17
        if (e.getSource() == vistaComp.btnComp17) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);            
            vistaInd.labelMsg.setText("Computadora N. 17");
            vistaInd.txtIdEquipo.setText("17");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 18
        if (e.getSource() == vistaComp.btnComp18) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 18");
            vistaInd.txtIdEquipo.setText("18");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 19
        if (e.getSource() == vistaComp.btnComp19) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);          
            vistaInd.labelMsg.setText("Computadora N. 19");
            vistaInd.txtIdEquipo.setText("19");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 20
        if (e.getSource() == vistaComp.btnComp20) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);          
            vistaInd.labelMsg.setText("Computadora N. 20");
            vistaInd.txtIdEquipo.setText("20");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 21
        if (e.getSource() == vistaComp.btnComp21) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);          
            vistaInd.labelMsg.setText("Computadora N. 21");
            vistaInd.txtIdEquipo.setText("21");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 22
        if (e.getSource() == vistaComp.btnComp22) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);          
            vistaInd.labelMsg.setText("Computadora N. 22");
            vistaInd.txtIdEquipo.setText("22");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 23
        if (e.getSource() == vistaComp.btnComp23) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);        
            vistaInd.labelMsg.setText("Computadora N. 23");
            vistaInd.txtIdEquipo.setText("23");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        }
        //compara si se ha seleccionado el boton de la computadora 24
        if (e.getSource() == vistaComp.btnComp24) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 24");
            vistaInd.txtIdEquipo.setText("24");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 25
        if (e.getSource() == vistaComp.btnComp25) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);            
            vistaInd.labelMsg.setText("Computadora N. 25");
            vistaInd.txtIdEquipo.setText("25");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 26
        if (e.getSource() == vistaComp.btnComp26) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 26");
            vistaInd.txtIdEquipo.setText("26");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 27
        if (e.getSource() == vistaComp.btnComp27) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 27");
            vistaInd.txtIdEquipo.setText("27");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 28
        if (e.getSource() == vistaComp.btnComp28) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);            
            vistaInd.labelMsg.setText("Computadora N. 28");
            vistaInd.txtIdEquipo.setText("28");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 29
        if (e.getSource() == vistaComp.btnComp29) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 29");
            vistaInd.txtIdEquipo.setText("29");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
        //compara si se ha seleccionado el boton de la computadora 30
        if (e.getSource() == vistaComp.btnComp30) {  
            fechaSistema();//actualiza la hora del sistema
            vistaInd.labelMsg2.setText(null);           
            vistaInd.labelMsg.setText("Computadora N. 30");
            vistaInd.txtIdEquipo.setText("30");//id del equipo con el que esta ligado la vista
            verificarIdEquipo();
            vistaInd.setLocationRelativeTo(null);
            vistaInd.setVisible(true);
        } 
       
        //compara si se ha seleccionado el boton de guardar de la vista de las computadoras individuales (uso interno)
        if (e.getSource() == vistaInd.btnGuardar) {
                if (!vistaInd.txtMatricula.getText().equals("")) {                   
                    fechaSistema();//actualiza la hora del sistema
                    //Connection con = null;
                    String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo                   
                    try {
                        con = (Connection) cc.getConnection();
                        ps = con.prepareStatement(temp);
                        ps.setString(1, vistaInd.txtIdEquipoTemp.getText());
                        rs = ps.executeQuery();
                        //itera el resultado
                        if (rs.next()) {                           
                            //si el id es correcto, consulta su status y se lo asigna a una variable local
                            String tem = rs.getString("status");//variable local del status del equipo                   
                            //compara si el equipo se encuentra disponible con la variable local
                            if (tem.equals("Disponible")) {                               
                                prestamo.setMatricula(vistaInd.txtMatricula.getText());
                                //prestamo.setIdequipo(Integer.parseInt(vistaInd.txtIdEquipo.getText()));
                                prestamo.setIdequipo(Integer.parseInt(vistaInd.txtIdEquipoTemp.getText()));
                                prestamo.setFechaPrestamo(vistaInd.txtFecha.getText()+" "+vistaInd.txtHora.getText());
                                prestamo.setHoraPrestamo(vistaInd.txtHora.getText());
                                prestamo.setFechaUso(vistaInd.txtFecha.getText()+" "+vistaInd.txtHora.getText());
                                prestamo.setFechaDevolucion(vistaInd.txtFecha.getText()+" "+vistaInd.txtHora.getText());
                                prestamo.setHoraUso(vistaInd.txtHora.getText());
                                prestamo.setHoraFinUso(vistaInd.txtHora.getText());
                                prestamo.setStatus("Prestado");
                                //si no ha habido nigun error, se realizara el turno
                                if (conP.registrar(prestamo)) {
                                    JOptionPane.showMessageDialog(null, "Turno registrado correctamente");
                                    this.vistaInd.dispose();
                                    modificarStatusEquipoNoDisponible();//modifica el status del equipo
                                    consultarStatusEquipo();
                                    limpiar();//limpia los campos de texto
                                    reiniciar();//reinicia las tablas
                                    fechaSistema();//actualiza la hora del sistema
                                    cargarTablaPrestados(vistaComp.tablePrestados);;//actualiza los datos de la tabla de prestados  
                                    //this.vistaInd.dispose();
                                } //De lo contrario se muestra el mensaje de error
                                else {
                                    JOptionPane.showMessageDialog(null, "No se ha registrado el turno\nNo existe la matricula ingresada");                                   
                                    limpiar();//limpia las cajas de los textos
                                }
                            } //si el equipo no esta disponible manda el mensaje al usuario
                            else {
                                vistaInd.labelMsg2.setText("Equipo no disponible, seleccione otro");
                                limpiar();//limpia los campos de texto
                            }
                        }

                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    //si los campos estan vacios mandara un mensaje al usuario
                    vistaInd.labelMsg2.setText("No se aceptan campos vacios");
                    indicadoraColor = false;
                    limpiar();
                }
            }
         //compara si se ha seleccionado el boton de devolver de la vista del mapa de computadoras
        if (e.getSource() == vistaComp.btnDevolver) {
            fechaSistema();//actualiza la hora del sistema
            //primero compara si estan vacios el campo de texto del folio
            if (!vistaComp.txtFolio.getText().equals("")) {
                prestamo.setFolio(Integer.parseInt(vistaComp.txtFolio.getText()));
                String status = "Devuelto";
                prestamo.setFechaDevolucion(vistaInd.txtFecha.getText()+" "+vistaInd.txtHora.getText());
                prestamo.setHoraFinUso(vistaInd.txtHora.getText());
                prestamo.setStatus(status);//modifica el status del prestamo              
                //si no ocurrio ningun error, se procedera a modificar los datos
                if (conP.devolverCompu(prestamo)) {
                    JOptionPane.showMessageDialog(null, "Devolución exitosa");   
                    modificarStatusEquipoDisponible();//actualiza el status del equipo
                    consultarStatusEquipo();
                    limpiar();//limpia los campos de texto
                    reiniciar();//reinicia las tablas
                    fechaSistema();//actualiza la hora del sistema
                    cargarTablaPrestados(vistaComp.tablePrestados);//actualiza los datos de la tabla de prestados                     
                } else {
                    JOptionPane.showMessageDialog(null, "Devolución errónea");                   
                    limpiar();//limpia las cajas de los textos
                }                              
            }
            else{              
            }            
        }
        //compara si se ha seleccionado el boton de cancelar de la vista del mapa de computadoras
        if (e.getSource() == vistaComp.btnCancelar) {
            fechaSistema();//actualiza la hora del sistema
            //primero compara si estan vacios el campo de texto del folio
            if (!vistaComp.txtFolio.getText().equals("")) {
                prestamo.setFolio(Integer.parseInt(vistaComp.txtFolio.getText()));
                String status = "Cancelado";
                prestamo.setFechaDevolucion(vistaInd.txtFecha.getText()+" "+vistaInd.txtHora.getText());
                prestamo.setHoraFinUso(vistaInd.txtHora.getText());
                prestamo.setStatus(status);//actualiza el status del prestamo
                //si no ocurrio ningun error, se procedera a modificar los datos
                if (conP.devolverCompu(prestamo)) {
                    JOptionPane.showMessageDialog(null, "Cancelación exitosa"); 
                    modificarStatusEquipoDisponible();//actualiza el status del equipo
                    consultarStatusEquipo();
                    limpiar();//limpia los campos de texto
                    reiniciar();//reinicia las tablas
                    fechaSistema();//actualiza la hora del sistema
                    cargarTablaPrestados(vistaComp.tablePrestados);//actualiza los datos de la tabla de prestados                    
                } else {
                    JOptionPane.showMessageDialog(null, "Cancelación errónea");                      
                    limpiar();//limpia las cajas de los textos
                }                               
            }
            else{               
            }            
        }
        //compara si se ha seleccionado el boton de limpiar de la vista del mapa de computadoras
        if (e.getSource() == vistaComp.btnLimpiar) {
            limpiar();//limpia las cajas de los textos
        }
        //compara si se ha seleccionado el boton actualizar de la vista del mapa de computadoras
        if (e.getSource() == vistaComp.btnActualizar) {           
            reiniciar();//reinicia las tablas
            fechaSistema();//actualiza la hora del sistema            
            cargarTablaPrestados(vistaComp.tablePrestados);//actualiza los datos de la tabla de prestados             
        }    
    }    
    //Modifica el status "No disponible" del equipo cuando se realiza un prestamo exitoso      
    public void modificarStatusEquipoNoDisponible() {
        String statusE = "No Disponible";//variable local para asignar el status
        try {
            equipo.setId(Integer.parseInt(vistaInd.txtIdEquipoTemp.getText()));
            equipo.setStatus(statusE);//actualiza el status del equipo
            if (conE.establecerStatus(equipo)) {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }
    }
    //Modifica el status "Disponible" del equipo cuando se realiza una devolucion o cancelacion exitosa     
    public void modificarStatusEquipoDisponible(){       
        String statusE = "Disponible";//variable local para asignar el status
        try {
            equipo.setId(Integer.parseInt(vistaComp.txtIdEquipo.getText()));
            equipo.setStatus(statusE);//actualiza el status del equipo           
            if (conE.establecerStatus(equipo)) {
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //este metodo sirve para verificar el estatus actual de las computadoras internas en uso y asignarle un color dado su estatus
    //verde para disponible
    //rojo cuando esta en uso
    //si esta inactivo, deshabilita el boton
    public void consultarStatusEquipo(){
        Connection con = null;
        //estas variables locales son solo para comparar su status de los equipos
        String equipoId1 = "1";
        String equipoId2 = "2";
        String equipoId3 = "3";
        String equipoId4 = "4";
        String equipoId5 = "5";
        String equipoId6 = "6";
        String equipoId7 = "7";
        String equipoId8 = "8";
        String equipoId9 = "9";
        String equipoId10 = "10";
        String equipoId11 = "11";
        String equipoId12 = "12";
        String equipoId13 = "13";
        String equipoId14 = "14";
        String equipoId15 = "15";
        String equipoId16 = "16";
        String equipoId17 = "17";
        String equipoId18 = "18";
        String equipoId19 = "19";
        String equipoId20 = "20";
        String equipoId21 = "21";
        String equipoId22 = "22";
        String equipoId23 = "23";
        String equipoId24 = "24";
        String equipoId25 = "25";
        String equipoId26 = "26";
        String equipoId27 = "27";
        String equipoId28 = "28";
        String equipoId29 = "29";
        String equipoId30 = "30";
        String temp = "SELECT * FROM equipo, equipointerno WHERE equipointerno.idEquipo=? AND equipo.numInv=equipointerno.numInv";//consulta para verificar el id del equipo
        
        //compara el status de cada boton con sus respectivos id de equipos   
        //cuando el id del equipo sea igual a "1"
        if (equipoId1.equals("1")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
                try {
                    con = (Connection) cc.getConnection();
                    ps = con.prepareStatement(temp);
                    ps.setString(1, equipoId1);
                    rs = ps.executeQuery();
                    //itera el resultado
                    if (rs.next()) {
                        //si el id es correcto, consulta su status y se lo asigna a una variable local
                        String tem = rs.getString("status");//variable local del status del equipo                   
                        //compara si el equipo se encuentra disponible con la variable local
                        if (tem.equals("Disponible")) {
                            //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                            vistaComp.btnComp1.setBackground(new java.awt.Color(0, 153, 0));
                            vistaComp.btnComp1.setToolTipText("Equipo disponible");
                        } //si el equipo no esta disponible cambiara el boton de color rojo
                        if (tem.equals("No Disponible")) {
                            vistaComp.btnComp1.setBackground(new java.awt.Color(255, 50, 50));
                            vistaComp.btnComp1.setToolTipText("Equipo en uso");
                        }
                        if (tem.equals("Inactivo")) {
                            vistaComp.btnComp1.setEnabled(false);
                            vistaComp.btnComp1.setToolTipText("Equipo Inactivo");
                        }
                    }
                } catch (Exception exc) {
                    System.out.println("Error: " + exc.getMessage());
                }            
        }
        //cuando el id del equipo sea igual a "2"
        if (equipoId2.equals("2")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
                try {
                    con = (Connection) cc.getConnection();
                    ps = con.prepareStatement(temp);
                    ps.setString(1, equipoId2);
                    rs = ps.executeQuery();
                    //itera el resultado
                    if (rs.next()) {
                        //si el id es correcto, consulta su status y se lo asigna a una variable local
                        String tem = rs.getString("status");//variable local del status del equipo                   
                        //compara si el equipo se encuentra disponible con la variable local
                        if (tem.equals("Disponible")) {
                            //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                            vistaComp.btnComp2.setBackground(new java.awt.Color(0, 153, 0));
                            vistaComp.btnComp2.setToolTipText("Equipo disponible");
                        }//si el equipo no esta disponible cambiara el boton de color rojo
                        if(tem.equals("No Disponible")) {
                            vistaComp.btnComp2.setBackground(new java.awt.Color(255, 50, 50));
                            vistaComp.btnComp2.setToolTipText("Equipo en uso");
                        }
                        if (tem.equals("Inactivo")) {
                            vistaComp.btnComp2.setEnabled(false);
                            vistaComp.btnComp2.setToolTipText("Equipo Inactivo");
                        }
                    }
                } catch (Exception exc) {
                    System.out.println("Error: " + exc.getMessage());
                }            
        }
        //cuando el id del equipo sea igual a "3"
        if (equipoId3.equals("3")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId3);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp3.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp3.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp3.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp3.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp3.setEnabled(false);
                        vistaComp.btnComp3.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "4"
        if (equipoId4.equals("4")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId4);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp4.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp4.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp4.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp4.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp4.setEnabled(false);
                        vistaComp.btnComp4.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "5"
        if (equipoId5.equals("5")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId5);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp5.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp5.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp5.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp5.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp5.setEnabled(false);
                        vistaComp.btnComp5.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "6"
        if (equipoId6.equals("6")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId6);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp6.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp6.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")){
                        vistaComp.btnComp6.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp6.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp6.setEnabled(false);
                        vistaComp.btnComp6.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "7"
        if (equipoId7.equals("7")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId7);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp7.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp7.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp7.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp7.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp7.setEnabled(false);
                        vistaComp.btnComp7.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "8"
        if (equipoId8.equals("8")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId8);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp8.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp8.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp8.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp8.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp8.setEnabled(false);
                        vistaComp.btnComp8.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "9"
        if (equipoId9.equals("9")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId9);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp9.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp9.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp9.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp9.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp9.setEnabled(false);
                        vistaComp.btnComp9.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "10"
        if (equipoId10.equals("10")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId10);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp10.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp10.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp10.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp10.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp10.setEnabled(false);
                        vistaComp.btnComp10.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "11"
        if (equipoId11.equals("11")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId11);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp11.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp11.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp11.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp11.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp11.setEnabled(false);
                        vistaComp.btnComp11.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "12"
        if (equipoId12.equals("12")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId12);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp12.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp12.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp12.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp12.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp12.setEnabled(false);
                        vistaComp.btnComp12.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "13"
        if (equipoId13.equals("13")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId13);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp13.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp13.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp13.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp13.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp13.setEnabled(false);
                        vistaComp.btnComp13.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "14"
        if (equipoId14.equals("14")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId14);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp14.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp14.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")){
                        vistaComp.btnComp14.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp14.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp14.setEnabled(false);
                        vistaComp.btnComp14.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "15"
        if (equipoId15.equals("15")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId15);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp15.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp15.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")){
                        vistaComp.btnComp15.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp15.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp15.setEnabled(false);
                        vistaComp.btnComp15.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "16"
        if (equipoId16.equals("16")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId16);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp16.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp16.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")){
                        vistaComp.btnComp16.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp16.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp16.setEnabled(false);
                        vistaComp.btnComp16.setToolTipText("Equipo Inactivo");
                    }
                }

            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "17"
        if (equipoId17.equals("17")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId17);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp17.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp17.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")){
                        vistaComp.btnComp17.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp17.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp17.setEnabled(false);
                        vistaComp.btnComp17.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "18"
        if (equipoId18.equals("18")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId18);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp18.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp18.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")){
                        vistaComp.btnComp18.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp18.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp18.setEnabled(false);
                        vistaComp.btnComp18.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "19"
        if (equipoId19.equals("19")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId19);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp19.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp19.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp19.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp19.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp19.setEnabled(false);
                        vistaComp.btnComp19.setToolTipText("Equipo Inactivo");
                    }                   
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "20"
        if (equipoId20.equals("20")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId20);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp20.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp20.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp20.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp20.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp20.setEnabled(false);
                        vistaComp.btnComp20.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "21"
        if (equipoId21.equals("21")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId21);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp21.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp21.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp21.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp21.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp21.setEnabled(false);
                        vistaComp.btnComp21.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "22"
        if (equipoId22.equals("22")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId22);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp22.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp22.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp22.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp22.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp22.setEnabled(false);
                        vistaComp.btnComp22.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }            
        }
        //cuando el id del equipo sea igual a "23"
        if (equipoId23.equals("23")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId23);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp23.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp23.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp23.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp23.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp23.setEnabled(false);
                        vistaComp.btnComp23.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }             
        }
        //cuando el id del equipo sea igual a "24"
        if (equipoId24.equals("24")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId24);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp24.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp24.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp24.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp24.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp24.setEnabled(false);
                        vistaComp.btnComp24.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }             
        }
        //cuando el id del equipo sea igual a "25"
        if (equipoId25.equals("25")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId25);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp25.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp25.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp25.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp25.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp25.setEnabled(false);
                        vistaComp.btnComp25.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }             
        }
        //cuando el id del equipo sea igual a "26"
        if (equipoId26.equals("26")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId26);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp26.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp26.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp26.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp26.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp26.setEnabled(false);
                        vistaComp.btnComp26.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }             
        }
        //cuando el id del equipo sea igual a "27"
        if (equipoId27.equals("27")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId27);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp27.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp27.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp27.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp27.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp27.setEnabled(false);
                        vistaComp.btnComp27.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }             
        }
        //cuando el id del equipo sea igual a "28"
        if (equipoId28.equals("28")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId28);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp28.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp28.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp28.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp28.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp28.setEnabled(false);
                        vistaComp.btnComp28.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }             
        }
        //cuando el id del equipo sea igual a "29"
        if (equipoId29.equals("29")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId29);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp29.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp29.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp29.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp29.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp29.setEnabled(false);
                        vistaComp.btnComp29.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }             
        }
        //cuando el id del equipo sea igual a "30"
        if (equipoId30.equals("30")) {
            //String temp = "SELECT * FROM equipo WHERE idEquipo=? ";//consulta para verificar el id del equipo
            try {
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, equipoId30);
                rs = ps.executeQuery();
                //itera el resultado
                if (rs.next()) {
                    //si el id es correcto, consulta su status y se lo asigna a una variable local
                    String tem = rs.getString("status");//variable local del status del equipo                   
                    //compara si el equipo se encuentra disponible con la variable local
                    if (tem.equals("Disponible")) {
                        //si el eeuipo se encuentra disponible mostrara su respectivo boton de color verde
                        vistaComp.btnComp30.setBackground(new java.awt.Color(0, 153, 0));
                        vistaComp.btnComp30.setToolTipText("Equipo disponible");
                    }//si el equipo no esta disponible cambiara el boton de color rojo
                    if (tem.equals("No Disponible")) {
                        vistaComp.btnComp30.setBackground(new java.awt.Color(255, 50, 50));
                        vistaComp.btnComp30.setToolTipText("Equipo en uso");
                    }
                    if (tem.equals("Inactivo")) {
                        vistaComp.btnComp30.setEnabled(false);
                        vistaComp.btnComp30.setToolTipText("Equipo Inactivo");
                    }
                }
            } catch (Exception exc) {
                System.out.println("Error: " + exc.getMessage());
            }             
        }    
    }   
    //metodo para vaciar los campos de texto 
    public void limpiar(){
        vistaComp.txtFolio.setText(null);
        vistaInd.txtMatricula.setText(null);
        vistaInd.txtNombre.setText(null);
        vistaInd.txtApellidoP.setText(null);
        vistaComp.labelMsg.setText(null);    
        vistaComp.btnDevolver.setEnabled(false);
        vistaComp.btnCancelar.setEnabled(false);
    }      
    //evento para buscar datos de la base de datos por medio de los textField de la vista individual de las computadoras
    @Override
    public void keyReleased(KeyEvent e) {
        //compara si fue la caja de texto de la matricula
        if (e.getSource() == vistaInd.txtMatricula) {
            Connection con = null;             
            fechaSistema();//reinicia la hora del sistema cada vez que se haga esta accion
            //string de la consulta
            String temp = "SELECT * FROM alumno, carrera WHERE alumno.matricula LIKE ? AND alumno.carrera_idCarrera=idCarrera";                  
            try {              
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, vistaInd.txtMatricula.getText());
                rs = ps.executeQuery();                      
                while (rs.next()) {                                      
                   vistaInd.txtNombre.setText(rs.getString("nombre"));
                   vistaInd.txtApellidoP.setText(rs.getString("apellidoP") + " " + rs.getString("apellidoM"));
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
        //compara si fue la caja de texto del nombre
        if (e.getSource() == vistaInd.txtNombre) {
            Connection con = null;
            fechaSistema();//reinicia la hora del sistema cada vez que se haga esta accion
            //string de la consulta
            String temp = "SELECT * FROM alumno, carrera WHERE alumno.nombre LIKE ? AND alumno.carrera_idCarrera=idCarrera";            
            try {               
                con = (Connection) cc.getConnection();
                ps = con.prepareStatement(temp);
                ps.setString(1, vistaInd.txtNombre.getText());
                rs = ps.executeQuery();                      
               while (rs.next()) {                                     
                    vistaInd.txtMatricula.setText(rs.getString("matricula")); 
                    vistaInd.txtApellidoP.setText(rs.getString("apellidoP") + " " + rs.getString("apellidoM"));
                }
           } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }         
    }
    
    //evento para pasar los datos de la tabla "equipos en uso" a los campos de los textos
    @Override
    public void mouseClicked(MouseEvent e){
        vistaComp.btnDevolver.setEnabled(true);
        vistaComp.btnCancelar.setEnabled(true);
        ps = null;
        rs = null;
        try{
            Conexion ObjCon = new Conexion();
            com.mysql.jdbc.Connection conn = (com.mysql.jdbc.Connection) ObjCon.getConnection();
            int fila = vistaComp.tablePrestados.getSelectedRow();//obtiene el numero de filas de la tabla
            String id = vistaComp.tablePrestados.getValueAt(fila, 0).toString();//obtiene el valor 0 de la fila de la tabla            
            //realiza la consulta a la base de datos
            ps = conn.prepareStatement("SELECT * FROM prestamosalum, alumno, equipo WHERE folio=? AND alumno.matricula = prestamosalum.alumno_matricula AND equipo.idEquipo = prestamosalum.equipo_idEquipo");
            ps.setString(1, id);
            rs = ps.executeQuery();
                 
            //itera los resultados que coincidieron con la consulta
            while(rs.next()){
                //asigna los valores que coincidieron
                vistaComp.txtFolio.setText(rs.getString("folio"));
                vistaComp.txtIdEquipo.setText(rs.getString("equipo_idEquipo"));              
            }
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + exc.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
        }            
    }
   
    /*
    estos metodos no son utilizados, se crearon por que el sistema lo pidio    
    */
    @Override
    public void keyTyped(KeyEvent e) {    
    }
    
    @Override
    public void keyPressed(KeyEvent e) {    
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
}
