package com.cinema.dao;

import com.cinema.entities.User;
import java.util.List;

/**
 * Interface spécifique pour les opérations liées aux utilisateurs.
 * Étend l'interface générique DAO<User>.
 */
public interface UserDAO extends DAO<User> {
    
    // ========== Méthodes héritées de DAO<User> ==========
    // boolean create(User user);
    // User read(int id);
    // boolean update(User user);
    // boolean delete(int id);
    // List<User> listAll();
    // ====================================================
    
    /**
     * Recherche un utilisateur par son ID.
     * @param id L'identifiant de l'utilisateur
     * @return L'utilisateur trouvé ou null
     */
    User findById(int id);
    
    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     * @param username Le nom d'utilisateur
     * @return L'utilisateur trouvé ou null
     */
    User findByUsername(String username);
    
    /**
     * Authentifie un utilisateur avec son nom d'utilisateur et mot de passe.
     * @param username Nom d'utilisateur
     * @param password Mot de passe en clair
     * @return L'utilisateur si authentification réussie, null sinon
     */
    User login(String username, String password);
    
    /**
     * Inscrit un nouvel utilisateur dans la base de données.
     * @param user L'utilisateur à inscrire (mot de passe en clair)
     * @return true si l'inscription a réussi, false sinon
     */
    boolean register(User user);
    
    /**
     * Met à jour le mot de passe d'un utilisateur.
     * @param userId ID de l'utilisateur
     * @param newPassword Nouveau mot de passe en clair
     * @return true si la mise à jour a réussi
     */
    boolean updatePassword(int userId, String newPassword);
    
    /**
     * Vérifie si le mot de passe correspond à celui de l'utilisateur.
     * @param userId ID de l'utilisateur
     * @param password Mot de passe à vérifier (en clair)
     * @return true si le mot de passe est correct
     */
    boolean checkPassword(int userId, String password);
    
    /**
     * Vérifie si un nom d'utilisateur existe déjà.
     * @param username Nom d'utilisateur à vérifier
     * @return true si le nom d'utilisateur existe déjà
     */
    boolean usernameExists(String username);
}