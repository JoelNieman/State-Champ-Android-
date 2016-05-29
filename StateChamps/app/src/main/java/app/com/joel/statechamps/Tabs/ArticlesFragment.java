package app.com.joel.statechamps.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.parse.Parse;

import java.util.ArrayList;
import java.util.UUID;

import app.com.joel.statechamps.DefaultImage;
import app.com.joel.statechamps.Model.Parse.ArticleSelectedDelegate;
import app.com.joel.statechamps.Model.Parse.ParseArticleQuery;
import app.com.joel.statechamps.Model.Parse.ParseImageDownloader;
import app.com.joel.statechamps.Model.Parse.ParseQueryDelegate;
import app.com.joel.statechamps.Model.Parse.SCArticle;
import app.com.joel.statechamps.Model.Parse.SCLibrary;
import app.com.joel.statechamps.Model.YouTube.OnVideoSelectedDelegate;
import app.com.joel.statechamps.R;

/**
 * Created by Joel on 5/10/16.
 */
public class ArticlesFragment extends Fragment implements ParseQueryDelegate {


    private FrameLayout articlePreviewFrame;
    private FragmentManager fm;
    private DefaultImage defaultImageFragment;


    private static final String ARTICLES_LIST = "articles_list";
    private ArrayList<SCArticle> sCArticles;
    private ParseQueryDelegate handler;
    private ParseArticleQuery parseAPI;
    private SCLibrary sCLibrary;

    private ArticlesAdapter articlesAdapter;
    private RecyclerView sCArticlesRecyclerView;
    private ParseImageDownloader parseImageDownloader;
    private SwipeRefreshLayout swipeRefresh;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.articles_list, container, false);

        Parse.initialize(getActivity(),"ix5mJYC2mshbbsOl7B0ykb3bNAvuku98jAsDfSRp", "SaLzyUYqPMJlK3bzrS5evi474f12kbrDsiX6i2ZB");

        fm = getChildFragmentManager();

        defaultImageFragment = new DefaultImage();
        FragmentTransaction transaction1 = fm.beginTransaction();
        transaction1.replace(R.id.article_preview_frame, defaultImageFragment, "DEFAULT_IMAGE_FRAGMENT").commit();

//        articlesListFragment = new ArticlesListFragment();
//        FragmentTransaction transaction2 = fm.beginTransaction();
//        transaction2.replace(R.id.article_list_frame, articlesListFragment, "ARTICLES_LIST_FRAGMENT").commit();

        if (savedInstanceState != null) {
            Log.d("SAVED INSTANCE STATE", "onSaveInstanceState: called");
            sCArticles = savedInstanceState.getParcelableArrayList(ARTICLES_LIST);
        }

        sCLibrary = SCLibrary.get(getActivity(), this);

        sCArticlesRecyclerView = (RecyclerView) v.findViewById(R.id.articles_recycler_view);
        sCArticlesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.articles_swipe_container);
        swipeRefresh.setRefreshing(true);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                articlesAdapter.clear();
                sCLibrary.makeNull();
                sCLibrary = SCLibrary.get(getActivity(), ArticlesFragment.this);

                swipeRefresh.setRefreshing(true);
            }
        });

        Log.d("VideoListFragment", "onCreateView: called");
        return v;
    }


    @Override
    public void onPause(){
        super.onPause();
        Log.d("ArticlesFragment", "onPause: called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("ArticlesFragment", "onDestroy: called");
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
        swipeRefresh.setRefreshing(false);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        if (sCArticles != null) {
            Log.d("SAVED INSTANCE STATE", "onSaveInstanceState: called");
            savedInstanceState.putParcelableArrayList(ARTICLES_LIST, sCArticles);
        }
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

        private ArticleSelectedDelegate articleHandler;


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
                }
            });

            holder.bindSCArticle(article);

        }

        @Override
        public int getItemCount() {
            return sCArticles.size();
        }

        public void clear(){
            sCArticles.clear();
            notifyDataSetChanged();
        }

        public void articleSelected(UUID articleId){
            articleHandler.onArticleSelected(articleId);
        }
    }

}
