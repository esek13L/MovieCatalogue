package com.esekiel.moviecatalogue.ui.favorite;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class FavoriteAdapter extends FragmentPagerAdapter {

    private final List<String> pageTitle = new ArrayList<>();
    private final List<Fragment> pageFragment = new ArrayList<>();

    public FavoriteAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pageFragment.get(position);
    }

    @Override
    public int getCount() {
        return pageTitle.size();
    }

    void populateTab(Fragment fragment, String title ){
        pageFragment.add(fragment);
        pageTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitle.get(position);
    }
}
