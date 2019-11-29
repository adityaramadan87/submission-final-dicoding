package co.id.ramadanrizky.watchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.id.ramadanrizky.watchmovie.adapter.MovieFavoriteAdapter;
import co.id.ramadanrizky.watchmovie.adapter.TVFavoriteAdapter;
import co.id.ramadanrizky.watchmovie.database.DatabaseContract;
import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.watchmovie.entity.ListTVEntity;
import co.id.ramadanrizky.watchmovie.helper.FavoriteHelper;
import co.id.ramadanrizky.watchmovie.helper.MappingHelper;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    public static String HEADER_MOVIE = "header_movie";
    public static String HEADER_TV = "header_tv";
    private RecyclerView rv_favorite;
    private MovieFavoriteAdapter movieFavoriteAdapter;
    private TVFavoriteAdapter tvFavoriteAdapter;
    private ArrayList<ListMovieEntity> listMovieEntities;
    private ArrayList<ListTVEntity> listTVEntities;
    private String movie;
    private String tv;
    private ImageView iconEmpty;
    private TextView txtEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        iconEmpty = findViewById(R.id.icon_empty);
        txtEmpty = findViewById(R.id.txt_empty);
        rv_favorite = findViewById(R.id.rv_favorite);
        rv_favorite.setLayoutManager(new LinearLayoutManager(this));
        rv_favorite.setHasFixedSize(true);


        data();

    }

    private void data() {
        movie = getIntent().getStringExtra(HEADER_MOVIE);
        tv = getIntent().getStringExtra(HEADER_TV);
        if (movie != null){
            getSupportActionBar().setTitle(movie);
            movieFavoriteAdapter = new MovieFavoriteAdapter(this);
            listMovieEntities = new ArrayList<>();
            Cursor cursor = this.getContentResolver().query(DatabaseContract.FavoriteMovieColumns.CONTENT_URI, null ,null,null,null);
            listMovieEntities = MappingHelper.mapMovie(cursor);
            movieFavoriteAdapter.setListMovieEntities(listMovieEntities);
            rv_favorite.setAdapter(movieFavoriteAdapter);
            checkEmptyListMovieFavorite();
            movieFavoriteAdapter.notifyDataSetChanged();
            cursor.close();
        }else if (tv != null){
            getSupportActionBar().setTitle(tv);
            tvFavoriteAdapter = new TVFavoriteAdapter(this);
            listTVEntities = new ArrayList<>();
            FavoriteHelper favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
            favoriteHelper.open();
            listTVEntities = favoriteHelper.getAllFavoriteTvShow();
            tvFavoriteAdapter.setListTVEntities(listTVEntities);
            rv_favorite.setAdapter(tvFavoriteAdapter);
            checkEmptyListTVFavorite();
            tvFavoriteAdapter.notifyDataSetChanged();
        }
    }

    private void checkEmptyListTVFavorite() {
        tvFavoriteAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkEmptyTVFavorite(tvFavoriteAdapter);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkEmptyTVFavorite(tvFavoriteAdapter);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkEmptyTVFavorite(tvFavoriteAdapter);
            }
        });
    }

    private void checkEmptyTVFavorite(TVFavoriteAdapter tvFavoriteAdapter) {
        iconEmpty.setVisibility(tvFavoriteAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        txtEmpty.setVisibility(tvFavoriteAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
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
    protected void onResume() {
        super.onResume();
        if (movie != null){
            movieFavoriteAdapter.notifyDataSetChanged();
        }else if (tv != null){
            tvFavoriteAdapter.notifyDataSetChanged();
        }
    }

}
