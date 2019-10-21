package com.esekiel.moviecatalogue.ui.tvshow;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import com.esekiel.moviecatalogue.data.di.DaggerRestComponent;
import com.esekiel.moviecatalogue.data.model.Tv;
import com.esekiel.moviecatalogue.data.rest.RestService;

public class TvViewModel extends ViewModel {

    MutableLiveData<Tv> tvShow = new MutableLiveData<>();
    MutableLiveData<Boolean> tvLoadError = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public RestService service;

    public TvViewModel() {
        super();
        DaggerRestComponent.create().inject(this);
    }

    public void refresh() {
        fetchTvShow();
    }

    private void fetchTvShow() {
        loading.setValue(true);
        disposable.add(
                service.getTv()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Tv>() {

                            @Override
                            public void onSuccess(Tv value) {
                                tvLoadError.setValue(false);
                                tvShow.setValue(value);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                tvLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );

    }

    public void search(String query){
        searchTvshow(query);
    }

    private void searchTvshow(String query){
        loading.setValue(true);
        disposable.add(
                service.searchTv(query)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Tv>() {

                            @Override
                            public void onSuccess(Tv value) {
                                tvShow.setValue(value);
                                tvLoadError.setValue(false);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                tvLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();

                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}