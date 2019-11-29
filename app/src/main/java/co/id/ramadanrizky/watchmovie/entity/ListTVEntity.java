package co.id.ramadanrizky.watchmovie.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ListTVEntity implements Parcelable {
    int id;
    String tvTittle;
    String tvDate;
    String tvDescription;
    String tvPoster;

    public ListTVEntity(JSONObject object) {
        try {
            int id = object.getInt("id");
            String tittle = object.getString("name");
            String date = object.getString("first_air_date");
            String description = object.getString("overview");
            String poster_path = object.getString("poster_path");

            this.id = id;
            this.tvTittle = tittle;
            this.tvDate = date;
            this.tvDescription = description;
            this.tvPoster = poster_path;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ListTVEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTvPoster() {
        return tvPoster;
    }

    public void setTvPoster(String tvPoster) {
        this.tvPoster = tvPoster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.tvTittle);
        dest.writeString(this.tvDate);
        dest.writeString(this.tvDescription);
        dest.writeString(this.tvPoster);
    }

    protected ListTVEntity(Parcel in) {
        this.id = in.readInt();
        this.tvTittle = in.readString();
        this.tvDate = in.readString();
        this.tvDescription = in.readString();
        this.tvPoster = in.readString();
    }

    public static final Parcelable.Creator<ListTVEntity> CREATOR = new Parcelable.Creator<ListTVEntity>() {
        @Override
        public ListTVEntity createFromParcel(Parcel source) {
            return new ListTVEntity(source);
        }

        @Override
        public ListTVEntity[] newArray(int size) {
            return new ListTVEntity[size];
        }
    };
}
