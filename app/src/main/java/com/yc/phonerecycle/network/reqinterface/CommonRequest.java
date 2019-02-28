package com.yc.phonerecycle.network.reqinterface;


import com.yc.phonerecycle.model.bean.base.BaseRep;
import com.yc.phonerecycle.model.bean.biz.*;
import com.yc.phonerecycle.model.bean.request.SaveBankReqBody;
import com.yc.phonerecycle.model.bean.request.*;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.*;

public interface CommonRequest {

    @GET("v1/dict/getDictType")
    Observable<Response<DictTypeRep>> getDictType();

    @GET("v1/dict/getDictMappingByType/{dicTypeId}")
    Observable<Response<DictMapRep>> getDictMappingByType(@Path("dicTypeId") String dicTypeId);

    @POST("v1/auth/login")
    Observable<Response<LoginRep>> login(@Body LoginReqBody info);


    @Headers("Content-Type: application/json")
    @POST("v1/auth/loginout")
    Observable<Response<BaseRep>> loginout();

    @Headers("Content-Type: application/json")
    @POST("v1/cSms/sendCode")
    Observable<Response<BaseRep>> sendCode(@Query("phone") String phone);


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
    Observable<Response<BaseRep>> changeName(@Query("name") String name,@Query("useId") String useId);

    @Headers("Content-Type: application/json")
    @POST("v1/setting/changeSignature")
    Observable<Response<BaseRep>> changeSignature(@Query("phone") String phone,@Query("useId") String useId, @Query("signature") String signature);


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

    @Headers("Content-Type: application/json")
    @POST("v1/tickling/addTickling")
    Observable<Response<BaseRep>> addFeedback(@Body FeedbackReqBody ticklingVO);

    @GET("v1/detection/getAboutUsVO")
    Observable<Response<AboutUsRep>> getAboutUsVO();


    // 回收相关.......
    @Headers("Content-Type: application/json")
    @POST("v1/goodsInstance/saveOrUpdate")
    Observable<Response<BaseRep>> saveOrUpdate(@Body CheckReqBody goodsInstanceVO,@Query("userId") String userId);

    //银行卡
    @Headers("Content-Type: application/json")
    @POST("v1/userBank/saveBankCard")
    Observable<Response<BaseRep>> saveBankCard(@Body SaveBankReqBody userBankVO);

    //回收
    @Headers("Content-Type: application/json")
    @POST("v1/order/addOrder")
    Observable<Response<BaseRep>> addOrder(@Body RecycleReqBody orderVO);

    @GET("v1/order/getGoodsInstanceById/{id}")
    Observable<Response<OrderDetailRep>> getGoodsInstanceById(@Path("id") String id);
}
