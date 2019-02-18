package com.buka.mooviz.database;

import com.buka.mooviz.models.Movie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MovieDao {
    @Insert
    void insertAll(List<Movie> movies);

    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("SELECT * FROM movie WHERE id = :id")
    Movie getMovieById(int id);

    @Query("DELETE FROM movie")
    void deleteAll();
}
