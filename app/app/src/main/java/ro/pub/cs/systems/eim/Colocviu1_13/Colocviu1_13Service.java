package ro.pub.cs.systems.eim.Colocviu1_13;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Colocviu1_13Service  extends Service {

    private ProcessingThread processingThread = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.TAG, "onCreate() method was invoked");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Constants.TAG, "onBind() method was invoked");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(Constants.TAG, "onUnbind() method was invoked");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(Constants.TAG, "onRebind() method was invoked");
    }

    @Override
    public void onDestroy() {
        Log.d(Constants.TAG, "onDestroy() method was invoked");
        processingThread.stopThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.TAG, "onStartCommand() method was invoked");
        String value = intent.getStringExtra(Constants.SERVICE_KEY);
        processingThread = new ProcessingThread(this, value);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

}

