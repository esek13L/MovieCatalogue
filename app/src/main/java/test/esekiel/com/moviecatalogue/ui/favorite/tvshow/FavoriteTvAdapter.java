package test.esekiel.com.moviecatalogue.ui.favorite.tvshow;

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
import test.esekiel.com.moviecatalogue.data.room.tvshow.Tv;
import test.esekiel.com.moviecatalogue.ui.detail.TvDetailActivity;
import test.esekiel.com.moviecatalogue.util.Utils;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder> {


    private List<Tv> tvs;

    public FavoriteTvAdapter(List<Tv> tvs) {
        this.tvs = tvs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(tvs.get(position));
    }

    @Override
    public int getItemCount() {
        if (tvs != null) {
            return tvs.size();
        } else return 0;
    }

    public void setTvs(List<Tv> newTv) {
        tvs = newTv;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView imgPoster;
        private TextView tvTitle;
        private TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview_layout);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
        }

        void bind(Tv tv) {
            if (tvs != null) {
                tvTitle.setText(tv.getTitle());
                tvOverview.setText(tv.getOverview());
                Utils.loadImage(imgPoster, tv.getPoster(), Utils.getProgressDrawable(itemView.getContext()));
                cardView.setOnClickListener(view -> {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, TvDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", tv.getId());
                    bundle.putString("title",tv.getTitle());
                    bundle.putString("overview", tv.getOverview());
                    bundle.putString("poster_path", tv.getPoster());
                    bundle.putString("back_path", tv.getBackPoster());
                    bundle.putDouble("vote_average", tv.getVote_rating());
                    bundle.putString("release_date", tv.getRelease_date());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                });
            }
        }
    }
}
