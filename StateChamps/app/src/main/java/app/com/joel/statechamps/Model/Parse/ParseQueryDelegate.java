package app.com.joel.statechamps.Model.Parse;

import java.util.ArrayList;

/**
 * Created by Joel on 5/24/16.
 */
public interface ParseQueryDelegate {

    public void onArticlesResponse(ArrayList<SCArticle> articles);
    public void onImagesResponse(ArrayList<SCArticle> articlesWithImages);
}
