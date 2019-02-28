package com.yc.phonerecycle.mvp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.SystemProperties;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.yc.phonerecycle.mvp.presenter.base.BasePresenter;
import com.yc.phonerecycle.mvp.presenter.base.BaseViewInf;

/**
 * Created by Administrator on 2018/1/18.
 */

public abstract class BaseActivity <P extends BasePresenter> extends AppCompatActivity implements BaseViewInf {

    private ProgressDialog loadingDialog;

    BasePresenter mPresenter;

    public P getPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
            if (mPresenter != null) mPresenter.attach(this);
        }
        return (P) mPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundle();
        setContentView(getContentView());
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
//        PushAgent.getInstance(this).onAppStart();
        initView();
        getPresenter();
        initDatas();
    }

//    public void initData() {
//    }

    protected abstract void initBundle();

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract P createPresenter();

    protected abstract void initDatas();


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
        if (getPresenter() == null) return;
        getPresenter().deAttach();
    }

    public void showLoading() {
        loadingDialog = ProgressDialog.show(this,"","");
    }

    public void dismissLoading() {
        loadingDialog.dismiss();
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void OnEventbus(MessageEvent messageEvent) {
//        switch (messageEvent.getMsgCode()) {
//            case EventConst.CHECK_APP_INFO:
//                AppVerInfo appinfo = (AppVerInfo) messageEvent.getObj();
//                int versionCode = BuildConfig.VERSION_CODE;
//                AppVerInfo.DataBean verinfo = appinfo.getData();
//                if (verinfo.getAppcode() > versionCode) {//有新版
//                    DownloadManager manager = DownloadManager.getInstance(this);
//                    manager.setApkName(verinfo.getAppname()+".apk").setApkUrl(verinfo.getAppurl()).setSmallIcon(R.drawable.ic_launcher)
//                            .setApkDescription(verinfo.getAppinfo()).setApkVersionCode(verinfo.getAppcode()).setApkVersionName(verinfo.getAppversion())
//                            .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate").download(BaseActivity.this);
//                }
//                break;
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }

    /** Get the system properties */
    protected boolean getSystemProperties(String name, boolean def) {
        return SystemProperties.getBoolean("debug.test.basic." + name, def);
    }

    protected boolean hasSystemFeature(String name) {
        return getPackageManager().hasSystemFeature(name);
    }
}
