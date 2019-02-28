package com.yc.phonerecycle.mvp.presenter.biz;

import android.util.Log;
import com.yc.phonerecycle.app.BaseApplication;
import com.yc.phonerecycle.model.bean.base.BaseRep;
import com.yc.phonerecycle.model.bean.biz.*;
import com.yc.phonerecycle.model.bean.request.*;
import com.yc.phonerecycle.mvp.presenter.base.BasePresenter;
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV;
import com.yc.phonerecycle.network.BaseRetrofit;
import com.yc.phonerecycle.network.reqinterface.CommonRequest;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


}
