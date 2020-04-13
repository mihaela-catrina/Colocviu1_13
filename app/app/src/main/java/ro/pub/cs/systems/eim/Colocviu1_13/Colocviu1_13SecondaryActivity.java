package ro.pub.cs.systems.eim.Colocviu1_13;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Colocviu1_13SecondaryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_​colocviu1_13​_secondary);

        Intent intent = getIntent();
        if (intent != null) {
            String instructions = intent.getStringExtra(Constants.SECONDARY_ACTIVITY_KEY);
            EditText instructionsEditText = (EditText) findViewById(R.id.instructions_edit_text);
            instructionsEditText.setText(instructions);
        } else {
                Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
        }

        Button register = (Button) findViewById(R.id.buttonRegister);
        register.setOnClickListener(buttonClickListener);

        Button cancel = (Button) findViewById(R.id.buttonCancel);
        cancel.setOnClickListener(buttonClickListener);
    }


    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent resultIntent = new Intent();
            String currentButtonName = ((Button) view).getText().toString();
            resultIntent.putExtra(Constants.SECONDARY_ACTIVITY_KEY, currentButtonName);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }
}
