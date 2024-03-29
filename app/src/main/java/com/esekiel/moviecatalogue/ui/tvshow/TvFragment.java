package com.esekiel.moviecatalogue.ui.tvshow;

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

import com.esekiel.moviecatalogue.R;
import com.esekiel.moviecatalogue.util.notifications.NotificationActivity;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class TvFragment extends Fragment {

    private RecyclerView itemList;

    private TextView errorList;

    private ProgressBar progressBar;

    private SwipeRefreshLayout refreshLayout;

    private TvViewModel viewModel;
    private final TvAdapter adapter = new TvAdapter(new ArrayList<>());

    private TvViewModel tvViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tvshow, container, false);
        itemList = root.findViewById(R.id.list_recycler);
        errorList = root.findViewById(R.id.list_error);
        progressBar = root.findViewById(R.id.progressBarCL);
        refreshLayout = root.findViewById(R.id.swipe_refresher);
        viewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        viewModel.refresh();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        itemList.setAdapter(adapter);
        observeTv();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        refreshLayout.setColorSchemeResources(
                R.color.colorSecondary,
                android.R.color.holo_blue_bright,
                R.color.colorAccent,
                android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(() -> {
            viewModel.refresh();
            refreshLayout.setRefreshing(false);
        });


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getContext()).getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search_item).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.search_item);

        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
        }
        searchView.setQueryHint("Search Latest Tvshow...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2) {
                    searchTv(query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchTv(newText);
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
            case R.id.action_settings:
                intent = new Intent(getContext(), NotificationActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void observeTv() {
        viewModel.tvShow.observe(this, tvItems -> {
            if (tvItems != null) {
                itemList.setVisibility(View.VISIBLE);
                adapter.updateTvShow(tvItems.getResults());
            }
        });
        viewModel.tvLoadError.observe(this, isError -> {
            if (isError != null) {
                errorList.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });
        viewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    errorList.setVisibility(View.GONE);
                    itemList.setVisibility(View.GONE);
                }
            }
        });
    }

    private void searchTv(String keyword) {
        if (keyword.length() > 0) {
            viewModel.search(keyword);
        } else {
            errorList.setVisibility(View.VISIBLE);
            errorList.setText(getResources().getString(R.string.not_found));
        }

    }

}