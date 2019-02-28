package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.base.BaseRep;

public class MyOrderDetailRep extends BaseRep {


    /**
     * data : {"address":"","area":"","consigneeName":"","consigneePhone":"","courierCompany":"","dealTime":"","dealUser":"","estimatePrice":"","goodsInstance":"","goodsInstanceVO":{"battery":0,"bluetooth":0,"brandId":"","brandName":"","call":0,"camera":0,"capacity":0,"colour":"","compass":0,"comprehensionAids":0,"facade":0,"fingerprint":0,"flashlight":0,"goodsId":"","gravitySensor":0,"gyroscope":0,"id":"","imei":"","lightSensor":0,"location":0,"lockAccount":0,"loudspeaker":0,"memory":0,"microphone":0,"multiTouch":0,"orderId":"","other":"","overhaul":0,"proximitySenso":0,"screen":0,"screenProblem":0,"spiritLevel":0,"startingState":0,"status":0,"system":"","type":"","vibrator":0,"water":0,"wifi":0,"wirelessNetwork":0},"id":"","orderOwner":"","orderOwnerName":"","orderType":"","ownerType":0,"status":0,"statusName":"","trackingNumber":""}
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
         * goodsInstanceVO : {"battery":0,"bluetooth":0,"brandId":"","brandName":"","call":0,"camera":0,"capacity":0,"colour":"","compass":0,"comprehensionAids":0,"facade":0,"fingerprint":0,"flashlight":0,"goodsId":"","gravitySensor":0,"gyroscope":0,"id":"","imei":"","lightSensor":0,"location":0,"lockAccount":0,"loudspeaker":0,"memory":0,"microphone":0,"multiTouch":0,"orderId":"","other":"","overhaul":0,"proximitySenso":0,"screen":0,"screenProblem":0,"spiritLevel":0,"startingState":0,"status":0,"system":"","type":"","vibrator":0,"water":0,"wifi":0,"wirelessNetwork":0}
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
             * capacity : 0
             * colour : 
             * compass : 0
             * comprehensionAids : 0
             * facade : 0
             * fingerprint : 0
             * flashlight : 0
             * goodsId : 
             * gravitySensor : 0
             * gyroscope : 0
             * id : 
             * imei : 
             * lightSensor : 0
             * location : 0
             * lockAccount : 0
             * loudspeaker : 0
             * memory : 0
             * microphone : 0
             * multiTouch : 0
             * orderId : 
             * other : 
             * overhaul : 0
             * proximitySenso : 0
             * screen : 0
             * screenProblem : 0
             * spiritLevel : 0
             * startingState : 0
             * status : 0
             * system : 
             * type : 
             * vibrator : 0
             * water : 0
             * wifi : 0
             * wirelessNetwork : 0
             */

            public int battery;
            public int bluetooth;
            public String brandId;
            public String brandName;
            public int call;
            public int camera;
            public int capacity;
            public String colour;
            public int compass;
            public int comprehensionAids;
            public int facade;
            public int fingerprint;
            public int flashlight;
            public String goodsId;
            public int gravitySensor;
            public int gyroscope;
            public String id;
            public String imei;
            public int lightSensor;
            public int location;
            public int lockAccount;
            public int loudspeaker;
            public int memory;
            public int microphone;
            public int multiTouch;
            public String orderId;
            public String other;
            public int overhaul;
            public int proximitySenso;
            public int screen;
            public int screenProblem;
            public int spiritLevel;
            public int startingState;
            public int status;
            public String system;
            public String type;
            public int vibrator;
            public int water;
            public int wifi;
            public int wirelessNetwork;

        }
    }
}
