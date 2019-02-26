package com.yc.phonerecycle.network.reqinterface;


import com.yc.phonerecycle.model.bean.base.BaseRep;
import com.yc.phonerecycle.model.bean.biz.LoginRep;
import com.yc.phonerecycle.model.bean.biz.UserInfoRep;
import com.yc.phonerecycle.model.bean.request.*;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

public interface CommonRequest {
    @POST("v1/auth/login")
    Observable<Response<LoginRep>> login(@Body LoginReqBody info);


    @Headers("Content-Type: application/json")
    @POST("v1/auth/loginout")
    Observable<Response<BaseRep>> loginout(@Query("Authorization") String Authorization);

    @Headers("Content-Type: application/json")
    @POST("v1/cSms/sendCode")
    Observable<Response<BaseRep>> sendCode(@Query("phone") String phone,@Query("Authorization") String Authorization);


    @Headers("Content-Type: application/json")
    @POST("v1/account/register")
    Observable<Response<BaseRep>> register(@Body RegisterReqBody info);

    @Headers("Content-Type: application/json")
    @POST("v1/account/restPasswordByPhone")
    Observable<Response<BaseRep>> restPasswordByPhone(@Body ResetPwdByPhoneReqBody info);


    @GET("v1/userCenter/getInfo")
    Observable<Response<UserInfoRep>> getInfo();

    @Headers("Content-Type: application/json")
    @POST("v1/setting/changeName")
    Observable<Response<BaseRep>> changeName(@Query("name") String name,@Query("useId") String useId,@Query("Authorization") String Authorization);

    @Headers("Content-Type: application/json")
    @POST("v1/setting/changeSignature")
    Observable<Response<BaseRep>> changeSignature(@Query("phone") String phone,@Query("useId") String useId, @Query("signature") String signature,@Query("Authorization") String Authorization);


    @Headers("Content-Type: application/json")
    @POST("v1/setting/createWithdrawPassword")
    Observable<Response<BaseRep>> createWithdrawPassword(@Query("password") String password,@Query("userId") String userId);

    @Headers("Content-Type: application/json")
    @POST("v1/setting/resetWithdrawPassword")
    Observable<Response<BaseRep>> resetWithdrawPassword(@Body ChangePwdReqBody settingChangeVO);

    @Headers("Content-Type: application/json")
    @POST("v1/setting/resetPassword")
    Observable<Response<BaseRep>> resetPassword(@Body ChangePwdReqBody settingChangeVO);


    @Headers("Content-Type: application/json")
    @POST("v1/setting/changePhone")
    Observable<Response<BaseRep>> changePhone(@Query("code") String code,@Query("phone") String phone);


    // 回收相关.......
    @Headers("Content-Type: application/json")
    @POST("v1/goodsInstance/saveOrUpdate")
    Observable<Response<BaseRep>> saveOrUpdate(@Body CheckReqBody goodsInstanceVO,@Query("userId") String userId);

}
