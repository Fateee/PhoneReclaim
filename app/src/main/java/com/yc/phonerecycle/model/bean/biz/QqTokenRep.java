package com.yc.phonerecycle.model.bean.biz;

import java.io.Serializable;

public class QqTokenRep implements Serializable {
    /**
     * ret : 0
     * openid : 4DD5739CD28159C98C540C8BD7A5B340
     * access_token : 095951CFD536CC4896FF8226E6790FB7
     * pay_token : 2C547E1128431D133C38A94E4604C1CC
     * expires_in : 7776000
     * pf : desktop_m_qq-10000144-android-2002-
     * pfkey : 4870aa285d1a9c44d22e74deb6e37fbc
     * msg : 
     * login_cost : 3376
     * query_authority_cost : 119
     * authority_cost : 0
     */

    public int ret;
    public String openid;
    public String access_token;
    public String pay_token;
//    public int expires_in;
    public String expires_in;
    public String pf;
    public String pfkey;
    public String msg;
    public int login_cost;
    public int query_authority_cost;
    public int authority_cost;

    public QqTokenRep() {
    }

    public QqTokenRep(String access_token, String expires_in, String refresh_token, String openid) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.access_token = refresh_token;
        this.openid = openid;
    }
}
