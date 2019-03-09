package com.yc.phonerecycle.model.bean.request;


public class ChangePhoneBody {


    /**
     * code :
     * phone :
     */

    public String code;
    public String phone;

    public ChangePhoneBody(String code, String phone) {
        this.code = code;
        this.phone = phone;
    }
}
