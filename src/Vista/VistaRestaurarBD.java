/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Conexion;
import Modelo.ControlBD;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico
 */
public class VistaRestaurarBD extends javax.swing.JFrame {

    
    String path = "";//string para construir la ruta
    String respaldo = "mysql -u root -proot prestamos";//aqui va el nombre de la base de datos en la cual se va a restaurar el respaldo
    VistaPrincipalAdmin pp;
    
    public VistaRestaurarBD() {
        initComponents();
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtRuta = new javax.swing.JTextField();
        btnRuta = new javax.swing.JButton();
        btnRestaurar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Restaurar ");
        setIconImage(getIconImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel1.setText("Restauración de la base de datos");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/botonRestaurarBD.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel3.setText("Ruta:");

        txtRuta.setToolTipText("Ruta de ubicación del respaldo");
        txtRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutaActionPerformed(evt);
            }
        });

        btnRuta.setText("...");
        btnRuta.setToolTipText("Abrir la carpeta de ubicación del respaldo");
        btnRuta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRutaActionPerformed(evt);
            }
        });

        btnRestaurar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnRestaurar.setText("Restaurar");
        btnRestaurar.setToolTipText("Restaurar respaldo");
        btnRestaurar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRestaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestaurarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancelar operación");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                            .addComponent(btnRestaurar, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                            .addComponent(txtRuta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRuta))
                .addGap(18, 18, 18)
                .addComponent(btnRestaurar)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutaActionPerformed

    private void btnRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRutaActionPerformed
        //aqui se abre la direccion donde esta guardado el respaldo 
        ControlBD o = new ControlBD();
        File file = o.Abrir(this);
        if (file != null) {
            String cad = file.getAbsolutePath();
            txtRuta.setText(cad);
        }
    }//GEN-LAST:event_btnRutaActionPerformed

    private void btnRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestaurarActionPerformed
        if (txtRuta.getText().length() > 0) {// compara si el campo de texto de la ruta tiene caracteres
            try {
                File file;
                file = new File(txtRuta.getText());
                path = file.getAbsolutePath();
                int c = JOptionPane.showConfirmDialog(pp, "¿Desea restaurar esta base de datos:?\n " + path, "mensaje de confirmacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (c == JOptionPane.YES_OPTION) {
                    Process p = Runtime.getRuntime().exec(respaldo);

                    OutputStream os = p.getOutputStream(); //Pedimos la salida
                    FileInputStream fis = new FileInputStream(path); //creamos el archivo para le respaldo
                    byte[] buffer = new byte[100000]; //Creamos una variable de tipo byte para el buffer

                    int leido = fis.read(buffer);//Devuelve el número de bytes leídos o -1 si se alcanzó el final del stream.
                    while (leido > 0) {
                        os.write(buffer, 0, leido); //Buffer de caracteres, Desplazamiento de partida para empezar a escribir caracteres, Número de caracteres para escribir
                        leido = fis.read(buffer);
                    }
                    JOptionPane.showMessageDialog(pp, "La base de datos ha sido restaurada correctamente");
                    VistaPrincipalAdmin.vistaRestaurarBD = null;
                    this.dispose();
                    os.flush(); //vacía los buffers de salida
                    os.close(); //Cierra
                    fis.close(); //Cierra respaldo
                }
                VistaPrincipalAdmin.vistaRestaurarBD = null;

            } catch (Exception e) {           
                JOptionPane.showMessageDialog(this, e.getMessage());
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + e.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        }
    }//GEN-LAST:event_btnRestaurarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        VistaPrincipalAdmin.vistaRestaurarBD = null;
        VistaRestaurarBD vista = new VistaRestaurarBD();
        vista.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        VistaPrincipalAdmin.vistaRestaurarBD = null;
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRestaurar;
    private javax.swing.JButton btnRuta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtRuta;
    // End of variables declaration//GEN-END:variables
}
