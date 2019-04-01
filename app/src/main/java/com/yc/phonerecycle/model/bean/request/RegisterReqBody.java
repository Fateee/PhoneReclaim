package com.yc.phonerecycle.model.bean.request;


public class RegisterReqBody {
    /**
     * code :
     * password :
     * phone :
     * referrer :
     */

    private String code;
    private String password;
    private String phone;
    private String referrer;
    public String openId;
    public RegisterReqBody(String code, String openId, String password, String phone, String referrer) {
        this.code = code;
        this.openId = openId;
        this.password = password;
        this.phone = phone;
        this.referrer = referrer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }
}
