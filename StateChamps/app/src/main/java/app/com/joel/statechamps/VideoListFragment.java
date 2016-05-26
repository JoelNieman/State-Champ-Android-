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

//    private static final String VIDEO_ID = "video_id";
    private static final String SHOWS_VIDEO_STORE = "shows_video_store";
    private static final String HIGHLIGHTS_VIDEO_STORE = "highlights_video_store";

    private RecyclerView sCVideoRecyclerView;
    private ShowsVideoAdapter showsAdapter;
    private HighlightsVideoAdapter highlightsAdapter;
    private ArrayList<SCVideo> sCShowsStore;
    private ArrayList<SCVideo> sCHighlightsStore;
    private ShowsImageDownloader showsImageDownloader;
    private HighlightImageDownloader highlightImageDownloader;
    private YouTubeAPICall showsAPICall;
    private String showsEndpoint;
    private YouTubeAPICall highlightsAPICall;
    private String highlightsEndpoint;
    private String videoToPass;
    private APIOnResponseDelegate handler;
    private float screenDensity;
    private Bundle bundle;
    private Button showsButton;
    private Button highlightsButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videos_list, container, false);

        Log.d("VideoListFragment", "onCreateView: called");
        showsEndpoint = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL8dd-D6tYC0DfIJarU3NrrTHvPmMkCjTd&maxResults=5&key=AIzaSyCBgwbRkQjNIPraASVj7KxzN0HgoEWiuiI";
        highlightsEndpoint = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL8dd-D6tYC0BeICQ2C3hym16jEyj0SzSJ&maxResults=20&key=AIzaSyCBgwbRkQjNIPraASVj7KxzN0HgoEWiuiI";

        showsButton = (Button) v.findViewById(R.id.shows_button);
        showsButton.setOnClickListener(new View.OnClickListener() {
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
                if (sCHighlightsStore == null) {
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
        showsButton.setTextColor(getResources().getColor(R.color.WhiteColor));
        highlightsButton.setBackgroundColor(getResources().getColor(R.color.SCGrayColor));
        highlightsButton.setTextColor(getResources().getColor(R.color.WhiteColor));

        if (sCVideoRecyclerView == null) {
            sCVideoRecyclerView = (RecyclerView) v.findViewById(R.id.videos_recycler_view);
            sCVideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        if (savedInstanceState != null) {
            Log.d("SAVED INSTANCE STATE", "onSaveInstanceState: called");
            sCShowsStore = savedInstanceState.getParcelableArrayList(SHOWS_VIDEO_STORE);
            if (savedInstanceState.getParcelableArrayList(HIGHLIGHTS_VIDEO_STORE) != null) {
                sCHighlightsStore = savedInstanceState.getParcelableArrayList(HIGHLIGHTS_VIDEO_STORE);
            } else {
                highlightsAPICall = new YouTubeAPICall(highlightsEndpoint, this, screenDensity);
            }
        }

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("VideoListFragment", "onStart: called");

        screenDensity = getResources().getDisplayMetrics().density;

        if (sCShowsStore == null && isNetworkEnabled(getContext())) {
            showsAPICall = new YouTubeAPICall(showsEndpoint, this, screenDensity);
            showsAPICall.execute();
            highlightsAPICall = new YouTubeAPICall(highlightsEndpoint, this, screenDensity);
        }
    }

    @Override
    public void onResume() {
        super.onStart();
        Log.d("VideoListFragment", "onResume: called");
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(SHOWS_VIDEO_STORE, sCShowsStore);
        if (sCHighlightsStore != null) {
            Log.d("SAVED INSTANCE STATE", "onSaveInstanceState: called");
            savedInstanceState.putParcelableArrayList(HIGHLIGHTS_VIDEO_STORE, sCHighlightsStore);
        }
    }

    


    @Override
    public void onPreStart() {
        handler.onPreStart();
    }




    @Override
    public void onVideoResponse(ArrayList<SCVideo> videoStore) {
        if (highlightsButton.isEnabled()){
            this.sCShowsStore = videoStore;
            showsImageDownloader = new ShowsImageDownloader(sCShowsStore, this);
            showsImageDownloader.execute();
        } else {
            this.sCHighlightsStore = videoStore;
            highlightImageDownloader = new HighlightImageDownloader(sCHighlightsStore, this);
            highlightImageDownloader.execute();
        }

    }


    @Override
    public void onShowImageDownload(ArrayList<SCVideo> sCShowVideos) {
        this.sCShowsStore = sCShowVideos;
        passVideo(sCShowsStore.get(0).getVideoID());
        showsUISetup();
    }

    @Override
    public void onHighlightImageDownload(ArrayList<SCVideo> sCHighlightVideos) {
        this.sCHighlightsStore = sCHighlightVideos;
        highlightsUISetup();
    }


    private void showsUISetup() {
        if (showsAdapter == null) {
            showsAdapter = new ShowsVideoAdapter(this.sCShowsStore);
            sCVideoRecyclerView.setAdapter(showsAdapter);
        } else {
            sCVideoRecyclerView.swapAdapter(showsAdapter, true);
        }
    }

    private void highlightsUISetup() {
        if (highlightsAdapter == null) {
            highlightsAdapter = new HighlightsVideoAdapter(this.sCHighlightsStore);
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


        public ShowsVideoAdapter(ArrayList<SCVideo> videos) {
            sCShowsStore = videos;
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
            Bitmap image = sCShowsStore.get(position).getThumbnailBitmap();

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

        public HighlightsVideoAdapter(ArrayList<SCVideo> videos) {
            sCHighlightsStore = videos;
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
            Bitmap image = sCHighlightsStore.get(position).getThumbnailBitmap();

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
            } else {
                Toast.makeText(context, "network is not available", Toast.LENGTH_LONG).show();
            }
        } return available;
    }


    private void passVideo(String videoID) {

        if (videoToPass != videoID) {
            videoToPass = videoID;
            FragmentManager fragmentManager = getFragmentManager();
            VideosFragment.YouTubePlayerFragment videosFragment = (VideosFragment.YouTubePlayerFragment) fragmentManager.findFragmentByTag("YOUTUBE_PLAYER_FRAGMENT");

            if (videosFragment != null) {
                videosFragment.onVideoSelected(videoToPass);
            }
        }
    }
}
