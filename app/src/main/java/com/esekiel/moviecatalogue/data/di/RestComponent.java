package com.esekiel.moviecatalogue.data.di;

import com.esekiel.moviecatalogue.data.rest.RestService;
import com.esekiel.moviecatalogue.ui.movie.MovieViewModel;
import com.esekiel.moviecatalogue.ui.tvshow.TvViewModel;

import dagger.Component;

@Component(modules = {RestModule.class})
public interface RestComponent {

    void inject (RestService service);

    void inject (MovieViewModel viewModel);

    void inject (TvViewModel viewModel);


}
