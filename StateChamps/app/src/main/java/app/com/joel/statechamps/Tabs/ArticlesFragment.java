package app.com.joel.statechamps.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import app.com.joel.statechamps.Model.Parse.ParseQueryDelegate;
import app.com.joel.statechamps.Model.Parse.SCArticle;
import app.com.joel.statechamps.R;

/**
 * Created by Joel on 5/10/16.
 */
public class ArticlesFragment extends Fragment {

    private ArrayList<SCArticle> sCArticles;
    private ParseQueryDelegate handler;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.articles_tab, container, false);

        Parse.initialize(getActivity(),"ix5mJYC2mshbbsOl7B0ykb3bNAvuku98jAsDfSRp", "SaLzyUYqPMJlK3bzrS5evi474f12kbrDsiX6i2ZB");
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();

        handler = new ParseQueryDelegate() {
            @Override
            public void onArticlesResponse(ArrayList<SCArticle> articles) {
                sCArticles = articles;
                System.out.println(sCArticles);
            }

            @Override
            public void onImagesResponse(ArrayList<SCArticle> articlesWithImages) {
                sCArticles = articlesWithImages;
            }
        };

        queryParseForArticles();


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }



    public void queryParseForArticles() {
        sCArticles = new ArrayList<SCArticle>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Article");
        query.whereGreaterThan("articleNumber",0);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> articleList, ParseException e) {
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    if (sCArticles != null){
                        sCArticles.clear();
                    }

                    for (ParseObject article : articleList) {
                        try {
                        SCArticle myArticle = new SCArticle();
                        myArticle.setTitle(article.getString("title"));
                        myArticle.setAuthor(article.getString("author"));
                        myArticle.setPublishedDate(article.getString("publishDate"));
                        myArticle.setBody(article.getString("body"));

                        String urlString = article.getString("sport");
//                        URL url = new URL(urlString);
//                        myArticle.setArticleURL(url);
//
//                        myArticle.setImageFile(article.getParseFile("imageFile"));

                        sCArticles.add(myArticle);
                        Log.d("PARSE QUERY", "Article title is " +myArticle.getTitle());

                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), "Parse Query Exception", Toast.LENGTH_SHORT).show();
                            Log.d("PARSE QUERY", "EXCEPTION");
                            ex.printStackTrace();
                        }
                    }

                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

        this.handler.onArticlesResponse(sCArticles);
    }
}
