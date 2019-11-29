package co.id.ramadanrizky.watchmovie.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class TVEntity implements Parcelable {
    String tvTittle;
    String tvDate;
    String tvDescription;
    String tvPosterPath;
    String tvBackdropPath;
    double tvVoteAverage;

    public TVEntity(JSONObject object) {
        try {
            String title = object.getString("name");
            String date = object.getString("first_air_date");
            String overview = object.getString("overview");
            String posterpath = object.getString("poster_path");
            String backdroppath = object.getString("backdrop_path");
            double voteaverage = object.getDouble("vote_average");

            this.tvTittle = title;
            this.tvDate = date;
            this.tvDescription = overview;
            this.tvPosterPath = posterpath;
            this.tvBackdropPath = backdroppath;
            this.tvVoteAverage = voteaverage;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TVEntity() {
    }

    public String getTvTittle() {
        return tvTittle;
    }

    public void setTvTittle(String tvTittle) {
        this.tvTittle = tvTittle;
    }

    public String getTvDate() {
        return tvDate;
    }

    public void setTvDate(String tvDate) {
        this.tvDate = tvDate;
    }

    public String getTvDescription() {
        return tvDescription;
    }

    public void setTvDescription(String tvDescription) {
        this.tvDescription = tvDescription;
    }

    public String getTvPosterPath() {
        return tvPosterPath;
    }

    public void setTvPosterPath(String tvPosterPath) {
        this.tvPosterPath = tvPosterPath;
    }

    public String getTvBackdropPath() {
        return tvBackdropPath;
    }

    public void setTvBackdropPath(String tvBackdropPath) {
        this.tvBackdropPath = tvBackdropPath;
    }

    public double getTvVoteAverage() {
        return tvVoteAverage;
    }

    public void setTvVoteAverage(double tvVoteAverage) {
        this.tvVoteAverage = tvVoteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tvTittle);
        dest.writeString(this.tvDate);
        dest.writeString(this.tvDescription);
        dest.writeString(this.tvPosterPath);
        dest.writeString(this.tvBackdropPath);
        dest.writeDouble(this.tvVoteAverage);
    }

    protected TVEntity(Parcel in) {
        this.tvTittle = in.readString();
        this.tvDate = in.readString();
        this.tvDescription = in.readString();
        this.tvPosterPath = in.readString();
        this.tvBackdropPath = in.readString();
        this.tvVoteAverage = in.readDouble();
    }

    public static final Creator<TVEntity> CREATOR = new Creator<TVEntity>() {
        @Override
        public TVEntity createFromParcel(Parcel source) {
            return new TVEntity(source);
        }

        @Override
        public TVEntity[] newArray(int size) {
            return new TVEntity[size];
        }
    };
}
