package com.buka.mooviz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.buka.mooviz.models.Movie;

public class MovieDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView titleTextView = findViewById(R.id.textview_title);
        final TextView overviewTextView = findViewById(R.id.textview_overview);
        final TextView releaseDateTextView = findViewById(R.id.textview_release_date);

        MovieDetailsViewModel movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);

        Intent intent = getIntent();
        if (intent != null) {
            int id = intent.getIntExtra(EXTRA_MOVIE_ID, 0);
            movieDetailsViewModel.getMovieById(id);
        }

        movieDetailsViewModel.getMovieLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                titleTextView.setText(movie.getTitle());
                overviewTextView.setText(movie.getOverview());
                releaseDateTextView.setText(movie.getReleaseDate());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
