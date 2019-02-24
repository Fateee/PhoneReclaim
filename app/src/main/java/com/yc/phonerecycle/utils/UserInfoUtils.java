package com.yc.phonerecycle.utils;


import com.yc.phonerecycle.app.BaseApplication;
import com.yc.phonerecycle.constant.CacheKey;
import com.yc.phonerecycle.model.bean.biz.LoginRep;

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
}
