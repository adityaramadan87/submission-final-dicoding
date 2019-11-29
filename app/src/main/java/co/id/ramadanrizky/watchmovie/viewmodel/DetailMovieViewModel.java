package co.id.ramadanrizky.watchmovie.viewmodel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import co.id.ramadanrizky.watchmovie.BuildConfig;
import co.id.ramadanrizky.watchmovie.entity.MovieEntity;
import cz.msebera.android.httpclient.Header;

public class DetailMovieViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<MovieEntity> movieEntityMutableLiveData = new MutableLiveData<>();

    public void getMovieDetails(int id, String language){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/"+id+"?api_key="+API_KEY+"&language="+language;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    MovieEntity movieEntity = new MovieEntity(jsonObject);
                    movieEntityMutableLiveData.postValue(movieEntity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<MovieEntity> getDetailMovie() {
        return movieEntityMutableLiveData;
    }
}
