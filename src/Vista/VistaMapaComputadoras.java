/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Federico
 */
public class VistaMapaComputadoras extends javax.swing.JFrame {

    /**
     * Creates new form VistaMapaComputadoras
     */
    public VistaMapaComputadoras() {
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

        btnComp16 = new javax.swing.JButton();
        btnComp1 = new javax.swing.JButton();
        btnComp2 = new javax.swing.JButton();
        btnComp3 = new javax.swing.JButton();
        btnComp4 = new javax.swing.JButton();
        btnComp5 = new javax.swing.JButton();
        btnComp6 = new javax.swing.JButton();
        btnComp7 = new javax.swing.JButton();
        btnComp8 = new javax.swing.JButton();
        btnComp9 = new javax.swing.JButton();
        btnComp10 = new javax.swing.JButton();
        btnComp11 = new javax.swing.JButton();
        btnComp12 = new javax.swing.JButton();
        btnComp13 = new javax.swing.JButton();
        btnComp14 = new javax.swing.JButton();
        btnComp15 = new javax.swing.JButton();
        btnComp17 = new javax.swing.JButton();
        btnComp18 = new javax.swing.JButton();
        btnComp19 = new javax.swing.JButton();
        btnComp20 = new javax.swing.JButton();
        btnComp21 = new javax.swing.JButton();
        btnComp22 = new javax.swing.JButton();
        btnComp23 = new javax.swing.JButton();
        btnComp24 = new javax.swing.JButton();
        btnComp25 = new javax.swing.JButton();
        btnComp26 = new javax.swing.JButton();
        btnComp27 = new javax.swing.JButton();
        btnComp28 = new javax.swing.JButton();
        btnComp29 = new javax.swing.JButton();
        btnComp30 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablePrestados = new javax.swing.JTable();
        btnDevolver = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        labelMsg = new javax.swing.JLabel();
        txtFolio = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        txtIdEquipo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de turnos internos");
        setIconImage(getIconImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnComp16.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp16.setText("16");
        btnComp16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp16, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, -1, 50));

        btnComp1.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp1.setText("1");
        btnComp1.setToolTipText("");
        btnComp1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnComp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComp1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnComp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 300, -1, 50));

        btnComp2.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp2.setText("2");
        btnComp2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 240, -1, 50));

        btnComp3.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp3.setText("3");
        btnComp3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 180, -1, 50));

        btnComp4.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp4.setText("4");
        btnComp4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnComp4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComp4ActionPerformed(evt);
            }
        });
        getContentPane().add(btnComp4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 120, -1, 50));

        btnComp5.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp5.setText("5");
        btnComp5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 50, -1, 50));

        btnComp6.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp6.setText("6");
        btnComp6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 50, -1, 50));

        btnComp7.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp7.setText("7");
        btnComp7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp7, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 50, -1, 50));

        btnComp8.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp8.setText("8");
        btnComp8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp8, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 50, -1, 50));

        btnComp9.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp9.setText("9");
        btnComp9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp9, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 50, -1, 50));

        btnComp10.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp10.setText("10");
        btnComp10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp10, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, -1, 50));

        btnComp11.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp11.setText("11");
        btnComp11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp11, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, -1, 50));

        btnComp12.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp12.setText("12");
        btnComp12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp12, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, -1, 50));

        btnComp13.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp13.setText("13");
        btnComp13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp13, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, -1, 50));

        btnComp14.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp14.setText("14");
        btnComp14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp14, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, -1, 50));

        btnComp15.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp15.setText("15");
        btnComp15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp15, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 240, -1, 50));

        btnComp17.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp17.setText("17");
        btnComp17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp17, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 380, -1, 50));

        btnComp18.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp18.setText("18");
        btnComp18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp18, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 380, -1, 50));

        btnComp19.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp19.setText("19");
        btnComp19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp19, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 380, -1, 50));

        btnComp20.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp20.setText("20");
        btnComp20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp20, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 380, -1, 50));

        btnComp21.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp21.setText("21");
        btnComp21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp21, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 380, -1, 50));

        btnComp22.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp22.setText("22");
        btnComp22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp22, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 380, -1, 50));

        btnComp23.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp23.setText("23");
        btnComp23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp23, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 280, -1, 50));

        btnComp24.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp24.setText("24");
        btnComp24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp24, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 150, -1, 50));

        btnComp25.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp25.setText("25");
        btnComp25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp25, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 150, -1, 50));

        btnComp26.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp26.setText("26");
        btnComp26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp26, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 150, -1, 50));

        btnComp27.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp27.setText("27");
        btnComp27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp27, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 150, -1, 50));

        btnComp28.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp28.setText("28");
        btnComp28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp28, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 280, -1, 50));

        btnComp29.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp29.setText("29");
        btnComp29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp29, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 280, -1, 50));

        btnComp30.setFont(new java.awt.Font("Swis721 BT", 1, 10)); // NOI18N
        btnComp30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ComputadoraSmall.png"))); // NOI18N
        btnComp30.setText("30");
        btnComp30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnComp30, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 280, -1, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PanelCompus.png"))); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel1.setFocusable(false);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 620, 420));

        jLabel2.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        jLabel2.setText("Mapa general del área de tareas");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 620, 20));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setFocusable(false);

        tablePrestados.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tablePrestados.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        tablePrestados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Folio", "Matricula", "Nombre", "ID Equipo", "Estatus"
            }
        ));
        tablePrestados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tablePrestados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePrestadosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablePrestados);

        btnDevolver.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnDevolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Devolver.png"))); // NOI18N
        btnDevolver.setText("Devolver");
        btnDevolver.setToolTipText("Realiza la devolución del equipo en uso");
        btnDevolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnCancelar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancela el prestamo seleccionado");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel7.setFont(new java.awt.Font("Swis721 BT", 1, 12)); // NOI18N
        jLabel7.setText("Equipos Internos en Uso");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Disponible");

        jLabel3.setBackground(new java.awt.Color(255, 0, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/CuadroVerde.png"))); // NOI18N

        jLabel4.setBackground(new java.awt.Color(255, 0, 0));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/CuadroRojo.png"))); // NOI18N

        jLabel6.setText("No Disponible");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addContainerGap())
        );

        btnLimpiar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Limpiar.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setToolTipText("Limpia los campos de texto");
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnActualizar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setToolTipText("Actualiza los datos de la tabla");
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDevolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar)
                    .addComponent(btnActualizar)))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 530, 420));

        labelMsg.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        labelMsg.setForeground(new java.awt.Color(204, 0, 0));
        getContentPane().add(labelMsg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 340, 20));

        txtFolio.setEditable(false);
        txtFolio.setEnabled(false);
        getContentPane().add(txtFolio, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 450, 30, 20));

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(0, 153, 0));
        txtFecha.setEnabled(false);
        getContentPane().add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 450, 30, 20));

        txtHora.setEditable(false);
        txtHora.setEnabled(false);
        getContentPane().add(txtHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 450, 30, 20));
        getContentPane().add(txtIdEquipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 450, 30, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablePrestadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePrestadosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablePrestadosMouseClicked

    private void btnComp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComp1ActionPerformed
        /*if (btnComp1.getBackground().equals(255, 50, 50)) {
            btnComp1.setToolTipText("Disponible");
        }*/
    }//GEN-LAST:event_btnComp1ActionPerformed

    private void btnComp4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComp4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnComp4ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        VistaPrincipalAdmin.vistaMapaC = null;
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnActualizar;
    public javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnComp1;
    public javax.swing.JButton btnComp10;
    public javax.swing.JButton btnComp11;
    public javax.swing.JButton btnComp12;
    public javax.swing.JButton btnComp13;
    public javax.swing.JButton btnComp14;
    public javax.swing.JButton btnComp15;
    public javax.swing.JButton btnComp16;
    public javax.swing.JButton btnComp17;
    public javax.swing.JButton btnComp18;
    public javax.swing.JButton btnComp19;
    public javax.swing.JButton btnComp2;
    public javax.swing.JButton btnComp20;
    public javax.swing.JButton btnComp21;
    public javax.swing.JButton btnComp22;
    public javax.swing.JButton btnComp23;
    public javax.swing.JButton btnComp24;
    public javax.swing.JButton btnComp25;
    public javax.swing.JButton btnComp26;
    public javax.swing.JButton btnComp27;
    public javax.swing.JButton btnComp28;
    public javax.swing.JButton btnComp29;
    public javax.swing.JButton btnComp3;
    public javax.swing.JButton btnComp30;
    public javax.swing.JButton btnComp4;
    public javax.swing.JButton btnComp5;
    public javax.swing.JButton btnComp6;
    public javax.swing.JButton btnComp7;
    public javax.swing.JButton btnComp8;
    public javax.swing.JButton btnComp9;
    public javax.swing.JButton btnDevolver;
    public javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JLabel labelMsg;
    public javax.swing.JTable tablePrestados;
    public javax.swing.JTextField txtFecha;
    public javax.swing.JTextField txtFolio;
    public javax.swing.JTextField txtHora;
    public javax.swing.JTextField txtIdEquipo;
    // End of variables declaration//GEN-END:variables
}
