package com.buka.mooviz.database;

import com.buka.mooviz.models.Movie;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { Movie.class }, exportSchema = false, version = 1)
public abstract class MoovizDatabase extends RoomDatabase {
    public abstract MovieDao getMovieDao();
}
