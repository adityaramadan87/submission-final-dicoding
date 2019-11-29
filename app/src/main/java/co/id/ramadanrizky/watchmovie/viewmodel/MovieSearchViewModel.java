package co.id.ramadanrizky.watchmovie.viewmodel;

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
import co.id.ramadanrizky.watchmovie.entity.ListMovieEntity;
import cz.msebera.android.httpclient.Header;

public class MovieSearchViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<ListMovieEntity>> movieMutable = new MutableLiveData<>();

    public void searchMovie(String language, String nameMovie){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ListMovieEntity> listMovieEntities = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language="+language+"&query="+nameMovie;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int index = 0;index<jsonArray.length();index++){
                        JSONObject resultMovie = jsonArray.getJSONObject(index);
                        ListMovieEntity listMovieEntity = new ListMovieEntity(resultMovie);
                        listMovieEntities.add(listMovieEntity);
                    }
                    movieMutable.postValue(listMovieEntities);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<ListMovieEntity>> getSearchListMovie(){
        return movieMutable;
    }
}
