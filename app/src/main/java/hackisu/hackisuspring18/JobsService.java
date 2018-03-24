package hackisu.hackisuspring18;

import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Shivkarthi on 3/24/2018.
 */

public class JobsService {

    private static final String BASE_URL = "https://ml.googleapis.com";
    private static AsyncHttpClient client = new AsyncHttpClient();

        public JobsService() {

        }

        private static String getAbsoluteUrl(String relativeUrl) {
            return BASE_URL + relativeUrl;
        }

        public boolean uploadFile(Object obj, String name) {
            RequestParams params = new RequestParams();
            params.put("file", obj);
            params.put("key", name);
            params.put("success_action_status", 204);
            client.put(getAbsoluteUrl("/v1/temp/jobs"), params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

            return true;
        }

        public boolean startTraining() {
            RequestParams params = new RequestParams();
            params.add("parent", "hackisu-aiapp");
            client.post(getAbsoluteUrl("/v1/temp/jobs"), params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

            return true;
        }
}