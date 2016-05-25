package app.com.joel.statechamps.Model.YouTube;

import android.os.AsyncTask;

import java.util.ArrayList;

import app.com.joel.statechamps.Model.Parse.ParseQueryDelegate;
import app.com.joel.statechamps.Model.Parse.SCArticle;

/**
 * Created by Joel on 5/24/16.
 */
public class ParseQuery extends AsyncTask<Void, Void, ArrayList<SCArticle>>{

    private ParseQueryDelegate handler;

    public ParseQuery(ParseQueryDelegate handler){
        this.handler = handler;
    }

    @Override
    protected ArrayList<SCArticle> doInBackground(Void... params) {
        return null;
    }


}
