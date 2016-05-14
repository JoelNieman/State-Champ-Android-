package app.com.joel.statechamps.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import app.com.joel.statechamps.R;

/**
 * Created by Joel on 5/10/16.
 */
public class VideosFragment extends Fragment implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayer youTubePlayer;
    private YouTubePlayerSupportFragment youTubePlayerFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videos_tab, container, false);

        youTubePlayerFragment = youTubePlayerFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.playerFrame, youTubePlayerFragment).commit();

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
