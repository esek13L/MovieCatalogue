package com.esekiel.moviecatalogue.ui.favorite.movie;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.esekiel.moviecatalogue.R;
import com.esekiel.moviecatalogue.util.widget.MovieWidget;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {

    private FavoriteMovieViewModel viewModel;
    private FavoriteMovieAdapter adapter = new FavoriteMovieAdapter(new ArrayList<>());
    private RecyclerView recyclerView;


    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        recyclerView = root.findViewById(R.id.list_item_movie);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        loadData();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.delete_all, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_all_onclick) {
            deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()), R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        builder.setTitle(R.string.delete_all_lists);
        builder
                .setMessage(R.string.delete_all_options)
                .setCancelable(true)
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    viewModel.deleteAll();
                    updateAllWidgets();
                })
                .setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadData() {
        viewModel.getAllMovies().observe(this, movies -> adapter.setMovies(movies));
    }

    private void updateAllWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getContext(), MovieWidget.class));
        // Tell the widgets that the list items should be invalidated and refreshed!
        // Will call onDatasetChanged in ListWidgetService, doing a new requery
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
    }
}
