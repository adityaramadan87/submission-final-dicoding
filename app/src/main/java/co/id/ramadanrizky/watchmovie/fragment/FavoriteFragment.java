package co.id.ramadanrizky.watchmovie.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import co.id.ramadanrizky.watchmovie.FavoriteActivity;
import co.id.ramadanrizky.watchmovie.R;
import co.id.ramadanrizky.watchmovie.helper.FavoriteHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements View.OnClickListener {

    private LinearLayout favorite_movie, favorite_tv;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        favorite_movie = view.findViewById(R.id.linear_favorite_movie);
        favorite_tv = view.findViewById(R.id.linear_favorite_tv);

        favorite_movie.setOnClickListener(this);
        favorite_tv.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int btn = view.getId();
        switch (btn){
            case R.id.linear_favorite_movie:
                Intent intentMovie = new Intent(getContext(), FavoriteActivity.class);
                intentMovie.putExtra(FavoriteActivity.HEADER_MOVIE, getString(R.string.tab_movie));
                startActivity(intentMovie);
                break;
            case R.id.linear_favorite_tv:
                Intent intentTV = new Intent(getContext(), FavoriteActivity.class);
                intentTV.putExtra(FavoriteActivity.HEADER_TV, getString(R.string.tab_tv));
                startActivity(intentTV);
                break;
        }
    }
}
