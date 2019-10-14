package test.esekiel.com.moviecatalogue.ui.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
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
}