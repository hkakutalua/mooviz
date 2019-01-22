package com.buka.mooviz.models;

import com.google.gson.annotations.SerializedName;

public class Movie {
    private int id;
    private String title;
    private String overview;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("release_date")
    private String releaseDate;

    public Movie(int id, String title, double voteAverage, String originalLanguage, String overview, String releaseDate) {
        this.id = id;
        this.title = title;
        this.voteAverage = voteAverage;
        this.originalLanguage = originalLanguage;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
