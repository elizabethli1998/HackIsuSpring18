package hackisu.hackisuspring18;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.AdapterView;
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
    private Button display;
    private TextView textView1;
    private TextView textView2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analysis);


        final ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("arraylist");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        final Spinner dynamicSpinner = (Spinner) findViewById(R.id.dynamic_spinner);
        textView1 = (TextView) findViewById(R.id.textView1);
        dynamicSpinner.setAdapter(adapter);
        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        String s1 = String.valueOf(list.get(position));
                        textView1.setText(s1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}

            });

        final Spinner dynamicSpinner1 = (Spinner) findViewById(R.id.dynamic_spinner2);
        textView2 = (TextView) findViewById(R.id.textView2);
        dynamicSpinner1.setAdapter(adapter);

        dynamicSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String s1 = String.valueOf(list.get(position));
                textView2.setText(s1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        });

        back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent readFileIntent = new Intent(DataAnalysisActivity.this, MainActivity.class);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(0, 0);
                startActivity(readFileIntent);
            }

        });

        display = (Button) findViewById(R.id.display);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent readFileIntent = new Intent(DataAnalysisActivity.this, DisplayActivity.class);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(0, 0);
                startActivity(readFileIntent);
            }

        });



    }

}


