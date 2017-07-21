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
    private int postId; //WHY AREN'T MY VALIDATION ERRORS WORKIIIIING :) :(

    @NotNull
    @Size(min=5, max=25)
    private String title;

    @Size(min=1)
    private String textBody;

    @Size(min=10, max=100)
    private String headline;

    //Here I would create a field for the image. Do I need to create
    //a separate class for images, and name it image?//

    public Post() {
    }

    public Post(String title, String textBody, String headline) {
        this.title = title;
        this.textBody = textBody;
        this.headline = headline;
    }

    public int getPostId() {
        return postId;
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
