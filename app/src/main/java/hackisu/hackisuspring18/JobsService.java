package hackisu.hackisuspring18;

import android.os.Environment;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;

/**
 * Created by Shivkarthi on 3/24/2018.
 */

public class JobsService {
    private static String test = "TEST";

    public JobsService() {

    }

    public StringRequest test(final Context cont) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://www.deepdetect.com/api/info",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        test = "success";
                        Toast.makeText(cont, "fail", Toast.LENGTH_LONG);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        test = "fail";
                        Toast.makeText(cont, error.getMessage(), Toast.LENGTH_LONG);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer 13bf1a1220da9d4cf89fcb3ce6a64aa242cc54af");
                return params;
            }
        };

        return stringRequest;
    }
    public StringRequest deleteBucket(final Context cont) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                "https://storage.googleapis.com/aiappbucket",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        test = "success";
                        VolleyLog.e(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        test = "fail";
                        Toast.makeText(cont, error.getMessage(), Toast.LENGTH_LONG);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer 13bf1a1220da9d4cf89fcb3ce6a64aa242cc54af");
                return params;
            }
        };

            return stringRequest;
    }

    public StringRequest uploadFile(String name) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), name);
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                "hackisuaiapp.s3.amazonaws.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        test = "success";
                        VolleyLog.e(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        test = "fail";
                        VolleyLog.e(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("x-amz-id-2", "JuKZqmXuiwFeDQxhD7M8KtsKobSzWA1QEjLbTMTagkKdBX2z7Il/jGhDeJ3j6s80");
                params.put("x-amz-request-id", "32FE2CEB32F5EE25");
                params.put("Connection", "close");
                params.put("Server", "AmazonS3");
                return params;
            }
        };

        return stringRequest;
    }

}