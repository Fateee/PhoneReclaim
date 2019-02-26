package com.yc.phonerecycle.model.bean.request;



public class ResetPwdByPhoneReqBody {
    /**
     * code :
     * password :
     * phone :
     */

    private String code;
    private String password;
    private String phone;

    public ResetPwdByPhoneReqBody(String code, String password, String phone) {
        this.code = code;
        this.password = password;
        this.phone = phone;
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
}
