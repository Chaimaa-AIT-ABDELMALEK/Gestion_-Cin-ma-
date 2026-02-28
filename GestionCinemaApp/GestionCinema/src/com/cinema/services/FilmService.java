package com.cinema.services;

import com.cinema.dao.FilmDAO;
import com.cinema.dao.impl.FilmDAOImpl;
import com.cinema.entities.Film;
import java.util.List;

public class FilmService {
    private FilmDAO filmDAO = new FilmDAOImpl();

    /**
     * Ajoute un nouveau film après validation.
     * @param film Le film à ajouter (sans ID)
     * @return true si l'ajout a réussi
     * @throws IllegalArgumentException si les données sont invalides
     */
    public boolean createFilm(Film film) {
        // Validations métier
        if (film.getTitre() == null || film.getTitre().trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre du film est obligatoire.");
        }
        if (film.getDuree() <= 0) {
            throw new IllegalArgumentException("La durée doit être un nombre positif.");
        }
        if (film.getGenre() == null || film.getGenre().trim().isEmpty()) {
            throw new IllegalArgumentException("Le genre est obligatoire.");
        }
        // Appel au DAO
        return filmDAO.create(film);
    }

    /**
     * Met à jour un film existant.
     * @param film Le film avec les nouvelles données (doit avoir un ID valide)
     * @return true si la mise à jour a réussi
     * @throws IllegalArgumentException si les données sont invalides ou si l'ID est <= 0
     */
    public boolean updateFilm(Film film) {
        if (film.getId() <= 0) {
            throw new IllegalArgumentException("ID de film invalide.");
        }
        if (film.getTitre() == null || film.getTitre().trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre du film est obligatoire.");
        }
        if (film.getDuree() <= 0) {
            throw new IllegalArgumentException("La durée doit être un nombre positif.");
        }
        return filmDAO.update(film);
    }

    /**
     * Supprime un film par son ID.
     * @param id L'ID du film à supprimer
     * @return true si la suppression a réussi
     * @throws IllegalArgumentException si l'ID est invalide
     */
    public boolean deleteFilm(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de film invalide.");
        }
        return filmDAO.delete(id);
    }

    /**
     * Récupère un film par son ID.
     * @param id L'ID du film
     * @return Le film trouvé, ou null s'il n'existe pas
     */
    public Film getFilm(int id) {
        return filmDAO.read(id);
    }

    /**
     * Retourne la liste de tous les films.
     * @return Liste des films
     */
    public List<Film> getAllFilms() {
        return filmDAO.listAll();
    }

    /**
     * Recherche des films par genre (contient la chaîne).
     * @param genre Le genre recherché (peut être partiel)
     * @return Liste des films correspondants
     */
    public List<Film> searchByGenre(String genre) {
        if (genre == null) genre = "";
        return filmDAO.findByGenre(genre);
    }
}