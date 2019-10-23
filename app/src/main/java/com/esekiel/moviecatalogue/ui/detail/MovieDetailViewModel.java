package com.esekiel.moviecatalogue.ui.detail;

import android.app.Application;

import com.esekiel.moviecatalogue.data.room.movie.Movie;
import com.esekiel.moviecatalogue.data.room.movie.MovieRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MovieDetailViewModel extends AndroidViewModel{

    public final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private final MovieRepository repository;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
    }

    public void refresh() {
        addMovies();
    }

    private void addMovies() {
        loading.setValue(true);
    }

    public void insert(Movie movie) {
        repository.insert(movie);
    }

    public void delete(Movie movie) {
        repository.delete(movie);
    }
}
