package com.esekiel.favoritelistmovie.data.room;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

class MovieRepository {

    private final MovieDao movieDao;
    private final LiveData<List<Movie>> allMovies;

    public MovieRepository(Application application) {
        MovieDatabase db = MovieDatabase.getInstance(application);
        movieDao = db.movieDao();
        allMovies = movieDao.getMovies();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    public void insert(Movie movie){
        new insertAsyncTask(movieDao).execute(movie);
    }

    public void delete(Movie movie){
        new deleteAsyncTask(movieDao).execute(movie);
    }

    public void deleteAllMovies(){
        new deleteAllAsyncTask(movieDao).execute();
    }


    static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private final MovieDao asyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    static class deleteAsyncTask extends AsyncTask<Movie, Void, Void> {

        private final MovieDao asyncTaskDao;

        deleteAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... params) {
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }

    static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private final MovieDao asyncTaskDao;

        deleteAllAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteMovie();
            return null;
        }
    }


}
