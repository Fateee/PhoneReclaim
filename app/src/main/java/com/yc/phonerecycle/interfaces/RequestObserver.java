package com.yc.phonerecycle.interfaces;

import android.content.Intent;
import android.util.Log;
import com.yc.phonerecycle.activity.LoginActivity;
import com.yc.phonerecycle.app.BaseApplication;
import com.yc.phonerecycle.model.bean.base.BaseRep;
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter;
import com.yc.phonerecycle.utils.ToastUtil;
import com.yc.phonerecycle.utils.UserInfoUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import java.io.IOException;

public abstract class RequestObserver<P> implements Observer<P> {
    public static CommonPresenter logoutPresenter = new CommonPresenter();


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(P p) {
        if (p instanceof Response) {
            Response value = (Response) p;
            if (value.code() == 200 && value.body() != null && value.body() instanceof BaseRep) {
                int code = ((BaseRep) value.body()).code;
                Log.e(TAG,"code == "+code);
                if (code == 401) {
                    onTokenExpire();
                } else {
                    onResponse(p);
                }
            } else if (value.code() == 401) {
                onTokenExpire();
            } else {
                try {
                    ToastUtil.showShortToastCenter(value.code()+" "+value.message()+"\n"
                    +value.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void onTokenExpire() {
        logoutPresenter.logout();
        UserInfoUtils.cleanUser();
        UserInfoUtils.cleanUserInfo();
        UserInfoUtils.cleanUserWxTokenRep();
        UserInfoUtils.cleanUserQQTokenRep();
        Intent intent = new Intent(BaseApplication.getAppContext(),LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        BaseApplication.getAppContext().startActivity(intent);
    }

    public abstract void onResponse(P p);

    private static final String TAG = "RequestObserver";

    @Override
    public void onError(Throwable e) {
        Log.w(TAG, "onError : " + e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
