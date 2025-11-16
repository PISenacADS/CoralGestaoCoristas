package br.com.coralgestaocoristas.coral.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/coraldb";
    private static final String USER = "root";        
    private static final String PASS = "1234";   

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
