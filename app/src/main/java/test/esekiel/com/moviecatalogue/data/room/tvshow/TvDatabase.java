package test.esekiel.com.moviecatalogue.data.room.tvshow;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Tv.class}, version = 1, exportSchema = false)
public abstract class TvDatabase extends RoomDatabase {

    public static final String TV_DATABASE = "tv_db";
    private static final Object LOCK = new Object();
    private static TvDatabase instance;
    private static final RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(instance).execute();
        }
    };

    public static TvDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room
                            .databaseBuilder(context.getApplicationContext(), TvDatabase.class, TV_DATABASE)
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract TvDao tvDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private TvDao dao;

        PopulateDbAsync(TvDatabase db){
            dao = db.tvDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.getTv();
            return null;
        }
    }
}
