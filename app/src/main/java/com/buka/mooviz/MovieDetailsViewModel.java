package com.buka.mooviz;

import android.app.Application;
import android.os.AsyncTask;

import com.buka.mooviz.database.DatabaseUtils;
import com.buka.mooviz.database.MoovizDatabase;
import com.buka.mooviz.database.MovieDao;
import com.buka.mooviz.models.Movie;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

public class MovieDetailsViewModel extends AndroidViewModel {
    private MovieDao movieDao;
    private MutableLiveData<Movie> movieLiveData = new MutableLiveData<>();

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);

        MoovizDatabase moovizDatabase = Room
                .databaseBuilder(application.getApplicationContext(), MoovizDatabase.class, DatabaseUtils.DATABASE_NAME)
                .build();

        movieDao = moovizDatabase.getMovieDao();
    }

    public void getMovieById(int id) {
        MovieFetchTask movieFetchTask = new MovieFetchTask();
        movieFetchTask.execute(id);
    }

    public LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }

    class MovieFetchTask extends AsyncTask<Integer, Void, Movie> {
        @Override
        protected Movie doInBackground(Integer... ids) {
            return movieDao.getMovieById(ids[0]);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
            movieLiveData.setValue(movie);
        }
    }
}
