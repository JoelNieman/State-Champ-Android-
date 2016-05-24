package app.com.joel.statechamps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.com.joel.statechamps.Model.YouTube.SCVideo;


/**
 * Created by Joel on 5/14/16.
 */
public class DummyFragment extends Fragment {

    private TextView textView;
    private SCVideo video;
    private List<SCVideo> videoList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dummy_layout, container, false);

        textView = (TextView) v.findViewById(R.id.textView2);

//        videoStore = SCVideoStore.getSCVideoStore(getActivity());
//        videoList = videoStore.getSCVideosList();
//        video = videoList.get(0);
//
//        textView.setText(video.getTitle());

        return v;
    }
}
