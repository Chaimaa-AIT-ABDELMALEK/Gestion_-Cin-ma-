package com.cinema.dao;

import com.cinema.entities.Film;
import java.util.List;

public interface FilmDAO {
    boolean create(Film film);
    boolean update(Film film);
    boolean delete(int id);
    Film read(int id);
    List<Film> listAll();
    List<Film> findByGenre(String genre);
}