package com.esekiel.moviecatalogue.ui.favorite.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esekiel.moviecatalogue.R;
import com.esekiel.moviecatalogue.data.room.movie.Movie;
import com.esekiel.moviecatalogue.ui.detail.MovieDetailActivity;
import com.esekiel.moviecatalogue.util.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {

    private List<Movie> movies;

    public FavoriteMovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        if (movies != null){
            return movies.size();
        }

        else return 0;
    }

    public void setMovies(List<Movie> newMovies) {
        movies = newMovies;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final ImageView imgPoster;
        private final TextView tvTitle;
        private final TextView tvOverview;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_layout);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
        }

        void bind(Movie movie){
            if (movies != null){
                tvTitle.setText(movie.getTitle());
                tvOverview.setText(movie.getOverview());
                Utils.loadImage(imgPoster, movie.getPoster(), Utils.getProgressDrawable(itemView.getContext()));
                cardView.setOnClickListener(view -> {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", movie.getId());
                    bundle.putString("title",movie.getTitle());
                    bundle.putString("overview", movie.getOverview());
                    bundle.putString("poster_path", movie.getPoster());
                    bundle.putString("back_path", movie.getBackPoster());
                    bundle.putDouble("vote_average", movie.getVote_rating());
                    bundle.putString("release_date", movie.getRelease_date());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                });
            }
        }
    }
}
