package co.id.ramadanrizky.consumerwatchmovie.helper;

import android.database.Cursor;

import java.util.ArrayList;


import co.id.ramadanrizky.consumerwatchmovie.entity.ListMovieEntity;

import static co.id.ramadanrizky.consumerwatchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_DATE;
import static co.id.ramadanrizky.consumerwatchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_ID;
import static co.id.ramadanrizky.consumerwatchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_TITLE;
import static co.id.ramadanrizky.consumerwatchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_DATE;
import static co.id.ramadanrizky.consumerwatchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_DESCRIPTION;
import static co.id.ramadanrizky.consumerwatchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_ID;
import static co.id.ramadanrizky.consumerwatchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_POSTER_PATH;
import static co.id.ramadanrizky.consumerwatchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_TITLE;

public class MappingHelper {

    public static ArrayList<ListMovieEntity> mapMovie(Cursor cursor) {
        ArrayList<ListMovieEntity> listMovieEntities = new ArrayList<>();
        while (cursor.moveToNext()){
            ListMovieEntity movieEntity = new ListMovieEntity();
            movieEntity.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
            movieEntity.setMovieTittle(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
            movieEntity.setMovieDate(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DATE)));
            movieEntity.setMovieDescription(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DESCRIPTION)));
            movieEntity.setMoviePosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POSTER_PATH)));
            listMovieEntities.add(movieEntity);
        }
        cursor.close();
        return listMovieEntities;
    }
}
