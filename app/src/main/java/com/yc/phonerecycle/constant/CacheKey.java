package com.yc.phonerecycle.constant;


import com.yc.phonerecycle.app.BaseApplication;

/**
 * Describe：缓存key
 * Created by 吴天强 on 2018/11/13.
 */

public interface CacheKey {

    /**
     * 保存用户信息
     */
    String USER_INFO = BaseApplication.getApplication().getPackageName() + ".UserInfo";

    /**
     * 保存登录状态
     */
    String USER_LOGGED = BaseApplication.getApplication().getPackageName() + ".UserLogged";

    /**
     * 引导页是否已经展示
     */
    String GUIDE_SHOWN = BaseApplication.getApplication().getPackageName() + ".GuideShown";

    /**
     * 保存用户中心信息
     */
    String USER_INFO_UC = BaseApplication.getApplication().getPackageName() + ".UserInfoUc";

    String USER_WX_TOKEN_REP = BaseApplication.getApplication().getPackageName() + ".WxTokenRep";

    String USER_QQ_TOKEN_REP = BaseApplication.getApplication().getPackageName() + ".QqTokenRep";
}
