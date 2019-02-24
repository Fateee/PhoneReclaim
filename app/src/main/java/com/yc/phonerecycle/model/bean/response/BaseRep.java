package com.yc.phonerecycle.model.bean.response;


import com.yc.phonerecycle.mvp.presenter.base.BaseModelInf;

public class BaseRep implements BaseModelInf {

    /**
     * message : 登陆成功
     * status : 0
     * data : {"userVO":{"id":"2","userName":"doctor","telephone":"1234567890","loginName":"doctor","password":"","directLeaderId":"","directLeaderName":"","divisionName":"","divisionId":"0","roleId":"102","roleName":""},"authtoken":"HB7tjo27dK05YnsPVhkP4kswzhAWz5zQPusyOmXvAAtmQG64cyxAodp7i9lRQnrTWo0sHt/0rw4/owde3bh2Q2tCfWCe66X9ma4xdmHoib97pn2H42EZrZJ/FVJgKFM3rO/OzJ95MfTPlIh43yzPmRY3txJeeJKo7VeefpVUmEBSjaRFJsGiDnm/CE8oe7gEwumQeQQbRgjjaErquwpQgXqEaGhPbnBPZC8YVHxcfsS3Oo9vah4Sj+PFOEUWawRMPs3n+Jo4sShH8z48dS9Y0g=="}
     */

    public String message;
    public int status;
}
