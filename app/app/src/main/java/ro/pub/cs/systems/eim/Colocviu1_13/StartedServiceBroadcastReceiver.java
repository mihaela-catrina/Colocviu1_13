package ro.pub.cs.systems.eim.Colocviu1_13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartedServiceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String data = null;
        if (Constants.SERVICE_ACTION.equals(action)) {
            data = intent.getStringExtra(Constants.SERVICE_KEY);
            Log.d(Constants.BROADCAST_RECEIVER_TAG, data);;
        }
    }
}


