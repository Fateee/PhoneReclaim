package com.yc.phonerecycle.mvp.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemProperties;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.phonerecycle.R;
import com.yc.phonerecycle.mvp.presenter.base.BasePresenter;
import com.yc.phonerecycle.mvp.presenter.base.BaseViewInf;
import com.yc.phonerecycle.utils.*;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/18.
 */

public abstract class BaseActivity <P extends BasePresenter> extends AppCompatActivity implements BaseViewInf {

    private ProgressDialog loadingDialog;

    BasePresenter mPresenter;
    private Dialog cameraDialog;

    public P getPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
            if (mPresenter != null) mPresenter.attach(this);
        }
        if (mPresenter != null && mPresenter.getView() == null) mPresenter.attach(this);
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
        if (!NetUtil.checkNetworkState()) {
            ToastUtil.showShortToastCenter("无网络连接");
        }
        if (loadingDialog != null && loadingDialog.isShowing()) {
            return;
        }
        if (isFinishing()) return;
        loadingDialog = ProgressDialog.show(this,"","",false,true);
    }

    public void dismissLoading() {
        if (loadingDialog == null) return;
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

    public void showPermissionDialog(String title,String msg) {
        if (cameraDialog == null) {
            cameraDialog = DialogHelper.showDialog(
                    "1",
                    BaseActivity.this,
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
                        PermissionUtils.openPermissionSettings(BaseActivity.this);
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

    /**
     * 友盟开始授权(登入)
     */
    public static void shareLoginUmeng(final Activity activity, SHARE_MEDIA share_media_type) {

        UMShareAPI.get(activity).getPlatformInfo(activity, share_media_type, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.e("授权开始", "onStart授权开始: ");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                //sdk是6.4.5的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");//名称
                String gender = map.get("gender");//性别
                String iconurl = map.get("iconurl");//头像地址

                Log.e("openid", "onStart授权完成: " + openid);
                Log.e("unionid", "onStart授权完成: " + unionid);
                Log.e("access_token", "onStart授权完成: " + access_token);
                Log.e("refresh_token", "onStart授权完成: " + refresh_token);
                Log.e("expires_in", "onStart授权完成: " + expires_in);
                Log.e("uid", "onStart授权完成: " + uid);
                Log.e("name", "onStart授权完成: " + name);
                Log.e("gender", "onStart授权完成: " + gender);
                Log.e("iconurl", "onStart授权完成: " + iconurl);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(activity, "授权失败", Toast.LENGTH_LONG).show();
                Log.e("onError", "onError: " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(activity, "授权取消", Toast.LENGTH_LONG).show();
                Log.e("onError", "onError: " + "授权取消");
            }
        });
    }
}
