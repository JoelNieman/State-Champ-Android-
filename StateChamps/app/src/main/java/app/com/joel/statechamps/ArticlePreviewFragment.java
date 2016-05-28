package app.com.joel.statechamps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.joel.statechamps.Model.Parse.ParseQueryDelegate;
import app.com.joel.statechamps.Model.Parse.SCArticle;

/**
 * Created by Joel on 5/27/16.
 */
public class ArticlePreviewFragment extends Fragment implements ParseQueryDelegate {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.article_preview, container, false);



        return v;
    }













    @Override
    public void onArticlesResponse(ArrayList<SCArticle> articles) {

    }

    @Override
    public void onImagesResponse(ArrayList<SCArticle> articlesWithImages) {

    }


}
