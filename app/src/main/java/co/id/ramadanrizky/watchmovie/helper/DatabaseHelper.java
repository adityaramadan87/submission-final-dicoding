package co.id.ramadanrizky.watchmovie.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import co.id.ramadanrizky.watchmovie.database.DatabaseContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "favoritedb";
    private static final int DATABASE_VERSION = 2;

    private static final String CREATE_TABLE_FAVORITE_MOVIE = String.format("CREATE TABLE %s"
                    +"(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                     " %s INTEGER NOT NULL,"+
                     " %s TEXT NOT NULL,"+
                     " %s TEXT NOT NULL,"+
                     " %s TEXT NOT NULL,"+
                     " %s TEXT NOT NULL)",
                    DatabaseContract.TABLE_FAVORITE_MOVIE,
                    DatabaseContract.FavoriteMovieColumns._ID,
                    DatabaseContract.FavoriteMovieColumns.MOVIE_ID,
                    DatabaseContract.FavoriteMovieColumns.MOVIE_TITLE,
                    DatabaseContract.FavoriteMovieColumns.MOVIE_DATE,
                    DatabaseContract.FavoriteMovieColumns.MOVIE_DESCRIPTION,
                    DatabaseContract.FavoriteMovieColumns.MOVIE_POSTER_PATH
    );

    private static final String CREATE_TABLE_FAVORITE_TVSHOW = String.format("CREATE TABLE %s"
                    +"(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    " %s INTEGER NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL)",
                    DatabaseContract.TABLE_FAVORITE_TVSHOW,
                    DatabaseContract.FavoriteMovieColumns._ID,
                    DatabaseContract.FavoriteTVColumns.TV_ID,
                    DatabaseContract.FavoriteTVColumns.TV_TITLE,
                    DatabaseContract.FavoriteTVColumns.TV_DATE,
                    DatabaseContract.FavoriteTVColumns.TV_DESCRIPTION,
                    DatabaseContract.FavoriteTVColumns.TV_POSTER_PATH
    );

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITE_MOVIE);
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITE_MOVIE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITE_TVSHOW);
        onCreate(sqLiteDatabase);
    }
}
