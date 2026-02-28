package com.cinema.entities;

public class User {
    private int id;
    private String username;
    private String password; // mot de passe haché

    // Constructeur par défaut
    public User() {
    }

    // Constructeur sans id
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Constructeur avec id
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username; // Utile pour l'affichage
    }
}