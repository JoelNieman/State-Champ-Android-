package app.com.joel.statechamps.Model.YouTube;

import org.json.JSONArray;

/**
 * Created by Joel on 4/28/16.
 */
public interface OnResponse {

    public void onPreStart();
    public void onResponse(JSONArray response);

}


