package test.esekiel.com.moviecatalogue.ui.tvshow;

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

public class TvFragment extends Fragment {

    private RecyclerView itemList;

    private TextView errorList;

    private ProgressBar progressBar;

    private SwipeRefreshLayout refreshLayout;

    private TvViewModel viewModel;
    private TvAdapter adapter = new TvAdapter(new ArrayList<>());

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
}