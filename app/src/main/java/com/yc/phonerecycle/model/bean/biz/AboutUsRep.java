package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.base.BaseRep;

public class AboutUsRep extends BaseRep {


    /**
     * data : {"email":"","hotline":"","id":"","name":"","qq":"","version":"","wx":""}
     */

    public DataBean data;


    public static class DataBean {
        /**
         * email : 
         * hotline : 
         * id : 
         * name : 
         * qq : 
         * version : 
         * wx : 
         */

        public String email;
        public String hotline;
        public String id;
        public String name;
        public String qq;
        public String version;
        public String wx;

    }
}
