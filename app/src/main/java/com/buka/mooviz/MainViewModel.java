package com.buka.mooviz;

import android.os.AsyncTask;
import android.util.Log;

import com.buka.mooviz.api.TmdbApi;
import com.buka.mooviz.models.Movie;
import com.buka.mooviz.models.Page;

import java.io.IOException;
import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    private TmdbApi tmdbApi;
    private MutableLiveData<ArrayList<Movie>> popularMoviesLiveData = new MutableLiveData<>();

    public MainViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tmdbApi = retrofit.create(TmdbApi.class);
    }

    public void fetchPopularMovies() {
        MoviesRequestTask moviesRequestTask = new MoviesRequestTask();
        moviesRequestTask.execute();
    }

    public LiveData<ArrayList<Movie>> getPopularMoviesLiveData() {
        return popularMoviesLiveData;
    }

    class MoviesRequestTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            try {
                Response<Page> response = tmdbApi.getPopularMovies().execute();

                if (response.isSuccessful()) {
                    Page page = response.body();
                    ArrayList<Movie> movies = page.getResults();
                    return movies;
                } else {
                    Log.e(MoviesRequestTask.class.getSimpleName(), response.message());
                    return null;
                }
            } catch (IOException exception) {
                Log.e(MoviesRequestTask.class.getSimpleName(), exception.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            popularMoviesLiveData.setValue(movies);
        }
    }
}
