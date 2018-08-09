package com.gkshanmugavel.newapp.view_model;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

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
}
