package app.com.joel.statechamps.Model.Parse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.joel.statechamps.R;

/**
 * Created by Joel on 5/26/16.
 */
public class ArticlePreviewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.article_preview, container, false);

        return v;
    }
}
