package com.yc.phonerecycle.model.bean.request;



public class LoginReqBody {
    public String userName;
    public String password;

    public LoginReqBody(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
