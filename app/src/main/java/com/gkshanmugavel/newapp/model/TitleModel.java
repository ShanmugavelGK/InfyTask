package com.gkshanmugavel.newapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Elements of Row items
 */
public class TitleModel implements Serializable {
    @SerializedName("title")
    @Expose
    private String title = null;
    @SerializedName("description")
    @Expose
    private String description = null;
    @SerializedName("imageHref")
    @Expose
    private String imageHref = null;

    public String getTitle() {
        return title == null ? "No Title" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description == null ? "No description" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }
}
