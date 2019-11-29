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
import co.id.ramadanrizky.watchmovie.entity.ListTVEntity;
import cz.msebera.android.httpclient.Header;

public class TVViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<ListTVEntity>> listTvEntitiesMutable = new MutableLiveData<>();

    public void getvList(String language){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ListTVEntity> listTVEntities = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/popular?api_key="+API_KEY+"&language="+language+"&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int index=0;index<jsonArray.length();index++){
                        JSONObject tvs = jsonArray.getJSONObject(index);
                        ListTVEntity tvEntity = new ListTVEntity(tvs);
                        listTVEntities.add(tvEntity);
                    }
                    listTvEntitiesMutable.postValue(listTVEntities);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<ListTVEntity>> getTv(){
        return listTvEntitiesMutable;
    }

}
