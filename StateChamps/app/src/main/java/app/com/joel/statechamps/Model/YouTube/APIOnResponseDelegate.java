package app.com.joel.statechamps.Model.YouTube;

import java.util.ArrayList;

/**
 * Created by Joel on 4/28/16.
 */
public interface APIOnResponseDelegate {

    public void onPreStart();
    public void onVideoResponse(ArrayList<SCVideo> videos);
}


