package com.gkshanmugavel.newapp.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gkshanmugavel.newapp.R;
import com.gkshanmugavel.newapp.databinding.ActivityHomeBinding;
import com.gkshanmugavel.newapp.model.ResponseBean;
import com.gkshanmugavel.newapp.model.TitleModel;
import com.gkshanmugavel.newapp.network.APIInterface;
import com.gkshanmugavel.newapp.network.RetrofitFactory;
import com.gkshanmugavel.newapp.utils.DividerItemDecoration;
import com.gkshanmugavel.newapp.utils.MyProgressDialog;
import com.gkshanmugavel.newapp.utils.Utility;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding activityHomeBinding;
    private Activity mActivity;
    private MyProgressDialog progressDialog;

    private APIInterface mAPIInterface;
    ArrayList<TitleModel> titleModels;
    RowAdapter adapter;
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
            mActivity = HomeActivity.this;

            title = getResources().getString(R.string.click_list);
            activityHomeBinding.sflRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    callAPI(false);
                }
            });

            // Scheme colors for animation
            activityHomeBinding.sflRefresh.setColorSchemeColors(
                    getResources().getColor(R.color.colorAccent),
                    getResources().getColor(R.color.md_yellow_900),
                    getResources().getColor(R.color.colorPrimary),
                    getResources().getColor(R.color.md_deep_purple_A200)
            );

            initView();
            if (savedInstanceState != null) {
                title = savedInstanceState.getString("title");
                titleModels = (ArrayList<TitleModel>) savedInstanceState.getSerializable("items"); //Restoring fiveDefns
                adapter.setDataSetChange(titleModels);
            }

            if (titleModels != null && titleModels.size() == 0)
                callAPI(true);

            if (titleModels.size() > 0) {
                activityHomeBinding.tvEmptyView.setText(R.string.no_data);
                activityHomeBinding.tvEmptyView.setVisibility(View.GONE);
                activityHomeBinding.rvItems.setVisibility(View.VISIBLE);
                activityHomeBinding.customToolBar.toolbarTitle.setText(title);
            } else {
                activityHomeBinding.tvEmptyView.setText(R.string.no_data);
                activityHomeBinding.tvEmptyView.setVisibility(View.VISIBLE);
                activityHomeBinding.rvItems.setVisibility(View.GONE);
                activityHomeBinding.customToolBar.toolbarTitle.setText(title);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        titleModels = new ArrayList<>();
        adapter = new RowAdapter(HomeActivity.this, titleModels);
        activityHomeBinding.rvItems.setLayoutManager(new LinearLayoutManager(mActivity));
        activityHomeBinding.rvItems.addItemDecoration(
                new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.divider)));
        activityHomeBinding.rvItems.setAdapter(adapter);
    }

    /**
     * API CAL
     *
     * @param isProgressBar progress bar visibilty
     */
    private void callAPI(final boolean isProgressBar) {

        if (!Utility.isInternetConnected(mActivity)) {
            Utility.showSnackBar(mActivity, mActivity.getString(R.string.no_internet_connection));
            if (activityHomeBinding.sflRefresh != null)
                activityHomeBinding.sflRefresh.setRefreshing(false);
            if (titleModels.size() > 0) {
                activityHomeBinding.tvEmptyView.setText(R.string.no_internet_connection);
                activityHomeBinding.tvEmptyView.setVisibility(View.GONE);
                activityHomeBinding.rvItems.setVisibility(View.VISIBLE);
            } else {
                activityHomeBinding.tvEmptyView.setText(R.string.no_internet_connection);
                activityHomeBinding.tvEmptyView.setVisibility(View.VISIBLE);
                activityHomeBinding.rvItems.setVisibility(View.GONE);
            }
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


                    if (response.raw().cacheResponse() != null) {
                        // true: response was served from cache
                        titleModels = response.body().rows;
                        if (titleModels.size() != 0) {
                            adapter.setDataSetChange(titleModels);
                            activityHomeBinding.tvEmptyView.setVisibility(View.GONE);
                            activityHomeBinding.rvItems.setVisibility(View.VISIBLE);
                        } else {
                            activityHomeBinding.tvEmptyView.setText(R.string.no_data);
                            activityHomeBinding.tvEmptyView.setVisibility(View.VISIBLE);
                            activityHomeBinding.rvItems.setVisibility(View.GONE);
                        }
                    }

                    if (response.raw().networkResponse() != null) {
                        // true: response was served from network/server
                        if (response.code() == 200 && response.isSuccessful()) {
                            titleModels = response.body().rows;
                            if (titleModels.size() != 0) {
                                adapter.setDataSetChange(titleModels);
                                activityHomeBinding.tvEmptyView.setVisibility(View.GONE);
                                activityHomeBinding.rvItems.setVisibility(View.VISIBLE);
                            } else {
                                activityHomeBinding.tvEmptyView.setText(R.string.no_data);
                                activityHomeBinding.tvEmptyView.setVisibility(View.VISIBLE);
                                activityHomeBinding.rvItems.setVisibility(View.GONE);
                            }
                        } else {
                            Utility.showSnackBar(mActivity, response.message());
                            activityHomeBinding.tvEmptyView.setText(response.message());
                            activityHomeBinding.tvEmptyView.setVisibility(View.VISIBLE);
                            activityHomeBinding.rvItems.setVisibility(View.GONE);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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
     * @param mTitle Tool bar Title
     */


    public void setChangeTitle(String mTitle) {
        title = mTitle;
        activityHomeBinding.customToolBar.toolbarTitle.setText(title);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            outState.putString("title", title);
            outState.putSerializable("items", titleModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            title = savedInstanceState.getString("title");
            titleModels = (ArrayList<TitleModel>) savedInstanceState.getSerializable("items"); //Restoring fiveDefns
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
