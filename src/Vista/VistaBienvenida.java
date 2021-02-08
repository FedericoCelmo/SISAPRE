/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Conexion;
import com.mysql.jdbc.Connection;
import static javafx.scene.paint.Color.color;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Federico
 */

//esta vista es de la bienvenida, la primera que se ejecuta
public class VistaBienvenida extends javax.swing.JFrame {    
    private int aux;
    private boolean window = false;
    hilo execute = new hilo();    
    public VistaBienvenida() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        VistaBienvenida.this.getRootPane().setOpaque(false);
        VistaBienvenida.this.getContentPane().setBackground(new Color(0, 0, 0, 0));
        VistaBienvenida.this.setBackground(new Color(0, 0 ,0, 0));
        this.setResizable(false);
        this.setLocationRelativeTo(this);
    }
    
    //este metodo es para insertar el icono de la ventana
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/Sisapre001.png"));
        return retValue;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barra = new javax.swing.JProgressBar();
        txtBienvenido = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(getIconImage());
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        barra.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        barra.setForeground(new java.awt.Color(153, 102, 0));
        barra.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        getContentPane().add(barra, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 384, 26));

        txtBienvenido.setFont(new java.awt.Font("Swis721 BT", 1, 14)); // NOI18N
        txtBienvenido.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(txtBienvenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 444, 320, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SIsapre_inicio01.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        if (window == false) {
            window = true;
            barra.setMaximum(49);
            barra.setMinimum(0);
            barra.setStringPainted(true);
            execute.start();
        }           
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaBienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaBienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaBienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaBienvenida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaBienvenida().setVisible(true);
            }
        });
    }
    
    private class hilo extends Thread {
        @Override
        public void run() {
            Conexion conn = new Conexion();
            try {
                while (true) {
                    aux++;
                    barra.setValue(aux);
                    repaint();
                    switch (aux) {
                        case 5:
                            txtBienvenido.setText("Cargando sistema...");
                            break;

                        case 30:
                            txtBienvenido.setText("Conectado con la Base de datos...");
                            break;

                        case 40:
                            Connection con = null;
                            con = (Connection) conn.getConnection();
                            if (conn.getAux() == 1) {
                                txtBienvenido.setText("Conexión establecida...");
                            } else {
                                txtBienvenido.setText("Conexión erronéa...");
                            }
                            break;

                        case 60:
                            txtBienvenido.setText("Iniciando...");
                            VistaLogin vista = new VistaLogin();
                            vista.setLocationRelativeTo(VistaBienvenida.this);
                            vista.setVisible(true);
                            VistaBienvenida.this.dispose();
                            break;
                    }
                    Thread.sleep(100);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel txtBienvenido;
    // End of variables declaration//GEN-END:variables
}

