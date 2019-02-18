package com.buka.mooviz;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.buka.mooviz.api.TmdbApi;
import com.buka.mooviz.database.DatabaseUtils;
import com.buka.mooviz.database.MoovizDatabase;
import com.buka.mooviz.database.MovieDao;
import com.buka.mooviz.models.Movie;
import com.buka.mooviz.models.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends AndroidViewModel {
    private TmdbApi tmdbApi;
    private MutableLiveData<List<Movie>> popularMoviesLiveData = new MutableLiveData<>();
    private MovieDao movieDao;

    public MainViewModel(Application application) {
        super(application);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tmdbApi = retrofit.create(TmdbApi.class);

        MoovizDatabase moovizDatabase = Room
                .databaseBuilder(application.getApplicationContext(), MoovizDatabase.class, DatabaseUtils.DATABASE_NAME)
                .build();

        movieDao = moovizDatabase.getMovieDao();
    }

    public void fetchPopularMovies() {
        MoviesRequestTask moviesRequestTask = new MoviesRequestTask();
        moviesRequestTask.execute();
    }

    public LiveData<List<Movie>> getPopularMoviesLiveData() {
        return popularMoviesLiveData;
    }

    class MoviesRequestTask extends AsyncTask<Void, Void, List<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            try {
                Response<Page> response = tmdbApi.getPopularMovies().execute();

                if (response.isSuccessful()) {
                    Page page = response.body();

                    ArrayList<Movie> movies = page.getResults();
                    movieDao.insertAll(movies);
                    return movies;
                } else {
                    Log.e(MoviesRequestTask.class.getSimpleName(), response.message());
                    return movieDao.getAll();
                }
            } catch (IOException exception) {
                Log.e(MoviesRequestTask.class.getSimpleName(), exception.getMessage());
                return movieDao.getAll();
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            popularMoviesLiveData.setValue(movies);
        }
    }
}
