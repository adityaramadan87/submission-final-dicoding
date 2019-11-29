package co.id.ramadanrizky.consumermovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.id.ramadanrizky.consumermovie.adapter.MovieFavoriteAdapter;
import co.id.ramadanrizky.consumermovie.contract.DatabaseContract;
import co.id.ramadanrizky.consumermovie.entity.ListMovieEntity;
import co.id.ramadanrizky.consumermovie.helper.MappingHelper;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadMovieCallbacks{

    private RecyclerView rv_favorite;
    private MovieFavoriteAdapter movieFavoriteAdapter;
    private ArrayList<ListMovieEntity> listMovieEntities;
    private ImageView iconEmpty;
    private TextView txtEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        iconEmpty = findViewById(R.id.icon_empty);
        txtEmpty = findViewById(R.id.txt_empty);
        rv_favorite = findViewById(R.id.rv_favorite);
        rv_favorite.setLayoutManager(new LinearLayoutManager(this));
        rv_favorite.setHasFixedSize(true);

        movieFavoriteAdapter = new MovieFavoriteAdapter(this);
        rv_favorite.setAdapter(movieFavoriteAdapter);
        checkEmptyListMovieFavorite();
        movieFavoriteAdapter.notifyDataSetChanged();

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver observer = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(DatabaseContract.FavoriteMovieColumns.CONTENT_URI, true, observer);
        new LoadMovieAsync(this, this).execute();
    }

    private void checkEmptyListMovieFavorite() {
        movieFavoriteAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkEmptyMovieFavorite(movieFavoriteAdapter);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkEmptyMovieFavorite(movieFavoriteAdapter);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkEmptyMovieFavorite(movieFavoriteAdapter);
            }
        });
    }

    private void checkEmptyMovieFavorite(MovieFavoriteAdapter movieFavoriteAdapter) {
        iconEmpty.setVisibility(movieFavoriteAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        txtEmpty.setVisibility(movieFavoriteAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<ListMovieEntity> listMovieEntities) {
        if (listMovieEntities.size() > 0){
            movieFavoriteAdapter.setListMovieEntities(listMovieEntities);
        }else {
            movieFavoriteAdapter.setListMovieEntities(new ArrayList<ListMovieEntity>());
        }
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<ListMovieEntity>> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallbacks> weakCallback;

        public LoadMovieAsync(Context context, LoadMovieCallbacks movieCallbacks) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(movieCallbacks);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<ListMovieEntity> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseContract.FavoriteMovieColumns.CONTENT_URI, null,null,null,null);
            return MappingHelper.mappingToMovie(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<ListMovieEntity> listMovieEntities) {
            super.onPostExecute(listMovieEntities);
            weakCallback.get().postExecute(listMovieEntities);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context){
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMovieAsync(context, (LoadMovieCallbacks) context).execute();
        }
    }
}
