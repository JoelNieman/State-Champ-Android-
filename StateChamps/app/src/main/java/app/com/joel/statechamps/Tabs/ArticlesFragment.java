package app.com.joel.statechamps.Tabs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.net.URL;
import java.util.List;

import app.com.joel.statechamps.ImageDownloader;
import app.com.joel.statechamps.Model.YouTube.SCVideo;
import app.com.joel.statechamps.Model.YouTube.SCVideoStore;
import app.com.joel.statechamps.R;

/**
 * Created by Joel on 5/10/16.
 */
public class ArticlesFragment extends Fragment {

    private ImageDownloader imageDownloader;
    private ImageView imageView;
    private List<SCVideo> videos;
    private SCVideo video;
    private Bitmap imageBitmap;
    private URL videoURL;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.articles_tab, container, false);

        SCVideoStore videoStore = SCVideoStore.getSCVideoStore(getActivity());
        videos = videoStore.getSCVideosList();
        video = videos.get(0);

        imageView = (ImageView) v.findViewById(R.id.thumbnail_image_view2);

//        imageDownloader = new ImageDownloader();
//        imageDownloader.DownloadImageTask(video.getThumbnailURL(), imageView, getContext());
//        imageBitmap = imageDownloader.doInBackground();



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
//        imageDownloader.onPostExecute(imageBitmap);
    }

}
