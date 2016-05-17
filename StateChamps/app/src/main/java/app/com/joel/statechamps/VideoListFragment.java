package app.com.joel.statechamps;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.com.joel.statechamps.Model.YouTube.APIOnResponseDelegate;
import app.com.joel.statechamps.Model.YouTube.SCVideo;
import app.com.joel.statechamps.Model.YouTube.SCVideoStore;
import app.com.joel.statechamps.Model.YouTube.YouTubeAPICall;

/**
 * Created by Joel on 5/14/16.
 */

public class VideoListFragment extends Fragment implements APIOnResponseDelegate {

    private RecyclerView mSCVideoRecyclerView;
    private VideoAdapter mAdapter;
    private List<SCVideo> mSCVideos;
    private ImageDownloader imageDownloader;

    private YouTubeAPICall showsAPICall;

    private String showsEndpoint;

    private YouTubeAPICall highlightsAPICall;
    private String highlightsEndpoint;

    private APIOnResponseDelegate handler;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videos_list, container, false);

        mSCVideoRecyclerView = (RecyclerView) v.findViewById(R.id.videos_recycler_view);
        mSCVideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        imageDownloader = new ImageDownloader();

        showsEndpoint = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL8dd-D6tYC0DfIJarU3NrrTHvPmMkCjTd&maxResults=5&key=AIzaSyCBgwbRkQjNIPraASVj7KxzN0HgoEWiuiI";


        updateUI();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        showsAPICall = new YouTubeAPICall(showsEndpoint, this);

        if (isNetworkEnabled(getContext())) {
            showsAPICall.execute();
        } else {
            Toast.makeText(getActivity(), "network not enabled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPreStart() {
        handler.onPreStart();
    }

//    @Override
//    public void onShowVideoResponse(JSONArray response) {
//        handler.onShowVideoResponse(response);
//        Toast.makeText(getActivity(), "The Shows APICall was successful",Toast.LENGTH_SHORT).show();
//    }


    private void updateUI() {
        SCVideoStore videoStore = SCVideoStore.getSCVideoStore(getActivity());
        List<SCVideo> videos = videoStore.getSCVideosList();

        mAdapter = new VideoAdapter(videos);
        mSCVideoRecyclerView.setAdapter(mAdapter);
    }


    private class VideoHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private ImageView mImageView;
        private SCVideo mSCVideo;


        public VideoHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.video_title);
            mImageView = (ImageView) itemView.findViewById(R.id.thumbnail_image_view);


        }

        public void bindSCVideo(SCVideo video) {
            mSCVideo = video;

            mTitleTextView.setText(mSCVideo.getTitle());

//            imageDownloader.DownloadImageTask(mImageView);
//            imageDownloader.onPostExecute(imageDownloader.doInBackground(mSCVideo.getThumbnailURL()));

//            imageDownloader.DownloadImageTask(mImageView).execute(mSCVideo.getThumbnailURL());

        }
    }



    private class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {


        public VideoAdapter(List<SCVideo> videos) {
            mSCVideos = videos;
        }


        @Override
        public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.video_list_item, parent, false);

            return new VideoHolder(view);
        }

        @Override
        public void onBindViewHolder(VideoHolder holder, int position) {
            SCVideo video = mSCVideos.get(position);
            holder.bindSCVideo(video);

        }

        @Override
        public int getItemCount() {
            return mSCVideos.size();
        }
    }


    public static boolean isNetworkEnabled(Context context) {
        boolean available = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                available = networkInfo.isAvailable();
                Toast.makeText(context, "network is enabled", Toast.LENGTH_SHORT).show();
            }
        } return available;
    }

}
