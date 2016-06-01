package app.com.joel.statechamps.Model.Parse;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Joel on 5/24/16.
 */
public interface ParseQueryDelegate {

    public void onArticlesResponse(ArrayList<SCArticle> articles);
    public void onImagesResponse(ArrayList<SCArticle> articlesWithImages);
    public void onImageResponse(Bitmap articleImage);
}
