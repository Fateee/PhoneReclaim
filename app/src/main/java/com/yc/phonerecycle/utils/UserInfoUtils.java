package com.yc.phonerecycle.utils;


import android.text.TextUtils;
import com.yc.phonerecycle.app.BaseApplication;
import com.yc.phonerecycle.constant.CacheKey;
import com.yc.phonerecycle.model.bean.biz.LoginRep;
import com.yc.phonerecycle.model.bean.biz.QqTokenRep;
import com.yc.phonerecycle.model.bean.biz.UserInfoRep;
import com.yc.phonerecycle.model.bean.biz.WxTokenRep;
import org.jetbrains.annotations.Nullable;

/**
 * Describe：用户信息帮助类
 * Created by 吴天强 on 2018/11/13.
 */

public class UserInfoUtils {


    /**
     * 保存用户信息
     *
     * @param user user
     */
    public static void saveUser(LoginRep user) {
        CacheUtils.get(BaseApplication.getApplication())
                .put(CacheKey.USER_INFO, user);
        CacheUtils.get(BaseApplication.getApplication())
                .put(CacheKey.USER_LOGGED, true);
    }

    /**
     * 获取用户信息
     *
     * @return user
     */
    public static LoginRep getUser() {
        LoginRep user = (LoginRep) CacheUtils.get(BaseApplication.getApplication())
                .getAsObject(CacheKey.USER_INFO);
        if (user == null) {
            user = new LoginRep();
        }
        return user;
    }

    /**
     * 清除用户信息
     */
    public static void cleanUser() {
        saveUser(null);
        CacheUtils.get(BaseApplication.getApplication())
                .put(CacheKey.USER_LOGGED, false);
    }

    /**
     * 是否登录
     *
     * @return boolean
     */
    public static boolean isLogged() {
        Object result = CacheUtils.get(BaseApplication.getApplication())
                .getAsObject(CacheKey.USER_LOGGED);
        return result != null && (boolean) result;
    }


    public static void saveGuideShown() {
        CacheUtils.get(BaseApplication.getApplication())
                .put(CacheKey.GUIDE_SHOWN, true);
    }

    /**
     * 是否展示引导页
     *
     * @return boolean
     */
    public static boolean isGuideShown() {
        Object result = CacheUtils.get(BaseApplication.getApplication())
                .getAsObject(CacheKey.GUIDE_SHOWN);
        return result != null && (boolean) result;
    }

    public static String getUserType() {
        LoginRep.DataBean user = getUser().data;
        if (user != null) {
            if (user.getUserInfoVO() != null) {
                if (TextUtils.isEmpty(user.getUserInfoVO().getType())||"null".equals(user.getUserInfoVO().getType())){
                    return "1";
                } else {
                    return user.getUserInfoVO().getType();
                }
            }
        }
        return "1";
    }

    public static String getUserId() {
        String ret="";
        LoginRep.DataBean user = getUser().data;
        if (user != null) {
            LoginRep.DataBean.UserInfoVOBean userinfo = user.getUserInfoVO();
            if (userinfo != null) {
                if (TextUtils.isEmpty(userinfo.getId())||"null".equals(userinfo.getId())){
                    return ret;
                } else {
                    ret = userinfo.getId();
                }
            } else {
                ret = getUserIdFromInfo();
            }
        } else {
            ret = getUserIdFromInfo();
        }
        return ret;
    }

    public static String getUserIdFromInfo() {
        String ret="";
        UserInfoRep.DataBean user = getUserInfo().data;
        if (user != null) {
            if (TextUtils.isEmpty(user.userId)||"null".equals(user.userId)){
                return ret;
            } else {
                ret = user.userId;
            }
        }
        return ret;
    }

    public static String getUserToken() {
        if (getUser().data != null) {
            return getUser().data.getToken();
        }
        return "";
    }

    public static void saveUserInfo(UserInfoRep body) {
        CacheUtils.get(BaseApplication.getApplication())
                .put(CacheKey.USER_INFO_UC, body);
    }

    /**
     * 获取用户信息
     *
     * @return userInfo
     */
    public static UserInfoRep getUserInfo() {
        UserInfoRep userInfo = (UserInfoRep) CacheUtils.get(BaseApplication.getApplication())
                .getAsObject(CacheKey.USER_INFO_UC);
        if (userInfo == null) {
            userInfo = new UserInfoRep();
        }
        return userInfo;
    }

    public static void cleanUserInfo() {
        saveUserInfo(null);
    }


    public static void saveUserWxTokenRep(WxTokenRep tokenRep) {
        CacheUtils.get(BaseApplication.getApplication())
                .put(CacheKey.USER_WX_TOKEN_REP, tokenRep);
    }

    /**
     * 获取WX
     *
     * @return user
     */
    public static WxTokenRep getUserWxTokenRep() {
        WxTokenRep tokenRep = (WxTokenRep) CacheUtils.get(BaseApplication.getApplication())
                .getAsObject(CacheKey.USER_WX_TOKEN_REP);
        if (tokenRep == null) {
            tokenRep = new WxTokenRep();
        }
        return tokenRep;
    }

    /**
     * 清除微信信息
     */
    public static void cleanUserWxTokenRep() {
        saveUserWxTokenRep(null);
    }

    public static void saveUserQQTokenRep(QqTokenRep tokenRep) {
        CacheUtils.get(BaseApplication.getApplication())
                .put(CacheKey.USER_QQ_TOKEN_REP, tokenRep);
    }

    /**
     * 获取WX
     *
     * @return user
     */
    public static QqTokenRep getUserQQTokenRep() {
        QqTokenRep tokenRep = (QqTokenRep) CacheUtils.get(BaseApplication.getApplication())
                .getAsObject(CacheKey.USER_QQ_TOKEN_REP);
        if (tokenRep == null) {
            tokenRep = new QqTokenRep();
        }
        return tokenRep;
    }
    /**
     * 清除微信信息
     */
    public static void cleanUserQQTokenRep() {
        saveUserQQTokenRep(null);
    }
}
