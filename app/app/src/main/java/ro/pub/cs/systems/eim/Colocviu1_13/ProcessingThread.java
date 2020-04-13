package ro.pub.cs.systems.eim.Colocviu1_13;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

class ProcessingThread  extends Thread {

    private Context context;
    private boolean isRunning = true;
    private String instruction = "";


    public ProcessingThread(Context context, String instruction) {
        this.context = context;
        this.instruction = instruction;
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(Constants.SERVICE_ACTION);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        broadcastIntent.putExtra(Constants.SERVICE_KEY, String.format("[%s] %s\n", sdf.format(new Timestamp(new Date().getTime())), instruction));
        context.sendBroadcast(broadcastIntent);
    }

    private void sleep() {
        try {
            Thread.sleep(Constants.SLEEP_TIME);
        } catch (InterruptedException interruptedException) {
            Log.e(Constants.TAG, interruptedException.getMessage());
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }

}
