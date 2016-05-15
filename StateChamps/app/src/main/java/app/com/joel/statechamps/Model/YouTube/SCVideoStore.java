package app.com.joel.statechamps.Model.YouTube;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joel on 5/14/16.
 */

public class SCVideoStore {

    private static SCVideoStore sSCVideoStore;
    private List<SCVideo> mSCVideosList;

    private SCVideo video1;
    private SCVideo video2;
    private SCVideo video3;


    public static SCVideoStore getSCVideoStore(Context context) {
//        if (sSCVideoStore == null) {
            sSCVideoStore = new SCVideoStore(context);
//        }
        return sSCVideoStore;
    }

    private SCVideoStore(Context context){

        video1 = new SCVideo();
        video1.setTitle("All Night Long (All Night)");
        video1.setVideoID("nqAvFx3NxUM");
        video1.setDescription("");
        video1.setPublishedDate("");
        video1.setThumbnailURL("");

        video2 = new SCVideo();
        video2.setTitle("Say You, Say Me");
        video2.setVideoID("we0mk_J0zyc");
        video2.setDescription("");
        video2.setPublishedDate("");
        video2.setThumbnailURL("");

        video3 = new SCVideo();
        video3.setTitle("Running With The Night");
        video3.setVideoID("6PnhlXLHKAE");
        video3.setDescription("");
        video3.setPublishedDate("");
        video3.setThumbnailURL("");

        mSCVideosList = new ArrayList<>();
        mSCVideosList.add(video1);
        mSCVideosList.add(video2);
        mSCVideosList.add(video3);

    }

    public List<SCVideo> getSCVideosList() {
        return mSCVideosList;
    }

}
