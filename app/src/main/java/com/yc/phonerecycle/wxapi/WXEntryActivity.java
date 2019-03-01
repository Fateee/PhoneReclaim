package com.yc.phonerecycle.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.*;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yc.phonerecycle.constant.BaseConst;
import com.yc.phonerecycle.utils.ToastUtil;
import third.ErrorResponseEntity;
import third.wx.SsoLoginManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by echo on 5/19/15.
 * 用来处理微信登录、微信分享的activity
 * 参考文档: https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317853&lang=zh_CN
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "SL_WeiXinHandlerActivit";

    /**
     * BaseResp的getType函数获得的返回值。1:第三方授权， 2:分享
     */
    static final int TYPE_LOGIN = 1;

    IWXAPI api;

    public static SsoLoginManager.WXLoginRespListener wxRespListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, BaseConst.WEIXIN_APPID, true);
        try {
            api.handleIntent(getIntent(), this);
        }catch (Exception e){
            e.printStackTrace();
        }
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (api != null) {
            try {
                api.handleIntent(getIntent(), this);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp != null) {
            if (resp instanceof SendAuth.Resp && resp.getType() == TYPE_LOGIN) {
                parseLoginResp(this, (SendAuth.Resp) resp, SsoLoginManager.listener);
            }else if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
                WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) resp;
                String extraData =launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
            }else {
//                parseShareResp(resp, SsoShareManager.listener);
            }
        }
        finish();
    }

    ///////////////////////////////////////////////////////////////////////////
    // login
    ///////////////////////////////////////////////////////////////////////////

    public static void login(@NonNull Context context) {
        String appId = BaseConst.WEIXIN_APPID;
        if (TextUtils.isEmpty(appId)) {
            throw new NullPointerException("请通过shareBlock初始化WeiXinAppId");
        }

        IWXAPI api = WXAPIFactory.createWXAPI(context.getApplicationContext(), appId, true);
        api.registerApp(appId);

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        api.sendReq(req); // 这里的请求的回调会在activity中收到，然后通过parseLoginResp方法解析
    }

    /**
     * 解析用户登录的结果
     */
    protected void parseLoginResp(final Activity activity, SendAuth.Resp resp,
                                  @Nullable SsoLoginManager.LoginListener listener) {
        StringBuilder message = new StringBuilder("api =授权; type = weixin; code =");
        message.append(resp.errCode);
        // 有可能是listener传入的是null，也可能是调用静态方法前没初始化当前的类
        if (listener != null) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK: // 登录成功
                    if (wxRespListener != null) {
                        wxRespListener.onLoginResp(resp.code, listener);
                    } else {
                        handlerLoginResp(activity, resp.code, listener); // 登录成功后开始通过code换取token
                    }
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    ToastUtil.showShortToast("用户取消授权");
                    message.append(";用户取消授权");
                    listener.onCancel();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    ToastUtil.showShortToast("用户拒绝授权");
                    message.append(";用户拒绝授权");
                    listener.onError(BaseResp.ErrCode.ERR_AUTH_DENIED, new ErrorResponseEntity("用户拒绝授权"));
                    break;
                default:
                    ToastUtil.showShortToast("未知错误");
                    message.append(";未知错误");
                    listener.onError(resp.errCode, new ErrorResponseEntity("未知错误"));
            }
        }
        message.append(";other =").append(resp);
        if (resp.errCode != BaseResp.ErrCode.ERR_OK)
            Log.e(TAG,message.toString());
    }

    void handlerLoginResp(Context context, String code,
                          final @Nullable SsoLoginManager.LoginListener listener) {
        Map<String, Object> body = new HashMap<>();
        body.put("login_type", "wechat");
        body.put("code", code);

        final String bodyStr = JSON.toJSONString(body);
        if (listener != null) listener.onSuccess(null, null, -1, bodyStr);
    }

}
