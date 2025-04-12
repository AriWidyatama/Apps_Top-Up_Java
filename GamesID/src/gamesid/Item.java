/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamesid;
import java.sql.Connection; //connection
import java.sql.DriverManager; //driver
import java.sql.Statement; //sql runner
import java.sql.SQLException; //error handler
import java.sql.ResultSet; //Menyimpan hasil query
import java.util.List; //Membuat List
import java.util.ArrayList; //Jenis List yg dipakai

/**
 *
 * @author Lenovo
 */
public class Item {
    private int id;
    private String nama_produk;
    
    public Item(int new_id, String new_nama_produk){
        this.id = new_id;
        this.nama_produk = new_nama_produk;
    }
    
    @Override
    public String toString(){
        return this.nama_produk;
    }
    
    public static List<Item> getListItem() throws SQLException{
        List<Item> items = new ArrayList<>();

        Connection conn = null;
        Statement sql = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/gamesid";
            String user = "root";
            String pass = "";
            
            conn = DriverManager.getConnection(url, user, pass);
            sql = conn.createStatement();
            ResultSet res = sql.executeQuery("Select * FROM produk");
            
            while (res.next()) {                
                Item item = new Item(res.getInt("id_produk"),res.getString("nama_produk"));
                
                items.add(item); //memasukkan prodi ke list
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return items;
    }
    
    int getIdProduk() {
        return id;
    }

    int getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getNamaProduk() {
        return nama_produk;
    }
    
    int getid() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}