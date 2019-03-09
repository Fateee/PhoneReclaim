package com.yc.phonerecycle.model.bean.base;


import com.yc.phonerecycle.model.bean.BaseBean;
import com.yc.phonerecycle.mvp.presenter.base.BaseModelInf;

import java.io.Serializable;

public class BaseRep implements BaseModelInf, Serializable {

    /**
     * message : 登陆成功
     * status : 0
     * data : {"userVO":{"id":"2","userName":"doctor","telephone":"1234567890","loginName":"doctor","password":"","directLeaderId":"","directLeaderName":"","divisionName":"","divisionId":"0","roleId":"102","roleName":""},"authtoken":"HB7tjo27dK05YnsPVhkP4kswzhAWz5zQPusyOmXvAAtmQG64cyxAodp7i9lRQnrTWo0sHt/0rw4/owde3bh2Q2tCfWCe66X9ma4xdmHoib97pn2H42EZrZJ/FVJgKFM3rO/OzJ95MfTPlIh43yzPmRY3txJeeJKo7VeefpVUmEBSjaRFJsGiDnm/CE8oe7gEwumQeQQbRgjjaErquwpQgXqEaGhPbnBPZC8YVHxcfsS3Oo9vah4Sj+PFOEUWawRMPs3n+Jo4sShH8z48dS9Y0g=="}
     */

    public String info;
    public int code;
//    public Object data;
//    {
//        "code": 0,
//            "info": "操作完成",
//            "data": {
//        "token": "Banner eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwZXJmb3JtZXIiLCJVU0VSIjoie1wiaWRcIjpcIjFcIixcImxvY2tlZFwiOnRydWUsXCJwaG9uZVwiOlwiMTU2MDAwMDAwMDBcIixcInJvbGVJZFwiOlwiMVwiLFwidHlwZVwiOlwiMVwiLFwidHlwZU5hbWVcIjpcIueUqOaIt1wiLFwidXNlck5hbWVcIjpcImFkbWluXCJ9IiwiZXhwIjoxNTUxMDE3OTMwLCJpYXQiOjE1NTA5ODE5MzB9.WZ-jwj_8J5XMDg8tH4oLLPgxjGSeCdvlxkBLCrrd4Vg",
//                "userInfoVO": {
//            "id": "1",
//                    "userName": "admin",
//                    "locked": true,
//                    "phone": "15600000000",
//                    "roleId": "1",
//                    "type": "1",
//                    "typeName": "用户"
//        }
//    }
//    }
}
