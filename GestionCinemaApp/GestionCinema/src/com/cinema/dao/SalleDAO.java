package com.cinema.dao;

import com.cinema.entities.Salle;
import com.cinema.connexion.Connexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO implements DAO<Salle> {
    private Connection conn = Connexion.getConnection();

    @Override
    public boolean create(Salle salle) {
        String sql = "INSERT INTO salle (nom, capacite) VALUES (?, ?)";
        System.out.println("🔍 SalleDAO.create() - Ajout d'une salle: " + salle.getNom());
        
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, salle.getNom());
            ps.setInt(2, salle.getCapacite());
            
            int rowsAffected = ps.executeUpdate();
            System.out.println("🔍 Lignes affectées: " + rowsAffected);
            
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    salle.setId(rs.getInt(1));
                    System.out.println("✅ Salle ajoutée avec ID: " + salle.getId());
                }
                rs.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL create(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Salle read(int id) {
        String sql = "SELECT * FROM salle WHERE id = ?";
        System.out.println("🔍 SalleDAO.read() - Recherche ID: " + id);
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Salle salle = new Salle();
                salle.setId(rs.getInt("id"));
                salle.setNom(rs.getString("nom"));
                salle.setCapacite(rs.getInt("capacite"));
                System.out.println("✅ Salle trouvée: " + salle.getNom());
                return salle;
            } else {
                System.out.println("⚠️ Aucune salle avec l'ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL read(): " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Salle salle) {
        String sql = "UPDATE salle SET nom = ?, capacite = ? WHERE id = ?";
        System.out.println("🔍 SalleDAO.update() - Modification salle ID: " + salle.getId());
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, salle.getNom());
            ps.setInt(2, salle.getCapacite());
            ps.setInt(3, salle.getId());
            
            int rowsAffected = ps.executeUpdate();
            System.out.println("🔍 Lignes affectées: " + rowsAffected);
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL update(): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM salle WHERE id = ?";
        System.out.println("🔍 SalleDAO.delete() - Suppression ID: " + id);
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            int rowsAffected = ps.executeUpdate();
            System.out.println("🔍 Lignes affectées: " + rowsAffected);
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL delete(): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Salle> listAll() {
        List<Salle> salles = new ArrayList<>();
        String sql = "SELECT * FROM salle ORDER BY id";
        
        System.out.println("\n🔍 ===== SalleDAO.listAll() =====");
        System.out.println("🔍 Connexion: " + (conn != null ? "OK" : "NULL"));
        System.out.println("🔍 Requête SQL: " + sql);
        
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            System.out.println("🔍 Requête exécutée avec succès");
            
            int compteur = 0;
            while (rs.next()) {
                compteur++;
                Salle salle = new Salle();
                salle.setId(rs.getInt("id"));
                salle.setNom(rs.getString("nom"));
                salle.setCapacite(rs.getInt("capacite"));
                salles.add(salle);
                System.out.println("✅ Salle " + compteur + ": ID=" + salle.getId() + 
                                 ", Nom=" + salle.getNom() + 
                                 ", Capacité=" + salle.getCapacite());
            }
            
            System.out.println("📊 Total salles trouvées: " + compteur);
            
        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL listAll(): " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("🔚 SalleDAO.listAll() retourne " + salles.size() + " salles\n");
        return salles;
    }

    // Méthode supplémentaire pour recherche par nom
    public List<Salle> findByNom(String nom) {
        List<Salle> salles = new ArrayList<>();
        String sql = "SELECT * FROM salle WHERE nom LIKE ? ORDER BY nom";
        
        System.out.println("🔍 SalleDAO.findByNom() - Recherche: " + nom);
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + nom + "%");
            ResultSet rs = ps.executeQuery();
            
            int compteur = 0;
            while (rs.next()) {
                compteur++;
                Salle salle = new Salle();
                salle.setId(rs.getInt("id"));
                salle.setNom(rs.getString("nom"));
                salle.setCapacite(rs.getInt("capacite"));
                salles.add(salle);
            }
            System.out.println("🔍 " + compteur + " salles trouvées pour le nom '" + nom + "'");
            
        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL findByNom(): " + e.getMessage());
            e.printStackTrace();
        }
        return salles;
    }
}