package com.esekiel.moviecatalogue.data.room.tvshow;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tv tv);

    @Delete
    void delete(Tv tv);

    @Query("DELETE FROM " + Tv.TABLE_NAME)
    void  deleteTv();

    @Query("SELECT * FROM " + Tv.TABLE_NAME + " ORDER BY " + Tv.COLUMN_NAME + " ASC ")
    LiveData<List<Tv>> getTv();


}
