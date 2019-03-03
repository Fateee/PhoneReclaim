package com.yc.phonerecycle.network.reqinterface;


import com.yc.phonerecycle.model.bean.biz.*;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.*;

public interface WeiXinRequest {

    //微信
    //access_token
    @GET("sns/oauth2/access_token")
    Observable<Response<WxTokenRep>> getAccessToken(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code, @Query("grant_type") String grant_type);

    @GET("sns/oauth2/refresh_token")
    Observable<Response<WxTokenRep>> refreshAccessToken(@Query("appid") String appid, @Query("refresh_token") String refresh_token, @Query("grant_type") String grant_type);

    @GET("sns/userinfo")
    Observable<Response<WxUserInfoRep>> getWxUserInfo(@Query("openid") String openid, @Query("access_token") String access_token);
}
