package c4n.net.cataloguemovieuiux.GcmNetwork;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;

/**
 * Created by c4n on 27/11/2017.
 */

public class SchedulerTask {
    private GcmNetworkManager mGcmNetworkManager;

    public SchedulerTask(Context context){
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        com.google.android.gms.gcm.Task periodicTask = new PeriodicTask.Builder()
                .setService(SchedulerServiceUpcoming.class)
                .setPeriod(60)
                .setFlex(10)
                .setTag(SchedulerServiceUpcoming.TAG_TASK_UPCOMING_LOG)
                .setPersisted(true)
                .build();

        mGcmNetworkManager.schedule(periodicTask);
    }

    public void cancelPeriodicTask(){
        if (mGcmNetworkManager != null){
            mGcmNetworkManager.cancelTask(SchedulerServiceUpcoming.TAG_TASK_UPCOMING_LOG, SchedulerServiceUpcoming.class);
        }
    }
}
