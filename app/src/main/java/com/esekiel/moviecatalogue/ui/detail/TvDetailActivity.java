package com.esekiel.moviecatalogue.ui.detail;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esekiel.moviecatalogue.R;
import com.esekiel.moviecatalogue.data.room.tvshow.Tv;
import com.esekiel.moviecatalogue.util.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class TvDetailActivity extends AppCompatActivity {

    Double textRating;
    Long textId;
    TextView tvTitle;
    TextView tvOverview;
    TextView tvRating;
    TextView tvDate;
    ProgressBar progressBar;
    TvDetailViewModel viewModel;
    Toolbar toolbar;
    FloatingActionButton fabFavorite;
    boolean isFavorite;
    ImageView imgPoster, imgBack;
    private String textTitle, textOverview, imgPic,  textDate, imgBck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvRating = findViewById(R.id.tv_rating);
        tvDate = findViewById(R.id.tv_date);
        imgPoster = findViewById(R.id.img_poster);
        imgBack = findViewById(R.id.img_back_poster);
        fabFavorite = findViewById(R.id.fab_fav);
        progressBar = findViewById(R.id.progressbar);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this).get(TvDetailViewModel.class);
        viewModel.refresh();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            textId = extras.getLong("id");
            textTitle = extras.getString("title");
            textOverview = extras.getString("overview");
            imgPic = extras.getString("poster_path");
            textRating = extras.getDouble("vote_average");
            textDate = extras.getString("release_date");
            imgBck = extras.getString("back_path");
        }

        loadTv();

        if (fabFavorite!= null) {
            fabFavorite.setOnClickListener(view -> {
                isFavorite = readFavoriteState();
                if (isFavorite) {
                    fabFavorite.setImageResource(R.drawable.ic_favorite_orange_24dp);
                    isFavorite = false;
                    Toast.makeText(this, getResources().getString(R.string.add_fav), Toast.LENGTH_SHORT).show();
                    saveTv();
                    saveFavorite(isFavorite);
                } else {
                    fabFavorite.setImageResource(R.drawable.ic_favorite_border_orange_24dp);
                    isFavorite = true;
                    Toast.makeText(this, getResources().getString(R.string.del_fav), Toast.LENGTH_SHORT).show();
                    deleteTv();
                    saveFavorite(isFavorite);
                }

            });
        }
    }


    private void loadTv() {
        viewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    tvTitle.setVisibility(View.GONE);
                    tvOverview.setVisibility(View.GONE);
                    imgPoster.setVisibility(View.GONE);
                    tvRating.setVisibility(View.GONE);
                    tvDate.setVisibility(View.GONE);
                    imgBack.setVisibility(View.GONE);

                }
            }
        });
        progressBar.postDelayed(() -> {
            tvTitle.setVisibility(View.VISIBLE);
            tvOverview.setVisibility(View.VISIBLE);
            imgPoster.setVisibility(View.VISIBLE);
            tvRating.setVisibility(View.VISIBLE);
            tvDate.setVisibility(View.VISIBLE);
            imgBack.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }, 500);
        Objects.requireNonNull(getSupportActionBar()).setTitle(textTitle);
        tvTitle.setText(textTitle);
        tvOverview.setText(textOverview);
        tvRating.setText(getResources().getString(R.string.rating, String.valueOf(textRating)));
        tvDate.setText(Utils.getDateDay(textDate));
        Utils.loadImage(imgPoster, imgPic, Utils.getProgressDrawable(this));
        Utils.loadBackPath(imgBack, imgBck, Utils.getProgressDrawable(this));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        if (!readFavoriteState()){
            fabFavorite.setImageResource(R.drawable.ic_favorite_orange_24dp);
        }else {
            fabFavorite.setImageResource(R.drawable.ic_favorite_border_orange_24dp);
        }
        super.onResume();
    }


    private void saveFavorite(Boolean isFavorite) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.pref_key_tv_fav), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getResources().getString(R.string.saved_fav_movie_key), isFavorite);
        editor.apply();
    }

    private Boolean readFavoriteState() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.pref_key_tv_fav), MODE_PRIVATE);
        return sharedPreferences.getBoolean(getResources().getString(R.string.saved_fav_movie_key), true);
    }

    private void saveTv(){
        Tv tv = new Tv(textId, textTitle, textOverview, imgPic, imgBck, textDate, textRating);
        viewModel.insert(tv);
    }

    private void deleteTv(){
        Tv tv = new Tv(textId, textTitle, textOverview, imgPic, imgBck, textDate, textRating);
        viewModel.delete(tv);
    }




}
