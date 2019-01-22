package com.buka.mooviz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.buka.mooviz.api.TmdbApi;
import com.buka.mooviz.models.Movie;
import com.buka.mooviz.models.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnItemClickedListener {
    private ProgressBar loadingProgressBar;
    private RecyclerView moviesRecyclerView;
    private MoviesAdapter moviesAdapter;
    private TmdbApi tmdbApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRecyclerView = findViewById(R.id.recyclerview_movies);
        loadingProgressBar = findViewById(R.id.progressbar_loading);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        moviesRecyclerView.setLayoutManager(linearLayoutManager);

        moviesAdapter = new MoviesAdapter(this);
        moviesRecyclerView.setAdapter(moviesAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tmdbApi = retrofit.create(TmdbApi.class);

        MoviesRequestTask moviesRequestTask = new MoviesRequestTask();
        moviesRequestTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            MoviesRequestTask moviesRequestTask = new MoviesRequestTask();
            moviesRequestTask.execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);

        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_TITLE, movie.getTitle());
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_DESCRIPTION, movie.getOverview());
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_RELEASE_DATE, movie.getReleaseDate());

        startActivity(intent);
    }

    class MoviesRequestTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgressBar.setVisibility(View.VISIBLE);
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

            loadingProgressBar.setVisibility(View.INVISIBLE);

            if (movies != null) {
                moviesAdapter.setMovies(movies);
            } else {
                Toast.makeText(MainActivity.this, "Ocorreu algum erro!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
