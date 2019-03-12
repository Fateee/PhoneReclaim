package com.yc.phonerecycle.interfaces;

import android.util.Log;
import com.yc.phonerecycle.app.BaseApplication;
import com.yc.phonerecycle.model.bean.base.BaseRep;
import com.yc.phonerecycle.model.bean.biz.DictTypeRep;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class RequestObserver<P> implements Observer<P> {

//    Observer mObserver = new Observer<P>() {
//        @Override
//        public void onSubscribe(Disposable d) {
//
//        }
//
//        @Override
//        public void onNext(P o) {
//
//        }
//
//        @Override
//        public void onError(Throwable e) {
//
//        }
//
//        @Override
//        public void onComplete() {
//
//        }
//    };


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(P p) {
        if (p instanceof Response) {
            Response value = (Response) p;
            if (value.code() == 200 && value.body() != null && value.body() instanceof BaseRep) {
                Log.e(TAG,"--------");
            }
        }
    }

    private static final String TAG = "RequestObserver";

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
