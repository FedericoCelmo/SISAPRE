/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Federico
 */
public class VistaPorcentajeOcupacion extends javax.swing.JFrame {
    
    Conexion conn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public VistaPorcentajeOcupacion() {
        initComponents();
        cbxCarrera.removeAllItems();
        cbxSala.removeAllItems();
        ArrayList<String> listaC = new ArrayList<>();
        ArrayList<String> listaS = new ArrayList<>();
        listaC = llenarComboCarrera();
        listaS = llenarComboSalon();
        //recorre el array para llenar el combobox de las carreras
        for (int i = 0; i < listaC.size(); i++) {
            cbxCarrera.addItem(String.valueOf(listaC.get(i)));
        } 
        //recorre el array para llenar el combobox de los salones
        for (int i = 0; i < listaS.size(); i++) {
            cbxSala.addItem(String.valueOf(listaS.get(i)));
        } 
    }

    //este metodo es para insertar el icono de la ventana
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/Sisapre001.png"));
        return retValue;
    }
    
    //este metodo llena el combobox de las carreras de los alumnos
    public  ArrayList<String> llenarComboCarrera(){
        ArrayList<String> lista = new ArrayList<String>();
        Connection con = null;
        try{
            con = conn.getConnection();
            ps = con.prepareStatement("SELECT * FROM carrera");
            rs = ps.executeQuery();
            while(rs.next()){
                lista.add(rs.getString("nomCarrera"));           
            }
                       
        } catch (Exception e) {
            System.out.println(e);
        }
        finally{
            try{
                con.close();
            }
            catch(Exception e){
                System.out.println("Error!");
            }        
        }       
        return lista;    
    }
    
    //este metodo llena el combobox de los salones 
    public  ArrayList<String> llenarComboSalon(){
        ArrayList<String> lista = new ArrayList<String>();
        Connection con = null;
        try{
            con = conn.getConnection();
            ps = con.prepareStatement("SELECT * FROM sala");
            rs = ps.executeQuery();
            while(rs.next()){
                lista.add(rs.getString("nomSala"));           
            }
                       
        } catch (Exception e) {
            System.out.println(e);
        }
        finally{
            try{
                con.close();
            }
            catch(Exception e){
                System.out.println("Error!");
            }        
        }       
        return lista;    
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupPorcentaje = new javax.swing.ButtonGroup();
        buttonGroupEquipo = new javax.swing.ButtonGroup();
        buttonGroupSalon = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        labelFecha = new javax.swing.JLabel();
        jDCFechaInicial = new com.toedter.calendar.JDateChooser();
        labelFechaInicial = new javax.swing.JLabel();
        labelEntre = new javax.swing.JLabel();
        labelFechaFinal = new javax.swing.JLabel();
        jDCFechaFinal = new com.toedter.calendar.JDateChooser();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jRadioBtnEquipo = new javax.swing.JRadioButton();
        jRadioBtnGeneralEquipo = new javax.swing.JRadioButton();
        jRadioLicEquipo = new javax.swing.JRadioButton();
        cbxCarrera = new javax.swing.JComboBox<>();
        btnMostrarE = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jRadioBtnSalon = new javax.swing.JRadioButton();
        jRadioBtnSalonGeneral = new javax.swing.JRadioButton();
        jRadioBtnPorSalon = new javax.swing.JRadioButton();
        cbxSala = new javax.swing.JComboBox<>();
        btnMostrarS = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePorcentaje = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDias = new javax.swing.JTextField();
        txtHoras = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Porcentaje de Ocupación");
        setIconImage(getIconImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelFecha.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        labelFecha.setText("Ingrese las fechas:");

        jDCFechaInicial.setDateFormatString("yyyy/MM/dd");

        labelFechaInicial.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        labelFechaInicial.setText("Inicial:");

        labelEntre.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        labelEntre.setText("entre");

        labelFechaFinal.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        labelFechaFinal.setText("Final:");

        jDCFechaFinal.setDateFormatString("yyyy/MM/dd");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFecha)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelFechaInicial)
                    .addComponent(jDCFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(labelEntre)
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelFechaFinal)
                    .addComponent(jDCFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(labelEntre))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(labelFechaFinal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDCFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelFechaInicial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDCFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelFecha))))
                .addContainerGap())
        );

        jSeparator1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        jLabel1.setText("Mostrar porcentaje de ocupación por:");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroupPorcentaje.add(jRadioBtnEquipo);
        jRadioBtnEquipo.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        jRadioBtnEquipo.setText("Equipos Internos");
        jRadioBtnEquipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        buttonGroupEquipo.add(jRadioBtnGeneralEquipo);
        jRadioBtnGeneralEquipo.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jRadioBtnGeneralEquipo.setText("General");
        jRadioBtnGeneralEquipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        buttonGroupEquipo.add(jRadioLicEquipo);
        jRadioLicEquipo.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jRadioLicEquipo.setText("Licenciatura");
        jRadioLicEquipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cbxCarrera.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        cbxCarrera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCarrera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnMostrarE.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnMostrarE.setText("Mostrar");
        btnMostrarE.setToolTipText("Mostrar el porcentaje de ocupación");
        btnMostrarE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jRadioLicEquipo)
                            .addComponent(jRadioBtnGeneralEquipo)
                            .addComponent(cbxCarrera, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMostrarE, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)))
                    .addComponent(jRadioBtnEquipo))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioBtnEquipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioBtnGeneralEquipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioLicEquipo)
                .addGap(18, 18, 18)
                .addComponent(cbxCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnMostrarE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroupPorcentaje.add(jRadioBtnSalon);
        jRadioBtnSalon.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        jRadioBtnSalon.setText("Salones");
        jRadioBtnSalon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        buttonGroupSalon.add(jRadioBtnSalonGeneral);
        jRadioBtnSalonGeneral.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jRadioBtnSalonGeneral.setText("General");
        jRadioBtnSalonGeneral.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        buttonGroupSalon.add(jRadioBtnPorSalon);
        jRadioBtnPorSalon.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jRadioBtnPorSalon.setText("Salón");
        jRadioBtnPorSalon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cbxSala.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        cbxSala.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxSala.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnMostrarS.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnMostrarS.setText("Mostrar");
        btnMostrarS.setToolTipText("Mostrar el porcentaje de ocupación");
        btnMostrarS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jRadioBtnPorSalon)
                            .addComponent(jRadioBtnSalonGeneral)
                            .addComponent(cbxSala, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMostrarS, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)))
                    .addComponent(jRadioBtnSalon))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioBtnSalon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioBtnSalonGeneral)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioBtnPorSalon)
                .addGap(18, 18, 18)
                .addComponent(cbxSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnMostrarS)
                .addContainerGap())
        );

        tablePorcentaje.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Equipo", "Descripción", "Número Inv.", "Porcentaje"
            }
        ));
        jScrollPane1.setViewportView(tablePorcentaje);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnImprimir.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imprimir.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setToolTipText("Imprime el contenido actual de la lista");
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnExportar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Excell.png"))); // NOI18N
        btnExportar.setText("Exportar");
        btnExportar.setToolTipText("Exporta el contenido actual de la lista a excell");
        btnExportar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel2.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel2.setText("Días Transcurridos:");

        jLabel3.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        jLabel3.setText("Horas Totales:");

        txtDias.setEditable(false);
        txtDias.setToolTipText("Días transcurridos sin contar sábados y domingos");

        txtHoras.setEditable(false);
        txtHoras.setToolTipText("Horas totales disponibles (dias transcurridos * 13)");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExportar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDias))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnImprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExportar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(txtDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHoras, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        VistaPrincipalAdmin.vistaPorcentaje = null;
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnExportar;
    public javax.swing.JButton btnImprimir;
    public javax.swing.JButton btnMostrarE;
    public javax.swing.JButton btnMostrarS;
    private javax.swing.ButtonGroup buttonGroupEquipo;
    private javax.swing.ButtonGroup buttonGroupPorcentaje;
    private javax.swing.ButtonGroup buttonGroupSalon;
    public javax.swing.JComboBox<String> cbxCarrera;
    public javax.swing.JComboBox<String> cbxSala;
    public com.toedter.calendar.JDateChooser jDCFechaFinal;
    public com.toedter.calendar.JDateChooser jDCFechaInicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JRadioButton jRadioBtnEquipo;
    public javax.swing.JRadioButton jRadioBtnGeneralEquipo;
    public javax.swing.JRadioButton jRadioBtnPorSalon;
    public javax.swing.JRadioButton jRadioBtnSalon;
    public javax.swing.JRadioButton jRadioBtnSalonGeneral;
    public javax.swing.JRadioButton jRadioLicEquipo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JLabel labelEntre;
    public javax.swing.JLabel labelFecha;
    public javax.swing.JLabel labelFechaFinal;
    public javax.swing.JLabel labelFechaInicial;
    public javax.swing.JTable tablePorcentaje;
    public javax.swing.JTextField txtDias;
    public javax.swing.JTextField txtHoras;
    // End of variables declaration//GEN-END:variables
}
