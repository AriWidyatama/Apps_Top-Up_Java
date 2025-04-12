/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamesid;

/**
 *
 * @author Lenovo
 */
public class UserSession {
    private static String username;
    private static String nama_pengguna;
    private static String akses;
    private static String email;
    private static double saldo;
    private static int id_pengguna;
        
    public static void setUsername(String username) {
        UserSession.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public static void setAkses(String akses) {
        UserSession.akses = akses;
    }

    public static String getAkses() {
        return akses;
    }
    
    public static void setEmail(String email) {
        UserSession.email = email;
    }

    public static String getEmail() {
        return email;
    }
    
    public static void setNamapengguna(String nama_pengguna) {
        UserSession.nama_pengguna = nama_pengguna;
    }

    public static String getNamapengguna() {
        return nama_pengguna;
    }

    public static void setSaldo(double saldo) {
        UserSession.saldo = saldo;
    }

    public static double getSaldo() {
        return saldo;
    }
    
    public static void setIdPengguna(int id_pengguna) {
        UserSession.id_pengguna = id_pengguna;
    }

    public static double getIdPengguna() {
        return id_pengguna;
    }
    
    public static void clearSession() {
        username = null;
        akses = null;
        email = null;
        saldo = 0;
        id_pengguna = 0;
    }
}
