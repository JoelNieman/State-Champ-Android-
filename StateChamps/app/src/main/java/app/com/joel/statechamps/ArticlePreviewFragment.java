package app.com.joel.statechamps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

import app.com.joel.statechamps.Model.Parse.ArticleSelectedDelegate;
import app.com.joel.statechamps.Model.Parse.ParseQueryDelegate;
import app.com.joel.statechamps.Model.Parse.SCArticle;
import app.com.joel.statechamps.Model.Parse.SCLibrary;

/**
 * Created by Joel on 5/27/16.
 */
public class ArticlePreviewFragment extends Fragment implements ParseQueryDelegate, ArticleSelectedDelegate {

//    private static final String PASSED_ARTICLE = "passed_article";
//    private static final java.lang.String PASSED_ARTICLE_POSITION = "passed_article_position";

    private TextView title;
    private TextView bodyPreview;
    private Button readMore;

    private SCLibrary sCLibrary;
    private SCArticle sCArticle;
    private UUID passedArticleUUID;
    private Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.article_preview, container, false);

        if (this.getArguments() != null) {
            bundle = this.getArguments();
            passedArticleUUID = (UUID) bundle.getSerializable("article_to_pass");
        }

        if (sCLibrary == null) {
            sCLibrary = SCLibrary.get(getContext(), this);
        }

        sCArticle = sCLibrary.getArticle(passedArticleUUID);


        title = (TextView) v.findViewById(R.id.article_preview_title);
        title.setText(sCArticle.getTitle());

        bodyPreview = (TextView) v.findViewById(R.id.article_preview_body);
        bodyPreview.setText(sCArticle.getBody());

        readMore = (Button) v.findViewById(R.id.read_more_button);

        return v;

        }





    @Override
    public void onArticlesResponse (ArrayList < SCArticle > articles) {

    }

    @Override
    public void onImagesResponse (ArrayList < SCArticle > articlesWithImages) {

    }

    @Override
    public void onArticleSelected(UUID articleId) {
        this.sCArticle = sCLibrary.getArticle(articleId);
        title.setText(sCArticle.getTitle());
        bodyPreview.setText(sCArticle.getBody());

    }
}
