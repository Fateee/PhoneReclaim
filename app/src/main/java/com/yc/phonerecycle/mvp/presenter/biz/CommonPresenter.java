package com.yc.phonerecycle.mvp.presenter.biz;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.phonerecycle.app.BaseApplication;
import com.yc.phonerecycle.constant.BaseConst;
import com.yc.phonerecycle.model.bean.base.BaseRep;
import com.yc.phonerecycle.model.bean.biz.*;
import com.yc.phonerecycle.model.bean.request.*;
import com.yc.phonerecycle.mvp.presenter.base.BasePresenter;
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV;
import com.yc.phonerecycle.network.BaseRetrofit;
import com.yc.phonerecycle.network.reqinterface.CommonRequest;
import com.yc.phonerecycle.network.reqinterface.WeiXinRequest;
import com.yc.phonerecycle.utils.UserInfoUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import third.wx.SsoLoginType;

import java.io.File;
import java.util.Map;

public class CommonPresenter extends BasePresenter<CommonBaseIV> {

    private static final String TAG = "CommonPresenter";
    private final CommonRequest mCommonRequest;
    private int pageNo = 1;
    private int pageNum = 15;


    public CommonPresenter() {
        mCommonRequest = BaseRetrofit.getInstance().createRequest(CommonRequest.class);
    }

    public void uploadFile(File file) {
        if (getView() == null) return;
        getView().showLoading();
        Log.i(TAG, "file.exists == " + file.exists());
        RequestBody firstBody = RequestBody.create( MediaType.parse("multipart/form-data"), "test");

        RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);//content-type为image/png，其中byteArray中的数据对应图中(5)处
        MultipartBody.Part fileparam = MultipartBody.Part.createFormData("interactionFile", file.getName(), body);//分别对应图中(3)、(4)

        mCommonRequest.uploadFile(firstBody,fileparam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<UploadFileRep>>() {

                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onNext(Response<UploadFileRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (getView() == null) return;
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });
    }

    public void getDictType() {
        mCommonRequest.getDictType()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Response<DictTypeRep>>() {

                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onNext(Response<DictTypeRep> value) {
                        if (value.code() == 200 && value.body() != null) {
                            BaseApplication.mRootItems = value.body().data;
                            if (BaseApplication.mRootItems != null && !BaseApplication.mRootItems.isEmpty()) {
                                for (DictTypeRep.DataBean obj :BaseApplication.mRootItems) {
                                    getDictMappingByType(obj.id);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });
    }

    public void getDictMappingByType(final String dicTypeId) {
        mCommonRequest.getDictMappingByType(dicTypeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DictMapRep>>() {

                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(Response<DictMapRep> value) {
                        if (value.code() == 200 && value.body() != null) {
                            BaseApplication.mOptionMap.put(dicTypeId,value.body().data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });
    }

    public void loginAction(String loginName, String pwd) {
        if (getView() == null) return;
        getView().showLoading();
        LoginReqBody body = new LoginReqBody(loginName,pwd);
        mCommonRequest.login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<LoginRep>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<LoginRep> value) {
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null) {
                            ((CommonBaseIV.LoginViewIV) getView()).loginResponse(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                        getView().dismissLoading();
                    }
                });
    }

    public void login(final Context context, @SsoLoginType final String type) {
        if (getView() == null) return;
        getView().showLoading();
        SHARE_MEDIA share_media_type = SHARE_MEDIA.WEIXIN;
        if (type.equals(SsoLoginType.WEIXIN)) {
            share_media_type = SHARE_MEDIA.WEIXIN;
        } else if (type.equals(SsoLoginType.QQ)) {
            share_media_type = SHARE_MEDIA.QQ;
        }
        UMShareAPI.get(context).getPlatformInfo((Activity) context, share_media_type, new UMAuthListener() {
            @Override
            public void onStart(com.umeng.socialize.bean.SHARE_MEDIA share_media) {
                Log.e("授权开始", "onStart授权开始: ");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                //sdk是6.4.5的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");//名称
                String gender = map.get("gender");//性别
                String iconurl = map.get("iconurl");//头像地址

                Log.e("openid", "onStart授权完成: " + openid);
                Log.e("unionid", "onStart授权完成: " + unionid);
                Log.e("access_token", "onStart授权完成: " + access_token);
                Log.e("refresh_token", "onStart授权完成: " + refresh_token);
                Log.e("expires_in", "onStart授权完成: " + expires_in);
                Log.e("uid", "onStart授权完成: " + uid);
                Log.e("name", "onStart授权完成: " + name);
                Log.e("gender", "onStart授权完成: " + gender);
                Log.e("iconurl", "onStart授权完成: " + iconurl);

                ThirdLoginInfoRep.DataBean thirdVO = new ThirdLoginInfoRep.DataBean();
                thirdVO.updateInfo(access_token,openid,refresh_token,expires_in);
                thirdVO.nickName = name;
                thirdVO.logo = iconurl;
                thirdVO.gender = gender;
                String json = JSON.toJSONString(thirdVO);
                Log.i(TAG, "saveThirdTokenInfo == " + json);
                thirdVO.responseJson = json;

                if (type.equals(SsoLoginType.WEIXIN)) {
                    UserInfoUtils.cleanUserWxTokenRep();
                    WxTokenRep tokenRep = new WxTokenRep(access_token,expires_in,refresh_token,openid,"");
                    UserInfoUtils.saveUserWxTokenRep(tokenRep);
                    thirdVO.openType = "2";
                } else if (type.equals(SsoLoginType.QQ)) {
                    UserInfoUtils.cleanUserQQTokenRep();
                    QqTokenRep result = new QqTokenRep(access_token,expires_in,refresh_token,openid);
                    UserInfoUtils.saveUserQQTokenRep(result);
                    thirdVO.openType = "1";
                }
                saveThirdTokenInfo(thirdVO,type);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(context, "授权失败", Toast.LENGTH_LONG).show();
                Log.e("onError", "onError: " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(context, "授权取消", Toast.LENGTH_LONG).show();
                Log.e("onError", "onError: " + "授权取消");
            }
        });
//        SsoLoginManager.login(context, type, new SsoLoginManager.LoginListener() {
//            @Override
//            public void onSuccess(String accessToken, String uId, long expiresIn, @Nullable final String wholeData) {
//                super.onSuccess(accessToken, uId, expiresIn, wholeData);
//                Map<String, Object> body = JSON.parseObject(wholeData, Map.class);
//                getView().showLoading();
////                Toast.makeText(context, "登录成功-----" + wholeData, Toast.LENGTH_LONG).show();
//                if (type.equals(SsoLoginType.WEIXIN)) {
//                    getWXToken(body);
////                    ((CommonBaseIV.LoginViewIV) getView()).loginWX(accessToken, uId, expiresIn, wholeData,body);
//                } else if (type.equals(SsoLoginType.QQ)) {
////                    ((CommonBaseIV.LoginViewIV) getView()).loginQQ(accessToken, uId, expiresIn, wholeData);
//                    QqTokenRep result = JSON.parseObject(wholeData, QqTokenRep.class);
//                    UserInfoUtils.saveUserQQTokenRep(result);
//                    ThirdLoginInfoRep.DataBean thirdVO = new ThirdLoginInfoRep.DataBean();
//                    thirdVO.updateInfo(result.access_token,result.openid,result.access_token,result.expires_in);
//                    saveThirdTokenInfo(thirdVO,SsoLoginType.QQ);
//                }
////                TokenUtils.requestToken(context, body, new JMHttpRequest.INetworkListener() {
////                    @Override
////                    public void onSuccess(BaseResponseEntity response) {
////                        handleWithLoginSucc(loginView, context);
////
////                    }
////
////                    @Override
////                    public void onError(int code, ErrorResponseEntity errorMsg) {
////                        Log.e(TAG,"; code = " + code + "; msg = " + errorMsg.errorMsg + "; body =" + wholeData);
////                        handleWithLoginFail(loginView, errorMsg);
////                        if (code == 10051) loginView.finishActivity();
////
////                    }
////                });
//            }
//
//            @Override
//            public void onError(int code, @NonNull ErrorResponseEntity errorMsg) {
//                super.onError(code, errorMsg);
////                handleWithLoginFail(loginView, errorMsg);
////                if (code == 10051) loginView.finishActivity();
//            }
//
//            @Override
//            public void onCancel() {
//                super.onCancel();
////                loginView.dismissProgressDialog();
//            }
//        });
    }

    public void getWXToken(Map<String, Object> body) {
        WeiXinRequest wxRequest = BaseRetrofit.getWxInstance().createRequest(WeiXinRequest.class);
        wxRequest.getAccessToken(BaseConst.WEIXIN_APPID,BaseConst.WEIXIN_SERCET, (String) body.get("code"),BaseConst.WEIXIN_TYPE_AUTH_CODE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<WxTokenRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<WxTokenRep> value) {
                        getView().dismissLoading();
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            UserInfoUtils.saveUserWxTokenRep(value.body());
                            refreshWXToken();
//                            ThirdLoginInfoRep.DataBean thirdVO = new ThirdLoginInfoRep.DataBean();
//                            thirdVO.updateInfo(value.body().access_token,value.body().openid,value.body().refresh_token,value.body().expires_in);
//                            getUserWXInfo(thirdVO);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                        getView().dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void refreshWXToken() {
        WeiXinRequest wxRequest = BaseRetrofit.getWxInstance().createRequest(WeiXinRequest.class);
        String refresh_token = UserInfoUtils.getUserWxTokenRep().refresh_token;
        if (TextUtils.isEmpty(refresh_token)) return;
        wxRequest.refreshAccessToken(BaseConst.WEIXIN_APPID,refresh_token, BaseConst.WEIXIN_TYPE_REFRESH_CODE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<WxTokenRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<WxTokenRep> value) {
                        getView().dismissLoading();
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            UserInfoUtils.saveUserWxTokenRep(value.body());
                            ThirdLoginInfoRep.DataBean thirdVO = new ThirdLoginInfoRep.DataBean();
                            thirdVO.updateInfo(value.body().access_token,value.body().openid,value.body().refresh_token,value.body().expires_in);
                            getUserWXInfo(thirdVO);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                        getView().dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getUserWXInfo(final ThirdLoginInfoRep.DataBean thirdVO) {
        String openid = thirdVO.openID;
        String access_token = thirdVO.accessToken;
        WeiXinRequest wxRequest = BaseRetrofit.getWxInstance().createRequest(WeiXinRequest.class);
        wxRequest.getWxUserInfo(openid,access_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<WxUserInfoRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<WxUserInfoRep> value) {
                        getView().dismissLoading();
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            thirdVO.nickName = value.body().nickname;
                            thirdVO.logo = value.body().headimgurl;
                            thirdVO.gender = value.body().sex+"";
                            saveThirdTokenInfo(thirdVO,SsoLoginType.WEIXIN);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                        getView().dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getThirdTokenByOpenId(final Context context,String openId,@SsoLoginType final String type) {
        if (getView() == null) return;
        mCommonRequest.getThirdTokenByOpenId(openId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ThirdLoginInfoRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(Response<ThirdLoginInfoRep> value) {
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null) {
                            if (value.body().data != null) {
                                //todo 是否过期
                                boolean expire = false;
                                if (expire) {

                                } else {
                                    if (type.equals(SsoLoginType.WEIXIN)) {
                                        refreshWXToken();
                                    } else if (type.equals(SsoLoginType.QQ)) {

                                    }
//                                    login(context,type);
                                }
                            } else {
                                login(context,type);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                        getView().dismissLoading();
                    }
                });
    }

    public void saveThirdTokenInfo(final ThirdLoginInfoRep.DataBean thirdVO, final String type) {
        if (getView() == null) return;
//        String json = JSON.toJSONString(thirdVO);
//        Log.i(TAG, "saveThirdTokenInfo == " + json);
//        thirdVO.responseJson = json;
//        if (type.equals(SsoLoginType.WEIXIN)) {
//            thirdVO.openType = "2";
//        } else if (type.equals(SsoLoginType.QQ)) {
//            thirdVO.openType = "1";
//        }
        mCommonRequest.saveThirdTokenInfo(thirdVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ThirdLoginInfoRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(Response<ThirdLoginInfoRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            if (value.body().data == null || TextUtils.isEmpty(value.body().data.userId)) {
                                //第一次创建
                                //去绑定手机页
                                ((CommonBaseIV.ThirdLoginViewIV) getView()).goBindPhoneView(value.body().data.openID,type);
                            } else {
                                getSystemToekn(value.body().data.userId,thirdVO.openID);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void saveUserPhone(String code, String openId, String phone) {
        if (getView() == null) return;
        ThirdPhoneBody thirdLoginSaveUserVo = new ThirdPhoneBody(code,openId,phone);
        mCommonRequest.saveUserPhone(thirdLoginSaveUserVo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ThirdLoginInfoRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(Response<ThirdLoginInfoRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null && value.body().data != null) {
                            getSystemToekn(value.body().data.userId,value.body().data.openID);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getSystemToekn(String userId, String openID) {
        if (getView() == null) return;
        GetTokenReqBody body = new GetTokenReqBody();
        body.userId = userId;
        body.openId = openID;
        mCommonRequest.getSystemToken(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<LoginRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<LoginRep> value) {
                        getView().dismissLoading();
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            UserInfoUtils.cleanUser();
                            UserInfoUtils.saveUser(value.body());
                            ((CommonBaseIV.LoginViewIV) getView()).loginResponse(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                        getView().dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    public void logout() {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.loginout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        getView().dismissLoading();
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.LoginViewIV) getView()).loginResponse(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                        getView().dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    public void sendCode(int businessType, String phone) {//1 注册验证码 2 忘记密码
        if (getView() == null) return;
        SendCodeBody phoneBody = new SendCodeBody(businessType, phone);
        mCommonRequest.sendCode(phoneBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.SignUpIv) getView()).requestCodeOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                        ((CommonBaseIV.SignUpIv) getView()).requestCodeError();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void register(String code, String password, String phone, String referrer) {
        if (getView() == null) return;
        getView().showLoading();
        RegisterReqBody info = new RegisterReqBody(code, password, phone, referrer);
        mCommonRequest.register(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        getView().dismissLoading();
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.SignUpIv) getView()).registerSuccess(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                        getView().dismissLoading();
                        ((CommonBaseIV.SignUpIv) getView()).registerError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void restPasswordByPhone(String code, String password, String phone) {
        if (getView() == null) return;
        getView().showLoading();
        ResetPwdByPhoneReqBody info = new ResetPwdByPhoneReqBody(code, password, phone);
        mCommonRequest.restPasswordByPhone(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        getView().dismissLoading();
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.ResetPwdByPhoneIv) getView()).resetPwdByPhoneOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                        getView().dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getInfo() {
        if (getView() == null) return;
        mCommonRequest.getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<UserInfoRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<UserInfoRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (getView() == null) return;
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.UserInfoIV) getView()).userInfoSuccess(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void changeLog(String logo, String useId) {
        if (getView() == null) return;
        ChangeLogoBody changeLogoVO = new ChangeLogoBody(logo,useId);
        mCommonRequest.changeLog(changeLogoVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.UploadFileIV) getView()).uploadFileSuccess(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void changeName(String name, String useId) {
        if (getView() == null) return;
        ChangeNameBody changeLogoVO = new ChangeNameBody(name,useId);
        mCommonRequest.changeName(changeLogoVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.EditUserInfoIV) getView()).editNickNameSuccess(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void changeSignature(String useId, String signature) {
        if (getView() == null) return;
        ChangeSignBody changeSignBody = new ChangeSignBody(signature,useId);
        mCommonRequest.changeSignature(changeSignBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.EditUserInfoIV) getView()).editUserSignSuccess(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void resetWithdrawPassword(String oldPasswd, String password, String userId) {
        if (getView() == null) return;
        ChangePwdReqBody body = new ChangePwdReqBody(oldPasswd, password, userId);
        mCommonRequest.resetWithdrawPassword(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.resetWithdrawPasswordIV) getView()).resetWithdrawPasswordOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void resetPassword(String oldPasswd, String password, String userId) {
        if (getView() == null) return;
        ChangePwdReqBody body = new ChangePwdReqBody(oldPasswd, password, userId);
        mCommonRequest.resetPassword(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.resetPasswordIV) getView()).resetPasswordOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void createWithdrawPassword(String password,String userId) {
        if (getView() == null) return;
        createWithdrawPasswordBody changePasswordVO = new createWithdrawPasswordBody(password,userId);
        mCommonRequest.createWithdrawPassword(changePasswordVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.createWithdrawPasswordIV) getView()).createWithdrawPassword(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void changePhone(String code, String phone) {
        if (getView() == null) return;
        ChangePhoneBody body = new ChangePhoneBody(code,phone);
        mCommonRequest.changePhone(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.changePhoneIV) getView()).changePhone(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void addFeedback(String id, String remark) {
        if (getView() == null) return;
        getView().showLoading();
        FeedbackReqBody ticklingVO = new FeedbackReqBody(id,remark);
        mCommonRequest.addFeedback(ticklingVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.FeedbakcIV) getView()).addFeedbackOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getAboutUsVO() {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getAboutUsVO()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<AboutUsRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<AboutUsRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.AboutUsIV) getView()).getAboutUsOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }



    // 回收相关.......
    public void saveOrUpdate(CheckReqBody goodsInstanceVO) {
        if (getView() == null) return;
        mCommonRequest.saveOrUpdate(goodsInstanceVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<SaveRecordRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<SaveRecordRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.saveOrUpdateIV) getView()).saveOrUpdate(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void saveBankCard(String acount, String cardholder, String id, String openingBank, String userId) {
        if (getView() == null) return;
        getView().showLoading();
        SaveBankReqBody ticklingVO = new SaveBankReqBody(acount, cardholder, id, openingBank, userId);
        mCommonRequest.saveBankCard(ticklingVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.saveBankCardIV) getView()).saveBankCardOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    public void deleteCardbyId(String id) {
        if (getView() != null ) {
            getView().showLoading();
        }
        mCommonRequest.deleteCardbyId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (getView() != null) {
                            getView().dismissLoading();
                        }
                        if (value.code() == 200 && value.body() != null ) {
                            if (getView() != null) {
                                ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getUserBankCard() {
        if (getView() != null ) {
            getView().showLoading();
        }
        mCommonRequest.getUserBankCard()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BankCardListRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BankCardListRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (getView() != null) {
                            getView().dismissLoading();
                        }
                        if (value.code() == 200 && value.body() != null ) {
                            if (getView() != null) {
                                ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                            }
                            if (bankcardListener != null) {
                                bankcardListener.onBankcardGetOk(value.body());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getUserMoney() {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getUserMoney()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<UserMoneyRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<UserMoneyRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void inputWithdrawPassword(String password) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.inputWithdrawPassword(password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.MoneyIV) getView()).cashPwdOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void saveUserMoney(CashAccountReqBody writeTrackingVO) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.saveUserMoney(writeTrackingVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.MoneyIV) getView()).saveMoneyBankOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void saveWXBankCard(String openId) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.saveWXBankCard(openId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.MoneyIV) getView()).saveMoneyWXOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    public void getNearbyShop(String longitude, String latitude) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getNearby(longitude,latitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<NearByShopRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<NearByShopRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    public void getStoreDetail(String id) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getStoreDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ShopDetailRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<ShopDetailRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getMyStore() {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getMyStore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ShopDetailRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<ShopDetailRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getBrandSelect(String name,final int type) {
        if (getView() == null) return;
        mCommonRequest.getBrandSelect(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BrandRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BrandRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonTypeIV) getView()).getDataOK(value.body(),type);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getGoodsByBrandId(final int type,String brandId) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getGoodsByBrandId(brandId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BrandGoodsRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BrandGoodsRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonTypeIV) getView()).getDataOK(value.body(),type);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getGoodsInstanceById(String id) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getGoodsInstanceById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<OrderDetailRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<OrderDetailRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getAssistantMyOrderList(String status) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getAssistantMyOrderList(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<MyOrderListlRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<MyOrderListlRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    public void getMyOrderList(String userId, String status) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getMyOrderList(userId,status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<MyOrderListlRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<MyOrderListlRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getAssistantDetection(String status) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getAssistantDetection(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DetectionRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<DetectionRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getGoodsInstanceReport(String goodsInstanceId) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getGoodsInstanceReportVO(goodsInstanceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<PhoneReportRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<PhoneReportRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getMyDetection() {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getMyDetection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DetectionRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<DetectionRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getOrderDetailbyId(String id) {
        if (getView() == null) return;
        getView().showLoading();
        mCommonRequest.getOrderDetailbyId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<MyOrderDetailRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<MyOrderDetailRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void writeTracking(String courierCompany, String orderId, String trackingNumber) {
        if (getView() == null) return;
        getView().showLoading();
        WriteTrackReqBody writeTrackingVO = new WriteTrackReqBody(courierCompany,orderId,trackingNumber);
        mCommonRequest.writeTracking(writeTrackingVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        getView().dismissLoading();
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissLoading();
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    public void queryDivision(DivisionQueryBody divisionQueryVo, final int type) {
        mCommonRequest.queryDivision(divisionQueryVo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DivisionRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<DivisionRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null && divisionListener !=null) {
                            divisionListener.onDivisionGetOk(value.body(),type);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void addOrder(RecycleReqBody orderVO) {
        mCommonRequest.addOrder(orderVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<BaseRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.CommonIV) getView()).getDataOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void saveOrUpdateAddress(RecycleAddrRep.DataBean addressVO) {
        mCommonRequest.saveOrUpdateAddress(addressVO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StringDataRep>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<StringDataRep> value) {
                        Log.i(TAG, "value.code() == " + value.code());
                        if (value.code() == 200 && value.body() != null ) {
                            ((CommonBaseIV.SaveAddrIV) getView()).saveAddrOK(value.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private DivisionListener divisionListener;

    public void setDivisionListener(DivisionListener divisionListener) {
        this.divisionListener = divisionListener;
    }

    public interface DivisionListener{
        void onDivisionGetOk(DivisionRep dataBean,int type);
    }

    private BankcardListener bankcardListener;

    public void setBankcardListener(BankcardListener bankcardListener) {
        this.bankcardListener = bankcardListener;
    }

    public interface BankcardListener{
        void onBankcardGetOk(BankCardListRep dataBean);
    }
}
