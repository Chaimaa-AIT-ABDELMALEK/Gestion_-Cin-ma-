package com.cinema.dao;

import java.util.List;

/**
 * Interface générique pour les opérations CRUD de base.
 * @param <T> Le type d'entité (Film, Salle, Seance, etc.)
 */
public interface DAO<T> {
    /**
     * Insère un objet dans la base de données.
     * @param obj L'objet à insérer
     * @return true si l'insertion a réussi, false sinon
     */
    boolean create(T obj);

    /**
     * Récupère un objet par son identifiant.
     * @param id L'identifiant de l'objet
     * @return L'objet trouvé, ou null s'il n'existe pas
     */
    T read(int id);

    /**
     * Met à jour un objet existant.
     * @param obj L'objet avec les nouvelles valeurs
     * @return true si la mise à jour a réussi, false sinon
     */
    boolean update(T obj);

    /**
     * Supprime un objet par son identifiant.
     * @param id L'identifiant de l'objet à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    boolean delete(int id);

    /**
     * Retourne la liste de tous les objets de ce type.
     * @return Liste de tous les objets
     */
    List<T> listAll();
}