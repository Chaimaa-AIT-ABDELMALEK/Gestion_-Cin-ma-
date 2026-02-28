package com.cinema.services;

import com.cinema.dao.UserDAO;
import com.cinema.dao.impl.UserDAOImpl;
import com.cinema.entities.User;

public class UserService {
    private UserDAO userDAO = new UserDAOImpl();
    
    /**
     * Inscrit un nouvel utilisateur.
     * @param username Nom d'utilisateur
     * @param password Mot de passe en clair
     * @return true si l'inscription a réussi
     * @throws IllegalArgumentException si les données sont invalides
     */
    public boolean register(String username, String password) {
        // Validations métier
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom d'utilisateur est obligatoire.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire.");
        }
        if (password.length() < 4) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 4 caractères.");
        }

        // Vérifier si l'utilisateur existe déjà
        User existingUser = userDAO.findByUsername(username.trim());
        if (existingUser != null) {
            throw new IllegalArgumentException("Ce nom d'utilisateur est déjà pris.");
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password); // en clair, sera haché dans le DAO

        return userDAO.register(user);
    }

    /**
     * Authentifie un utilisateur.
     * @param username Nom d'utilisateur
     * @param password Mot de passe en clair
     * @return L'objet User si authentification réussie, null sinon
     * @throws IllegalArgumentException si les identifiants sont vides
     */
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom d'utilisateur est obligatoire.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire.");
        }

        return userDAO.login(username.trim(), password);
    }

    /**
     * Met à jour le mot de passe d'un utilisateur.
     * @param userId ID de l'utilisateur
     * @param oldPassword Ancien mot de passe (pour vérification)
     * @param newPassword Nouveau mot de passe
     * @return true si la mise à jour a réussi
     * @throws IllegalArgumentException si validation échoue ou ancien mot de passe incorrect
     */
    public boolean updatePassword(int userId, String oldPassword, String newPassword) {
        if (userId <= 0) {
            throw new IllegalArgumentException("ID utilisateur invalide.");
        }
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ancien mot de passe est obligatoire.");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nouveau mot de passe est obligatoire.");
        }
        if (newPassword.length() < 4) {
            throw new IllegalArgumentException("Le nouveau mot de passe doit contenir au moins 4 caractères.");
        }

        // Récupérer l'utilisateur pour vérifier l'ancien mot de passe
        User userFromDb = userDAO.read(userId);
        if (userFromDb == null) {
            throw new IllegalArgumentException("Utilisateur introuvable.");
        }

        // Vérifier l'ancien mot de passe
        if (!userDAO.checkPassword(userId, oldPassword)) {
            throw new IllegalArgumentException("Ancien mot de passe incorrect.");
        }

        // Mettre à jour le mot de passe
        return userDAO.updatePassword(userId, newPassword);
    }

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     * @param username Nom d'utilisateur
     * @return L'utilisateur trouvé ou null
     */
    public User findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        return userDAO.findByUsername(username.trim());
    }
    
    /**
     * Recherche un utilisateur par son ID.
     * @param id ID de l'utilisateur
     * @return L'utilisateur trouvé ou null
     */
    public User findById(int id) {
        if (id <= 0) {
            return null;
        }
        return userDAO.read(id);
    }
    
    /**
     * Crée un administrateur par défaut si la table est vide
     */
    public void createDefaultAdmin() {
        UserDAOImpl impl = (UserDAOImpl) userDAO;
        impl.createDefaultAdmin();
    }
}