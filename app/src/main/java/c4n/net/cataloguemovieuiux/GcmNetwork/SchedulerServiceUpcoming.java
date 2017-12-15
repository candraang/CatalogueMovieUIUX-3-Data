package c4n.net.cataloguemovieuiux.GcmNetwork;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;

import c4n.net.cataloguemovieuiux.R;
import cz.msebera.android.httpclient.Header;

/**
 * Created by c4n on 27/11/2017.
 */

public class SchedulerServiceUpcoming extends GcmTaskService {
    final String TAG = SchedulerServiceUpcoming.class.getSimpleName();
    final String API_KEY = "fb7b874f72a7b4c167f50b9e1380e7e2";


    static String TAG_TASK_UPCOMING_LOG = "Upcoming_Catalog";

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_UPCOMING_LOG)){
            getMovieUpcoming();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    private void getMovieUpcoming(){
        Log.d("GetUpcomingCatalog", "Running");
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US" ;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {

                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    int id = responseObject.getInt("id");
                    String title = responseObject.getString("title");
                    String description = responseObject.getString("overview");
                    double movieRatet = responseObject.getDouble("vote_average");
                    String movieRate = new DecimalFormat("#.#").format(movieRatet);
                    String releaseDate = responseObject.getString("release_date");

                    description = description.length() <= 5  ? description.substring(0,5)+"...":description;

                    String message = description;
                    String movieRat = "Movie Rate : " + movieRate +" Release Date : "+releaseDate;
                    int notifId = 100;

                    showNotification(getApplicationContext(), title, message, movieRat, notifId);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("GetWeather", "Failed");
            }
        });
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        SchedulerTask mSchedulerTask = new SchedulerTask(this);
        mSchedulerTask.createPeriodicTask();
    }

    private void showNotification(Context context, String title, String message, String movieRate, int notifId){
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_forward_30_black_24dp)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}
