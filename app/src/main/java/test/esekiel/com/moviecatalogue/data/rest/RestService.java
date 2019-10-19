package test.esekiel.com.moviecatalogue.data.rest;

import android.os.Build;

import javax.inject.Inject;

import io.reactivex.Single;
import test.esekiel.com.moviecatalogue.BuildConfig;
import test.esekiel.com.moviecatalogue.data.di.DaggerRestComponent;
import test.esekiel.com.moviecatalogue.data.model.Movie;
import test.esekiel.com.moviecatalogue.data.model.Tv;

public class RestService {

    private static RestService instance;
    private String apiKey = BuildConfig.TMDB_API_KEY;

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
}