package app.com.joel.statechamps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.com.joel.statechamps.Model.YouTube.SCVideo;
import app.com.joel.statechamps.Model.YouTube.SCVideoStore;

/**
 * Created by Joel on 5/14/16.
 */

public class VideoListFragment extends Fragment {

    private RecyclerView mSCVideoRecyclerView;
    private VideoAdapter mAdapter;
    private List<SCVideo> mSCVideos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videos_list, container, false);

        mSCVideoRecyclerView = (RecyclerView) v.findViewById(R.id.videos_recycler_view);
        mSCVideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }


    private void updateUI() {
        SCVideoStore videoStore = SCVideoStore.getSCVideoStore(getActivity());
        List<SCVideo> videos = videoStore.getSCVideosList();

        mAdapter = new VideoAdapter(videos);
        mSCVideoRecyclerView.setAdapter(mAdapter);
    }





    private class VideoHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private SCVideo mSCVideo;

        public VideoHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.video_title);
        }

        public void bindSCVideo(SCVideo video) {
            mSCVideo = video;
            mTitleTextView.setText(mSCVideo.getTitle());
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

}
