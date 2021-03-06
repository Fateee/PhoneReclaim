package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.base.BaseRep;

public class RegisterRep extends BaseRep {

    /**
     * data : {"accessToken":"","expiresIn":0,"gender":"","logo":"","nickName":"","openID":"","openType":"","qqAppId":"","qqAppSecret":"","qqRedirectUrl":"","redirectUrl":"","refreshToken":"","responseJson":"","userId":"","weCatAppId":"","weCatAppSecret":"","weCatRedirectUrl":""}
     */

    public DataBean data;

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
        public long expiresIn;
        public String gender;
        public String logo;
        public String nickName;
        public String openID;
        public String openType;
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

    }
}
