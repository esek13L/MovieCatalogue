package com.esekiel.moviecatalogue.ui.tvshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esekiel.moviecatalogue.R;
import com.esekiel.moviecatalogue.data.model.TvResult;
import com.esekiel.moviecatalogue.ui.detail.TvDetailActivity;
import com.esekiel.moviecatalogue.util.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private final List<TvResult> tv;

    public TvAdapter(List<TvResult> tv) {
        this.tv = tv;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        holder.bind(tv.get(position));
    }

    @Override
    public int getItemCount() {
        return tv.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {

        private final TextView textTitle;
        private final TextView textOverview;
        private final ImageView imagePoster;
        private final CardView cardView;

        TvViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.tv_title);
            textOverview = itemView.findViewById(R.id.tv_overview);
            imagePoster = itemView.findViewById(R.id.img_poster);
            cardView = itemView.findViewById(R.id.cardview_layout);

        }
        void bind(TvResult tv){
            textTitle.setText(tv.getName());
            textOverview.setText(tv.getOverview());
            Utils.loadImage(imagePoster, tv.getPosterPath(), Utils.getProgressDrawable(itemView.getContext()));

            cardView.setOnClickListener(view -> {
                Context context = view.getContext();
                Intent intent = new Intent(context, TvDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", tv.getId());
                bundle.putString("title",tv.getName());
                bundle.putString("overview", tv.getOverview());
                bundle.putString("poster_path", tv.getPosterPath());
                bundle.putString("back_path", tv.getBackdropPath());
                bundle.putDouble("vote_average", tv.getVoteAverage());
                bundle.putString("release_date", tv.getFirstAirDate());
                intent.putExtras(bundle);
                context.startActivity(intent);
            });


        }
    }

    public void updateTvShow(List<TvResult> newTv){
        tv.clear();
        tv.addAll(newTv);
        notifyDataSetChanged();
    }
}

