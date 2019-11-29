package co.id.ramadanrizky.watchmovie.fragment;


import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.id.ramadanrizky.watchmovie.R;
import co.id.ramadanrizky.watchmovie.adapter.MovieAdapter;
import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.watchmovie.entity.MovieEntity;
import co.id.ramadanrizky.watchmovie.viewmodel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView rv_movie;
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    private ProgressBar pb;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        pb = view.findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        setRecyclerView(view);

        return view;
    }

    private void setRecyclerView(View view) {
        rv_movie = view.findViewById(R.id.rv_movie);
        movieAdapter = new MovieAdapter(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_movie.setLayoutManager(layoutManager);

        String language = getResources().getString(R.string.api_language);
        movieViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.getMovie(language);

        movieViewModel.getListMovie().observe(this, new Observer<ArrayList<ListMovieEntity>>() {
            @Override
            public void onChanged(ArrayList<ListMovieEntity> listMovieEntities) {
                if (listMovieEntities != null){
                    movieAdapter.setEntMovie(listMovieEntities);
                    pb.setVisibility(View.GONE);
                }
            }
        });

        rv_movie.setAdapter(movieAdapter);

    }

}
