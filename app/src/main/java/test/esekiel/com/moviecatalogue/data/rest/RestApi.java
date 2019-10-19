package test.esekiel.com.moviecatalogue.data.rest;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.esekiel.com.moviecatalogue.data.model.Movie;
import test.esekiel.com.moviecatalogue.data.model.Tv;

public interface RestApi {

    @GET("3/movie/now_playing")
    Single<Movie> getMovies(@Query("api_key") String API_KEY);

    @GET("3/tv/airing_today")
    Single<Tv> getTv(@Query("api_key") String API_KEY);
}