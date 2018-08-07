package com.gkshanmugavel.newapp.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.gkshanmugavel.newapp.R;
import com.gkshanmugavel.newapp.databinding.ActivityHomeBinding;
import com.gkshanmugavel.newapp.model.ResponseBean;
import com.gkshanmugavel.newapp.model.TitleModel;
import com.gkshanmugavel.newapp.network.APIInterface;
import com.gkshanmugavel.newapp.network.RetrofitFactory;
import com.gkshanmugavel.newapp.utils.MyProgressDialog;
import com.gkshanmugavel.newapp.utils.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding activityHomeBinding;
    private Activity mActivity;
    private MyProgressDialog progressDialog;

    private APIInterface mAPIInterface;
    List<TitleModel> titleModels;
    RowAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        mActivity = HomeActivity.this;

        activityHomeBinding.customToolBar.toolbarTitle.setText("Click List change title");
        activityHomeBinding.sflRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAPI(false);
            }
        });

        // Scheme colors for animation
        activityHomeBinding.sflRefresh.setColorSchemeColors(
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.md_white_1000),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.md_deep_purple_A200)
        );

        callAPI(true);

    }

    /**
     * API CAL
     * @param isProgressBar progress bar visibilty
     */
    private void callAPI(final boolean isProgressBar) {

        if (!Utility.isInternetConnected(mActivity)) {
            Utility.showSnackBar(mActivity, mActivity.getString(R.string.no_internet_connection));
            return;
        }

        if (isProgressBar) {
            progressDialog = new MyProgressDialog();
            progressDialog.showDialog(mActivity);
        }
        mAPIInterface = RetrofitFactory.createService(APIInterface.class);
        Call<ResponseBean> call = mAPIInterface.getRowList();

        call.enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                try {
                    if (activityHomeBinding.sflRefresh != null)
                        activityHomeBinding.sflRefresh.setRefreshing(false);
                    if (isProgressBar)
                        progressDialog.dismissDialog();
                    if (response.code() == 200 && response.isSuccessful()) {
                        titleModels = response.body().rows;
                        adapter = new RowAdapter(HomeActivity.this, titleModels);
                        activityHomeBinding.rvItems.setLayoutManager(new LinearLayoutManager(mActivity));
                        activityHomeBinding.rvItems.setAdapter(adapter);
                    } else {
                        Utility.showSnackBar(mActivity, response.message());
                    }
                } catch (
                        NullPointerException e)

                {
                    e.printStackTrace();
                } catch (
                        Exception e)

                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {
                try {
                    if (activityHomeBinding.sflRefresh != null)
                        activityHomeBinding.sflRefresh.setRefreshing(false);
                    if (isProgressBar)
                        progressDialog.dismissDialog();
                    Utility.logError(t.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * @param title Tool bar Title
     */

    public void setChangeTitle(String title) {
        activityHomeBinding.customToolBar.toolbarTitle.setText(title);
    }
}
