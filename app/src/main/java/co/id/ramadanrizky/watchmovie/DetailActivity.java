package co.id.ramadanrizky.watchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import co.id.ramadanrizky.watchmovie.database.DatabaseContract;
import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.watchmovie.entity.ListTVEntity;
import co.id.ramadanrizky.watchmovie.entity.MovieEntity;
import co.id.ramadanrizky.watchmovie.entity.TVEntity;
import co.id.ramadanrizky.watchmovie.helper.DatabaseHelper;
import co.id.ramadanrizky.watchmovie.helper.FavoriteHelper;
import co.id.ramadanrizky.watchmovie.viewmodel.DetailMovieViewModel;
import co.id.ramadanrizky.watchmovie.viewmodel.DetailTVViewModel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.List;

import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.TABLE_FAVORITE_MOVIE;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.TABLE_FAVORITE_TVSHOW;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE = "movie";
    public static final String TVSHOW = "tv";
    private TextView txt_detail_title, txt_detail_date, txt_detail_desc;
    private ImageView img_detail, img_poster_path;
    private Switch btn_favorite;
    private DetailMovieViewModel detailMovieViewModel;
    private DetailTVViewModel detailTVViewModel;
    private ProgressBar pb;
    private SimpleRatingBar ratingBar;
    private ConstraintLayout content;
    private ListMovieEntity listMovieEntity;
    private ListTVEntity listTVEntity;
    private FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        txt_detail_title = findViewById(R.id.detail_title);
        txt_detail_date = findViewById(R.id.detail_date);
        txt_detail_desc = findViewById(R.id.detail_description);
        img_detail = findViewById(R.id.img_detail_movie);
        img_poster_path = findViewById(R.id.imgPosterPath);
        btn_favorite = findViewById(R.id.btn_favorite);
        favoriteHelper = new FavoriteHelper(this);
        pb = findViewById(R.id.pb);
        content = findViewById(R.id.constraintContent);
        ratingBar = findViewById(R.id.rateBar);


        content.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);

        if (getIntent().getParcelableExtra(MOVIE) != null){
            listMovieEntity = getIntent().getParcelableExtra(MOVIE);
            setDataMovieDetail(listMovieEntity);

            listenerMovie();

        }else if (getIntent().getParcelableExtra(TVSHOW) != null){
            listTVEntity = getIntent().getParcelableExtra(TVSHOW);
            setDataTvDetail(listTVEntity);

            listenerTV();
        }


    }

    private void listenerTV() {
        if (CheckTV(listTVEntity.getTvTittle())){
            btn_favorite.setChecked(true);

            btn_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!b){
                        int id = listTVEntity.getId();
                        FavoriteHelper favoriteHelper = new FavoriteHelper(DetailActivity.this);
                        favoriteHelper.open();
                        favoriteHelper.deleteTvShow(id);
                        btn_favorite.setChecked(false);
                        Toast.makeText(DetailActivity.this, "Delete "+listTVEntity.getTvTittle()+" from favorite", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            btn_favorite.setChecked(false);

            btn_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        int id = listTVEntity.getId();
                        String title = listTVEntity.getTvTittle();
                        String date = listTVEntity.getTvDate();
                        String description = listTVEntity.getTvDescription();
                        String poster_path = listTVEntity.getTvPoster();

                        listTVEntity.setId(id);
                        listTVEntity.setTvTittle(title);
                        listTVEntity.setTvDate(date);
                        listTVEntity.setTvDescription(description);
                        listTVEntity.setTvPoster(poster_path);

                        favoriteHelper.open();
                        favoriteHelper.insertTvShow(listTVEntity);
                        btn_favorite.setChecked(true);
                        Toast.makeText(DetailActivity.this, "Success add Favorite "+title, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void listenerMovie() {
        if (CheckMovie(listMovieEntity.getMovieTittle())){
            btn_favorite.setChecked(true);

            btn_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!b){
                        btn_favorite.setChecked(false);
                        int id = listMovieEntity.getId();
                        favoriteHelper.open();
                        favoriteHelper.deleteMovie(id);

                        Toast.makeText(DetailActivity.this, "Delete "+listMovieEntity.getMovieTittle()+" from favorite", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            btn_favorite.setChecked(false);
            btn_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        btn_favorite.setChecked(true);
                        int id = listMovieEntity.getId();
                        String title = listMovieEntity.getMovieTittle();
                        String date = listMovieEntity.getMovieDate();
                        String description = listMovieEntity.getMovieDescription();
                        String poster_path = listMovieEntity.getMoviePosterPath();

                        listMovieEntity.setId(id);
                        listMovieEntity.setMovieTittle(title);
                        listMovieEntity.setMovieDate(date);
                        listMovieEntity.setMovieDescription(description);
                        listMovieEntity.setMoviePosterPath(poster_path);

                        favoriteHelper.open();
                        favoriteHelper.insertMovie(listMovieEntity);

                        Toast.makeText(DetailActivity.this, "Success add Favorite "+title, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setDataTvDetail(ListTVEntity tvEntity) {
        detailTVViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailTVViewModel.class);
        detailTVViewModel.getDetailsTv(tvEntity.getId(), getResources().getString(R.string.api_language));

        detailTVViewModel.getTvDetail().observe(this, new Observer<TVEntity>() {
            @Override
            public void onChanged(TVEntity tvEntity) {
                if (tvEntity != null){
                    txt_detail_title.setText(tvEntity.getTvTittle());
                    txt_detail_date.setText(tvEntity.getTvDate());
                    txt_detail_desc.setText(tvEntity.getTvDescription());
                    ratingBar.setRating((float) (tvEntity.getTvVoteAverage()/2));
                    Glide.with(DetailActivity.this)
                            .load("https://image.tmdb.org/t/p/original/"+tvEntity.getTvPosterPath())
                            .into(img_poster_path);
                    Glide.with(DetailActivity.this)
                            .load("https://image.tmdb.org/t/p/original/"+tvEntity.getTvBackdropPath())
                            .into(img_detail);
                    pb.setVisibility(View.GONE);
                    content.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setDataMovieDetail(ListMovieEntity movieEntity) {
        detailMovieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailMovieViewModel.class);
        detailMovieViewModel.getMovieDetails(movieEntity.getId(), getResources().getString(R.string.api_language));

        detailMovieViewModel.getDetailMovie().observe(this, new Observer<MovieEntity>() {
            @Override
            public void onChanged(MovieEntity movieEntity) {
                if (movieEntity != null){
                    txt_detail_title.setText(movieEntity.getMovieTittle());
                    txt_detail_date.setText(movieEntity.getMovieDate());
                    txt_detail_desc.setText(movieEntity.getMovieDescription());
                    ratingBar.setRating((float) (movieEntity.getMovieVoteAverage()/2));
                    Glide.with(DetailActivity.this)
                            .load("https://image.tmdb.org/t/p/original/"+movieEntity.getMoviePosterPath())
                            .into(img_poster_path);
                    Glide.with(DetailActivity.this)
                            .load("https://image.tmdb.org/t/p/original/"+movieEntity.getMovieBackdropPath())
                            .into(img_detail);
                    pb.setVisibility(View.GONE);
                    content.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean CheckTV(String tvTittle) {
        boolean checked;
        String select = DatabaseContract.FavoriteTVColumns.TV_TITLE + " = ?";
        String zone = "1";
        String[] selectArgument = {tvTittle};
        favoriteHelper.open();
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query(TABLE_FAVORITE_TVSHOW,null, select, selectArgument, null, null, null, zone);
        checked = (cursor.getCount() > 0);
        cursor.close();
        return checked;
    }

    private boolean CheckMovie(String movieTittle) {
        boolean checked;
        String select = DatabaseContract.FavoriteMovieColumns.MOVIE_TITLE + " = ?";
        String zone = "1";
        String[] selectArgument = {movieTittle};
        favoriteHelper.open();
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query(TABLE_FAVORITE_MOVIE,null, select, selectArgument, null, null, null, zone);
        checked = (cursor.getCount() > 0);
        cursor.close();
        return checked;
    }
}
