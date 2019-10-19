package test.esekiel.com.moviecatalogue.ui.movie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import test.esekiel.com.moviecatalogue.R;
import test.esekiel.com.moviecatalogue.data.model.MovieResult;

public class MovieFragment extends Fragment {

    private RecyclerView itemList;

    private TextView errorList;

    private ProgressBar progressBar;

    private SwipeRefreshLayout refreshLayout;

    private MovieViewModel viewModel;
    private MovieAdapter adapter = new MovieAdapter(new ArrayList<>());

    private MovieViewModel movieViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        itemList = root.findViewById(R.id.list_recycler);
        errorList = root.findViewById(R.id.list_error);
        progressBar = root.findViewById(R.id.progressBarCL);
        refreshLayout = root.findViewById(R.id.swipe_refresher);
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.refresh();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout.setColorSchemeResources(
                R.color.colorSecondary,
                android.R.color.holo_blue_bright,
                R.color.colorAccent,
                android.R.color.holo_green_light);
        itemList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        itemList.setAdapter(adapter);
        observeMovie();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        refreshLayout.setOnRefreshListener(() -> {
            viewModel.refresh();
            refreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getContext()).getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search_item).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.search_item);

        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
        }
        searchView.setQueryHint("Search Latest Movies...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    searchMovie(query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMovie(newText);
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_lang:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    private void observeMovie(){
        viewModel.movies.observe(this, movieItems -> {
            if (movieItems != null){
                itemList.setVisibility(View.VISIBLE);
                adapter.updateMovies(movieItems.getResults());
            }
        });
        viewModel.movieLoadError.observe(this, isError ->{
            if (isError!= null){
                errorList.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });
        viewModel.loading.observe(this, isLoading ->{
            if (isLoading != null){
                progressBar.setVisibility(isLoading? View.VISIBLE : View.GONE);
                if (isLoading){
                    errorList.setVisibility(View.GONE);
                    itemList.setVisibility(View.GONE);
                }
            }
        });

    }

    private void searchMovie(String keyword){
        if (keyword.length() > 0 ){
            viewModel.search(keyword);
        }
        else {
            errorList.setVisibility(View.VISIBLE);
            errorList.setText("Not Found");
        }
    }
}