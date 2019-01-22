package com.buka.mooviz.api;

import com.buka.mooviz.models.Page;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TmdbApi {
    @GET("https://api.themoviedb.org/3/discover/movie?api_key=02e7ea83e252923ae63e50b78d3c7e2c")
    Call<Page> getPopularMovies();
}
