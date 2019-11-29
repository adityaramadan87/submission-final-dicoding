package co.id.ramadanrizky.consumermovie.contract;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_FAVORITE_MOVIE = "favoritemovie";
    public static final String AUTHORITY =  "co.id.ramadanrizky.watchmovie.provider.MovieProvider";
    private static final String SCHEME = "content";

    public static final class FavoriteMovieColumns implements BaseColumns {
        public static String MOVIE_ID = "movie_id";
        public static String MOVIE_TITLE = "movie_title";
        public static String MOVIE_DATE = "movie_date";
        public static String MOVIE_DESCRIPTION = "movie_description";
        public static String MOVIE_POSTER_PATH = "movie_poster_path";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE_MOVIE)
                .build();
    }
}
