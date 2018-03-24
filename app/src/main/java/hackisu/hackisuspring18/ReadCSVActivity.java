package hackisu.hackisuspring18;

/**
 * Created by elizabethli on 3/23/18.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class ReadCSVActivity extends Activity
{
    private ListView listView;
    private ItemArrayAdapter itemArrayAdapter;
    private TextView title;
    private Button home;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_cvs);
        title = (TextView) findViewById(R.id.title);
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

        listView = (ListView) findViewById(R.id.listView);
        itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);

        InputStream inputStream = getResources().openRawResource(R.raw.pass_12_13);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> scoreList = csvFile.read();

        for(String[] scoreData:scoreList )
        {
            itemArrayAdapter.add(scoreData);
        }

    }

}
