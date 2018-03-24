package hackisu.hackisuspring18;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;


/**
 * Created by elizabethli on 3/24/18.
 */

public class DataAnalysisActivity extends Activity
{
    private Button back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analysis);

        final Spinner dynamicSpinner = (Spinner) findViewById(R.id.dynamic_spinner);
        ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("arraylist");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dynamicSpinner.setAdapter(adapter);

        back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent readFileIntent = new Intent(DataAnalysisActivity.this, ReadCSVActivity.class);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(0, 0);
                startActivity(readFileIntent);
            }

        });



    }
}
