package com.yc.phonerecycle.mvp.presenter.biz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import third.ErrorResponseEntity;
import third.wx.SsoLoginManager;
import third.wx.SsoLoginType;

import java.util.Map;

public class CommonPresenter extends BasePresenter<CommonBaseIV> {

    private static final String TAG = "CommonPresenter";
    private final CommonRequest mCommonRequest;
    private int pageNo = 1;
    private int pageNum = 15;


    public CommonPresenter() {
        mCommonRequest = BaseRetrofit.getInstance().createRequest(CommonRequest.class);
    }

    public void getDictType() {
        if (getView() == null) return;
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
        if (getView() == null) return;
        mCommonRequest.getDictMappingByType(dicTypeId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
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
//        loginView.showProgressDialog();
        SsoLoginManager.login(context, type, new SsoLoginManager.LoginListener() {
            @Override
            public void onSuccess(String accessToken, String uId, long expiresIn, @Nullable final String wholeData) {
                super.onSuccess(accessToken, uId, expiresIn, wholeData);
                Map<String, Object> body = JSON.parseObject(wholeData, Map.class);
                getView().showLoading();
//                Toast.makeText(context, "登录成功-----" + wholeData, Toast.LENGTH_LONG).show();
                if (type.equals(SsoLoginType.WEIXIN)) {
                    getWXToken(body);
//                    ((CommonBaseIV.LoginViewIV) getView()).loginWX(accessToken, uId, expiresIn, wholeData,body);
                } else if (type.equals(SsoLoginType.QQ)) {
//                    ((CommonBaseIV.LoginViewIV) getView()).loginQQ(accessToken, uId, expiresIn, wholeData);
                    QqTokenRep result = JSON.parseObject(wholeData, QqTokenRep.class);
                    UserInfoUtils.saveUserQQTokenRep(result);
                    ThirdLoginInfoRep.DataBean thirdVO = new ThirdLoginInfoRep.DataBean();
                    thirdVO.updateInfo(result.access_token,result.openid,result.access_token,result.expires_in);
                    saveThirdTokenInfo(thirdVO);
                }
//                TokenUtils.requestToken(context, body, new JMHttpRequest.INetworkListener() {
//                    @Override
//                    public void onSuccess(BaseResponseEntity response) {
//                        handleWithLoginSucc(loginView, context);
//
//                    }
//
//                    @Override
//                    public void onError(int code, ErrorResponseEntity errorMsg) {
//                        Log.e(TAG,"; code = " + code + "; msg = " + errorMsg.errorMsg + "; body =" + wholeData);
//                        handleWithLoginFail(loginView, errorMsg);
//                        if (code == 10051) loginView.finishActivity();
//
//                    }
//                });
            }

            @Override
            public void onError(int code, @NonNull ErrorResponseEntity errorMsg) {
                super.onError(code, errorMsg);
//                handleWithLoginFail(loginView, errorMsg);
//                if (code == 10051) loginView.finishActivity();
            }

            @Override
            public void onCancel() {
                super.onCancel();
//                loginView.dismissProgressDialog();
            }
        });
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
                            saveThirdTokenInfo(thirdVO);
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
                                    login(context,type);
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

    public void saveThirdTokenInfo(final ThirdLoginInfoRep.DataBean thirdVO) {
        if (getView() == null) return;
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

    private void getSystemToekn(String userId, String openID) {
        if (getView() == null) return;
        GetTokenReqBody body = new GetTokenReqBody();
        body.userId = userId;
        body.openId = openID;
        mCommonRequest.getSystemToken(body)
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
    public void sendCode(String phone) {
        if (getView() == null) return;
        mCommonRequest.sendCode(phone)
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

    public void changeName(String name, String useId) {
        if (getView() == null) return;
        mCommonRequest.changeName(name,useId)
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

    public void changeSignature(String phone, String useId, String signature) {
        if (getView() == null) return;
        mCommonRequest.changeSignature(phone,useId,signature)
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
        mCommonRequest.createWithdrawPassword(password,userId)
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
        mCommonRequest.changePhone(code,phone)
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
    public void saveOrUpdate(CheckReqBody goodsInstanceVO,String userId) {
        if (getView() == null) return;
        mCommonRequest.saveOrUpdate(goodsInstanceVO,userId)
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

    public void getUserBankCard() {
        if (getView() == null) return;
        getView().showLoading();
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
                .subscribe(new Observer<Response<NearByShopRep.DataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<NearByShopRep.DataBean> value) {
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
                .subscribe(new Observer<Response<NearByShopRep.DataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<NearByShopRep.DataBean> value) {
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
}
