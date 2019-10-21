package com.esekiel.moviecatalogue.data.rest;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.esekiel.moviecatalogue.data.model.Movie;
import com.esekiel.moviecatalogue.data.model.Tv;

public interface RestApi {

    @GET("3/movie/now_playing")
    Single<Movie> getMovies(@Query("api_key") String API_KEY);

    @GET("3/tv/airing_today")
    Single<Tv> getTv(@Query("api_key") String API_KEY);

    @GET("3/search/movie")
    Single<Movie> searchMovies(@Query("api_key")String API_KEY, @Query("main_menu") String language, @Query("query") String keyword);

    @GET("3/search/tv")
    Single<Tv> searchTv(@Query("api_key")String API_KEY, @Query("main_menu") String language, @Query("query") String keyword);

    @GET("3/discover/movie")
    Call<Movie> discoverMovie(@Query("api_key")String API_KEY, @Query("primary_release_date.gte") String gte, @Query("primary_release_date.lte") String lte);
}
