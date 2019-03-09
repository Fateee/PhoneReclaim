package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.BaseBean;
import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.util.List;

public class BrandGoodsRep extends BaseRep {

    public List<DataBean> data;

    public static class DataBean extends BaseBean {
        /**
         * brandId : 
         * brandName : 
         * capacity : 
         * code : 
         * colour : 
         * highest : 
         * id : 
         * logo : 
         * memory : 
         * minimum : 
         * networkSystem : 
         * regionalVersion : 
         * type : 
         */

        public String brandId;
        public String brandName;
        public String capacity;
        public String code;
        public String colour;
        public String highest;
        public String id;
        public String logo;
        public String memory;
        public String minimum;
        public String networkSystem;
        public String regionalVersion;
        public String type;

    }
}
