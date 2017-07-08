package com.example.finalproject.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//Use the Date Time API somewhere in here?//
/**
 * Created by Natalie on 7/3/2017.
 */
@Entity
public class Post {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String title;

    @NotNull
    private String textBody;

    @NotNull
    @Size(min=10, max=50)
    private String headline;

    //Here I would create a field for the image. Do I need to create
    //a separate class for images, and name it image?//

    public Post() {
    }

    public int getId() {
        return id;
    }

    public Post(int id, String title, String textBody, String headline) {
        this.id = id;
        this.title = title;
        this.textBody = textBody;
        this.headline = headline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }
}
