package third.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yc.phonerecycle.constant.BaseConst;
import org.json.JSONObject;
import third.ErrorResponseEntity;
import third.wx.SsoLoginManager;

/**
 * @author Jack Tony
 * @date 2015/10/26
 * <p>
 * http://wiki.connect.qq.com/sdk%E4%B8%8B%E8%BD%BD
 * http://wiki.open.qq.com/wiki/mobile/API%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E
 * http://wiki.open.qq.com/wiki/mobile/SDK%E4%B8%8B%E8%BD%BD
 * <p>
 * 仅仅qq分享的sdk支持url，但是竟然不支持https的图片！！！
 */
public class SL_QQHandlerActivity extends Activity {

    public static final String KEY_TO_FRIEND = "key_to_friend";
    public static final String KEY_IS_LOGIN_TYPE = "action_type";

    boolean isToFriend;

    IUiListener uiListener;

    /**
     * 防止不保留活动情况下activity被重置后直接进行操作的情况
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        boolean isLogin = intent.getBooleanExtra(KEY_IS_LOGIN_TYPE, true);
        String appId = BaseConst.QQ_APPID;
        if (TextUtils.isEmpty(appId)) {
            throw new NullPointerException("请通过shareBlock初始化appId");
        }

        if (isLogin) {
            initLoginListener(SsoLoginManager.listener);

            if (savedInstanceState == null) {
                doLogin(this, appId);
            }
        } else {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (uiListener != null) {
            Tencent.handleResultData(data, uiListener);
        }
        finish();
    }

    ///////////////////////////////////////////////////////////////////////////
    // login
    ///////////////////////////////////////////////////////////////////////////

    void initLoginListener(final SsoLoginManager.LoginListener listener) {
        uiListener = new IUiListener() {
            @Override
            public void onComplete(Object object) {
                if (listener != null) {
                    JSONObject jsonObject = ((JSONObject) object);
                    try {
                        String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                        String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
                        String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                        listener.onSuccess(token, openId, Long.valueOf(expires), object.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(UiError uiError) {
                if (listener != null) {
                    listener.onError(uiError.errorCode,new ErrorResponseEntity(uiError.errorCode + " - " + uiError.errorMessage + " - " + uiError.errorDetail));
                }
            }

            @Override
            public void onCancel() {
                if (listener != null) {
                    listener.onCancel();
                }
            }
        };
    }

    void doLogin(Activity activity, String appId) {
        Tencent tencent = Tencent.createInstance(appId, activity.getApplicationContext());
        if (!tencent.isSessionValid()) {
            if (!TextUtils.isEmpty(BaseConst.qqScope))
                tencent.login(activity, BaseConst.qqScope, uiListener);
        } else {
            tencent.logout(activity);
        }
    }

}
