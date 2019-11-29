package co.id.ramadanrizky.watchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.id.ramadanrizky.watchmovie.adapter.MovieAdapter;
import co.id.ramadanrizky.watchmovie.adapter.TVAdapter;
import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.watchmovie.entity.ListTVEntity;
import co.id.ramadanrizky.watchmovie.viewmodel.MovieSearchViewModel;
import co.id.ramadanrizky.watchmovie.viewmodel.TVSearchViewModel;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static String HEADER_MOVIE = "header_movie";
    public static String HEADER_TV = "header_tv";
    private RecyclerView rv_search;
    private MovieAdapter movieAdapter;
    private TVAdapter tvAdapter;;
    private SearchView searchView;
    private String language;
    private ImageView icon_empty;
    private TextView txt_empty;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);

        language = getString(R.string.api_language);
        rv_search = findViewById(R.id.rv_search);
        icon_empty = findViewById(R.id.icon_empty);
        txt_empty = findViewById(R.id.txt_empty);
        pb = findViewById(R.id.pb);
        searchView = (SearchView) findViewById(R.id.searchview);
        movieAdapter = new MovieAdapter(this);
        tvAdapter = new TVAdapter(this);

        rv_search.setLayoutManager(new LinearLayoutManager(this));
        rv_search.setHasFixedSize(true);

        data();
    }

    private void data() {
        String movie = getIntent().getStringExtra(HEADER_MOVIE);
        String tv = getIntent().getStringExtra(HEADER_TV);

        if (movie != null){
            getSupportActionBar().setTitle(movie);
            checkAdapterMovie();
            rv_search.setAdapter(movieAdapter);
            searchView.setQueryHint(getString(R.string.tab_movie));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    pb.setVisibility(View.VISIBLE);
                    visibility();
                    searchMovieList();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }else if (tv != null){
            getSupportActionBar().setTitle(tv);
            checkAdapterTV();
            rv_search.setAdapter(tvAdapter);
            searchView.setQueryHint(getString(R.string.tab_tv));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    pb.setVisibility(View.VISIBLE);
                    visibility();
                    searchTVlist();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }

    private void checkAdapterTV() {
        tvAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkEmptyTV(tvAdapter);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkEmptyTV(tvAdapter);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkEmptyTV(tvAdapter);
            }
        });
    }

    private void checkEmptyTV(TVAdapter tvAdapter) {
        icon_empty.setVisibility(tvAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        txt_empty.setVisibility(tvAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private void checkAdapterMovie() {
        movieAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkEmptyMovie(movieAdapter);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkEmptyMovie(movieAdapter);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkEmptyMovie(movieAdapter);
            }
        });
    }

    private void checkEmptyMovie(MovieAdapter movieAdapter) {
        icon_empty.setVisibility(movieAdapter.getItemCount() == 0 || movieAdapter.getEntListMovie() == null ? View.VISIBLE : View.GONE);
        txt_empty.setVisibility(movieAdapter.getItemCount() == 0 || movieAdapter.getEntListMovie() == null ? View.VISIBLE : View.GONE);

    }

    private void visibility(){
        icon_empty.setVisibility(View.GONE);
        txt_empty.setVisibility(View.GONE);
    }

    private void searchTVlist() {
        String tvName = searchView.getQuery().toString();

        TVSearchViewModel tvSearchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVSearchViewModel.class);
        tvSearchViewModel.searchTV(language, tvName);

        tvSearchViewModel.getSearchListTV().observe(this, new Observer<ArrayList<ListTVEntity>>() {
            @Override
            public void onChanged(ArrayList<ListTVEntity> listTVEntities) {
                if (listTVEntities != null){
                    tvAdapter.setListTVEntities(listTVEntities);
                    pb.setVisibility(View.GONE);
                }
            }
        });
    }

    private void searchMovieList() {
        String movieName = searchView.getQuery().toString();

        MovieSearchViewModel movieSearchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieSearchViewModel.class);
        movieSearchViewModel.searchMovie(language, movieName);

        movieSearchViewModel.getSearchListMovie().observe(this, new Observer<ArrayList<ListMovieEntity>>() {
            @Override
            public void onChanged(ArrayList<ListMovieEntity> listMovieEntities) {
                if (listMovieEntities != null){
                    movieAdapter.setEntMovie(listMovieEntities);
                    pb.setVisibility(View.GONE);
                }
            }
        });
    }
}
