package co.id.ramadanrizky.watchmovie.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.watchmovie.entity.ListTVEntity;

import static android.provider.BaseColumns._ID;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_DATE;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_DESCRIPTION;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_ID;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_POSTER_PATH;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteMovieColumns.MOVIE_TITLE;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteTVColumns.TV_DATE;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteTVColumns.TV_DESCRIPTION;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteTVColumns.TV_ID;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteTVColumns.TV_POSTER_PATH;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteTVColumns.TV_TITLE;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.TABLE_FAVORITE_MOVIE;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.TABLE_FAVORITE_TVSHOW;

public class FavoriteHelper {
    private static final String DATABASE_TABLE_MOVIE = TABLE_FAVORITE_MOVIE;
    private static final String DATABASE_TABLE_TV = TABLE_FAVORITE_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavoriteHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen()){
            database.close();
        }
    }

    public Cursor queryAllFavoriteMovie(){
        return database.query(
                DATABASE_TABLE_MOVIE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
    }

    public ArrayList<ListMovieEntity> getAllFavoriteMovie() {
        Cursor cursor = database.query(
                DATABASE_TABLE_MOVIE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        return MappingHelper.mappingToMovie(cursor);
    }

    public ArrayList<ListTVEntity> getAllFavoriteTvShow() {
        Cursor cursor = database.query(
                DATABASE_TABLE_TV,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        return MappingHelper.mappingToTvShow(cursor);
    }

    public long insertMovie(ListMovieEntity movieEntity){
        ContentValues values = new ContentValues();
        values.put(MOVIE_ID, movieEntity.getId());
        values.put(MOVIE_TITLE, movieEntity.getMovieTittle());
        values.put(MOVIE_DATE, movieEntity.getMovieDate());
        values.put(MOVIE_DESCRIPTION, movieEntity.getMovieDescription());
        values.put(MOVIE_POSTER_PATH, movieEntity.getMoviePosterPath());
        return database.insert(DATABASE_TABLE_MOVIE, null, values);
    }

    public long insertTvShow(ListTVEntity tvEntity){
        ContentValues values = new ContentValues();
        values.put(TV_ID, tvEntity.getId());
        values.put(TV_TITLE, tvEntity.getTvTittle());
        values.put(TV_DATE, tvEntity.getTvDate());
        values.put(TV_DESCRIPTION, tvEntity.getTvDescription());
        values.put(TV_POSTER_PATH, tvEntity.getTvPoster());
        return database.insert(DATABASE_TABLE_TV, null, values);
    }

    public int deleteMovie(int id){
        return database.delete(DATABASE_TABLE_MOVIE, MOVIE_ID + " = '"+ id + "'", null);
    }

    public int deleteTvShow(int id){
        return database.delete(DATABASE_TABLE_TV, TV_ID + " = '"+ id + "'", null);
    }

}
