package com.esekiel.moviecatalogue.ui.favorite;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esekiel.moviecatalogue.R;
import com.esekiel.moviecatalogue.ui.favorite.movie.FavoriteMovieFragment;
import com.esekiel.moviecatalogue.ui.favorite.tvshow.FavoriteTvFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        viewPager = root.findViewById(R.id.viewPager);
        tabLayout = root.findViewById(R.id.tabLayout);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FavoriteAdapter adapter = new FavoriteAdapter(getChildFragmentManager(), 1);
        adapter.populateTab(new FavoriteMovieFragment(), getResources().getString(R.string.title_movies));
        adapter.populateTab(new FavoriteTvFragment(), getResources().getString(R.string.title_tvshows));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
