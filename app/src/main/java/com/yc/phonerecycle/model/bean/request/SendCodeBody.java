package com.yc.phonerecycle.model.bean.request;

public class SendCodeBody {
    /**
     * businessType : 0
     * phone :
     */

    private int businessType;
    private String phone;

    public SendCodeBody(int businessType, String phone) {
        this.businessType = businessType;
        this.phone = phone;
    }
}
