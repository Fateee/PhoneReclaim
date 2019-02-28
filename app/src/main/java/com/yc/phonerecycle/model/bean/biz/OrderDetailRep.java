package com.yc.phonerecycle.model.bean.biz;

import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.io.Serializable;

public class OrderDetailRep extends BaseRep implements Serializable {
    /**
     * code : 0
     * data : {"address":"","addressId":"","area":"","consigneeName":"","consigneePhone":"","courierCompany":"","dealTime":"","dealUser":"","estimatePrice":"","goodsInstance":"","id":"","number":"","orderOwner":"","orderOwnerName":"","orderType":"","ownerType":0,"returnCourierCompany":"","returnTrackingNumber":"","status":0,"statusName":"","trackingNumber":""}
     * info : 
     */

    public DataBean data;


    public static class DataBean {
        /**
         * address : 
         * addressId : 
         * area : 
         * consigneeName : 
         * consigneePhone : 
         * courierCompany : 
         * dealTime : 
         * dealUser : 
         * estimatePrice : 
         * goodsInstance : 
         * id : 
         * number : 
         * orderOwner : 
         * orderOwnerName : 
         * orderType : 
         * ownerType : 0
         * returnCourierCompany : 
         * returnTrackingNumber : 
         * status : 0
         * statusName : 
         * trackingNumber : 
         */

        public String address;
        public String addressId;
        public String area;
        public String consigneeName;
        public String consigneePhone;
        public String courierCompany;
        public String dealTime;
        public String dealUser;
        public String estimatePrice;
        public String goodsInstance;
        public String id;
        public String number;
        public String orderOwner;
        public String orderOwnerName;
        public String orderType;
        public int ownerType;
        public String returnCourierCompany;
        public String returnTrackingNumber;
        public int status;
        public String statusName;
        public String trackingNumber;

    }
}
