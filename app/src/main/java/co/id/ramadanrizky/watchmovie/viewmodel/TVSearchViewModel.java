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
import co.id.ramadanrizky.watchmovie.entity.ListTVEntity;
import cz.msebera.android.httpclient.Header;

public class TVSearchViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<ListTVEntity>> tvMutable = new MutableLiveData<>();

    public void searchTV(String language, String nameTV){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ListTVEntity> listTVEntities = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language="+language+"&query="+nameTV;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int index = 0;index<jsonArray.length();index++){
                        JSONObject resultTV = jsonArray.getJSONObject(index);
                        ListTVEntity listTVEntity = new ListTVEntity(resultTV);
                        listTVEntities.add(listTVEntity);
                    }
                    tvMutable.postValue(listTVEntities);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<ListTVEntity>> getSearchListTV(){
        return tvMutable;
    }
}
