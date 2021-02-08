/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.ControladorPrestamo;
import Modelo.ControlBD;
import Modelo.Conexion;
import Modelo.ConsultasEquipo;
import Modelo.ConsultasPrestamo;
import Modelo.ConsultasUsuario;
import Modelo.Equipo;
import Modelo.Hash;
import Modelo.Prestamo;
import Modelo.Usuario;
import java.awt.Image;
import java.awt.Toolkit;
import com.mysql.jdbc.Connection;
import java.awt.Dimension;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author Federico
 */
public class VistaLogin extends javax.swing.JFrame {

    private Dimension dim;//esta variable es para ajustar el tamaño de la pantalla segun su resolucion
    Conexion cc = new Conexion();
    Connection cn = (Connection) cc.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    VistaAcercaDe vistaAcerca = new VistaAcercaDe();
    VistaRestaurarBD vistaRBD = new VistaRestaurarBD();
    
    public VistaLogin() {
        //con esto se obtienes el tamano en en x y y del monitor
        dim=super.getToolkit().getScreenSize();
        super.setSize(dim);       
        initComponents();          
    }
         
    //este metodo es para insertar el icono de la ventana
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/Sisapre001.png"));
        return retValue;//
    }
  /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtCon = new javax.swing.JPasswordField();
        btnIniciar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelWarning = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuRestaurar = new javax.swing.JMenuItem();
        menuAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Iniciar Sesión");
        setIconImage(getIconImage());
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Sisapre001.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Letras Sisapre.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Swis721 BT", 0, 24)); // NOI18N
        jLabel3.setText("Inicio Sesión ");

        txtUsuario.setToolTipText("Ingrese su nombre de usuario");
        txtUsuario.setName(""); // NOI18N
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });

        txtCon.setToolTipText("Ingrese su contraseña");
        txtCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConActionPerformed(evt);
            }
        });

        btnIniciar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnIniciar.setText("Iniciar Sesión");
        btnIniciar.setToolTipText("Iiniciar la sesión");
        btnIniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Swis721 Lt BT", 0, 12)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setToolTipText("Salir del sistema");
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Swis721 Blk BT", 0, 12)); // NOI18N
        jLabel4.setText("Usuario");

        jLabel5.setFont(new java.awt.Font("Swis721 Blk BT", 0, 12)); // NOI18N
        jLabel5.setText("Contraseña");

        labelWarning.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        labelWarning.setForeground(new java.awt.Color(204, 0, 0));

        jMenuBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenu1.setText("Opciones");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menuRestaurar.setText("Restaurar base de datos");
        menuRestaurar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuRestaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRestaurarActionPerformed(evt);
            }
        });
        jMenu1.add(menuRestaurar);

        menuAcercaDe.setText("Acerca de");
        menuAcercaDe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAcercaDeActionPerformed(evt);
            }
        });
        jMenu1.add(menuAcercaDe);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4)
                                .addComponent(txtCon, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                                .addComponent(btnIniciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelWarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel3)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConActionPerformed
        
    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        // TODO add your handling code here:   
        ConsultasUsuario mod = new ConsultasUsuario();
        Usuario usuario = new Usuario();
        String pass = new String(txtCon.getPassword());
        if (!txtUsuario.getText().equals("") && !pass.equals("")) {
            String nuevoPass = Hash.sha1(pass);
            usuario.setNick(txtUsuario.getText());
            usuario.setPassword(nuevoPass);           
            if (mod.login(usuario)) {              
                Prestamo prestamo = new Prestamo();
                Equipo equipo = new Equipo();
                ConsultasEquipo conE = new ConsultasEquipo();
                ConsultasPrestamo conP= new ConsultasPrestamo();
                VistaPrincipalAdmin vistaP = new VistaPrincipalAdmin(usuario);
                ControladorPrestamo ctrl = new ControladorPrestamo(prestamo, conP, vistaP, equipo, conE);
                ctrl.iniciar();              
                vistaP.setVisible(true);
                this.dispose();
            }
            else{
                /*JOptionPane.showMessageDialog(null, "Acceso denegado:\n"
                    + "Por favor ingrese un usuario y/o contraseña correctos", "Acceso denegado",
                    JOptionPane.ERROR_MESSAGE);*/
                labelWarning.setText("Usuario y/o contraseña incorrectos");
            }
        }
        else{
            /*JOptionPane.showMessageDialog(null, "No se aceptan campos vacíos:\n"
                    + "Por favor ingrese un usuario y/o contraseña correctos", "Acceso denegado",
                    JOptionPane.ERROR_MESSAGE);*/
            labelWarning.setText("No se aceptan campos vacíos");
        }        
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void menuRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRestaurarActionPerformed
        // TODO add your handling code here:
        //VistaRestaurarBD vistaRBD = new VistaRestaurarBD();
        vistaRBD.setLocationRelativeTo(null);
        vistaRBD.setVisible(true);
    }//GEN-LAST:event_menuRestaurarActionPerformed

    private void menuAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAcercaDeActionPerformed
        // TODO add your handling code here:
        //VistaAcercaDe vistaAcerca = new VistaAcercaDe();
        vistaAcerca.setLocationRelativeTo(null);
        vistaAcerca.setVisible(true);
    }//GEN-LAST:event_menuAcercaDeActionPerformed
    
    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel labelWarning;
    private javax.swing.JMenuItem menuAcercaDe;
    private javax.swing.JMenuItem menuRestaurar;
    private javax.swing.JPasswordField txtCon;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
