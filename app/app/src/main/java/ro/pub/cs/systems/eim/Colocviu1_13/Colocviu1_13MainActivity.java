package ro.pub.cs.systems.eim.Colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Colocviu1_13MainActivity extends AppCompatActivity {

    private int noClicks = 0;
    StartedServiceBroadcastReceiver broadcastReceiver = new StartedServiceBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_13_main);

        intentFilter.addAction(Constants.SERVICE_ACTION);

        Button northButton = (Button) findViewById(R.id.buttonNorth);
        northButton.setOnClickListener(buttonClickListener);

        Button southButton = (Button) findViewById(R.id.buttonSouth);
        southButton.setOnClickListener(buttonClickListener);

        Button eastButton = (Button) findViewById(R.id.buttonEast);
        eastButton.setOnClickListener(buttonClickListener);

        Button westButton = (Button) findViewById(R.id.buttonWest);
        westButton.setOnClickListener(buttonClickListener);

        Button secondaryActivityButton = (Button) findViewById(R.id.buttonNavigate);
        secondaryActivityButton.setOnClickListener(secondaryActivityButtonClickListener);
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, Colocviu1_13Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: exercise 8c - register the broadcast receiver with the corresponding intent filter
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        // TODO: exercise 8c - unregister the broadcast receiver
        unregisterReceiver(broadcastReceiver);

        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(Constants.NO_CLICK_KEY, noClicks);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        noClicks = savedInstanceState.getInt(Constants.NO_CLICK_KEY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Pressed Button " + intent.getStringExtra(Constants.SECONDARY_ACTIVITY_KEY), Toast.LENGTH_LONG).show();
        }
    }


    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            noClicks++;
            //Toast.makeText(getApplicationContext(), "No clicks: " + noClicks, Toast.LENGTH_SHORT).show();
            EditText points_edit_text = (EditText) findViewById(R.id.points_edit_text);
            String currentString = points_edit_text.getText().toString();

            String currentButtonName = ((Button) view).getText().toString();

            if (currentString.equals(""))
                points_edit_text.setText(String.format("%s", currentButtonName));
            else
                points_edit_text.setText(String.format("%s, %s", currentString, currentButtonName));

            if (noClicks == 4) {
                Intent intentStartService = new Intent(getApplicationContext(), Colocviu1_13Service.class);
                intentStartService.putExtra(Constants.SERVICE_KEY, points_edit_text.getText().toString());
                //intentStartService.setComponent(new ComponentName(getApplicationContext(), Colocviu1_13Service.class));
                startService(intentStartService);
            }

        }
    }

    private SecondaryActivityButtonClickListener secondaryActivityButtonClickListener = new SecondaryActivityButtonClickListener();

    private class SecondaryActivityButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            EditText points_edit_text = (EditText) findViewById(R.id.points_edit_text);
            String currentString = points_edit_text.getText().toString();

            if (points_edit_text.length() > 0) {
                Intent intent = new Intent("ro.pub.cs.systems.eim.Colocviu1_13.intent.action.Colocviu1_13SecondaryActivity");
                intent.putExtra(Constants.SECONDARY_ACTIVITY_KEY, currentString);
                startActivityForResult(intent, Constants.REQUEST_CODE);
            } else {
                Toast.makeText(getApplication(), getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
            }

            points_edit_text.setText("");
            noClicks = 0;
        }
    }
}
