package app.com.joel.statechamps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import app.com.joel.statechamps.Model.YouTube.OnHighlightImageDownloadDelegate;
import app.com.joel.statechamps.Model.YouTube.SCVideo;

public class HighlightImageDownloader extends AsyncTask<ArrayList<SCVideo>, Void, ArrayList<SCVideo>> {

    private ArrayList<SCVideo> videoArrayList;
    private OnHighlightImageDownloadDelegate handler;
    private Bitmap myBitmap;

    public HighlightImageDownloader(ArrayList<SCVideo> sCVideoStore, OnHighlightImageDownloadDelegate handler){
        this.videoArrayList = sCVideoStore;
        this.handler = handler;

    }

    public ArrayList<SCVideo> doInBackground(ArrayList<SCVideo>... params) {
        for (int i = 0; i < videoArrayList.size(); i++) {
            try {
                URL urlConnection = new URL(videoArrayList.get(i).getThumbnailURLString());
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);

                videoArrayList.get(i).setThumbnailBitmap(myBitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return videoArrayList;
    }

    @Override
    public void onPostExecute(ArrayList<SCVideo> sCHighlightVideos) {
        this.videoArrayList = sCHighlightVideos;
        this.handler.onHighlightImageDownload(videoArrayList);
    }
}
