package com.cinema.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Modifiez l'URL pour utiliser votre base cinema_db
    private static final String URL = "jdbc:mysql://localhost:3306/cinema_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        try {
            // Charger le driver MySQL (optionnel avec JDBC 4+ mais recommandé)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL chargé avec succès");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur: Driver MySQL non trouvé!");
            e.printStackTrace();
        }
        
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connexion à la base de données établie!");
        return conn;
    }
    
    // Méthode utilitaire pour tester la connexion
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Connexion réussie à la base '" + conn.getCatalog() + "'");
        } catch (SQLException e) {
            System.err.println("❌ Échec de connexion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}