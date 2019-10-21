package com.esekiel.moviecatalogue.data.room.movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("DELETE FROM " + Movie.TABLE_NAME)
    void  deleteMovie();


    @Query("SELECT * FROM " + Movie.TABLE_NAME + " ORDER BY " + Movie.COLUMN_NAME + " ASC")
    LiveData <List<Movie>> getMovies ();

    @Query("SELECT * from " + Movie.TABLE_NAME)
    List<Movie> getWidgetMovie();





}
