package app.com.joel.statechamps.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.joel.statechamps.R;

/**
 * Created by Joel on 5/10/16.
 */
public class ArticlesFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.articles_tab, container, false);



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
