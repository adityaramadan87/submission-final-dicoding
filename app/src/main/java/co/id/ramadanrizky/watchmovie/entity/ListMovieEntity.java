package co.id.ramadanrizky.watchmovie.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ListMovieEntity implements Parcelable {
    int id;
    String movieTittle;
    String movieDate;
    String movieDescription;
    String moviePosterPath;

    public ListMovieEntity(JSONObject object) {
        try {
            int id = object.getInt("id");
            String tittle = object.getString("title");
            String date = object.getString("release_date");
            String overview = object.getString("overview");
            String posterPath = object.getString("poster_path");

            this.id = id;
            this.movieTittle = tittle;
            this.movieDate = date;
            this.movieDescription = overview;
            this.moviePosterPath = posterPath;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ListMovieEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.movieTittle);
        dest.writeString(this.movieDate);
        dest.writeString(this.movieDescription);
        dest.writeString(this.moviePosterPath);
    }

    protected ListMovieEntity(Parcel in) {
        this.id = in.readInt();
        this.movieTittle = in.readString();
        this.movieDate = in.readString();
        this.movieDescription = in.readString();
        this.moviePosterPath = in.readString();
    }

    public static final Creator<ListMovieEntity> CREATOR = new Creator<ListMovieEntity>() {
        @Override
        public ListMovieEntity createFromParcel(Parcel source) {
            return new ListMovieEntity(source);
        }

        @Override
        public ListMovieEntity[] newArray(int size) {
            return new ListMovieEntity[size];
        }
    };
}
