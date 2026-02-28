package com.cinema.services;

import com.cinema.dao.SeanceDAO;
import com.cinema.entities.Seance;
import com.cinema.entities.Salle;
import java.util.List;

public class SeanceService {
    private SeanceDAO seanceDAO = new SeanceDAO();

    /**
     * Ajoute une nouvelle séance après validation.
     * @param seance La séance à ajouter (sans ID)
     * @return true si l'ajout a réussi
     * @throws IllegalArgumentException si les données sont invalides
     */
    public boolean createSeance(Seance seance) {
        // Validations métier
        if (seance.getFilm() == null) {
            throw new IllegalArgumentException("Le film est obligatoire.");
        }
        if (seance.getSalle() == null) {
            throw new IllegalArgumentException("La salle est obligatoire.");
        }
        if (seance.getDateProjection() == null) {
            throw new IllegalArgumentException("La date de projection est obligatoire.");
        }
        if (seance.getPrix() <= 0) {
            throw new IllegalArgumentException("Le prix doit être un nombre positif.");
        }
        if (seance.getTicketsVendus() < 0) {
            throw new IllegalArgumentException("Le nombre de tickets vendus ne peut pas être négatif.");
        }
        // Vérifier que le nombre de tickets ne dépasse pas la capacité de la salle
        Salle salle = seance.getSalle();
        if (seance.getTicketsVendus() > salle.getCapacite()) {
            throw new IllegalArgumentException("Le nombre de tickets vendus ne peut pas dépasser la capacité de la salle (" + salle.getCapacite() + ").");
        }
        // Appel au DAO
        return seanceDAO.create(seance);
    }

    /**
     * Met à jour une séance existante.
     * @param seance La séance avec les nouvelles données (doit avoir un ID valide)
     * @return true si la mise à jour a réussi
     * @throws IllegalArgumentException si les données sont invalides ou si l'ID est <= 0
     */
    public boolean updateSeance(Seance seance) {
        if (seance.getId() <= 0) {
            throw new IllegalArgumentException("ID de séance invalide.");
        }
        if (seance.getFilm() == null) {
            throw new IllegalArgumentException("Le film est obligatoire.");
        }
        if (seance.getSalle() == null) {
            throw new IllegalArgumentException("La salle est obligatoire.");
        }
        if (seance.getDateProjection() == null) {
            throw new IllegalArgumentException("La date de projection est obligatoire.");
        }
        if (seance.getPrix() <= 0) {
            throw new IllegalArgumentException("Le prix doit être un nombre positif.");
        }
        if (seance.getTicketsVendus() < 0) {
            throw new IllegalArgumentException("Le nombre de tickets vendus ne peut pas être négatif.");
        }
        // Vérifier que le nombre de tickets ne dépasse pas la capacité de la salle
        Salle salle = seance.getSalle();
        if (seance.getTicketsVendus() > salle.getCapacite()) {
            throw new IllegalArgumentException("Le nombre de tickets vendus ne peut pas dépasser la capacité de la salle (" + salle.getCapacite() + ").");
        }
        return seanceDAO.update(seance);
    }

    /**
     * Supprime une séance par son ID.
     * @param id L'ID de la séance à supprimer
     * @return true si la suppression a réussi
     * @throws IllegalArgumentException si l'ID est invalide
     */
    public boolean deleteSeance(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de séance invalide.");
        }
        return seanceDAO.delete(id);
    }

    /**
     * Récupère une séance par son ID.
     * @param id L'ID de la séance
     * @return La séance trouvée, ou null si elle n'existe pas
     */
    public Seance getSeance(int id) {
        return seanceDAO.read(id);
    }

    /**
     * Retourne la liste de toutes les séances.
     * @return Liste des séances
     */
    public List<Seance> getAllSeances() {
        return seanceDAO.listAll();
    }

    /**
     * Filtre les séances par mois et année.
     * @param annee L'année (ex: 2025)
     * @param mois Le mois (1-12)
     * @return Liste des séances correspondantes
     */
    public List<Seance> filterByDate(int annee, int mois) {
        return seanceDAO.findByDate(annee, mois);
    }

    /**
     * Retourne les recettes mensuelles sous forme de liste d'objets [annee, mois, total].
     * @return Liste des recettes mensuelles
     */
    public List<Object[]> getMonthlyRevenue() {
        return seanceDAO.getMonthlyRevenue();
    }
}