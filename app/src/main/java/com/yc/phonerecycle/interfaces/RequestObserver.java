package com.yc.phonerecycle.interfaces;

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
    public void onNext(P o) {

    }


    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
