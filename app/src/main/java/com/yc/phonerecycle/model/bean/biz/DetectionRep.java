package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.BaseBean;
import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.util.List;

public class DetectionRep extends BaseRep {
    public List<DataBean> data;


    public static class DataBean extends BaseBean {
        /**
         * brandId : 
         * brandName : 
         * capacity : 
         * customerName : 
         * customerPhone : 
         * dealTime : 
         * goodsInstance : 
         * id : 
         * logo : 
         * status : 0
         * statusName : 
         * type : 
         */

        public String brandId;
        public String brandName;
        public String capacity;
        public String customerName;
        public String customerPhone;
        public String dealTime;
        public String createTime;
        public String goodsInstance;
        public String id;
        public String logo;
        public int status;
        public String statusName;
        public String type;

        public String capacityValue;
        public String estimatePrice;
    }
}
