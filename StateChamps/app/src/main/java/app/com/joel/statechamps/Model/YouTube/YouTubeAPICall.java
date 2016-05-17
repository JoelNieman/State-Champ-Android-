package app.com.joel.statechamps.Model.YouTube;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import app.com.joel.statechamps.APIKeys;

/**
 * Created by Joel on 4/28/16.
 */
public class YouTubeAPICall extends AsyncTask <Void, Void, JSONArray> {

    public static final int METHOD_GET = 0;
    public static final int METHOD_POST = 1;

    private String endpoint;
    private APIOnResponseDelegate handler;
    private int method;
    private ArrayList<SCVideo> sCVideoStore;
    private JSONObject result;
    private JSONArray resultJSONArray;
    private ArrayList<SCVideo> videoArray;
    private SCVideo sCVideo;
    private JSONObject sCVideoJSONObject;
    private JSONArray sCVideoJSONArray;


    private String playlistID;
    private int numberOfVideos;
    private String apiKey;
    private APIKeys keys = null;



    public YouTubeAPICall(String endpoint, APIOnResponseDelegate handler) {

        if (keys == null) keys = new APIKeys();
        apiKey = keys.getYouTubeApiKey();
        this.endpoint = endpoint;
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
//        this.handler.onShowVideoResponse(response);
    }

    private JSONArray getRequest() {
        resultJSONArray = null;
        result = null;
        HttpURLConnection connection = null;

        try {

            URL url = new URL(this.endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int status = connection.getResponseCode();
            Log.d("Connection", "STATUS: " + status);

            if (status == 200){
                InputStream stream = connection.getInputStream();
                Scanner scanner = new Scanner(stream, "UTF-8").useDelimiter("\\A");
                String stringResult = scanner.hasNext() ? scanner.next() : "";
                System.out.println(stringResult);
                scanner.close();

                result = new JSONObject(stringResult);
                sCVideoStore = parseJSON(result);

            } else {
                Log.d("** STATUS CODE **", "did not get 200");
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultJSONArray;
    }


    protected ArrayList<SCVideo> parseJSON(JSONObject result) {

        sCVideo = new SCVideo();
        videoArray = new ArrayList<>();

        try {
            sCVideoJSONArray = result.getJSONArray("items");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < sCVideoJSONArray.length(); i++) {
            try {
                sCVideoJSONObject = (JSONObject) sCVideoJSONArray.get(i);
                JSONObject snippet = sCVideoJSONObject.optJSONObject("snippet");
                String title = snippet.optString("title");

                sCVideo.setTitle(title);
                videoArray.add(sCVideo);

                System.out.println(sCVideo.getTitle());

            } catch (JSONException e) {
                Log.d("JSON EXCEPTION", "God dang JSON Exception");
                e.printStackTrace();
            }

        }
        return videoArray;
    }

}

