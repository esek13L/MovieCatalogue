package com.esekiel.favoritelistmovie;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esekiel.favoritelistmovie.data.room.Movie;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Cursor cursor;
    private final Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(cursor.moveToPosition(position));
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textTitle;
        final TextView textOverview;
        final ImageView imgPoster;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            textTitle = itemView.findViewById(R.id.tv_title);
            textOverview = itemView.findViewById(R.id.tv_overview);
        }

        void bind(boolean moveToPosition){
            if (moveToPosition){
                textTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_NAME)));
                textOverview.setText(cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_OVERVIEW)));
                Glide.with(context).load("http://image.tmdb.org/t/p/w185/" + cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_POSTER))).into(imgPoster);
            }
        }
    }

    public void setData(Cursor mCursor) {
        cursor = mCursor;
        notifyDataSetChanged();
    }
}

