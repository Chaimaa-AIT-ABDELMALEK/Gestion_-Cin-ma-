package com.cinema.services;

import com.cinema.dao.SalleDAO;
import com.cinema.entities.Salle;
import java.util.List;

public class SalleService {
    private SalleDAO salleDAO = new SalleDAO();

    /**
     * Ajoute une nouvelle salle après validation.
     * @param salle La salle à ajouter (sans ID)
     * @return true si l'ajout a réussi
     * @throws IllegalArgumentException si les données sont invalides
     */
    public boolean createSalle(Salle salle) {
        // Validations métier
        if (salle.getNom() == null || salle.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la salle est obligatoire.");
        }
        if (salle.getCapacite() <= 0) {
            throw new IllegalArgumentException("La capacité doit être un nombre positif.");
        }
        // Appel au DAO
        return salleDAO.create(salle);
    }

    /**
     * Met à jour une salle existante.
     * @param salle La salle avec les nouvelles données (doit avoir un ID valide)
     * @return true si la mise à jour a réussi
     * @throws IllegalArgumentException si les données sont invalides ou si l'ID est <= 0
     */
    public boolean updateSalle(Salle salle) {
        if (salle.getId() <= 0) {
            throw new IllegalArgumentException("ID de salle invalide.");
        }
        if (salle.getNom() == null || salle.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la salle est obligatoire.");
        }
        if (salle.getCapacite() <= 0) {
            throw new IllegalArgumentException("La capacité doit être un nombre positif.");
        }
        return salleDAO.update(salle);
    }

    /**
     * Supprime une salle par son ID.
     * @param id L'ID de la salle à supprimer
     * @return true si la suppression a réussi
     * @throws IllegalArgumentException si l'ID est invalide
     */
    public boolean deleteSalle(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de salle invalide.");
        }
        return salleDAO.delete(id);
    }

    /**
     * Récupère une salle par son ID.
     * @param id L'ID de la salle
     * @return La salle trouvée, ou null si elle n'existe pas
     */
    public Salle getSalle(int id) {
        return salleDAO.read(id);
    }

    /**
     * Retourne la liste de toutes les salles.
     * @return Liste des salles
     */
    public List<Salle> getAllSalles() {
        return salleDAO.listAll();
    }

    /**
     * Recherche des salles par nom (contient la chaîne).
     * @param nom Le nom recherché (peut être partiel)
     * @return Liste des salles correspondantes
     */
    public List<Salle> searchByNom(String nom) {
        if (nom == null) nom = "";
        return salleDAO.findByNom(nom);
    }
}