package co.id.ramadanrizky.consumerwatchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import co.id.ramadanrizky.consumerwatchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.consumerwatchmovie.entity.MovieEntity;
import co.id.ramadanrizky.consumerwatchmovie.viewmodel.DetailMovieViewModel;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE = "movie";
    private TextView txt_detail_title, txt_detail_date, txt_detail_desc;
    private ImageView img_detail, img_poster_path;
    private DetailMovieViewModel detailMovieViewModel;
    private ProgressBar pb;
    private SimpleRatingBar ratingBar;
    private ConstraintLayout content;
    private ListMovieEntity listMovieEntity;

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
        pb = findViewById(R.id.pb);
        content = findViewById(R.id.constraintContent);
        ratingBar = findViewById(R.id.rateBar);


        content.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);

        if (getIntent().getParcelableExtra(MOVIE) != null) {
            listMovieEntity = getIntent().getParcelableExtra(MOVIE);
            setDataMovieDetail(listMovieEntity);

        }
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
}
