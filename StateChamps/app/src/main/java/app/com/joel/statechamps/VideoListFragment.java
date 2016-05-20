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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.util.ArrayList;

import app.com.joel.statechamps.Model.YouTube.APIOnResponseDelegate;
import app.com.joel.statechamps.Model.YouTube.OnHighlightImageDownloadDelegate;
import app.com.joel.statechamps.Model.YouTube.OnShowImageDownloadDelegate;
import app.com.joel.statechamps.Model.YouTube.OnVideoSelectedDelegate;
import app.com.joel.statechamps.Model.YouTube.SCVideo;
import app.com.joel.statechamps.Model.YouTube.YouTubeAPICall;
import app.com.joel.statechamps.Tabs.VideosFragment;

/**
 * Created by Joel on 5/14/16.
 */

public class VideoListFragment extends Fragment implements APIOnResponseDelegate, OnShowImageDownloadDelegate, OnHighlightImageDownloadDelegate {

    private static final String VIDEO_ID = "video_id";

    private RecyclerView sCVideoRecyclerView;
    private ShowsVideoAdapter showsAdapter;
    private HighlightsVideoAdapter highlightsAdapter;
    private ArrayList<SCVideo> sCShowsStore;
    private ArrayList<SCVideo> sCHighlightsStore;
    private ArrayList<Bitmap> showsImageStore;
    private ArrayList<Bitmap> highlightsImageStore;
    private ShowsImageDownloader showsImageDownloader;
    private HighlightImageDownloader highlightImageDownloader;
    private YouTubeAPICall showsAPICall;
    private String showsEndpoint;
    private YouTubeAPICall highlightsAPICall;
    private String highlightsEndpoint;
    private APIOnResponseDelegate handler;

    private Button showsButton;
    private Button highlightsButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videos_list, container, false);

        Log.d("VideoListFragment", "onCreateView: called");

        showsButton = (Button) v.findViewById(R.id.shows_button);
        showsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showsButton.setBackgroundColor(getResources().getColor(R.color.SCRedColor));
                highlightsButton.setBackgroundColor(getResources().getColor(R.color.SCGrayColor));
                showsButton.setClickable(false);
                showsButton.setEnabled(false);
                highlightsButton.setClickable(true);
                highlightsButton.setEnabled(true);

                showsUISetup();
            }
        });


        highlightsButton = (Button) v.findViewById(R.id.highlights_button);
        highlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (highlightsImageStore == null){
                    highlightsAPICall.execute();
                    showsButton.setBackgroundColor(getResources().getColor(R.color.SCGrayColor));
                    highlightsButton.setBackgroundColor(getResources().getColor(R.color.SCRedColor));
                    showsButton.setClickable(true);
                    showsButton.setEnabled(true);
                    highlightsButton.setClickable(false);
                    highlightsButton.setEnabled(false);
                } else {
                    showsButton.setBackgroundColor(getResources().getColor(R.color.SCGrayColor));
                    highlightsButton.setBackgroundColor(getResources().getColor(R.color.SCRedColor));
                    showsButton.setClickable(true);
                    showsButton.setEnabled(true);
                    highlightsButton.setClickable(false);
                    highlightsButton.setEnabled(false);
                    highlightsUISetup();
                }
            }
        });

        showsButton.setBackgroundColor(getResources().getColor(R.color.SCRedColor));
        highlightsButton.setBackgroundColor(getResources().getColor(R.color.SCGrayColor));

        if (sCVideoRecyclerView == null) {
            sCVideoRecyclerView = (RecyclerView) v.findViewById(R.id.videos_recycler_view);
            sCVideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        showsEndpoint = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL8dd-D6tYC0DfIJarU3NrrTHvPmMkCjTd&maxResults=5&key=AIzaSyCBgwbRkQjNIPraASVj7KxzN0HgoEWiuiI";
        highlightsEndpoint = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL8dd-D6tYC0BeICQ2C3hym16jEyj0SzSJ&maxResults=5&key=AIzaSyCBgwbRkQjNIPraASVj7KxzN0HgoEWiuiI";

        if (isNetworkEnabled(getContext()) && (sCShowsStore == null)) {
            showsAPICall = new YouTubeAPICall(showsEndpoint, this);
            showsAPICall.execute();
            highlightsAPICall = new YouTubeAPICall(highlightsEndpoint, this);


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
    public void onShowVideoResponse(ArrayList<SCVideo> sCShowsStore) {
        if (highlightsButton.isEnabled()){
            this.sCShowsStore = sCShowsStore;
            showsImageDownloader = new ShowsImageDownloader(sCShowsStore, this);
            showsImageDownloader.execute();
            passVideo(sCShowsStore.get(0).getVideoID());
        } else {
            this.sCHighlightsStore = sCShowsStore;
            highlightImageDownloader = new HighlightImageDownloader(sCHighlightsStore, this);
            highlightImageDownloader.execute();
        }

    }


    @Override
    public void onHighlightVideoResponse(ArrayList<SCVideo> highlights) {
//        this.sCHighlightsStore = highlights;
//        highlightImageDownloader = new HighlightImageDownloader(sCHighlightsStore, this);
//        highlightImageDownloader.execute();
    }

    @Override
    public void onShowImageDownload(ArrayList<Bitmap> myBitmaps) {
        this.showsImageStore = myBitmaps;
        showsUISetup();
    }

    @Override
    public void onHighlightImageDownload(ArrayList<Bitmap> myBitmaps) {
        this.highlightsImageStore = myBitmaps;
        highlightsUISetup();
    }


    private void showsUISetup() {
        if (showsAdapter == null) {
            showsAdapter = new ShowsVideoAdapter(this.sCShowsStore, this.showsImageStore);
            sCVideoRecyclerView.setAdapter(showsAdapter);
        } else {
            sCVideoRecyclerView.swapAdapter(showsAdapter, true);
        }
    }

    private void highlightsUISetup() {
        if (highlightsAdapter == null) {
            highlightsAdapter = new HighlightsVideoAdapter(this.sCHighlightsStore, this.highlightsImageStore);
            sCVideoRecyclerView.swapAdapter(highlightsAdapter, true);
        } else {
            sCVideoRecyclerView.swapAdapter(highlightsAdapter, true);
        }
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



    private class ShowsVideoAdapter extends RecyclerView.Adapter<VideoHolder> {


        public ShowsVideoAdapter(ArrayList<SCVideo> videos, ArrayList<Bitmap> images) {
            sCShowsStore = videos;
            showsImageStore = images;
        }


        @Override
        public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.video_list_item, parent, false);

            return new VideoHolder(view);
        }

        @Override
        public void onBindViewHolder(VideoHolder holder, int position) {
            final SCVideo video = sCShowsStore.get(position);
            Bitmap image = showsImageStore.get(position);

            holder.rippleView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    passVideo(video.getVideoID());
                }
            });

            holder.bindSCVideo(video, image);

        }

        @Override
        public int getItemCount() {
            return sCShowsStore.size();
        }
    }






    private class HighlightsVideoAdapter extends RecyclerView.Adapter<VideoHolder> {


        public HighlightsVideoAdapter(ArrayList<SCVideo> videos, ArrayList<Bitmap> images) {
            sCHighlightsStore = videos;
            highlightsImageStore = images;
        }


        @Override
        public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.video_list_item, parent, false);

            return new VideoHolder(view);
        }

        @Override
        public void onBindViewHolder(VideoHolder holder, int position) {
            final SCVideo video = sCHighlightsStore.get(position);
            Bitmap image = highlightsImageStore.get(position);

            holder.rippleView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    passVideo(video.getVideoID());
                }
            });

            holder.bindSCVideo(video, image);

        }

        @Override
        public int getItemCount() {
            return sCHighlightsStore.size();
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
