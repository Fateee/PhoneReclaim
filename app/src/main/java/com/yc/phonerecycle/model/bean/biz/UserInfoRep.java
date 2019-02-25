package com.yc.phonerecycle.model.bean.biz;

import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.io.Serializable;

public class UserInfoRep extends BaseRep implements Serializable {
    /**
     * data : {"instance":0,"logo":"","money":0,"myUser":"","name":"","orderCount":0,"signature":"","testCount":0,"userId":""}
     */

    private DataBean data;

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

        private int instance;
        private String logo;
        private int money;
        private String myUser;
        private String name;
        private int orderCount;
        private String signature;
        private int testCount;
        private String userId;

        public int getInstance() {
            return instance;
        }

        public void setInstance(int instance) {
            this.instance = instance;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getMyUser() {
            return myUser;
        }

        public void setMyUser(String myUser) {
            this.myUser = myUser;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getTestCount() {
            return testCount;
        }

        public void setTestCount(int testCount) {
            this.testCount = testCount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
