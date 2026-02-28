package com.cinema.dao;

import com.cinema.entities.Film;
import com.cinema.entities.Salle;
import com.cinema.entities.Seance;
import com.cinema.connexion.Connexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SeanceDAO implements DAO<Seance> {
    private Connection conn = Connexion.getConnection();

    // Vérifie si une séance identique existe déjà (film, salle, date)
    private boolean isDuplicate(Seance seance) {
        String sql = "SELECT id FROM seance WHERE film_id = ? AND salle_id = ? AND date_projection = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, seance.getFilm().getId());
            ps.setInt(2, seance.getSalle().getId());
            ps.setTimestamp(3, Timestamp.valueOf(seance.getDateProjection()));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Vérifie les doublons en excluant l'ID courant (pour la modification)
    private boolean isDuplicateExcluding(Seance seance) {
        String sql = "SELECT id FROM seance WHERE film_id = ? AND salle_id = ? AND date_projection = ? AND id != ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, seance.getFilm().getId());
            ps.setInt(2, seance.getSalle().getId());
            ps.setTimestamp(3, Timestamp.valueOf(seance.getDateProjection()));
            ps.setInt(4, seance.getId());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean create(Seance seance) {
        if (isDuplicate(seance)) {
            return false;
        }
        String sql = "INSERT INTO seance (film_id, salle_id, date_projection, prix, tickets_vendus) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, seance.getFilm().getId());
            ps.setInt(2, seance.getSalle().getId());
            ps.setTimestamp(3, Timestamp.valueOf(seance.getDateProjection()));
            ps.setDouble(4, seance.getPrix());
            ps.setInt(5, seance.getTicketsVendus());
            
            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    seance.setId(rs.getInt(1));
                }
                rs.close();
            }
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Seance read(int id) {
        String sql = "SELECT s.*, f.titre, f.genre, f.duree, f.realisateur, " +
                     "sal.nom, sal.capacite " +
                     "FROM seance s " +
                     "JOIN film f ON s.film_id = f.id " +
                     "JOIN salle sal ON s.salle_id = sal.id " +
                     "WHERE s.id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Film film = new Film();
                film.setId(rs.getInt("film_id"));
                film.setTitre(rs.getString("titre"));
                film.setGenre(rs.getString("genre"));
                film.setDuree(rs.getInt("duree"));
                film.setRealisateur(rs.getString("realisateur"));

                Salle salle = new Salle();
                salle.setId(rs.getInt("salle_id"));
                salle.setNom(rs.getString("nom"));
                salle.setCapacite(rs.getInt("capacite"));

                Seance seance = new Seance();
                seance.setId(rs.getInt("id"));
                seance.setFilm(film);
                seance.setSalle(salle);
                seance.setDateProjection(rs.getTimestamp("date_projection").toLocalDateTime());
                seance.setPrix(rs.getDouble("prix"));
                seance.setTicketsVendus(rs.getInt("tickets_vendus"));
                return seance;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Seance seance) {
        if (isDuplicateExcluding(seance)) {
            return false;
        }
        String sql = "UPDATE seance SET film_id = ?, salle_id = ?, date_projection = ?, prix = ?, tickets_vendus = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, seance.getFilm().getId());
            ps.setInt(2, seance.getSalle().getId());
            ps.setTimestamp(3, Timestamp.valueOf(seance.getDateProjection()));
            ps.setDouble(4, seance.getPrix());
            ps.setInt(5, seance.getTicketsVendus());
            ps.setInt(6, seance.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM seance WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Seance> listAll() {
        List<Seance> seances = new ArrayList<>();
        String sql = "SELECT s.*, f.titre, f.genre, f.duree, f.realisateur, " +
                     "sal.nom, sal.capacite " +
                     "FROM seance s " +
                     "JOIN film f ON s.film_id = f.id " +
                     "JOIN salle sal ON s.salle_id = sal.id " +
                     "ORDER BY s.date_projection DESC";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Film film = new Film();
                film.setId(rs.getInt("film_id"));
                film.setTitre(rs.getString("titre"));
                film.setGenre(rs.getString("genre"));
                film.setDuree(rs.getInt("duree"));
                film.setRealisateur(rs.getString("realisateur"));

                Salle salle = new Salle();
                salle.setId(rs.getInt("salle_id"));
                salle.setNom(rs.getString("nom"));
                salle.setCapacite(rs.getInt("capacite"));

                Seance seance = new Seance();
                seance.setId(rs.getInt("id"));
                seance.setFilm(film);
                seance.setSalle(salle);
                seance.setDateProjection(rs.getTimestamp("date_projection").toLocalDateTime());
                seance.setPrix(rs.getDouble("prix"));
                seance.setTicketsVendus(rs.getInt("tickets_vendus"));
                seances.add(seance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seances;
    }

    public List<Seance> findByDate(int annee, int mois) {
        List<Seance> seances = new ArrayList<>();
        String sql = "SELECT s.*, f.titre, f.genre, f.duree, f.realisateur, " +
                     "sal.nom, sal.capacite " +
                     "FROM seance s " +
                     "JOIN film f ON s.film_id = f.id " +
                     "JOIN salle sal ON s.salle_id = sal.id " +
                     "WHERE YEAR(s.date_projection) = ? AND MONTH(s.date_projection) = ? " +
                     "ORDER BY s.date_projection DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, annee);
            ps.setInt(2, mois);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Film film = new Film();
                film.setId(rs.getInt("film_id"));
                film.setTitre(rs.getString("titre"));
                film.setGenre(rs.getString("genre"));
                film.setDuree(rs.getInt("duree"));
                film.setRealisateur(rs.getString("realisateur"));

                Salle salle = new Salle();
                salle.setId(rs.getInt("salle_id"));
                salle.setNom(rs.getString("nom"));
                salle.setCapacite(rs.getInt("capacite"));

                Seance seance = new Seance();
                seance.setId(rs.getInt("id"));
                seance.setFilm(film);
                seance.setSalle(salle);
                seance.setDateProjection(rs.getTimestamp("date_projection").toLocalDateTime());
                seance.setPrix(rs.getDouble("prix"));
                seance.setTicketsVendus(rs.getInt("tickets_vendus"));
                seances.add(seance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seances;
    }

public List<Object[]> getMonthlyRevenue() {
    List<Object[]> result = new ArrayList<>();
    String sql = "SELECT YEAR(date_projection) annee, MONTH(date_projection) mois, SUM(prix * tickets_vendus) total " +
                 "FROM seance " +
                 "GROUP BY annee, mois " +
                 "ORDER BY annee ASC, mois ASC";  // ← CHANGÉ (DESC → ASC)
    try (Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            Object[] row = new Object[3];
            row[0] = rs.getInt("annee");
            row[1] = rs.getInt("mois");
            row[2] = rs.getDouble("total");
            result.add(row);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return result;
}
}