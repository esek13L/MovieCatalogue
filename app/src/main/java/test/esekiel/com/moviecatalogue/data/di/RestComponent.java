package test.esekiel.com.moviecatalogue.data.di;

import dagger.Component;
import test.esekiel.com.moviecatalogue.data.rest.RestService;
import test.esekiel.com.moviecatalogue.ui.movie.MovieViewModel;
import test.esekiel.com.moviecatalogue.ui.tvshow.TvViewModel;

@Component(modules = {RestModule.class})
public interface RestComponent {

    void inject (RestService service);

    void inject (MovieViewModel viewModel);

    void inject (TvViewModel viewModel);


}
