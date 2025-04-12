/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gamesid;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Lenovo
 */
public class HalamanAdmin extends javax.swing.JFrame {

    /**
     * Creates new form HalamanAdmin
     */
    public HalamanAdmin() {
        initComponents();
        txt_nama.setText(": " + UserSession.getNamapengguna());
        loadDataProduk(tbl_Produk);
        loadDataProduk(tbl_produk1);
        loadDataTransaksi(tbl_laporan);

    }
    private void loadDataProduk(javax.swing.JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Connection con = new Koneksi().getConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM produk");
            while (rs.next()) {
                int id_produk = rs.getInt("id_produk");
                String nama = rs.getString("nama_produk");
                double harga = rs.getDouble("harga");
                int stok = rs.getInt("stok");
                model.addRow(new Object[]{id_produk, nama, harga, stok});
            }
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void loadDataTransaksi(javax.swing.JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table before loading new data
        Connection con = new Koneksi().getConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM transaksi");
            while (rs.next()) {
                int id_transaksi = rs.getInt("id_transaksi");
                int id_pengguna = rs.getInt("id_pengguna");
                int id_produk = rs.getInt("id_produk");
                Date tgl_transaksi = rs.getDate("tgl_transaksi");
                int jumlah = rs.getInt("jumlah");
                double total = rs.getDouble("total");

                // Format the date as a string for display
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(tgl_transaksi);

                model.addRow(new Object[]{id_transaksi, formattedDate, id_pengguna, id_produk, jumlah, total});
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    
    
    private void loadDataTransaksi(JTable table, int month, int year) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table before loading new data
        Connection con = new Koneksi().getConnection();
        try {
            String sql = "SELECT * FROM transaksi";
            if (month != 0 && year != 0) {
                sql += " WHERE MONTH(tgl_transaksi) = ? AND YEAR(tgl_transaksi) = ?";
            }
            PreparedStatement pst = con.prepareStatement(sql);

            if (month != 0 && year != 0) {
                pst.setInt(1, month);
                pst.setInt(2, year);
            }

            ResultSet rs = pst.executeQuery();

            // Check if ResultSet is empty
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "Tidak ada data transaksi untuk bulan dan tahun yang dipilih");
                return;
            }

            while (rs.next()) {
                int id_transaksi = rs.getInt("id_transaksi");
                int id_pengguna = rs.getInt("id_pengguna");
                int id_produk = rs.getInt("id_produk");
                Date tgl_transaksi = rs.getDate("tgl_transaksi");
                int jumlah = rs.getInt("jumlah");
                double total = rs.getDouble("total");

                // Format the date as a string for display
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(tgl_transaksi);

                model.addRow(new Object[]{id_transaksi, formattedDate, id_pengguna, id_produk, jumlah, total});
            }

            pst.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    
    private void loadDataTransaksiBulan(JTable table, int month) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table before loading new data
        Connection con = new Koneksi().getConnection();
        try {
            String sql = "SELECT * FROM transaksi";
            sql += " WHERE MONTH(tgl_transaksi) = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, month);
            ResultSet rs = pst.executeQuery();

            // Check if ResultSet is empty
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "Tidak ada data transaksi untuk bulan dipilih");
                return;
            }

            while (rs.next()) {
                int id_transaksi = rs.getInt("id_transaksi");
                int id_pengguna = rs.getInt("id_pengguna");
                int id_produk = rs.getInt("id_produk");
                Date tgl_transaksi = rs.getDate("tgl_transaksi");
                int jumlah = rs.getInt("jumlah");
                double total = rs.getDouble("total");

                // Format the date as a string for display
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(tgl_transaksi);

                model.addRow(new Object[]{id_transaksi, formattedDate, id_pengguna, id_produk, jumlah, total});
            }

            pst.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void loadDataTransaksiTahun(JTable table, int year) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table before loading new data
        Connection con = new Koneksi().getConnection();
        try {
            String sql = "SELECT * FROM transaksi WHERE YEAR(tgl_transaksi) = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);

            ResultSet rs = pst.executeQuery();

            // Check if ResultSet is empty
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "Tidak ada data transaksi untuk tahun yang dipilih");
                return;
            }

            while (rs.next()) {
                int id_transaksi = rs.getInt("id_transaksi");
                int id_pengguna = rs.getInt("id_pengguna");
                int id_produk = rs.getInt("id_produk");
                Date tgl_transaksi = rs.getDate("tgl_transaksi");
                int jumlah = rs.getInt("jumlah");
                double total = rs.getDouble("total");

                // Format the date as a string for display
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(tgl_transaksi);

                model.addRow(new Object[]{id_transaksi, formattedDate, id_pengguna, id_produk, jumlah, total});
            }

            pst.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }




            

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Produk = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_namaProduk = new javax.swing.JTextField();
        txt_hargaProduk = new javax.swing.JTextField();
        txt_stokProduk = new javax.swing.JTextField();
        btn_tambahProduk = new javax.swing.JButton();
        btn_editProduk = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_produk1 = new javax.swing.JTable();
        btn_deleteProduk = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_laporan = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_Filter = new javax.swing.JButton();
        ComboBulan = new javax.swing.JComboBox<>();
        ComboTahun = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JLabel();
        logoutbtn = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Selamat Datang Di Halaman Admin");

        tbl_Produk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Produk", "Nama Produk", "Harga", "Stok"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbl_Produk);

        jTabbedPane2.addTab("Produk", jScrollPane2);

        jLabel2.setText("Nama Produk");

        jLabel3.setText("Harga");

        jLabel4.setText("Stok");

        txt_namaProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaProdukActionPerformed(evt);
            }
        });

        txt_hargaProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaProdukActionPerformed(evt);
            }
        });

        btn_tambahProduk.setText("Add");
        btn_tambahProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahProdukActionPerformed(evt);
            }
        });

        btn_editProduk.setText("Edit");
        btn_editProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editProdukActionPerformed(evt);
            }
        });

        jButton3.setText("Delete");

        tbl_produk1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Produk", "Nama Produk", "Harga", "Stok"
            }
        ));
        tbl_produk1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_produk1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_produk1);

        btn_deleteProduk.setText("Delete");
        btn_deleteProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteProdukActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_namaProduk, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                            .addComponent(txt_hargaProduk)
                                            .addComponent(txt_stokProduk)))
                                    .addComponent(btn_deleteProduk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 1, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btn_tambahProduk)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_editProduk)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txt_namaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txt_hargaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txt_stokProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_editProduk)
                            .addComponent(btn_tambahProduk))
                        .addGap(18, 18, 18)
                        .addComponent(btn_deleteProduk))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Add And Edit", jPanel2);

        tbl_laporan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Transaksi", "Tgl Transaksi", "Id Pengguna", "Id Produk", "Jumlah", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbl_laporan);

        jLabel6.setText("Filter");

        jLabel7.setText("Set Bulan");

        jLabel8.setText("Set Tahun");

        btn_Filter.setText("Filter");
        btn_Filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FilterActionPerformed(evt);
            }
        });

        ComboBulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));

        ComboTahun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "2022", "2023", "2024" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(btn_Filter)
                    .addComponent(ComboBulan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ComboTahun, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Filter)
                        .addGap(0, 81, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("History Transaksi", jPanel3);

        jLabel5.setText("Nama Pengguna");

        txt_nama.setText("jLabel6");

        logoutbtn.setText("Logout");
        logoutbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutbtnActionPerformed(evt);
            }
        });

        jButton5.setText("About US");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(logoutbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_nama)
                    .addComponent(logoutbtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutbtnActionPerformed
        // TODO add your handling code here:
        UserSession.clearSession();

        // Display login screen
        new Login().setVisible(true);

        // Close the current window
        this.dispose();
    }//GEN-LAST:event_logoutbtnActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new About().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txt_hargaProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaProdukActionPerformed

    private void txt_namaProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaProdukActionPerformed

    private void tbl_produk1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_produk1MouseClicked
        // TODO add your handling code here:
        int selectedRow = tbl_produk1.getSelectedRow();
        if (selectedRow != -1) {
            txt_namaProduk.setText(tbl_produk1.getValueAt(selectedRow, 1).toString());
            txt_hargaProduk.setText(tbl_produk1.getValueAt(selectedRow, 2).toString());
            txt_stokProduk.setText(tbl_produk1.getValueAt(selectedRow, 3).toString());
        }
        
    }//GEN-LAST:event_tbl_produk1MouseClicked

    private void btn_editProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editProdukActionPerformed
        // TODO add your handling code here:
        try {
            // Get selected row index
            int selectedRow = tbl_produk1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih produk yang ingin diubah.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get data from selected row
            int id_produk = (int) tbl_produk1.getValueAt(selectedRow, 0);
            String namaProduk = txt_namaProduk.getText();
            double hargaProduk = Double.parseDouble(txt_hargaProduk.getText());
            int stokProduk = Integer.parseInt(txt_stokProduk.getText());

            // Update data in database
            Connection con = new Koneksi().getConnection();
            String sql = "UPDATE produk SET nama_produk = ?, harga = ?, stok = ? WHERE id_produk = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, namaProduk);
            pst.setDouble(2, hargaProduk);
            pst.setInt(3, stokProduk);
            pst.setInt(4, id_produk);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Update Berhasil");
                loadDataProduk(tbl_produk1); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate produk.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            this.setVisible(false);
            new HalamanAdmin().setVisible(true);
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format harga atau stok tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_editProdukActionPerformed

    private void btn_tambahProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahProdukActionPerformed
        // TODO add your handling code here:
        try (Connection con = new Koneksi().getConnection()) {
            String nama = txt_namaProduk.getText();
            String hargapr = txt_hargaProduk.getText();
            String stokpr = txt_stokProduk.getText();

            // Validasi input kosong
            if (nama.isEmpty() || hargapr.isEmpty() || stokpr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Harap lengkapi semua data!");
            } else {
                // Query SQL dengan PreparedStatement
                double harga = Double.parseDouble(hargapr);
                int stok = Integer.parseInt(stokpr);
                String sql = "INSERT INTO produk (nama_produk, harga, stok) VALUES (?, ?, ?)";
                try (PreparedStatement pstm = con.prepareStatement(sql)) {
                    // Mengikat nilai parameter
                    pstm.setString(1, nama);
                    pstm.setDouble(2, harga);
                    pstm.setInt(3, stok);
                    

                    // Eksekusi query
                    pstm.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
                    this.setVisible(false);
                    new HalamanAdmin().setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Koneksi ke database gagal: " + ex.getMessage());
        }
    }//GEN-LAST:event_btn_tambahProdukActionPerformed

    private void btn_deleteProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteProdukActionPerformed
        // TODO add your handling code here:
        Connection con = new Koneksi().getConnection();
        try {
            int jawab;
            if ((jawab = JOptionPane.showConfirmDialog(null, "Ingin menghapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION)) == 0) {
                int selectedRow = tbl_produk1.getSelectedRow();
                if (selectedRow != -1) { // Ensure a row is selected
                    String sql = "DELETE FROM produk WHERE id_produk = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(tbl_produk1.getValueAt(selectedRow, 0).toString()));
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                    this.setVisible(false);
                    new HalamanAdmin().setVisible(true);
                    loadDataProduk(tbl_produk1); // Refresh table
                } else {
                    JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus terlebih dahulu");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_deleteProdukActionPerformed

    private void btn_FilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FilterActionPerformed
        // TODO add your handling code here:
        int month = ComboBulan.getSelectedIndex(); // Bulan dalam SQL dimulai dari 0 untuk Januari, 11 untuk Desember
        String tahun = ComboTahun.getSelectedItem().toString();
        String bulan = ComboBulan.getSelectedItem().toString();

        // Validate selected year
        if (tahun.equals("None") && bulan.equals("None")) {
            // Load all data if "None" is selected in both year and month
            loadDataTransaksi(tbl_laporan);
        } else if (tahun.equals("None")) {
            // Only month selected, load data by month
            loadDataTransaksiBulan(tbl_laporan, month);
        } else if (bulan.equals("None")) {
            // Only year selected, load data by year
            int year = Integer.parseInt(tahun);
            loadDataTransaksiTahun(tbl_laporan, year);
        } else {
            // Both year and month selected, load filtered data
            int year = Integer.parseInt(tahun);
            loadDataTransaksi(tbl_laporan, month, year);
        }
    }//GEN-LAST:event_btn_FilterActionPerformed

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
            java.util.logging.Logger.getLogger(HalamanAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HalamanAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HalamanAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HalamanAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HalamanAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBulan;
    private javax.swing.JComboBox<String> ComboTahun;
    private javax.swing.JButton btn_Filter;
    private javax.swing.JButton btn_deleteProduk;
    private javax.swing.JButton btn_editProduk;
    private javax.swing.JButton btn_tambahProduk;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JButton logoutbtn;
    private javax.swing.JTable tbl_Produk;
    private javax.swing.JTable tbl_laporan;
    private javax.swing.JTable tbl_produk1;
    private javax.swing.JTextField txt_hargaProduk;
    private javax.swing.JLabel txt_nama;
    private javax.swing.JTextField txt_namaProduk;
    private javax.swing.JTextField txt_stokProduk;
    // End of variables declaration//GEN-END:variables
}
