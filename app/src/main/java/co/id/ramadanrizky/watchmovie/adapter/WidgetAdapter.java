package co.id.ramadanrizky.watchmovie.adapter;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import co.id.ramadanrizky.watchmovie.BannerMovieWidget;
import co.id.ramadanrizky.watchmovie.R;
import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.watchmovie.helper.FavoriteHelper;

public class WidgetAdapter implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<ListMovieEntity> listMovieEntities = new ArrayList<>();
    private FavoriteHelper favoriteHelper;
    private final Context context;

    public WidgetAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        favoriteHelper = new FavoriteHelper(context);
        favoriteHelper.open();
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        listMovieEntities = favoriteHelper.getAllFavoriteMovie();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        favoriteHelper.close();
    }

    @Override
    public int getCount() {
        return listMovieEntities.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);
        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w185/"+listMovieEntities.get(i).getMoviePosterPath())
                    .submit()
                    .get();
            remoteViews.setImageViewBitmap(R.id.widgetImage, bitmap);

            Bundle bundle = new Bundle();
            bundle.putInt(BannerMovieWidget.EXTRA_ITEM, i);
            Intent fIntent = new Intent();
            fIntent.putExtras(bundle);

            remoteViews.setOnClickFillInIntent(R.id.widgetImage, fIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return remoteViews;
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
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
