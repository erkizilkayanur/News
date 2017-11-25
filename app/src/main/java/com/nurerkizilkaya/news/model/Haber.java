package com.nurerkizilkaya.news.model;

/**
 * Created by a s u s on 24.11.2017.
 */
public class Haber {
    public Haber()
    {

    }
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    private String link;
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    private String date;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String spot;

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
