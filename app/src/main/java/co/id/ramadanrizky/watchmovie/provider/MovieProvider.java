package co.id.ramadanrizky.watchmovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import co.id.ramadanrizky.watchmovie.helper.FavoriteHelper;

import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.AUTHORITY;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.FavoriteMovieColumns.CONTENT_URI;
import static co.id.ramadanrizky.watchmovie.database.DatabaseContract.TABLE_FAVORITE_MOVIE;

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private FavoriteHelper favoriteHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_MOVIE, MOVIE);

        uriMatcher.addURI(AUTHORITY,
                TABLE_FAVORITE_MOVIE + "/#",
                MOVIE_ID);
    }

    public MovieProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();
        return true;
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case MOVIE:
                cursor = favoriteHelper.queryAllFavoriteMovie();
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int deleted;
        switch (uriMatcher.match(uri)){
            case MOVIE_ID:
                deleted = favoriteHelper.deleteMovie(Integer.parseInt(uri.getLastPathSegment()));
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
