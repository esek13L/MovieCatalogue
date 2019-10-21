package com.esekiel.moviecatalogue.ui.favorite.tvshow;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.esekiel.moviecatalogue.data.room.tvshow.Tv;
import com.esekiel.moviecatalogue.data.room.tvshow.TvRepository;

public class FavoriteTvViewModel extends AndroidViewModel {

    private final TvRepository repository;
    private final LiveData<List<Tv>> allTv;

    public FavoriteTvViewModel(@NonNull Application application) {
        super(application);
        repository = new TvRepository(application);
        allTv = repository.getAllTv();
    }

    public LiveData<List<Tv>> getAllTv() {
        return allTv;
    }

    public void insert(Tv tv) {
        repository.insert(tv);
    }

    public void delete(Tv tv) {
        repository.delete(tv);
    }

    public void deleteAll() {
        repository.deleteAllTv();
    }
}
