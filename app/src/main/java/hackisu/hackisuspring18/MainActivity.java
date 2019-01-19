package hackisu.hackisuspring18;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView title;
    private Button check;
    private Button check2;
    private Button check3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (TextView) findViewById(R.id.title);
        check = (Button) findViewById(R.id.checkBox1);
        check2 = (Button) findViewById(R.id.checkBox2);
        check3 = (Button) findViewById(R.id.checkBox3);


        check.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            JobsService js = new JobsService();
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(js.test(getApplicationContext()));
            Intent intent=new Intent(MainActivity.this, ReadCSVActivity.class);
            intent.putExtra("data", "pass");
            startActivity(intent);
        }
        });
        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ReadCSVActivity.class);
                intent.putExtra("data", "hotel");
                startActivity(intent);
            }
        });

        check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this, ReadCSVActivity.class);
                intent.putExtra("data", "lottery");
                startActivity(intent);
            }
        });


    }
}
