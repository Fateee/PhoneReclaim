package com.yc.phonerecycle.model.bean.request;



public class ChangePwdReqBody {
    /**
     * oldPasswd :
     * password :
     * userId : 1
     */

    private String oldPasswd;
    private String password;
    private String userId;

    public ChangePwdReqBody(String oldPasswd, String password, String userId) {
        this.oldPasswd = oldPasswd;
        this.password = password;
        this.userId = userId;
    }

    public String getOldPasswd() {
        return oldPasswd;
    }

    public void setOldPasswd(String oldPasswd) {
        this.oldPasswd = oldPasswd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
