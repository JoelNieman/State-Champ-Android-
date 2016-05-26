package app.com.joel.statechamps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.util.ArrayList;

import app.com.joel.statechamps.Model.Parse.ParseArticleQuery;
import app.com.joel.statechamps.Model.Parse.ParseImageDownloader;
import app.com.joel.statechamps.Model.Parse.ParseQueryDelegate;
import app.com.joel.statechamps.Model.Parse.SCArticle;
import app.com.joel.statechamps.Model.Parse.SCLibrary;
import app.com.joel.statechamps.Model.YouTube.OnVideoSelectedDelegate;

/**
 * Created by Joel on 5/25/16.
 */
public class ArticlesListFragment extends Fragment implements ParseQueryDelegate {

    private ArrayList<SCArticle> sCArticles;
    private ParseQueryDelegate handler;
    private ParseArticleQuery parseAPI;
    private SCLibrary sCLibrary;

    private ArticlesAdapter articlesAdapter;
    private RecyclerView sCArticlesRecyclerView;
    private ParseImageDownloader parseImageDownloader;
//    private SCArticle article;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.articles_list, container, false);

        sCLibrary = SCLibrary.get(getActivity(), this);

        sCArticlesRecyclerView = (RecyclerView) v.findViewById(R.id.articles_recycler_view);
        sCArticlesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.d("VideoListFragment", "onCreateView: called");
        return v;
    }

    @Override
    public void onArticlesResponse(ArrayList<SCArticle> articles) {
        this.sCArticles = articles;
        parseImageDownloader = new ParseImageDownloader(sCArticles, this);
        parseImageDownloader.execute();


        Log.d("ArticlesFragment", "Article number 25: " + sCArticles.get(24).getTitle());

    }

    @Override
    public void onImagesResponse(ArrayList<SCArticle> articlesWithImages) {
        this.sCArticles = articlesWithImages;
        articlesAdapter = new ArticlesAdapter(this.sCArticles);
        sCArticlesRecyclerView.setAdapter(articlesAdapter);

    }


    private class ArticleHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private ImageView mImageView;
        private RippleView rippleView;
        private SCArticle mSCArticle;


        private OnVideoSelectedDelegate clickHandler;
        private LinearLayout mListViewCell;


        public ArticleHolder(View itemView) {
            super(itemView);

            rippleView = (RippleView) itemView.findViewById(R.id.articles_ripple_view);
            mTitleTextView = (TextView) itemView.findViewById(R.id.article_title);
            mImageView = (ImageView) itemView.findViewById(R.id.article_thumbnail_image_view);

        }

        public void bindSCArticle(SCArticle article) {
            mSCArticle = article;

            mTitleTextView.setText(mSCArticle.getTitle());
            mImageView.setImageBitmap(mSCArticle.getImageBitmap());
        }

    }


    private class ArticlesAdapter extends RecyclerView.Adapter<ArticleHolder> {


        public ArticlesAdapter(ArrayList<SCArticle> articles) {
            sCArticles = articles;
        }


        @Override
        public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.articles_list_item, parent, false);

            return new ArticleHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleHolder holder, int position) {
            final SCArticle article = sCArticles.get(position);

            holder.rippleView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                    passVideo(video.getVideoID());
                }
            });

            holder.bindSCArticle(article);

        }

        @Override
        public int getItemCount() {
            return sCArticles.size();
        }
    }
}

