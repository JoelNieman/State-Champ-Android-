package app.com.joel.statechamps.Model.Parse;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joel on 5/25/16.
 */
public class ParseArticleQuery {

    private ParseQueryDelegate handler;
    private ArrayList<SCArticle> retrievedArticles;

    public ParseArticleQuery(ParseQueryDelegate handler) {
        this.handler = handler;
    }

    public ArrayList<SCArticle> queryParseForArticles() {

        Log.d("ParseArticleQuery", "This is a new Parse API call");
        retrievedArticles = new ArrayList<SCArticle>();

        com.parse.ParseQuery<ParseObject> query = com.parse.ParseQuery.getQuery("Article");
        query.whereGreaterThan("articleNumber",0);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> articleList, ParseException e) {
                if (e == null) {
                    if (retrievedArticles != null){
                        retrievedArticles.clear();
                    } else {
                        Log.d("ParseQuery", "done: exception: " +e);
                    }

                    for (ParseObject article : articleList) {
                        try {
                            SCArticle myArticle = new SCArticle();
                            myArticle.setTitle(article.getString("title"));
                            myArticle.setAuthor(article.getString("author"));
                            myArticle.setPublishedDate(article.getString("publishDate"));
                            myArticle.setBody(article.getString("body"));
                            myArticle.setSport(article.getString("sport"));

                            String urlString = article.getString("articleURL");
                            URL url = new URL(urlString);
                            myArticle.setArticleURL(url);

                            ParseFile imageFile = article.getParseFile("imageFile");
                            myArticle.setImageFile(imageFile);

                            retrievedArticles.add(myArticle);

                        } catch (Exception ex) {
                            Log.d("PARSE QUERY", "EXCEPTION");
                            ex.printStackTrace();
                        }
                    }

                    handler.onArticlesResponse(retrievedArticles);

                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

        return retrievedArticles;
    }

}
