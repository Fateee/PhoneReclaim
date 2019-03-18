package com.yc.phonerecycle.network.reqinterface;


import com.yc.phonerecycle.model.bean.base.BaseRep;
import com.yc.phonerecycle.model.bean.biz.*;
import com.yc.phonerecycle.model.bean.request.SaveBankReqBody;
import com.yc.phonerecycle.model.bean.request.*;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.Map;

public interface CommonRequest {

    @Multipart
    @POST("v1/cFileManger/upload")
    Observable<Response<UploadFileRep>> uploadFile(@Part("interactionFile") RequestBody firstBody,@Part MultipartBody.Part file/* @PartMap Map<String, RequestBody> parms*/);

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
    Observable<Response<BaseRep>> sendCode(@Body SendCodeBody phone);


    @Headers("Content-Type: application/json")
    @POST("v1/account/register")
    Observable<Response<BaseRep>> register(@Body RegisterReqBody info);

    @Headers("Content-Type: application/json")
    @POST("v1/account/restPasswordByPhone")
    Observable<Response<BaseRep>> restPasswordByPhone(@Body ResetPwdByPhoneReqBody info);


    @GET("v1/userCenter/getInfo")
    Observable<Response<UserInfoRep>> getInfo();


    //设置
    @Headers("Content-Type: application/json")
    @POST("v1/setting/changeLog")
    Observable<Response<BaseRep>> changeLog(@Body ChangeLogoBody changeLogoVO);

    @Headers("Content-Type: application/json")
    @POST("v1/setting/changeName")
    Observable<Response<BaseRep>> changeName(@Body ChangeNameBody changeLogoVO);

    @Headers("Content-Type: application/json")
    @POST("v1/setting/changeSignature")
    Observable<Response<BaseRep>> changeSignature(@Body ChangeSignBody changeSingnatureVO);

    @Headers("Content-Type: application/json")
    @POST("v1/setting/createWithdrawPassword")
    Observable<Response<BaseRep>> createWithdrawPassword(@Body createWithdrawPasswordBody changePasswordVO);

    @Headers("Content-Type: application/json")
    @POST("v1/setting/resetWithdrawPassword")
    Observable<Response<BaseRep>> resetWithdrawPassword(@Body ChangePwdReqBody settingChangeVO);

    @Headers("Content-Type: application/json")
    @POST("v1/setting/resetPassword")
    Observable<Response<BaseRep>> resetPassword(@Body ChangePwdReqBody settingChangeVO);

    @Headers("Content-Type: application/json")
    @POST("v1/setting/changePhone")
    Observable<Response<BaseRep>> changePhone(@Body ChangePhoneBody changePhoneVO);

    //添加用户反馈
    @Headers("Content-Type: application/json")
    @POST("v1/tickling/addTickling")
    Observable<Response<BaseRep>> addFeedback(@Body FeedbackReqBody ticklingVO);

    //关于我们
    @GET("v1/detection/getAboutUsVO")
    Observable<Response<AboutUsRep>> getAboutUsVO();


    // 回收相关.......
    @Headers("Content-Type: application/json")
    @POST("v1/goodsInstance/saveOrUpdate")
    Observable<Response<SaveRecordRep>> saveOrUpdate(@Body CheckReqBody goodsInstanceVO);

    //银行卡
    @Headers("Content-Type: application/json")
    @POST("v1/userBank/saveBankCard")
    Observable<Response<BaseRep>> saveBankCard(@Body SaveBankReqBody userBankVO);

    @Headers("Content-Type: application/json")
    @POST("v1/userBank/deleteCardbyId")
    Observable<Response<BaseRep>> deleteCardbyId(@Query("id") String id);

    @GET("v1/userBank/getUserBankCard")
    Observable<Response<BankCardListRep>> getUserBankCard();




    //回收
    @Headers("Content-Type: application/json")
    @POST("v1/order/addOrder")
    Observable<Response<BaseRep>> addOrder(@Body RecycleReqBody orderVO);

    @GET("v1/goodsInstance/getGoodsInstanceById/{id}")
    Observable<Response<OrderDetailRep>> getGoodsInstanceById(@Path("id") String id);

//    @GET("v1/order/getGoodsInstanceById/{id}")
//    Observable<Response<OrderDetailRep>> getGoodsInstanceById(@Path("id") String id);


    //个人中心 我的订单
    @GET("v1/orders/getAssistantMyOrderList/{status}")
    Observable<Response<MyOrderListlRep>> getAssistantMyOrderList(@Path("status") String status);

    @GET("v1/orders/getMyOrderList/{userId}/{status}")
    Observable<Response<MyOrderListlRep>> getMyOrderList(@Path("userId") String userId,@Path("status") String status);

    @GET("v1/orders/getOrderDetailbyId/{id}")
    Observable<Response<MyOrderDetailRep>> getOrderDetailbyId(@Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("v1/orders/writeTracking")
    Observable<Response<BaseRep>> writeTracking(@Body WriteTrackReqBody writeTrackingVO);

    //个人中心 附近的商家
    @GET("v1/store/getNearby/{longitude}/{latitude}")
    Observable<Response<NearByShopRep>> getNearby(@Path("longitude") String longitude,@Path("latitude") String latitude);

    @GET("v1/store/getStoreDetail/{id}")
    Observable<Response<ShopDetailRep>> getStoreDetail(@Path("id") String id);

    //店铺信息(店员)
    @GET("v1/stores/getMyStore")
    Observable<Response<ShopDetailRep>> getMyStore();


    //检测记录
    //获取我的检测记录(店员)
    @GET("v1/detection/getAssistantDetection/{status}")
    Observable<Response<DetectionRep>> getAssistantDetection(@Path("status") String status);

    //0802-根据检测记录查询验机报告
    @GET("v1/detection/getGoodsInstanceReportVO/{goodsInstanceId}")
    Observable<Response<PhoneReportRep>> getGoodsInstanceReportVO(@Path("goodsInstanceId") String goodsInstanceId);

    //获取我的检测记录
    @GET("v1/detection/getMyDetection")
    Observable<Response<DetectionRep>> getMyDetection();




    //钱包-提现
    //余额没有返回数字
    @GET("v1/userMoney/existDrawPassword")
    Observable<Response<ExistDrawPasswordRep>> existDrawPassword();

    //余额没有返回数字
    @GET("v1/userMoney/getUserBankCard")
    Observable<Response<UserMoneyRep>> getUserMoney();

    @Headers("Content-Type: application/json")
    @POST("v1/userMoney/inputWithdrawPassword")//输入验证提现密码
    Observable<Response<BaseRep>> inputWithdrawPassword(@Query("password") String password);

    @Headers("Content-Type: application/json")
    @POST("v1/userMoney/saveUserMoney")
    Observable<Response<BaseRep>> saveUserMoney(@Body CashAccountReqBody writeTrackingVO);

    @Headers("Content-Type: application/json")
    @POST("v1/userMoney/saveWXBankCard")//保存用户微信授权
    Observable<Response<BaseRep>> saveWXBankCard(@Query("openId") String openId);

    //手动检测品牌
    //获取品牌
    @GET("v1/mBrand/getBrandSelect")
    Observable<Response<BrandRep>> getBrandSelect(@Query("name") String name);

    //获取商品
    @GET("v1/mBrand/getGoodsByBrandId/{brandId}/")
    Observable<Response<BrandGoodsRep>> getGoodsByBrandId(@Path("brandId") String brandId);


    //评估 回收信息 地址

    @Headers("Content-Type: application/json")
    @POST("v1/address/deleteById")//根据id删除地址
    Observable<Response<BaseRep>> deleteById(@Query("id") String id);

    @GET("v1/address/getByAddressById/{id}")
    Observable<Response<RecycleAddrRep>> getByAddressById(@Path("id") String id);

    @GET("v1/address/getUserAddress")
    Observable<Response<RecycleAddrRep>> getUserAddress();

    @Headers("Content-Type: application/json")
    @POST("v1/address/saveOrUpdateAddress")
    Observable<Response<StringDataRep>> saveOrUpdateAddress(@Body RecycleAddrRep.DataBean addressVO);


    //第三方授权登录
    @Headers("Content-Type: application/json")
    @POST("v1/thirdLogin/getSystemToken")
    Observable<Response<LoginRep>> getSystemToken(@Body GetTokenReqBody thirdLoginVO);

    @GET("v1/thirdLogin/getThirdTokenByOpenId/{openId}")
    Observable<Response<ThirdLoginInfoRep>> getThirdTokenByOpenId(@Path("openId") String openId);

    @Headers("Content-Type: application/json")
    @POST("v1/thirdLogin/saveThirdTokenInfo")
    Observable<Response<ThirdLoginInfoRep>> saveThirdTokenInfo(@Body ThirdLoginInfoRep.DataBean thirdVO);

    @Headers("Content-Type: application/json")
    @POST("v1/thirdLogin/saveUserPhone")
    Observable<Response<ThirdLoginInfoRep>> saveUserPhone(@Body ThirdPhoneBody thirdLoginSaveUserVo);


    @Headers("Content-Type: application/json")
    @POST("v1/adminDivision/list")
    Observable<Response<DivisionRep>> queryDivision(/*@Path("page") String page,@Path("size") String size,*/@Body DivisionQueryBody adminDivisionQueryVo);
}
