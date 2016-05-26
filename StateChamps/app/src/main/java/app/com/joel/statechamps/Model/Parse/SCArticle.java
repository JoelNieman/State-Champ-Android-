package app.com.joel.statechamps.Model.Parse;

import android.graphics.Bitmap;

import com.parse.ParseFile;

import java.net.URL;
import java.util.UUID;

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
}
