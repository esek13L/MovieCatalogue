package test.esekiel.com.moviecatalogue.ui.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import test.esekiel.com.moviecatalogue.R;
import test.esekiel.com.moviecatalogue.data.model.MovieResult;
import test.esekiel.com.moviecatalogue.util.Utils;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieResult> movies;

    public MovieAdapter(List<MovieResult> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textOverview;
        private ImageView imagePoster;
        private CardView cardView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.tv_title);
            textOverview = itemView.findViewById(R.id.tv_overview);
            imagePoster = itemView.findViewById(R.id.img_poster);
            cardView = itemView.findViewById(R.id.cardview_layout);
        }

        void bind(MovieResult movie){
            textTitle.setText(movie.getTitle());
            textOverview.setText(movie.getOverview());
            Utils.loadImage(imagePoster, movie.getPosterPath(), Utils.getProgressDrawable(itemView.getContext()));
            /*
            cardView.setOnClickListener(view -> {
                Context context = view.getContext();
                Intent intent = new Intent(context, MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",movie.getTitle());
                bundle.putString("overview", movie.getOverview());
                bundle.putString("poster_path", movie.getPosterPath());
                intent.putExtras(bundle);
                context.startActivity(intent);
            });

             */
        }
    }

    public void updateMovies(List<MovieResult> newMovies) {
        movies.clear();
        movies.addAll(newMovies);
        notifyDataSetChanged();
    }
}
