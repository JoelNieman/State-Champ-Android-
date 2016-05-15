package app.com.joel.statechamps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joel on 5/15/16.
 */
public class ImageDownloader extends AsyncTask<Void, Void, Bitmap> {

    private ImageView imageView;
    private String url;
    private Context context;

    public void DownloadImageTask(String url, ImageView imageView, Context context){
        this.context = context;
        this.url = url;
        this.imageView = imageView;

        Toast.makeText(context, "DownloadImageTask called", Toast.LENGTH_SHORT).show();
    }

    public Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Toast.makeText(context, "doInBackground success", Toast.LENGTH_SHORT).show();

            return myBitmap;

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "doInBackground failure", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        this.imageView.setImageBitmap(result);
        Toast.makeText(context, "onPostExecute called", Toast.LENGTH_SHORT).show();
    }

}
