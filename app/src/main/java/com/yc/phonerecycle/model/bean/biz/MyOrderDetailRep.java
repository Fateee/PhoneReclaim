package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.base.BaseRep;

public class MyOrderDetailRep extends BaseRep {
    /**
     * data : {"address":"","area":"","consigneeName":"","consigneePhone":"","courierCompany":"","dealTime":"","dealUser":"","estimatePrice":"","goodsInstance":"","goodsInstanceVO":{"battery":0,"bluetooth":0,"brandId":"","brandName":"","call":0,"camera":0,"capacity":"","checkTime":"","colour":"","compass":0,"comprehensionAids":0,"estimatePrice":0,"facade":"","fingerprint":0,"flashlight":0,"goodsId":"","gravitySensor":0,"gyroscope":0,"id":"","imei":"","lightSensor":0,"location":0,"lockAccount":"","loudspeaker":0,"memory":"","microphone":0,"model":"","multiTouch":0,"orderId":"","other":"","overhaul":"","proximitySenso":0,"regional":"","screen":0,"screenProblem":"","spiritLevel":0,"startingState":"","status":0,"system":"","type":"","vibrator":0,"warranty":"","water":"","wifi":0,"wirelessNetwork":""},"id":"","orderOwner":"","orderOwnerName":"","orderType":"","ownerType":0,"status":0,"statusName":"","trackingNumber":""}
     */

    public DataBean data;


    public static class DataBean {
        /**
         * address :
         * area :
         * consigneeName :
         * consigneePhone :
         * courierCompany :
         * dealTime :
         * dealUser :
         * estimatePrice :
         * goodsInstance :
         * goodsInstanceVO : {"battery":0,"bluetooth":0,"brandId":"","brandName":"","call":0,"camera":0,"capacity":"","checkTime":"","colour":"","compass":0,"comprehensionAids":0,"estimatePrice":0,"facade":"","fingerprint":0,"flashlight":0,"goodsId":"","gravitySensor":0,"gyroscope":0,"id":"","imei":"","lightSensor":0,"location":0,"lockAccount":"","loudspeaker":0,"memory":"","microphone":0,"model":"","multiTouch":0,"orderId":"","other":"","overhaul":"","proximitySenso":0,"regional":"","screen":0,"screenProblem":"","spiritLevel":0,"startingState":"","status":0,"system":"","type":"","vibrator":0,"warranty":"","water":"","wifi":0,"wirelessNetwork":""}
         * id :
         * orderOwner :
         * orderOwnerName :
         * orderType :
         * ownerType : 0
         * status : 0
         * statusName :
         * trackingNumber :
         */

        public String address;
        public String area;
        public String consigneeName;
        public String consigneePhone;
        public String courierCompany;
        public String dealTime;
        public String dealUser;
        public String estimatePrice;
        public String goodsInstance;
        public GoodsInstanceVOBean goodsInstanceVO;
        public String id;
        public String orderOwner;
        public String orderOwnerName;
        public String orderType;
        public int ownerType;
        public int status;
        public String statusName;
        public String trackingNumber;


        public static class GoodsInstanceVOBean {
            /**
             * battery : 0
             * bluetooth : 0
             * brandId :
             * brandName :
             * call : 0
             * camera : 0
             * capacity :
             * checkTime :
             * colour :
             * compass : 0
             * comprehensionAids : 0
             * estimatePrice : 0
             * facade :
             * fingerprint : 0
             * flashlight : 0
             * goodsId :
             * gravitySensor : 0
             * gyroscope : 0
             * id :
             * imei :
             * lightSensor : 0
             * location : 0
             * lockAccount :
             * loudspeaker : 0
             * memory :
             * microphone : 0
             * model :
             * multiTouch : 0
             * orderId :
             * other :
             * overhaul :
             * proximitySenso : 0
             * regional :
             * screen : 0
             * screenProblem :
             * spiritLevel : 0
             * startingState :
             * status : 0
             * system :
             * type :
             * vibrator : 0
             * warranty :
             * water :
             * wifi : 0
             * wirelessNetwork :
             */

            public int battery;
            public int bluetooth;
            public String brandId;
            public String brandName;
            public int call;
            public int camera;
            public String capacity;
            public String checkTime;
            public String colour;
            public int compass;
            public int comprehensionAids;
            public int estimatePrice;
            public String facade;
            public int fingerprint;
            public int flashlight;
            public String goodsId;
            public int gravitySensor;
            public int gyroscope;
            public String id;
            public String imei;
            public int lightSensor;
            public int location;
            public String lockAccount;
            public int loudspeaker;
            public String memory;
            public int microphone;
            public String model;
            public int multiTouch;
            public String orderId;
            public String other;
            public String overhaul;
            public int proximitySenso;
            public String regional;
            public int screen;
            public String screenProblem;
            public int spiritLevel;
            public String startingState;
            public int status;
            public String system;
            public String type;
            public int vibrator;
            public String warranty;
            public String water;
            public int wifi;
            public String wirelessNetwork;

        }
    }
}
