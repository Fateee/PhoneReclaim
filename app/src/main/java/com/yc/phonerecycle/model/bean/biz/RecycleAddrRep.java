package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.base.BaseRep;

public class RecycleAddrRep extends BaseRep {

    /**
     * data : {"address":"","addressDetail":"","defaultAdd":0,"id":"","name":"","phone":""}
     */

    public DataBean data;


    public static class DataBean {
        /**
         * address : 
         * addressDetail : 
         * defaultAdd : 0
         * id : 
         * name : 
         * phone : 
         */

        public String address;
        public String addressDetail;
        public int defaultAdd;
        public String id;
        public String name;
        public String phone;

    }
}
