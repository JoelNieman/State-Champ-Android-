package app.com.joel.statechamps;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.joel.statechamps.Model.Parse.ParseQueryDelegate;
import app.com.joel.statechamps.Model.Parse.SCArticle;
import app.com.joel.statechamps.Model.Parse.SCLibrary;

/**
 * Created by Joel on 5/31/16.
 */
public class FullArticleActivity extends AppCompatActivity implements ParseQueryDelegate {


    private TextView articleTitle;
    private TextView articleAuthor;
    private TextView articleSport;
    private TextView articleDate;
    private TextView articleBody;
    private ImageView articleImage;
    private Toolbar toolbar;

    private SCLibrary sCLibrary;
    private SCArticle sCArticle;
    private int articlePosition;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_article);

        Bundle b = getIntent().getExtras();
        articlePosition = b.getInt("passed_article_position");

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("State Champs!");

        sCLibrary = SCLibrary.get(this, this);
        sCArticle = sCLibrary.getArticleAtPosition(articlePosition);

        articleTitle = (TextView) findViewById(R.id.full_article_title);
        articleTitle.setText(sCArticle.getTitle());

        articleAuthor = (TextView) findViewById(R.id.full_article_author);
        articleAuthor.setText(sCArticle.getAuthor());

        articleSport = (TextView) findViewById(R.id.full_article_sport);
        articleSport.setText(sCArticle.getSport());

        articleDate = (TextView) findViewById(R.id.full_article_date);
        articleDate.setText(sCArticle.getPublishedDate());

        articleBody = (TextView) findViewById(R.id.full_article_body);
        articleBody.setText(sCArticle.getBody());


    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
////            case R.id.new_game:
////                newGame();
////                return true;
////            case R.id.help:
////                showHelp();
////                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onArticlesResponse(ArrayList<SCArticle> articles) {

    }

    @Override
    public void onImagesResponse(ArrayList<SCArticle> articlesWithImages) {

    }

    @Override
    public void onImageResponse(Bitmap articleImage) {

    }



}
