package co.id.ramadanrizky.watchmovie;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import co.id.ramadanrizky.watchmovie.service.WidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class BannerMovieWidget extends AppWidgetProvider {

    private static final String TOAST_ACTION = "co.id.ramadanrizky.TOAST_ACTION";
    public static final String EXTRA_ITEM = "co.id.ramadanrizky.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.banner_movie_widget);
        views.setRemoteAdapter(R.id.widgetStack, intent);
        views.setEmptyView(R.id.widgetStack, R.id.widgetEmpty);

        Intent toastInt = new Intent(context, BannerMovieWidget.class);
        toastInt.setAction(BannerMovieWidget.TOAST_ACTION);
        toastInt.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent penTent = PendingIntent.getBroadcast(context, 0, toastInt, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widgetStack, penTent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null){
            if (intent.getAction().equals(TOAST_ACTION)) {
                int index = intent.getIntExtra(EXTRA_ITEM, 0);
                Toast.makeText(context, "Touched "+index, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

