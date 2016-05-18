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

/**
 * Created by Joel on 4/28/16.
 */
public class YouTubeAPICall extends AsyncTask <Void, Void, ArrayList<SCVideo>> {

    public static final int METHOD_GET = 0;
    public static final int METHOD_POST = 1;

    private APIOnResponseDelegate handler;
    private int method;
    private ArrayList<SCVideo> sCVideoStore;
    private ArrayList<SCVideo> parsedVideos;
    private JSONObject result;
    private ArrayList<SCVideo> videoArray;
    private SCVideo sCVideo;
    private JSONObject sCVideoJSONObject;
    private JSONArray sCVideoJSONArray;
    private String endpoint;




    public YouTubeAPICall(String endpoint, APIOnResponseDelegate handler) {
        this.endpoint = endpoint;
        this.handler = handler;
        this.method = METHOD_GET;
    }

    @Override
    protected void onPreExecute() {
//        this.handler.onPreStart();
    }

    @Override
    public ArrayList<SCVideo> doInBackground(Void... params) {
        ArrayList<SCVideo> videoStore = null;
        switch (this.method) {
            case METHOD_GET:
                videoStore = this.getRequest();
                break;
            case METHOD_POST:
                break;
        }
        return videoStore;
    }

    @Override
    protected void onPostExecute(ArrayList<SCVideo> sCVideoStore) {
        this.sCVideoStore = sCVideoStore;
        this.handler.onShowVideoResponse(sCVideoStore);
    }

    private ArrayList<SCVideo> getRequest() {
        parsedVideos = null;
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
                parsedVideos = parseJSON(result);

            } else {
                Log.d("** STATUS CODE **", "did not get 200");
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
        return parsedVideos;
    }


    protected ArrayList<SCVideo> parseJSON(JSONObject result) {


        videoArray = new ArrayList<>();

        try {
            sCVideoJSONArray = result.getJSONArray("items");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < sCVideoJSONArray.length(); i++) {
            try {
                sCVideo = new SCVideo();
                sCVideoJSONObject = (JSONObject) sCVideoJSONArray.get(i);
                JSONObject snippet = sCVideoJSONObject.optJSONObject("snippet");
                JSONObject thumbnails = snippet.optJSONObject("thumbnails");
                JSONObject defaultThumbnials = thumbnails.optJSONObject("default");
                JSONObject resourceId = snippet.optJSONObject("resourceId");

                String title = snippet.optString("title");
                String publishedDate = snippet.optString("publishedAt");
                String videoID = resourceId.optString("videoId");
                String thumbnailURLString = defaultThumbnials.optString("url");

                sCVideo.setTitle(title);
                sCVideo.setPublishedDate(publishedDate);
                sCVideo.setVideoID(videoID);
                sCVideo.setThumbnailURL(thumbnailURLString);

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

