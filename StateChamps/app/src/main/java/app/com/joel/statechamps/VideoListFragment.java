package app.com.joel.statechamps;

import android.content.Context;
import android.graphics.Bitmap;
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

import java.util.ArrayList;

import app.com.joel.statechamps.Model.YouTube.APIOnResponseDelegate;
import app.com.joel.statechamps.Model.YouTube.OnImageDownloadDelegate;
import app.com.joel.statechamps.Model.YouTube.SCVideo;
import app.com.joel.statechamps.Model.YouTube.YouTubeAPICall;

/**
 * Created by Joel on 5/14/16.
 */

public class VideoListFragment extends Fragment implements APIOnResponseDelegate, OnImageDownloadDelegate {

    private RecyclerView sCVideoRecyclerView;
    private VideoAdapter adapter;
    private ArrayList<SCVideo> sCVideoStore;
    private ArrayList<Bitmap> imageStore;
    private ImageDownloader imageDownloader;
    private YouTubeAPICall showsAPICall;
    private String showsEndpoint;
    private YouTubeAPICall highlightsAPICall;
    private String highlightsEndpoint;
    private APIOnResponseDelegate handler;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videos_list, container, false);

        sCVideoRecyclerView = (RecyclerView) v.findViewById(R.id.videos_recycler_view);
        sCVideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        showsEndpoint = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL8dd-D6tYC0DfIJarU3NrrTHvPmMkCjTd&maxResults=5&key=AIzaSyCBgwbRkQjNIPraASVj7KxzN0HgoEWiuiI";

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

    @Override
    public void onShowVideoResponse(ArrayList<SCVideo> sCVideoStore) {
        this.sCVideoStore = sCVideoStore;
        imageDownloader = new ImageDownloader(sCVideoStore, this);
        imageDownloader.execute();
        Toast.makeText(getActivity(), "The Shows APICall was successful",Toast.LENGTH_SHORT).show();
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




    private class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private ImageView mImageView;
        private SCVideo mSCVideo;
        private Bitmap mBitmap;


        public VideoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.video_title);
            mImageView = (ImageView) itemView.findViewById(R.id.thumbnail_image_view);

        }

        public void bindSCVideo(SCVideo video, Bitmap image) {
            mSCVideo = video;
            mBitmap = image;

            mTitleTextView.setText(mSCVideo.getTitle());
            mImageView.setImageBitmap(mBitmap);
        }

        @Override
        public void onClick(View v) {

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
            SCVideo video = sCVideoStore.get(position);
            Bitmap image = imageStore.get(position);

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

}
