package com.yc.phonerecycle.model.bean.biz;

import java.io.Serializable;

public class WxTokenRep implements Serializable {
    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     * refresh_token : REFRESH_TOKEN
     * openid : OPENID
     * scope : SCOPE
     */

    public String access_token;
//    public int expires_in;
    public String expires_in;
    public String refresh_token;
    public String openid;
    public String scope;

    public WxTokenRep() {
    }

    public WxTokenRep(String access_token, String expires_in, String refresh_token, String openid, String scope) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.openid = openid;
        this.scope = scope;
    }
}
