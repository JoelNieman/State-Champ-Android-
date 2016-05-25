package app.com.joel.statechamps.Model.Parse;

import android.graphics.Bitmap;

import com.parse.ParseFile;

import java.net.URL;

/**
 * Created by Joel on 5/24/16.
 */
public class SCArticle {
    private String title;
    private String author;
    private String publishedDate;
    private String body;
    private String sport;
    private URL articleURL;
    private String imageString;
    private Bitmap imageBitmap;
    private ParseFile imageFile;

    public SCArticle() {

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

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
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
}
