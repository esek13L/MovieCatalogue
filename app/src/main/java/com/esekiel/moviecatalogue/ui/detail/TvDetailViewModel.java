package com.esekiel.moviecatalogue.ui.detail;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.esekiel.moviecatalogue.data.room.tvshow.Tv;
import com.esekiel.moviecatalogue.data.room.tvshow.TvRepository;

public class TvDetailViewModel extends AndroidViewModel{
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private TvRepository repository;

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
