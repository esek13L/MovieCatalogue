package com.esekiel.moviecatalogue.ui.favorite.movie;

import android.app.Application;

import com.esekiel.moviecatalogue.data.room.movie.Movie;
import com.esekiel.moviecatalogue.data.room.movie.MovieRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavoriteMovieViewModel extends AndroidViewModel {

    private final MovieRepository repository;
    private final LiveData<List<Movie>> allMovies;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        allMovies = repository.getAllMovies();
    }

    public LiveData<List<Movie>> getAllMovies(){
        return  allMovies;
    }

    public void deleteAll(){
        repository.deleteAllMovies();
    }
}
