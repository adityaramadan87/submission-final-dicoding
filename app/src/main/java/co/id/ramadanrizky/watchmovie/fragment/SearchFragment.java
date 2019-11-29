package co.id.ramadanrizky.watchmovie.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import co.id.ramadanrizky.watchmovie.R;
import co.id.ramadanrizky.watchmovie.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    private LinearLayout btn_search_movie, btn_search_tv;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        btn_search_movie = view.findViewById(R.id.linear_search_movie);
        btn_search_tv = view.findViewById(R.id.linear_search_tv);

        btn_search_movie.setOnClickListener(this);
        btn_search_tv.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int item = view.getId();
        switch (item){
            case R.id.linear_search_movie:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra(SearchActivity.HEADER_MOVIE, getString(R.string.tab_movie));
                startActivity(intent);
                break;
            case R.id.linear_search_tv:
                Intent tvIntent = new Intent(getContext(), SearchActivity.class);
                tvIntent.putExtra(SearchActivity.HEADER_TV, getString(R.string.tab_tv));
                startActivity(tvIntent);
                break;
        }
    }
}
