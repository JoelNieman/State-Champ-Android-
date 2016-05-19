package app.com.joel.statechamps;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.util.ArrayList;

import app.com.joel.statechamps.Model.YouTube.APIOnResponseDelegate;
import app.com.joel.statechamps.Model.YouTube.OnImageDownloadDelegate;
import app.com.joel.statechamps.Model.YouTube.OnVideoSelectedDelegate;
import app.com.joel.statechamps.Model.YouTube.SCVideo;
import app.com.joel.statechamps.Model.YouTube.YouTubeAPICall;
import app.com.joel.statechamps.Tabs.VideosFragment;

/**
 * Created by Joel on 5/14/16.
 */

public class VideoListFragment extends Fragment implements APIOnResponseDelegate, OnImageDownloadDelegate {

    private static final String VIDEO_ID = "video_id";

    private RecyclerView sCVideoRecyclerView;
    private VideoAdapter adapter;
    private ArrayList<SCVideo> sCVideoStore;
    private ArrayList<Bitmap> imageStore;
    private ImageDownloader imageDownloader;
    private YouTubeAPICall showsAPICall;
    private String showsEndpoint;
    private APIOnResponseDelegate handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videos_list, container, false);

        Log.d("VideoListFragment", "onCreateView: called");

        if (sCVideoRecyclerView == null) {
            sCVideoRecyclerView = (RecyclerView) v.findViewById(R.id.videos_recycler_view);
            sCVideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }


        showsEndpoint = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL8dd-D6tYC0DfIJarU3NrrTHvPmMkCjTd&maxResults=5&key=AIzaSyCBgwbRkQjNIPraASVj7KxzN0HgoEWiuiI";


        if (isNetworkEnabled(getContext()) && (sCVideoStore == null)) {
            showsAPICall = new YouTubeAPICall(showsEndpoint, this);
            showsAPICall.execute();
        } else {
            Toast.makeText(getActivity(), "network not available", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("VideoListFragment", "onStart: called");
    }

    @Override
    public void onResume() {
        super.onStart();
        Log.d("VideoListFragment", "onResume: called");
    }


    @Override
    public void onPreStart() {
        handler.onPreStart();
    }

    @Override
    public void onShowVideoResponse(ArrayList<SCVideo> sCVideoStore) {
        this.sCVideoStore = sCVideoStore;
        imageDownloader = new ImageDownloader(sCVideoStore, this);
        imageDownloader.execute();

        passVideo(sCVideoStore.get(0).getVideoID());
    }


    @Override
    public void onHighlightVideoResponse(ArrayList<SCVideo> highlights) {

    }

    @Override
    public void onImageDownload(ArrayList<Bitmap> myBitmaps) {
        this.imageStore = myBitmaps;
        updateUI();
    }


    private void updateUI() {
        adapter = new VideoAdapter(this.sCVideoStore, this.imageStore);
        sCVideoRecyclerView.setAdapter(adapter);
    }




    private class VideoHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private ImageView mImageView;
        private RippleView rippleView;
        private SCVideo mSCVideo;
        private Bitmap mBitmap;

        private OnVideoSelectedDelegate clickHandler;
        private LinearLayout mListViewCell;


        public VideoHolder(View itemView) {
            super(itemView);

            rippleView = (RippleView) itemView.findViewById(R.id.ripple_view);
            mTitleTextView = (TextView) itemView.findViewById(R.id.video_title);
            mImageView = (ImageView) itemView.findViewById(R.id.thumbnail_image_view);

        }

        public void bindSCVideo(SCVideo video, Bitmap image) {
            mSCVideo = video;
            mBitmap = image;

            mTitleTextView.setText(mSCVideo.getTitle());
            mImageView.setImageBitmap(mBitmap);
        }

    }



    private class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {


        public VideoAdapter(ArrayList<SCVideo> videos, ArrayList<Bitmap> images) {
            sCVideoStore = videos;
            imageStore = images;
        }


        @Override
        public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.video_list_item, parent, false);

            return new VideoHolder(view);
        }

        @Override
        public void onBindViewHolder(VideoHolder holder, int position) {
            final SCVideo video = sCVideoStore.get(position);
            Bitmap image = imageStore.get(position);

            holder.rippleView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    passVideo(video.getVideoID());
                }
            });

            holder.bindSCVideo(video, image);

        }

        @Override
        public int getItemCount() {
            return sCVideoStore.size();
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
            } else {
                Toast.makeText(context, "network is not available", Toast.LENGTH_LONG).show();
            }
        } return available;
    }


    private void passVideo(String videoID) {
        String videoToPass = videoID;
        FragmentManager fragmentManager = getFragmentManager();
        VideosFragment.YouTubePlayerFragment videosFragment = (VideosFragment.YouTubePlayerFragment) fragmentManager.findFragmentByTag("YOUTUBE_PLAYER_FRAGMENT");

        if (videosFragment != null) {
            videosFragment.onVideoSelected(videoToPass);
        }
    }
}
