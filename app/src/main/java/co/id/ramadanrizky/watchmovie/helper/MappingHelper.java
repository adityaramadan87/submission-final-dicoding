package co.id.ramadanrizky.watchmovie.helper;

import android.database.Cursor;

import java.util.ArrayList;

import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.watchmovie.entity.ListTVEntity;

import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_ID;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_TITLE;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_DATE;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_DESCRIPTION;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_POSTER_PATH;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteTVColumns.TV_DATE;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteTVColumns.TV_DESCRIPTION;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteTVColumns.TV_ID;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteTVColumns.TV_POSTER_PATH;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteTVColumns.TV_TITLE;

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
        return listMovieEntities;
    }

    public static ArrayList<ListTVEntity> mappingToTvShow(Cursor cursor) {
        ArrayList<ListTVEntity> listTVEntities = new ArrayList<>();
        if (cursor.getCount() > 0){
            do {
                ListTVEntity tvEntity = new ListTVEntity();
                tvEntity.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TV_ID)));
                tvEntity.setTvTittle(cursor.getString(cursor.getColumnIndexOrThrow(TV_TITLE)));
                tvEntity.setTvDate(cursor.getString(cursor.getColumnIndexOrThrow(TV_DATE)));
                tvEntity.setTvDescription(cursor.getString(cursor.getColumnIndexOrThrow(TV_DESCRIPTION)));
                tvEntity.setTvPoster(cursor.getString(cursor.getColumnIndexOrThrow(TV_POSTER_PATH)));
                listTVEntities.add(tvEntity);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return listTVEntities;
    }
}
