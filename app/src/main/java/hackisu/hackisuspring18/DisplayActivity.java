package hackisu.hackisuspring18;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by elizabethli on 3/24/18.
 */

public class DisplayActivity extends Activity
{

    private Button back;
    private TextView displayOutput;
    private TextView displayInput;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        displayOutput = (TextView) findViewById(R.id.output);
        displayInput = (TextView) findViewById(R.id.input);
        back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent readFileIntent = new Intent(DisplayActivity.this, MainActivity.class);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(0, 0);
                startActivity(readFileIntent);
            }

        });



    }
}
