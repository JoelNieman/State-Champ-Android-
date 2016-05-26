package app.com.joel.statechamps.Model.Parse;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseFile;

import java.net.URL;
import java.util.UUID;

/**
 * Created by Joel on 5/24/16.
 */

public class SCArticle implements Parcelable {
    private String title;
    private String author;
    private String publishedDate;
    private String body;
    private String sport;
    private URL articleURL;
    private String imageURLString;
    private Bitmap imageBitmap;
    private ParseFile imageFile;
    private UUID Id;

    public SCArticle() {
        Id = UUID.randomUUID();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public URL getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(URL articleURL) {
        this.articleURL = articleURL;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public ParseFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(ParseFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageURLString() {
        return imageURLString;
    }

    public void setImageURLString(String imageURLString) {
        this.imageURLString = imageURLString;
    }

    public UUID getId(){
        return Id;
    }


    protected SCArticle(Parcel in) {
        title = in.readString();
        author = in.readString();
        publishedDate = in.readString();
        body = in.readString();
        sport = in.readString();
        articleURL = (URL) in.readValue(URL.class.getClassLoader());
        imageURLString = in.readString();
        imageBitmap = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        imageFile = (ParseFile) in.readValue(ParseFile.class.getClassLoader());
        Id = (UUID) in.readValue(UUID.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publishedDate);
        dest.writeString(body);
        dest.writeString(sport);
        dest.writeValue(articleURL);
        dest.writeString(imageURLString);
        dest.writeValue(imageBitmap);
        dest.writeValue(imageFile);
        dest.writeValue(Id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SCArticle> CREATOR = new Parcelable.Creator<SCArticle>() {
        @Override
        public SCArticle createFromParcel(Parcel in) {
            return new SCArticle(in);
        }

        @Override
        public SCArticle[] newArray(int size) {
            return new SCArticle[size];
        }
    };
}
