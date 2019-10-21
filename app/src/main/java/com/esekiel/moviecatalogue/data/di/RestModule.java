package com.esekiel.moviecatalogue.data.di;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.esekiel.moviecatalogue.data.rest.RestApi;
import com.esekiel.moviecatalogue.data.rest.RestService;

@Module
public class RestModule {

    private static final String BASE_URL = "http://api.themoviedb.org/";

    @Provides
    public RestApi provideSourceApi(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RestApi.class);
    }

    @Provides
    public RestService provideSourceService(){
        return RestService.getInstance();
    }

}
