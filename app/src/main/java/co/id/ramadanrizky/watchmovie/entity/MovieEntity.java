package co.id.ramadanrizky.watchmovie.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieEntity implements Parcelable {
    String movieTittle;
    String movieDate;
    String movieDescription;
    String moviePosterPath;
    String movieBackdropPath;
    double movieVoteAverage;

    public MovieEntity(JSONObject object) {
        try {
            String tittle = object.getString("title");
            String date = object.getString("release_date");
            String overview = object.getString("overview");
            String posterPath = object.getString("poster_path");
            String backdropPath = object.getString("backdrop_path");
            double voteAverage = object.getDouble("vote_average");

            this.movieTittle = tittle;
            this.movieDate = date;
            this.movieDescription = overview;
            this.moviePosterPath = posterPath;
            this.movieBackdropPath = backdropPath;
            this.movieVoteAverage = voteAverage;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public MovieEntity() {
    }

    public String getMovieTittle() {
        return movieTittle;
    }

    public void setMovieTittle(String movieTittle) {
        this.movieTittle = movieTittle;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public String getMovieBackdropPath() {
        return movieBackdropPath;
    }

    public void setMovieBackdropPath(String movieBackdropPath) {
        this.movieBackdropPath = movieBackdropPath;
    }

    public double getMovieVoteAverage() {
        return movieVoteAverage;
    }

    public void setMovieVoteAverage(double movieVoteAverage) {
        this.movieVoteAverage = movieVoteAverage;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movieTittle);
        dest.writeString(this.movieDate);
        dest.writeString(this.movieDescription);
        dest.writeString(this.moviePosterPath);
        dest.writeString(this.movieBackdropPath);
        dest.writeDouble(this.movieVoteAverage);
    }

    protected MovieEntity(Parcel in) {
        this.movieTittle = in.readString();
        this.movieDate = in.readString();
        this.movieDescription = in.readString();
        this.moviePosterPath = in.readString();
        this.movieBackdropPath = in.readString();
        this.movieVoteAverage = in.readDouble();
    }

    public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel source) {
            return new MovieEntity(source);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };
}
