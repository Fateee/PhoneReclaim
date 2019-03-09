package third.wx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yc.phonerecycle.constant.BaseConst;
import com.yc.phonerecycle.utils.ToastUtil;
import com.yc.phonerecycle.wxapi.WXEntryActivity;
import third.ErrorResponseEntity;
import third.qq.SL_QQHandlerActivity;

import java.util.List;
import java.util.Locale;

import static third.wx.SsoLoginType.QQ;
import static third.wx.SsoLoginType.WEIXIN;

/**
 * @author Kale
 * @date 2016/3/30
 */
public class SsoLoginManager {


    @Nullable
    public static LoginListener listener;

    public static void login(@NonNull Context context, @SsoLoginType String type, @Nullable LoginListener listener) {
        login(context, type, listener, null);
    }

    /**
     * @param weixinCodeRespListener 得到微信code的listener。如果不为空，loginListener将不会被自动调用，必须要手动调用。
     */
    public static void login(@NonNull Context context, @SsoLoginType String type,
                             @Nullable LoginListener listener, @Nullable WXLoginRespListener weixinCodeRespListener) {
        SsoLoginManager.listener = listener;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            switch (type) {
                case QQ:
                    if (isQQInstalled(activity)) {
//                        Intent intent = new Intent(activity, SL_QQHandlerActivity.class).putExtra(SL_QQHandlerActivity.KEY_IS_LOGIN_TYPE, true);
//                        activity.startActivity(intent);
//                        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } else {
                        ToastUtil.showShortToast("未安装QQ");
                        if (listener != null) {
                            listener.onError(-1,new ErrorResponseEntity("未安装QQ"));
                        }
                    }
                    break;
                case WEIXIN:
                    if (isWeiXinInstalled(activity)) {
//                        WXEntryActivity.wxRespListener = weixinCodeRespListener;
//                        WXEntryActivity.login(activity.getApplicationContext());
//                        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } else {
                        ToastUtil.showShortToast("未安装微信");
                        if (listener != null) {
                            listener.onError(-1,new ErrorResponseEntity("未安装微信"));
                        }
                    }
                    break;
            }
        }
    }

    public static void recycle() {
        listener = null;
//        WXEntryActivity.wxRespListener = null;
    }

    public static class LoginListener {

        /**
         * @param accessToken 第三方给的一次性token，几分钟内会失效
         * @param uId         用户的id
         * @param expiresIn   过期时间 (-1：表示未返回）
         * @param wholeData   第三方本身返回的全部json数据
         */
        @CallSuper
        public void onSuccess(String accessToken, String uId, long expiresIn, @Nullable String wholeData) {
            onComplete();
        }

        @CallSuper
        public void onError(int code, ErrorResponseEntity errorMsg) {
            onComplete();
        }

        @CallSuper
        public void onCancel() {
            onComplete();
        }

        @CallSuper
        protected void onComplete() {
            SsoLoginManager.recycle();
        }
    }

    public interface WXLoginRespListener {

        void onLoginResp(String respCode, SsoLoginManager.LoginListener listener);
    }


    public static boolean isQQInstalled(@NonNull Context context) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        if (pm == null) {
            return false;
        }
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo info : packages) {
            String name = info.packageName.toLowerCase(Locale.ENGLISH);
            if ("com.tencent.mobileqq".equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWeiXinInstalled(Context context) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, BaseConst.WEIXIN_APPID, true);
        return api.isWXAppInstalled() && api.getWXAppSupportAPI() >= Build.OPENID_SUPPORTED_SDK_INT;
    }
}
