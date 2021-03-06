package com.gkshanmugavel.newapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of Base Response
 */
public class ResponseBean {

    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("rows")
    @Expose
    public ArrayList<TitleModel> rows = null;
}
