package co.id.ramadanrizky.watchmovie.viewmodel;

import android.content.Context;
import android.content.res.Resources;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import co.id.ramadanrizky.watchmovie.BuildConfig;
import co.id.ramadanrizky.watchmovie.R;
import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {

    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<ListMovieEntity>> listMovieEntitys = new MutableLiveData<>();

    public void getMovie(String language){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ListMovieEntity> listMovieEntities = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language="+language+"&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int index=0;index<jsonArray.length();index++){
                        JSONObject movies = jsonArray.getJSONObject(index);
                        ListMovieEntity listMovieEntity = new ListMovieEntity(movies);
                        listMovieEntities.add(listMovieEntity);
                    }
                    listMovieEntitys.postValue(listMovieEntities);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<ListMovieEntity>> getListMovie(){
        return listMovieEntitys;
    }
}
