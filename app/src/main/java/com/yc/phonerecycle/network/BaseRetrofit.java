package com.yc.phonerecycle.network;

import android.util.Log;
import com.yc.phonerecycle.constant.UrlConst;
import com.yc.phonerecycle.utils.UserInfoUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseRetrofit {

    private OkHttpClient client;
    private volatile static BaseRetrofit mBaseRetrofit;
    private Retrofit retrofit;
    private String baseUrl;

    enum UrlType {
        DEFAULT,
        WEIXIN,
        QQ
    }

    private BaseRetrofit(UrlType type) {
        switch (type) {
            case DEFAULT:
                baseUrl = UrlConst.mBaseUrl;
                break;
            case WEIXIN:
                baseUrl = UrlConst.mWXUrl;
                break;
            case QQ:
                baseUrl = UrlConst.mQQUrl;
                break;
            default:
                baseUrl = UrlConst.mBaseUrl;
                break;
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Log.i("RetrofitLog", "Authorization = " + UserInfoUtils.getUserToken());
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Authorization", UserInfoUtils.getUserToken())
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .build();
    }

    public static BaseRetrofit getInstance() {
        if (mBaseRetrofit == null) {
            synchronized (BaseRetrofit.class) {
                if (mBaseRetrofit == null) {
                    mBaseRetrofit = new BaseRetrofit(UrlType.DEFAULT);
                }
            }
        }
        return mBaseRetrofit;
    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public <T> T initRetrofit(Class<T> service) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(UrlConst.mBaseUrl).addConverterFactory(GsonConverterFactory.create());
        if (UrlConst.debug && client != null) {
            builder.client(client);
        }
        Retrofit mRetrofit = builder.build();
        return mRetrofit.create(service);
    }

    public <T> T initRetrofit(String url, Class<T> service) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url).addConverterFactory(GsonConverterFactory.create());
        if (UrlConst.debug && client != null) {
            builder.client(client);
        }
        Retrofit mRetrofit = builder.build();
        return mRetrofit.create(service);
    }

    public <T> T createRequest(final Class<T> service) {
        return getRetrofit().create(service);
    }

    private static class WxTokenHolder {
        private static BaseRetrofit instance = new BaseRetrofit(UrlType.WEIXIN);
    }

    public static BaseRetrofit getWxInstance() {
        return WxTokenHolder.instance;
    }

    private static class QQTokenHolder {
        private static BaseRetrofit instance = new BaseRetrofit(UrlType.QQ);
    }

    public static BaseRetrofit getQQInstance() {
        return QQTokenHolder.instance;
    }
}
