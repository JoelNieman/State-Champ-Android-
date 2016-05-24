package app.com.joel.statechamps.Model.YouTube;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Joel on 4/28/16.
 */
public class SCVideo implements Parcelable {

    private String title;
    private String publishedDate;
    private String videoID;
    private String thumbnailURL;
    private Bitmap thumbnailBitmap;

    public SCVideo() {

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public Bitmap getThumbnailBitmap() {
        return thumbnailBitmap;
    }

    public void setThumbnailBitmap(Bitmap thumbnailBitmap) {
        this.thumbnailBitmap = thumbnailBitmap;
    }


    protected SCVideo(Parcel in) {
        title = in.readString();
        publishedDate = in.readString();
        videoID = in.readString();
        thumbnailURL = in.readString();
        thumbnailBitmap = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d("Parcelable", "writeToParcel: SCVideo");
        dest.writeString(title);
        dest.writeString(publishedDate);
        dest.writeString(videoID);
        dest.writeString(thumbnailURL);
        dest.writeValue(thumbnailBitmap);
    }


    public static final Parcelable.Creator<SCVideo> CREATOR = new Parcelable.Creator<SCVideo>() {
        @Override
        public SCVideo createFromParcel(Parcel in) {
            return new SCVideo(in);
        }

        @Override
        public SCVideo[] newArray(int size) {
            return new SCVideo[size];
        }
    };
}
