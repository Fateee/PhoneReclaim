package com.yc.phonerecycle.model.bean.biz;

import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.io.Serializable;

public class UserInfoRep extends BaseRep implements Serializable {
    /**
     * data : {"instance":0,"logo":"","money":0,"myUser":"","name":"","orderCount":0,"signature":"","testCount":0,"userId":""}
     */

    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * instance : 0
         * logo :
         * money : 0
         * myUser :
         * name :
         * orderCount : 0
         * signature :
         * testCount : 0
         * userId :
         */

        public int instance;
        public String logo;
        public int money;
        public String myUser;
        public String name;
        public int orderCount;
        public String signature;
        public int testCount;
        public String userId;

    }
}
