package test.esekiel.com.moviecatalogue.data.room.movie;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Movie.TABLE_NAME)
public class Movie {

    public static final String TABLE_NAME = "movie_table";

    public static final String COLUMN_ID = BaseColumns._ID;

    public static final String COLUMN_NAME = "title";

    public static final String COLUMN_OVERVIEW = "overview";

    public static final String COLUMN_POSTER = "poster";

    public static final String COLUMN_DATE = "release_date";

    public static final String COLUMN_RATE = "vote_rating";

    public static final String COLUMN_BACKPOST = "back_poster";


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

    public Movie(Long id, String title, String overview, String poster, String backPoster, String release_date, Double vote_rating) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster = poster;
        this.backPoster = backPoster;
        this.release_date = release_date;
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

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster() {
        return poster;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Double getVote_rating() {
        return vote_rating;
    }
}
