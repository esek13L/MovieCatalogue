package com.esekiel.favoritelistmovie.data.room;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "movie_db";
    private static final Object LOCK = new Object();
    private static MovieDatabase instance;
    private static final Callback callback = new Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(instance).execute();
        }
    };

    public static MovieDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room
                            .databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DATABASE_NAME)
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return instance;

    }

    public abstract MovieDao movieDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final MovieDao dao;

        PopulateDbAsync(MovieDatabase db){
            dao = db.movieDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.getWidgetMovie();
            dao.getMovies();
            return null;
        }
    }
}
