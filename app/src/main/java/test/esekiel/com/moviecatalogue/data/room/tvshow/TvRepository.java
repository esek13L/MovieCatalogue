package test.esekiel.com.moviecatalogue.data.room.tvshow;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TvRepository {

    private final TvDao tvDao;
    private final LiveData<List<Tv>> allTv;

    public TvRepository(Application application){
        TvDatabase db = TvDatabase.getInstance(application);
        tvDao = db.tvDao();
        allTv = tvDao.getTv();
    }

    public LiveData<List <Tv>> getAllTv(){
        return allTv;
    }

    public void insert(Tv tv){
        new insertAsyncTask(tvDao).execute(tv);
    }

    public void delete(Tv tv){
        new deleteAsyncTask(tvDao).execute(tv);
    }

    public void deleteAllTv(){
        new deleteAllAsyncTask(tvDao).execute();
    }

    static class insertAsyncTask extends AsyncTask<Tv, Void, Void> {

        private final TvDao asyncTaskDao;

        insertAsyncTask(TvDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Tv... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    static class deleteAsyncTask extends AsyncTask<Tv, Void, Void>{

        private final TvDao asyncTaskDao;

        deleteAsyncTask(TvDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Tv... params) {
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }

    static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private final TvDao asyncTaskDao;

        deleteAllAsyncTask(TvDao dao){
            asyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteTv();
            return null;
        }
    }
}
