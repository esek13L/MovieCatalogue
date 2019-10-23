package com.esekiel.moviecatalogue.data.room.tvshow;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Tv.TABLE_NAME)
public class Tv {

    public static final String TABLE_NAME = "tv_table";

    private static final String COLUMN_ID = BaseColumns._ID;

    public static final String COLUMN_NAME = "title";

    private static final String COLUMN_OVERVIEW = "overview";

    private static final String COLUMN_POSTER = "poster";

    private static final String COLUMN_DATE = "release_date";

    private static final String COLUMN_RATE = "vote_rating";

    private static final String COLUMN_BACKPOST = "back_poster";


    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID, index = true)
    private Long id;

    @ColumnInfo(name = COLUMN_NAME)
    private String title;

    @ColumnInfo(name = COLUMN_OVERVIEW)
    private String overview;

    @ColumnInfo(name = COLUMN_POSTER)
    private String poster;

    @ColumnInfo(name = COLUMN_BACKPOST)
    private String backPoster;

    @ColumnInfo(name = COLUMN_DATE)
    private String release_date;

    @ColumnInfo(name = COLUMN_RATE)
    private Double vote_rating;

    public Tv(Long id, String title, String overview, String poster, String backPoster, String release_date, Double vote_rating) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster = poster;
        this.backPoster = backPoster;
        this.release_date = release_date;
        this.vote_rating = vote_rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Double getVote_rating() {
        return vote_rating;
    }

    public void setVote_rating(Double vote_rating) {
        this.vote_rating = vote_rating;
    }

    public String getBackPoster() {
        return backPoster;
    }

    public void setBackPoster(String backPoster) {
        this.backPoster = backPoster;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
