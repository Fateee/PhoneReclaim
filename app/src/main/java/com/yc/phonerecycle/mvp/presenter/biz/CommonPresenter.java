package com.yc.phonerecycle.mvp.presenter.biz;

import android.util.Log;
import com.yc.phonerecycle.mvp.presenter.base.BasePresenter;
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV;
import com.yc.phonerecycle.network.BaseRetrofit;
import com.yc.phonerecycle.network.reqinterface.CommonRequest;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CommonPresenter extends BasePresenter<CommonBaseIV> {

    private static final String TAG = "CommonPresenter";
    private final CommonRequest mCommonRequest;
    private int pageNo = 1;
    private int pageNum = 15;


    public CommonPresenter() {
        mCommonRequest = BaseRetrofit.getInstance().createRequest(CommonRequest.class);
    }


//    public void getNewBabyDetail(String residentId) {
//        if (getView() == null) return;
//        getView().showLoading();
//        mCommonRequest.getNewbornBabyFollowup(residentId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Response<NewBornVisitRepBean>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(Response<NewBornVisitRepBean> value) {
//                        getView().dismissLoading();
//                        Log.i(TAG, "value.code() == " + value.code());
//                        if (value.code() == 200 && value.body() != null && value.body() instanceof NewBornVisitRepBean) {
//                            Log.i(TAG, "value.body() == " + value.body());
//                            NewBornVisitRepBean configResponse = value.body();
//                            if (configResponse != null) {
//                                if (getView() instanceof CommonBaseIV.AddNewBabyVisitIV)
//                                    ((CommonBaseIV.AddNewBabyVisitIV) getView()).getNewBabyvistSuccess(configResponse.data);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.w(TAG, "onError : " + e.getMessage());
//                        getView().dismissLoading();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//    }

}
