package app.com.joel.statechamps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import app.com.joel.statechamps.Model.Parse.SCArticle;
import app.com.joel.statechamps.Model.Parse.SCLibrary;

/**
 * Created by Joel on 5/25/16.
 */
public class DefaultImage extends Fragment {

    private SCLibrary sCLibrary;
    private SCArticle sCArticle;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_image, container, false);

        ImageView imageView = (ImageView) v.findViewById(R.id.default_image);
        imageView.setImageResource(R.drawable.state_champs_logo);
        return v;
    }
}
