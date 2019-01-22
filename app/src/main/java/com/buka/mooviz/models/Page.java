package com.buka.mooviz.models;

import java.util.ArrayList;

public class Page {
    private int page;
    private int totalResults;
    private int totalPages;
    private ArrayList<Movie> results;

    public Page(int page, int totalResults, int totalPages, ArrayList<Movie> results) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }
}
