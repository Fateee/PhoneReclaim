package com.yc.phonerecycle.model.bean.request;

public class ThirdPhoneBody {
    /**
     * code :
     * openId :
     * phone :
     */

    public String code;
    public String openId;
    public String phone;

    public ThirdPhoneBody(String code, String openId, String phone) {
        this.code = code;
        this.openId = openId;
        this.phone = phone;
    }
}
