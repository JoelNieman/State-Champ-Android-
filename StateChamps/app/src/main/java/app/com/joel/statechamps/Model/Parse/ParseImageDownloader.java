package app.com.joel.statechamps.Model.Parse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Joel on 5/25/16.
 */
public class ParseImageDownloader extends AsyncTask<ArrayList<SCArticle>, Void, ArrayList<SCArticle>> {

    private ArrayList<SCArticle> articlesArrayList;
    private ParseQueryDelegate handler;
    private Bitmap myBitmap;

    public ParseImageDownloader(ArrayList<SCArticle> articles, ParseQueryDelegate handler){
        this.articlesArrayList = articles;
        this.handler = handler;

    }

    public ArrayList<SCArticle> doInBackground(ArrayList<SCArticle>... articles) {
        for (int i = 0; i < articlesArrayList.size(); i++) {
            try {
                SCArticle myArticle = new SCArticle();
                myArticle = articlesArrayList.get(i);
                myArticle.setImageURLString(myArticle.getImageFile().getUrl());

                URL urlConnection = new URL(myArticle.getImageURLString());
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);

                myArticle.setImageBitmap(myBitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return articlesArrayList;
    }

    @Override
    public void onPostExecute(ArrayList<SCArticle> scArticles) {
        this.articlesArrayList = scArticles;
        this.handler.onImagesResponse(articlesArrayList);
    }
}