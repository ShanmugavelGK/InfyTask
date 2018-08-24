package com.gkshanmugavel.newapp.view_model;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gkshanmugavel.newapp.R;
import com.gkshanmugavel.newapp.model.TitleModel;
import com.gkshanmugavel.newapp.view.HomeActivity;

/**
 * Home class view model
 */
public class HomeViewModel {
    Activity activity;

    public HomeViewModel(Activity activity) {
        this.activity = activity;
    }

    /**
     * @param view     image view
     * @param imageUrl image url
     */
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_image);
        requestOptions.error(R.drawable.ic_image);

        Glide.with(view.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl)

                .into(view);
    }

    /**
     * @param v     View
     * @param model set the Model class
     */
    public void onClick(View v, TitleModel model) {
        ((HomeActivity) activity).setChangeTitle(model.getTitle());
    }


    /**
     * @param v
     * @param description description from API
     */
    @BindingAdapter({"bind:setDescription"})
    public static void setDescription(TextView v, String description) {
        if (description == null) {
            v.setText(v.getContext().getString(R.string.no_description));
        } else {
            v.setText(description);
        }
    }


    /**
     * @param v
     * @param title title from API
     */
    @BindingAdapter({"bind:setTitle"})
    public static void setTitle(TextView v, String title) {
        v.setText(title != null ? title : v.getContext().getString(R.string.no_title));
    }


    @BindingAdapter({"bind:visibility"})
    public static void setVisibility(View v, TitleModel mTitle) {
        if (mTitle.getTitle() == null && mTitle.getDescription() == null && mTitle.getImageHref() == null)
            v.setVisibility(View.GONE);
        else
            v.setVisibility(View.VISIBLE);

    }
}
