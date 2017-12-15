package c4n.net.cataloguemovieuiux.Loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import c4n.net.cataloguemovieuiux.Model.MovieItem;
import cz.msebera.android.httpclient.Header;

/**
 * Created by c4n on 18/11/2017.
 */

public class NowPlayAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {
    public ArrayList<MovieItem> nowPlayData;
    public boolean mHasResult = false;



    public NowPlayAsyncTaskLoader(final Context context, ArrayList<MovieItem> nowPlayData) {
        super(context);
        onForceLoad();
    }



    @Override
    protected void onStartLoading(){
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(nowPlayData);
    }

    @Override
    public void deliverResult(ArrayList<MovieItem> data){
        nowPlayData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
        if (mHasResult){
            onReleaseResources(nowPlayData);
            nowPlayData = null;
            mHasResult = false;
        }
    }

    private void onReleaseResources(ArrayList<MovieItem> data) {
    }

    public static final String API_KEY = "fb7b874f72a7b4c167f50b9e1380e7e2";


    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItem> movieItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US" ;


        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItem movieItems = new MovieItem(movie);
                        movieItemses.add(movieItems);
                    }
                    Log.d("REQUEST SUCCESS", "1");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("REQUEST FAILED", "1");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
            }
        });


        return movieItemses;
    }
}