package hackisu.hackisuspring18;

/**
 * Created by elizabethli on 3/23/18.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class ReadCSVActivity extends Activity
{
    private ListView listView;
    private ItemArrayAdapter itemArrayAdapter;
    private TextView title;
    private Button home;
    private Button dataAnalysis;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_cvs);
        title = (TextView) findViewById(R.id.title);

        listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        InputStream inputStream = getResources().openRawResource(R.raw.hotel);
        CSVFile csvFile = new CSVFile(inputStream);
        ArrayList<String> scoreList = csvFile.read();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.checkbox_layout, R.id.checkbox1,scoreList);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(adapter);
        listView.onRestoreInstanceState(state);

        final ArrayList<String> selectedItems = new ArrayList<String>();

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = ((TextView)view).getText().toString();
                if(selectedItems.contains(selectedItem))
                {
                    selectedItems.remove(selectedItem);
                }
                else
                {
                    selectedItems.add(selectedItem);

                }
            }
        });



        home = (Button) findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent readFileIntent = new Intent(ReadCSVActivity.this, MainActivity.class);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(0, 0);
                startActivity(readFileIntent);
            }

        });

        dataAnalysis = (Button) findViewById(R.id.dataAnalysis);
        dataAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent readFileIntent = new Intent(ReadCSVActivity.this, DataAnalysisActivity.class);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                readFileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                readFileIntent.putExtra("arraylist", selectedItems);
                overridePendingTransition(0, 0);
                startActivity(readFileIntent);
            }

        });


    }

}
