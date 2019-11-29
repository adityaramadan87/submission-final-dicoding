package co.id.ramadanrizky.consumerwatchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.id.ramadanrizky.consumerwatchmovie.adapter.MovieFavoriteAdapter;
import co.id.ramadanrizky.consumerwatchmovie.database.DatabaseContract;
import co.id.ramadanrizky.consumerwatchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.consumerwatchmovie.helper.MappingHelper;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ListMovieEntity> listMovieEntities;
    private ImageView iconEmpty;
    private TextView txtEmpty;
    private RecyclerView rv_favorite;
    private MovieFavoriteAdapter movieFavoriteAdapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        iconEmpty = findViewById(R.id.icon_empty);
        txtEmpty = findViewById(R.id.txt_empty);
        rv_favorite = findViewById(R.id.rv_favorite);
        rv_favorite.setLayoutManager(new LinearLayoutManager(this));
        rv_favorite.setHasFixedSize(true);

        movieFavoriteAdapter = new MovieFavoriteAdapter(this);
        listMovieEntities = new ArrayList<>();
        cursor = this.getContentResolver().query(DatabaseContract.FavoriteMovieColumns.CONTENT_URI, null ,null,null,null);
        listMovieEntities = MappingHelper.mapMovie(cursor);
        movieFavoriteAdapter.setListMovieEntities(listMovieEntities);
        rv_favorite.setAdapter(movieFavoriteAdapter);
        checkEmptyListMovieFavorite();
        movieFavoriteAdapter.notifyDataSetChanged();
        cursor.close();
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

}
