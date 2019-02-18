package com.yc.phonerecycle.mvp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.yc.phonerecycle.mvp.presenter.base.BasePresenter;
import com.yc.phonerecycle.mvp.presenter.base.BaseViewInf;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseViewInf {

    private P mP;
    public static final int LATEST_TYPE = 1;
    public static final int CATEGORY_TYPE = 2;
    public static final int CHOSEN_TYPE = 3;
    private ProgressDialog loadingDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
        mP = getPresenter();
        if (mP == null) return;
        mP.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initData();
    }

    protected abstract P getPresenter();

    protected abstract int getContentView();

    protected abstract void initViews(View view);

    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
        if (mP == null) return;
        mP.deAttach();
    }
    public void showLoading() {
        loadingDialog = ProgressDialog.show(getContext(),"","");
    }

    public void dismissLoading() {
        loadingDialog.dismiss();
    }
}
