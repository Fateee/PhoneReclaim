package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.util.List;

public class BrandRep extends BaseRep {

    public List<DataBean> data;


    public static class DataBean {
        /**
         * id : 
         * name : 
         */

        public String id;
        public String name;

    }
}
