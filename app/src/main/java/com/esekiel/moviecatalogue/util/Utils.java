package com.esekiel.moviecatalogue.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import com.esekiel.moviecatalogue.R;

public class Utils {

    public final static String DATE_FORMAT_DAY = "EEEE, MMM d, yyyy";

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
                .fitCenter()
                .error(R.mipmap.ic_launcher_round);
        Glide.with(view.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load("http://image.tmdb.org/t/p/w185/" + url)
                .into(view);
    }

    public static void loadBackPath(ImageView view, String url, CircularProgressDrawable progressDrawable) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(progressDrawable)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round);
        Glide.with(view.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load("http://image.tmdb.org/t/p/w780" + url)
                .into(view);
    }

    private static String format(String date) {
        String result = "";

        DateFormat old = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oldDate = old.parse(date);
            DateFormat newFormat = new SimpleDateFormat(Utils.DATE_FORMAT_DAY);
            result = newFormat.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static  String getDateDay(String date)
    {
        return format(date);
    }



}
