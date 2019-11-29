package co.id.ramadanrizky.watchmovie.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import co.id.ramadanrizky.watchmovie.adapter.WidgetAdapter;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetAdapter(this.getApplicationContext());
    }
}
