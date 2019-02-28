package com.yc.phonerecycle.model.bean.biz;

import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.io.Serializable;
import java.util.List;

public class MyOrderListlRep extends BaseRep implements Serializable {
    public List<DataBean> data;

    public static class DataBean {
        /**
         * brandId :
         * brandName :
         * capacity : 0
         * dealTime :
         * estimatePrice :
         * goodsInstance :
         * id :
         * logo :
         * status : 0
         * statusName :
         * type :
         */

        public String brandId;
        public String brandName;
        public int capacity;
        public String dealTime;
        public String estimatePrice;
        public String goodsInstance;
        public String id;
        public String logo;
        public int status;
        public String statusName;
        public String type;
    }
}
