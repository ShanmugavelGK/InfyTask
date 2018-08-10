package com.gkshanmugavel.newapp.network;

import com.gkshanmugavel.newapp.BaseApp;
import com.gkshanmugavel.newapp.utils.Constants;
import com.gkshanmugavel.newapp.utils.Utility;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    //setup Cache Directory
    private static File httpCacheDirectory = new File(BaseApp.getAppContext().getCacheDir(), "http-cache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);


    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient httpClient = new OkHttpClient
            .Builder()
            .cache(cache)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addNetworkInterceptor(new ResponseCacheInterceptor())
            .addInterceptor(new OfflineResponseCacheInterceptor())
            .build();



    private static Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create());
    //.addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).baseUrl(Constants.BASE_URL).build();
        return retrofit.create(serviceClass);
    }


    /**
     * Interceptor to cache data and maintain it for a minute.
     * <p>
     * If the same network request is sent within a minute,
     * the response is retrieved from cache.
     */
    private static class ResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=60")
                    .build();
        }
    }

    /**
     * Interceptor to cache data and maintain it for four weeks.
     * <p>
     * If the device is offline, stale (at most four weeks old)
     * response is fetched from the cache.
     */
    private static class OfflineResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!Utility.isInternetConnected(BaseApp.getAppContext())) {
                request = request.newBuilder()
                        .header("Cache-Control",
                                "public, only-if-cached, max-stale=2419200")
                        .build();
            }
            return chain.proceed(request);
        }
    }
}
