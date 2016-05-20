package app.com.joel.statechamps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import app.com.joel.statechamps.Model.YouTube.OnShowImageDownloadDelegate;
import app.com.joel.statechamps.Model.YouTube.SCVideo;

/**
 * Created by Joel on 5/15/16.
 */
public class ShowsImageDownloader extends AsyncTask<Void, Void, ArrayList<Bitmap>> {

    private ArrayList<SCVideo> videoArrayList;
    private ArrayList<Bitmap> bitmapsArrayList;
    private OnShowImageDownloadDelegate handler;
    private Bitmap myBitmap;

    public ShowsImageDownloader(ArrayList<SCVideo> sCVideoStore, OnShowImageDownloadDelegate handler){
        this.videoArrayList = sCVideoStore;
        this.handler = handler;

    }

    public ArrayList<Bitmap> doInBackground(Void... params) {
        ArrayList<Bitmap> bitmapCollection = new ArrayList<>();

        for (int i = 0; i < videoArrayList.size(); i++) {
            try {
                URL urlConnection = new URL(videoArrayList.get(i).getThumbnailURL());
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);

                bitmapCollection.add(myBitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmapCollection;
    }

    @Override
    public void onPostExecute(ArrayList<Bitmap> result) {
        this.bitmapsArrayList = result;
        this.handler.onShowImageDownload(bitmapsArrayList);
    }
}


