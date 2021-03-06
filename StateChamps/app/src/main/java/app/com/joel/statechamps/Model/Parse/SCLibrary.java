package app.com.joel.statechamps.Model.Parse;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Joel on 5/25/16.
 */
public class SCLibrary {

    private static SCLibrary sCLibrary = null;
    private ArrayList<SCArticle> sCArticles;
//    private ParseArticleQuery parseQuery;
    private ParseQueryDelegate handler;

    public static SCLibrary get(Context context, ParseQueryDelegate handler){
        if (sCLibrary == null) {
            sCLibrary = new SCLibrary(context, handler);
        }
        return sCLibrary;
    }

    private SCLibrary(Context context, ParseQueryDelegate handler){
        this.sCArticles = new ArrayList<>();
        this.handler = handler;

        ParseArticleQuery parseQuery = new ParseArticleQuery(handler);
        sCArticles = parseQuery.queryParseForArticles();
    }

    public ArrayList<SCArticle> getArticles(){
        return sCArticles;
    }

    public SCArticle getArticle(UUID id){
        for (SCArticle article : sCArticles){
            if (article.getId().equals(id)) {
                return article;
            }
        }
        return null;
    }

    public void makeNull(){
        sCLibrary = null;
    }


    public SCArticle getArticleAtPosition(int position){
        SCArticle myArticle = sCArticles.get(position);
        return myArticle;
    }
}
