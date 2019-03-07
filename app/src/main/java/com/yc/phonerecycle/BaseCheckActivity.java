package com.yc.phonerecycle;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemProperties;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.yc.phonerecycle.mvp.presenter.base.BasePresenter;
import com.yc.phonerecycle.mvp.presenter.base.BaseViewInf;
import com.yc.phonerecycle.utils.ToastUtil;

import static android.net.wifi.WifiManager.WIFI_STATE_UNKNOWN;

/**
 * Created by Administrator on 2018/1/18.
 */

public abstract class BaseCheckActivity<P extends BasePresenter> extends AppCompatActivity implements BaseViewInf {

    private ProgressDialog loadingDialog;

    BasePresenter mPresenter;
    private PackageManager pm;
    private Context applicationContext;
    private WifiManager mWifiManager;

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
        applicationContext = getApplicationContext();
        pm = applicationContext.getPackageManager();
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
        if (loadingDialog == null) return;
        loadingDialog.dismiss();
    }

    private void registerWifiBroadcast() {
        mWifiManager = (WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
    }

    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == WifiManager.WIFI_STATE_CHANGED_ACTION) {
                switch (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WIFI_STATE_UNKNOWN)) {

                }
            }
        }

    };


    /** Get the system properties */
    protected boolean getSystemProperties(String name, boolean def) {
        return SystemProperties.getBoolean("debug.test.basic." + name, def);
    }

    protected boolean hasSystemFeature(String name) {
        return getPackageManager().hasSystemFeature(name);
    }
}
