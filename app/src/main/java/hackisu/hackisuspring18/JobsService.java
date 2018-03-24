package hackisu.hackisuspring18;

import android.os.Environment;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.*;
import java.io.File;

/**
 * Created by Shivkarthi on 3/24/2018.
 */

public class JobsService {
    public JobsService() {

    }

    public void uploadFile(String name) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), name);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "aiappbucket.storage.googleapis.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        VolleyLog.v(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.v(error.getMessage());
                    }
                }
        );
    }
}