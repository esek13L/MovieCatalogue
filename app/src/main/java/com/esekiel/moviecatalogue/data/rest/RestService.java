package com.esekiel.moviecatalogue.data.rest;

import com.esekiel.moviecatalogue.BuildConfig;
import com.esekiel.moviecatalogue.data.di.DaggerRestComponent;
import com.esekiel.moviecatalogue.data.model.Movie;
import com.esekiel.moviecatalogue.data.model.Tv;

import javax.inject.Inject;

import io.reactivex.Single;

public class RestService {

    private static RestService instance;
    private final String apiKey = BuildConfig.TMDB_API_KEY;
    private final String language = "en-US";

    @Inject
    public RestApi api;

    public static RestService getInstance(){
        if (instance == null){
            //make new rest service if its nulll
            instance = new RestService();
        }
        return instance;
    }

    private RestService(){
        DaggerRestComponent.create().inject(this);
    }

    public Single<Movie> getMovies(){
        return api.getMovies(apiKey);
    }

    public Single<Tv> getTv(){
        return api.getTv(apiKey);
    }

    public Single<Movie> searchMovies(String query){
        return api.searchMovies(apiKey, language, query );
    }

    public Single<Tv> searchTv(String query){
        return api.searchTv(apiKey,language, query);
    }
}
