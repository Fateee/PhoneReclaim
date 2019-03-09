package com.yc.phonerecycle.model.bean.request;


public class createWithdrawPasswordBody {
    /**
     * password :
     * useId :
     */

    public String password;
    public String useId;

    public createWithdrawPasswordBody(String password, String useId) {
        this.password = password;
        this.useId = useId;
    }

}
