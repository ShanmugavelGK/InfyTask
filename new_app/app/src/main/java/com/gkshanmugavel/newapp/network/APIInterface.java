package com.gkshanmugavel.newapp.network;


import com.gkshanmugavel.newapp.model.ResponseBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface APIInterface {

    @Headers({"Content-Type: application/json"})
    @GET("facts.json")
    Call<ResponseBean> getRowList();
}
