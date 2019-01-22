package com.buka.mooviz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MovieDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_TITLE = "extra_movie_title";
    public static final String EXTRA_MOVIE_DESCRIPTION = "extra_movie_description";
    public static final String EXTRA_MOVIE_RELEASE_DATE = "extra_movie_release_date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView titleTextView = findViewById(R.id.textview_title);
        TextView overviewTextView = findViewById(R.id.textview_overview);
        TextView releaseDateTextView = findViewById(R.id.textview_release_date);

        Intent intent = getIntent();
        if (intent != null) {
            String movieTitle = intent.getStringExtra(EXTRA_MOVIE_TITLE);
            String description = intent.getStringExtra(EXTRA_MOVIE_DESCRIPTION);
            String releaseDate = intent.getStringExtra(EXTRA_MOVIE_RELEASE_DATE);

            titleTextView.setText(movieTitle);
            overviewTextView.setText(description);
            releaseDateTextView.setText(releaseDate);
        }
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
