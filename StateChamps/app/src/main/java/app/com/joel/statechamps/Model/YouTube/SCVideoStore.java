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
        if (sSCVideoStore == null) {
            sSCVideoStore = new SCVideoStore(context);
        }
        return sSCVideoStore;
    }

    private SCVideoStore(Context context){

        video1 = new SCVideo();
        video1.setTitle("State Champs! - May 8th, 2016 Show");
        video1.setVideoID("l9x3C6kF-zQ");
        video1.setPublishedDate("2016-05-09T12:35:41.000Z");
        video1.setThumbnailURL("https://i.ytimg.com/vi/l9x3C6kF-zQ/default.jpg");

        video2 = new SCVideo();
        video2.setTitle("State Champs! High School Sports Show - May 1st, 2016 Show");
        video2.setVideoID("we0mk_J0zyc");
        video2.setPublishedDate("2016-05-09T12:35:41.000Z");
        video2.setThumbnailURL("https://i.ytimg.com/vi/Ed-GmaTnywM/default.jpg");

        video3 = new SCVideo();
        video3.setTitle("State Champs! - April 24th, 2016 Show");
        video3.setVideoID("6PnhlXLHKAE");
        video3.setPublishedDate("2016-04-25T16:07:52.000Z");
        video3.setThumbnailURL("https://i.ytimg.com/vi/UgBpAegPrtM/default.jpg");

        mSCVideosList = new ArrayList<>();
        mSCVideosList.add(video1);
        mSCVideosList.add(video2);
        mSCVideosList.add(video3);

    }

    public List<SCVideo> getSCVideosList() {
        return mSCVideosList;
    }

}
