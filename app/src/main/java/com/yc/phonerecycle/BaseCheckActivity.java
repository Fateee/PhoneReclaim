package com.yc.phonerecycle;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.fingerprint.FingerprintManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.yc.phonerecycle.app.BaseApplication;
import com.yc.phonerecycle.mvp.presenter.base.BasePresenter;
import com.yc.phonerecycle.mvp.presenter.base.BaseViewInf;
import com.yc.phonerecycle.mvp.view.BaseActivity;
import com.yc.phonerecycle.utils.*;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

    public boolean wifiTest() {
        return DeviceUtil.isWifiAvailable();
    }

    public boolean microTest() {
        PackageManager pm = BaseApplication.getAppContext().getPackageManager();
        boolean hasMicrophone = pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
        return hasMicrophone;
    }

    public boolean speakerTest() {
        return CheckPhoneUtil.doSpeakerTest();
    }

    public boolean openLightOn() {
        if (!BaseApplication.getAppContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
            return false;
        Camera localCamera = Camera.open();
        if (localCamera == null) return false;
        Camera.Parameters localParameters = localCamera.getParameters();
        localParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        localCamera.setParameters(localParameters);
        localCamera.startPreview();
        try
        {
            Thread.sleep(1000L);
//            localParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//            localCamera.setParameters(localParameters);
            localCamera.stopPreview();
            localCamera.release();
            if (localCamera != null)
                return true;
        }
        catch (InterruptedException localInterruptedException)
        {
            return false;
        }
        return false;
    }

    public boolean isVibratorGood() {
        Vibrator localVibrator = (Vibrator)BaseApplication.getAppContext().getSystemService(Context.VIBRATOR_SERVICE);
        localVibrator.vibrate(1000L);
//        localVibrator.vibrate(new long[] { 100L, 10L, 100L, 1000L }, -1);
        return localVibrator.hasVibrator();
    }


    private Dialog cameraDialog;
    public void showPermissionDialog(String title,String msg) {
        if (cameraDialog == null) {
            cameraDialog = DialogHelper.showDialog(
                    "1",
                    BaseCheckActivity.this,
                    null,
                    "",
                    "",
                    title,
                    msg,
                    getString(R.string.cancel),
                    getString(R.string.setting),
                    "",
                    "0168b7",
                    new BaseDialog.IClickListener() {
                        @Override
                        public void click(Dialog dialog) {

                        }
                    },
                    new BaseDialog.IClickListener() {
                        @Override
                        public void click(Dialog dialog) {
                            PermissionUtils.openPermissionSettings(BaseCheckActivity.this);
                        }
                    });
            cameraDialog.setCanceledOnTouchOutside(false);
            cameraDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialogInterface) {

                }
            });
        } else if (cameraDialog.isShowing()) {
            cameraDialog.show();
        }
    }
}
