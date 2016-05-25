package app.com.joel.statechamps.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.parse.Parse;

import app.com.joel.statechamps.ArticlesListFragment;
import app.com.joel.statechamps.DefaultImage;
import app.com.joel.statechamps.R;

/**
 * Created by Joel on 5/10/16.
 */
public class ArticlesFragment extends Fragment {


    private FrameLayout articlePreviewFrame;
    private FragmentManager fm;
    private DefaultImage defaultImageFragment;
    private ArticlesListFragment articlesListFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.articles_tab, container, false);

        Parse.initialize(getActivity(),"ix5mJYC2mshbbsOl7B0ykb3bNAvuku98jAsDfSRp", "SaLzyUYqPMJlK3bzrS5evi474f12kbrDsiX6i2ZB");

        fm = getChildFragmentManager();

        defaultImageFragment = new DefaultImage();
        FragmentTransaction transaction1 = fm.beginTransaction();
        transaction1.replace(R.id.article_preview_frame, defaultImageFragment, "DEFAULT_IMAGE_FRAGMENT").commit();

        articlesListFragment = new ArticlesListFragment();
        FragmentTransaction transaction2 = fm.beginTransaction();
        transaction2.replace(R.id.article_list_frame, articlesListFragment, "ARTICLES_LIST_FRAGMENT").commit();

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
}
