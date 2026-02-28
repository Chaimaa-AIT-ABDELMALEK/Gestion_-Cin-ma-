package com.cinema.entities;

public class Film {
    private int id;
    private String titre;
    private String genre;
    private int duree; // durée en minutes
    private String realisateur;

    // Constructeur par défaut
    public Film() {
    }

    // Constructeur avec tous les champs (sans id)
    public Film(String titre, String genre, int duree, String realisateur) {
        this.titre = titre;
        this.genre = genre;
        this.duree = duree;
        this.realisateur = realisateur;
    }

    // Constructeur avec id (pour les lectures depuis la base)
    public Film(int id, String titre, String genre, int duree, String realisateur) {
        this.id = id;
        this.titre = titre;
        this.genre = genre;
        this.duree = duree;
        this.realisateur = realisateur;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    @Override
    public String toString() {
        return titre; // Utile pour l'affichage dans les JComboBox
    }
}