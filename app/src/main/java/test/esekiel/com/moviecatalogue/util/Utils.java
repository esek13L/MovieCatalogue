package test.esekiel.com.moviecatalogue.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import test.esekiel.com.moviecatalogue.R;

public class Utils {

    public static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.start();
        return progressDrawable;
    }

    public static void loadImage(ImageView view, String url, CircularProgressDrawable progressDrawable) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(view.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load("http://image.tmdb.org/t/p/w185/" + url)
                .into(view);
    }
}
