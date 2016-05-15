package app.com.joel.statechamps.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import app.com.joel.statechamps.R;
import app.com.joel.statechamps.VideoListFragment;

/**
 * Created by Joel on 5/10/16.
 */
public class VideosFragment extends Fragment implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayer youTubePlayer;
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private Fragment dummyFragment;
    private Fragment videoListFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videos_tab, container, false);

        FragmentManager fm = getChildFragmentManager();

        youTubePlayerFragment = youTubePlayerFragment.newInstance();
//        dummyFragment = new DummyFragment();
        videoListFragment = new VideoListFragment();

        FragmentTransaction transaction1 = fm.beginTransaction();
        transaction1.add(R.id.player_frame, youTubePlayerFragment).commit();

        FragmentTransaction transaction2 = fm.beginTransaction();
        transaction2.add(R.id.list_frame, videoListFragment).commit();

        youTubePlayerFragment.initialize("AIzaSyC2l7QsJ54_prbdqZlI1vtbRmBYYms8OVo", this);


        return v;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        this.youTubePlayer = youTubePlayer;
        if (!wasRestored) {
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            youTubePlayer.cueVideo("GaMcsKtBDwE");
        } else {
            Toast.makeText(getContext(), "YouTubePlayer was restored", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getContext(), "YouTubePlayer failed to initialize", Toast.LENGTH_SHORT).show();
    }
}
