package co.id.ramadanrizky.consumermovie.helper;

import android.database.Cursor;

import java.util.ArrayList;

import co.id.ramadanrizky.consumermovie.entity.ListMovieEntity;

import static co.id.ramadanrizky.consumermovie.contract.DatabaseContract.FavoriteMovieColumns.MOVIE_DATE;
import static co.id.ramadanrizky.consumermovie.contract.DatabaseContract.FavoriteMovieColumns.MOVIE_DESCRIPTION;
import static co.id.ramadanrizky.consumermovie.contract.DatabaseContract.FavoriteMovieColumns.MOVIE_ID;
import static co.id.ramadanrizky.consumermovie.contract.DatabaseContract.FavoriteMovieColumns.MOVIE_POSTER_PATH;
import static co.id.ramadanrizky.consumermovie.contract.DatabaseContract.FavoriteMovieColumns.MOVIE_TITLE;

public class MappingHelper {
    public static ArrayList<ListMovieEntity> mappingToMovie(Cursor cursor) {
        ArrayList<ListMovieEntity> listMovieEntities = new ArrayList<>();
        if (cursor.getCount() > 0){
            do {
                ListMovieEntity movieEntity = new ListMovieEntity();
                movieEntity.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                movieEntity.setMovieTittle(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
                movieEntity.setMovieDate(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DATE)));
                movieEntity.setMovieDescription(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DESCRIPTION)));
                movieEntity.setMoviePosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POSTER_PATH)));
                listMovieEntities.add(movieEntity);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return listMovieEntities;
    }
    public static ListMovieEntity mapToObject(Cursor cursor){
        cursor.moveToFirst();
        ListMovieEntity movieEntity = new ListMovieEntity();
        movieEntity.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
        movieEntity.setMovieTittle(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
        movieEntity.setMovieDate(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DATE)));
        movieEntity.setMovieDescription(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DESCRIPTION)));
        movieEntity.setMoviePosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POSTER_PATH)));
        return movieEntity;
    }
}
