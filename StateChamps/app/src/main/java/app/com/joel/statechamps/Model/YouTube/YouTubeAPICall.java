package app.com.joel.statechamps.Model.YouTube;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Joel on 4/28/16.
 */
public class YouTubeAPICall extends AsyncTask <Void, Void, JSONArray> {

    public static final int METHOD_GET = 0;
    public static final int METHOD_POST = 1;

    private String endpoint;
    private OnResponse handler;
    private int method;
    private Context context;


    public YouTubeAPICall(String url, OnResponse handler) {
        this.endpoint = url;
        this.handler = handler;
        this.method = METHOD_GET;
    }

    @Override
    protected void onPreExecute() {
//        this.handler.onPreStart();
    }

    @Override
    public JSONArray doInBackground(Void... params) {
        JSONArray response = null;
        switch (this.method) {
            case METHOD_GET:
                response = this.getRequest();
                break;
            case METHOD_POST:
                break;
        }
        return response;
    }

    @Override
    protected void onPostExecute(JSONArray response) {
//        this.handler.onResponse(response);
    }

    private JSONArray getRequest() {
        JSONArray result = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(this.endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int status = connection.getResponseCode();
            Log.d("Connection", "STATUS: " + status);

            if (status == 200){
                InputStream stream = connection.getInputStream();
                Scanner scanner = new Scanner(stream, "UTF-8").useDelimiter("\\A");
                String stringResult = scanner.hasNext() ? scanner.next() : "";
                System.out.println(stringResult);
                scanner.close();
                result = new JSONArray(stringResult);
            } else {
                Log.d("** STATUS CODE **", "did not get 200");
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

