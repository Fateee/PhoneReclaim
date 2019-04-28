package com.yc.phonerecycle.model.bean.biz;

import com.yc.phonerecycle.model.bean.BaseBean;
import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.io.Serializable;
import java.util.List;

public class MyOrderListlRep extends BaseRep implements Serializable {
    public List<DataBean> data;

    public static class DataBean extends BaseBean {
        /**
         * id : 302506698915057664
         * logo : e6d0c0deab9949b49c195d0f19a2ac97.jpg@e7be4b8f89424e299c39a3448162bd9f.jpg
         * goodsInstance : 302233330202578944
         * brandId : 302219216159903744
         * brandName : HUAWEI
         * type : honor10
         * capacity : 0202
         * capacityValue : 32
         * estimatePrice : -17584.0
         * dealTime : 2019-03-11 15:34:42
         * status : 1
         * statusName : 待寄出
         */

        public String id;
        public String logo;
        public String goodsInstance;
        public String brandId;
        public String brandName;
        public String type;
        public String capacity;
        public String capacityValue;
        public String price;
        public String estimatePrice;
        public String dealTime;
        public String createTime;
        public int status;
        public String statusName;
    }
}
