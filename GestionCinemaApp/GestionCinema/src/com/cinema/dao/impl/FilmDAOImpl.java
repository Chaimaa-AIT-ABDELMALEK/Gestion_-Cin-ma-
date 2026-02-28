package com.cinema.dao.impl;

import com.cinema.connexion.Connexion;
import com.cinema.dao.FilmDAO;
import com.cinema.entities.Film;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAOImpl implements FilmDAO {
    
    private Connection conn = Connexion.getConnection();

    @Override
    public boolean create(Film film) {
        String sql = "INSERT INTO film (titre, genre, duree, realisateur) VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, film.getTitre());
            pstmt.setString(2, film.getGenre());
            pstmt.setInt(3, film.getDuree());
            pstmt.setString(4, film.getRealisateur());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    film.setId(rs.getInt(1)); // Récupère l'ID généré automatiquement
                }
                rs.close();
            }
            
            pstmt.close();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Film film) {
        String sql = "UPDATE film SET titre = ?, genre = ?, duree = ?, realisateur = ? WHERE id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, film.getTitre());
            pstmt.setString(2, film.getGenre());
            pstmt.setInt(3, film.getDuree());
            pstmt.setString(4, film.getRealisateur());
            pstmt.setInt(5, film.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM film WHERE id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Film read(int id) {
        String sql = "SELECT * FROM film WHERE id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Film film = new Film();
                film.setId(rs.getInt("id"));
                film.setTitre(rs.getString("titre"));
                film.setGenre(rs.getString("genre"));
                film.setDuree(rs.getInt("duree"));
                film.setRealisateur(rs.getString("realisateur"));
                
                rs.close();
                pstmt.close();
                
                return film;
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<Film> listAll() {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT * FROM film ORDER BY titre";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Film film = new Film();
                film.setId(rs.getInt("id"));
                film.setTitre(rs.getString("titre"));
                film.setGenre(rs.getString("genre"));
                film.setDuree(rs.getInt("duree"));
                film.setRealisateur(rs.getString("realisateur"));
                films.add(film);
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return films;
    }

    @Override
    public List<Film> findByGenre(String genre) {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT * FROM film WHERE genre LIKE ? ORDER BY titre";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + genre + "%");
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Film film = new Film();
                film.setId(rs.getInt("id"));
                film.setTitre(rs.getString("titre"));
                film.setGenre(rs.getString("genre"));
                film.setDuree(rs.getInt("duree"));
                film.setRealisateur(rs.getString("realisateur"));
                films.add(film);
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return films;
    }
}