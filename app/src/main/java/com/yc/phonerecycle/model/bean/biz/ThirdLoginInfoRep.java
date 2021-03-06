package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.base.BaseRep;

public class ThirdLoginInfoRep extends BaseRep {

    /**
     * data : {"accessToken":"","expiresIn":0,"gender":"","logo":"","nickName":"","openID":"","openType":"","qqAppId":"","qqAppSecret":"","qqRedirectUrl":"","redirectUrl":"","refreshToken":"","responseJson":"","userId":"","weCatAppId":"","weCatAppSecret":"","weCatRedirectUrl":""}
     */

    public DataBean data = null;


    public static class DataBean {
        /**
         * accessToken : 
         * expiresIn : 0
         * gender : 
         * logo : 
         * nickName : 
         * openID : 
         * openType : 
         * qqAppId : 
         * qqAppSecret : 
         * qqRedirectUrl : 
         * redirectUrl : 
         * refreshToken : 
         * responseJson : 
         * userId : 
         * weCatAppId : 
         * weCatAppSecret : 
         * weCatRedirectUrl : 
         */

        public String accessToken;
        public String expiresIn;
        public String gender;
        public String logo;
        public String nickName;
        public String openID;
        public String openType;//类型 1 qq 2 wx
        public String qqAppId;
        public String qqAppSecret;
        public String qqRedirectUrl;
        public String redirectUrl;
        public String refreshToken;
        public String responseJson;
        public String userId;
        public String weCatAppId;
        public String weCatAppSecret;
        public String weCatRedirectUrl;

        public void updateInfo(String accessToken,String openid,String refresh_token,String expiresIn) {
            this.accessToken = accessToken;
            this.openID = openid;
            this.refreshToken = refresh_token;
            this.expiresIn = expiresIn;
        }

    }
}
