package com.esekiel.moviecatalogue.ui.detail;

import android.app.Application;

import com.esekiel.moviecatalogue.data.room.tvshow.Tv;
import com.esekiel.moviecatalogue.data.room.tvshow.TvRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class TvDetailViewModel extends AndroidViewModel{
    public final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private final TvRepository repository;

    public TvDetailViewModel(Application application){
        super(application);
        repository = new TvRepository(application);
    }

    public void refresh() {
        addTv();
    }

    private void addTv() {
        loading.setValue(true);
    }

    public void insert(Tv tv){
        repository.insert(tv);
    }

    public void delete(Tv tv){
        repository.delete(tv);
    }
}
