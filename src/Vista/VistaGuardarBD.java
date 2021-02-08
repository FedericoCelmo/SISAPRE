/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.ControlBD;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Federico
 */
public class VistaGuardarBD extends javax.swing.JFrame {

    String path = "";
    String respaldo = "mysqldump -u root -proot prestamos";
    //String backup="mysqldump -u root -proot usuarios > "+path;
    private String extension = ".sql";   
    VistaPrincipalAdmin pp;
    
    public VistaGuardarBD() {
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

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtRuta = new javax.swing.JTextField();
        btnRuta = new javax.swing.JButton();
        btnCrear = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear copia de seguridad");
        setIconImage(getIconImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/botonGuardarBD.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel1.setText("Crear copia de seguridad de la base de datos");

        jLabel3.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel3.setText("Ruta:");

        txtRuta.setToolTipText("Ubicación para guardar la base de datos");

        btnRuta.setText("...");
        btnRuta.setToolTipText("Elige la carpeta de destino");
        btnRuta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRutaActionPerformed(evt);
            }
        });

        btnCrear.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnCrear.setText("Crear");
        btnCrear.setToolTipText("Crear respaldo");
        btnCrear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                        .addComponent(btnCrear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRuta))
                .addGap(18, 18, 18)
                .addComponent(btnCrear)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        VistaPrincipalAdmin.vistaGuardarBS = null;
        VistaGuardarBD vista = new VistaGuardarBD();
        vista.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed
    
    private void btnRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRutaActionPerformed
        //aqui se seleciona la direccion donde se guardara la base de datos
        ControlBD o = new ControlBD();
        o.ResetFiltros();
        File file= o.guardar(this);
        if(file!=null){
           String cad= file.getAbsolutePath();
           txtRuta.setText(cad);
        }
    }//GEN-LAST:event_btnRutaActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed

        if (txtRuta.getText().length() > 0) {// compara si el campo de texto de la ruta tiene caracteres
            try {
                File file;
                file = new File(txtRuta.getText());
                path = file.getAbsolutePath() + extension;
                System.out.println("" + path);
                int c = JOptionPane.showConfirmDialog(pp, "¿Desea crear una copia de seguridad en esta ruta:?\n " + path, "Mensaje de Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (c == JOptionPane.YES_OPTION) {
                    Process p = Runtime.getRuntime().exec(respaldo);

                    InputStream is = p.getInputStream();//Pedimos la entrada
                    FileOutputStream fos = new FileOutputStream(path); //creamos el archivo para le respaldo
                    byte[] buffer = new byte[100000]; //Creamos una variable de tipo byte para el buffer

                    int leido = is.read(buffer); //Devuelve el número de bytes leídos o -1 si se alcanzó el final del stream.
                    while (leido > 0) {
                        fos.write(buffer, 0, leido);//Buffer de caracteres, Desplazamiento de partida para empezar a escribir caracteres, Número de caracteres para escribir
                        leido = is.read(buffer);
                    }
                    JOptionPane.showMessageDialog(pp, "Respaldo creado correctamente en \n"+ file.getPath());
                    VistaPrincipalAdmin.vistaGuardarBS = null;
                    this.dispose();
                    fos.close();//Cierra respaldo
                }
                VistaPrincipalAdmin.vistaGuardarBS = null;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:\n" + ex.getMessage(), "Error", 
                                                        JOptionPane.ERROR_MESSAGE); 
            }
        } else {
            VistaPrincipalAdmin.vistaGuardarBS = null;
            JOptionPane.showMessageDialog(this, "Eliga la ruta para guardar la base de datos, \nposteriormente escriba el nombre de la base de datos");
        }
    }//GEN-LAST:event_btnCrearActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        VistaPrincipalAdmin.vistaGuardarBS = null;
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnRuta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtRuta;
    // End of variables declaration//GEN-END:variables
}
