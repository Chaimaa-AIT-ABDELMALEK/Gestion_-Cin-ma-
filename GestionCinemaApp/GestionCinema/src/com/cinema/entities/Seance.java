package com.cinema.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Seance {
    private int id;
    private Film film;
    private Salle salle;
    private LocalDateTime dateProjection;
    private double prix;
    private int ticketsVendus;

    // Constructeur par défaut
    public Seance() {
    }

    // Constructeur sans id
    public Seance(Film film, Salle salle, LocalDateTime dateProjection, double prix, int ticketsVendus) {
        this.film = film;
        this.salle = salle;
        this.dateProjection = dateProjection;
        this.prix = prix;
        this.ticketsVendus = ticketsVendus;
    }

    // Constructeur avec id
    public Seance(int id, Film film, Salle salle, LocalDateTime dateProjection, double prix, int ticketsVendus) {
        this.id = id;
        this.film = film;
        this.salle = salle;
        this.dateProjection = dateProjection;
        this.prix = prix;
        this.ticketsVendus = ticketsVendus;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public LocalDateTime getDateProjection() {
        return dateProjection;
    }

    public void setDateProjection(LocalDateTime dateProjection) {
        this.dateProjection = dateProjection;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getTicketsVendus() {
        return ticketsVendus;
    }

    public void setTicketsVendus(int ticketsVendus) {
        this.ticketsVendus = ticketsVendus;
    }

    // Méthode utilitaire pour calculer la recette de cette séance
    public double getRecette() {
        return prix * ticketsVendus;
    }

    @Override
    public String toString() {
        return film.getTitre() + " - " + salle.getNom() + " - " +
               dateProjection.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}