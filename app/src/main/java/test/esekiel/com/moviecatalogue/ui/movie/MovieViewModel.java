package test.esekiel.com.moviecatalogue.ui.movie;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import test.esekiel.com.moviecatalogue.data.di.DaggerRestComponent;
import test.esekiel.com.moviecatalogue.data.model.Movie;
import test.esekiel.com.moviecatalogue.data.rest.RestService;

public class MovieViewModel extends ViewModel {

    MutableLiveData<Movie> movies = new MutableLiveData<>();
    MutableLiveData<Boolean> movieLoadError = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public RestService service;

    public MovieViewModel() {
        super();
        DaggerRestComponent.create().inject(this);
    }

    public void refresh(){
        fetchMovie();
    }

    private void fetchMovie() {
        loading.setValue(true);
        disposable.add(
                service.getMovies()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Movie>() {

                            @Override
                            public void onSuccess(Movie value) {
                                movies.setValue(value);
                                movieLoadError.setValue(false);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                movieLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );

    }

    public void search(String query){
        searchMovies(query);

    }
    private void searchMovies(String keyword){
        loading.setValue(true);
        disposable.add(
                service.searchMovies(keyword)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Movie>() {
                            @Override
                            public void onSuccess(Movie value) {
                                movies.setValue(value);
                                movieLoadError.setValue(false);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                movieLoadError.setValue(true);
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