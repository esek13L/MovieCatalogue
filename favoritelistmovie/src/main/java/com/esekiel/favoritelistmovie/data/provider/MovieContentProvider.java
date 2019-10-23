package com.esekiel.favoritelistmovie.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.esekiel.favoritelistmovie.data.room.MovieDao;
import com.esekiel.favoritelistmovie.data.room.MovieDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.esekiel.favoritelistmovie.data.room.Movie.TABLE_NAME;

public class MovieContentProvider extends ContentProvider {

    private MovieDao dao;

    private static final String AUTHORITY = "com.esekiel.moviecatalogue.provider";

    public static final Uri URI_MOVIE = Uri.parse(
            "content://" + AUTHORITY + "/" + TABLE_NAME);

    private static final int CODE_MOVIE_DIR = 1;

    private static final int CODE_MOVIE_ITEM = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, TABLE_NAME, CODE_MOVIE_DIR);
        // * = Matches a string of any valid characters of any length
        MATCHER.addURI(AUTHORITY, TABLE_NAME + "/*", CODE_MOVIE_ITEM);
    }


    @Override
    public boolean onCreate() {
        MovieDatabase database = MovieDatabase.getInstance(getContext());
        dao = database.movieDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final int code = MATCHER.match(uri);
        if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            final Cursor cursor;
            if (code == CODE_MOVIE_DIR) {
                cursor = dao.selectAll();
            } else {
                cursor = dao.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_NAME;
            case CODE_MOVIE_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String
            s, @Nullable String[] strings) {
        return 0;
    }
}
