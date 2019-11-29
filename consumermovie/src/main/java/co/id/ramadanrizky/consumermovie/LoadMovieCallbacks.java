package co.id.ramadanrizky.consumermovie;

import java.util.ArrayList;

import co.id.ramadanrizky.consumermovie.entity.ListMovieEntity;

public interface LoadMovieCallbacks {
    void preExecute();
    void postExecute(ArrayList<ListMovieEntity> listMovieEntities);
}
