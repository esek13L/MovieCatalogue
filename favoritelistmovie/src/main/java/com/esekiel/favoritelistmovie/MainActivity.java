package com.esekiel.favoritelistmovie;

import android.database.Cursor;
import android.os.Bundle;

import com.esekiel.favoritelistmovie.data.provider.MovieContentProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CODE_MOVIE = 1;
    private MovieAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list_movie_provider);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MovieAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(CODE_MOVIE, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (i == CODE_MOVIE) {
            return new CursorLoader(getApplicationContext(), MovieContentProvider.URI_MOVIE, null, null, null, null);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (loader.getId() == CODE_MOVIE) {
            try {
                adapter.setData(cursor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == CODE_MOVIE) {
            adapter.setData(null);
        }
    }
}
