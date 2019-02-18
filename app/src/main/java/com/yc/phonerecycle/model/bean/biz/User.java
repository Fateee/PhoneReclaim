package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.BaseBean;

/**
 * Describe：用户信息
 * Created by 吴天强 on 2018/11/13.
 */
public class User extends BaseBean {

    /**
     * email : banshengcy@163.com
     * icon :
     * id : 1260
     * password : *******
     * token :
     * type : 0
     * username : 于慢慢家的吴蜀黍
     */

    private int id;
    private String username;
    private String email;
    private String icon;
    private String password;
    private String token;
    private int type;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
