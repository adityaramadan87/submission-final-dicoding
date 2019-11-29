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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.id.ramadanrizky.watchmovie.R;
import co.id.ramadanrizky.watchmovie.adapter.TVAdapter;
import co.id.ramadanrizky.watchmovie.entity.ListTVEntity;
import co.id.ramadanrizky.watchmovie.entity.TVEntity;
import co.id.ramadanrizky.watchmovie.viewmodel.TVViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment {

    private RecyclerView rv_tv;
    private TVAdapter tvAdapter;
    private TVViewModel tvViewModel;
    private ProgressBar pb;
    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        pb = view.findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        setRecyclerView(view);

        return view;
    }
    private void setRecyclerView(View view) {
        rv_tv = view.findViewById(R.id.rv_tv);
        tvAdapter = new TVAdapter(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_tv.setLayoutManager(layoutManager);

        String language = getResources().getString(R.string.api_language);
        tvViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVViewModel.class);
        tvViewModel.getvList(language);

        tvViewModel.getTv().observe(this, new Observer<ArrayList<ListTVEntity>>() {
            @Override
            public void onChanged(ArrayList<ListTVEntity> listTVEntities) {
                if (listTVEntities != null){
                    tvAdapter.setListTVEntities(listTVEntities);
                    pb.setVisibility(View.GONE);
                }
            }
        });

        rv_tv.setAdapter(tvAdapter);
    }

}
