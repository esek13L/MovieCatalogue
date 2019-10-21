package com.esekiel.moviecatalogue.util.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.esekiel.moviecatalogue.R;
import com.esekiel.moviecatalogue.data.room.movie.Movie;
import com.esekiel.moviecatalogue.data.room.movie.MovieDatabase;

public class MovieRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private List<Movie> mlist = new ArrayList<>();
    private MovieDatabase db;

    public MovieRemoteViewsFactory(Context mContext, Intent intent) {
        this.context = mContext;
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        db = MovieDatabase.getInstance(context);
    }

    @Override
    public void onDataSetChanged() {
        mlist = db.movieDao().getWidgetMovie();


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return (mlist == null) ? 0 : mlist.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (mlist == null || mlist.size() == 0) return null;

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.item_widget);
        Movie movies = mlist.get(i);


        Bitmap bmp = null;
        try {

            bmp = Glide.with(context)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w185" + mlist.get(i).getPoster())
                    //.error(new ColorDrawable(context.getResources().getColor(R.color.colorPrimary)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            rv.setImageViewBitmap(R.id.img_poster, bmp);
            rv.setTextViewText(R.id.tv_title, movies.getTitle());
            rv.setTextViewText(R.id.tv_overview, movies.getOverview());

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }


        Bundle bundle = new Bundle();
        bundle.putInt(MovieWidget.EXTRA_ITEM, i);
        Intent fillInInntent = new Intent();
        fillInInntent.putExtras(bundle);
        rv.setOnClickFillInIntent(R.id.img_poster, fillInInntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}