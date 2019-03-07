package com.yc.phonerecycle.constant;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseConst {

    public static final int NEW_BABY_VISIT = 3401;
    @NotNull
    public static final String DETAIL_KEY = "DETAIL_KEY";
    public static final int COMMON_EDIT = 0;
    public static final int COMMON_SINGLE = 2;
    public static final int COMMON_MULT = 3;
    public static final int COMMON_ADDR = 4;
    public static final int COMMON_DATE = 5;
    public static final int COMMON_CHOSE_EDIT = 6;

    public static final int ADDR_ROOT = 0;
    public static final int ADDR_TWO = 1;
    public static final int ADDR_THREE = 2;

    public static final String ROOT_MENUPARM_KEY = "PARM_KEY";
    public static final String ROOT_FAMILY_KEY = "root_family_list";
    public static final String ROOT_PEOPLE_KEY = "root_people_list";
    public static final String ROOT_NEWBORN_KEY = "root_newborn_list";
    @Nullable
    public static final String EXTRA_FAMILY_OBJ_KEY="FAMILY_OBJ";


//    public static final String WEIXIN_APPID = "wx0ba34feedb7d4f13";
//    public static final String WEIXIN_APPID = "wx59d13320bd8f36f4";
//public static final String WEIXIN_SERCET = "c341d6508f44e08f592a673afa6ee876";
    public static final String WEIXIN_APPID = "wx0f40ac56c1e0a032";
    public static final String WEIXIN_SERCET = "4ecea66c0dd244ed649817e46620d3e9";
    public static final String WEIXIN_TYPE_AUTH_CODE = "authorization_code";
    public static final String WEIXIN_TYPE_REFRESH_CODE = "refresh_token";

    public static final String QQ_APPID = "1103278945";//"1106223286";
    public static String qqScope="all";

    //0、已完成 1、待寄出 2、待收货 3、已退回 4、验机 5、待打款
    public static final int ORDER_DONE = 0;
    public static final int ORDER_WAIT_EMS = 1;
    public static final int ORDER_WATI_RECEIVE = 2;
    public static final int ORDER_HAS_RETURN = 3;
    public static final int ORDER_CHECK = 4;
    public static final int ORDER_WAIT_CASH = 5;
}
