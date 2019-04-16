package com.yc.phonerecycle.model.bean.biz;

import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.io.Serializable;

public class OrderDetailRep extends BaseRep implements Serializable {
    /**
     * data : {"battery":0,"bluetooth":0,"brandId":"","brandName":"","call":0,"camera":0,"capacity":"","capacityName":"","checkTime":"","colour":"","colourName":"","compass":0,"comprehensionAids":0,"estimatePrice":0,"facade":"","facadeName":"","fingerprint":0,"flashlight":0,"goodsId":"","goodsName":"","gravitySensor":0,"gyroscope":0,"id":"","imei":"","lightSensor":0,"location":0,"lockAccount":"","lockAccountName":"","loudspeaker":0,"memory":"","memoryName":"","microphone":0,"model":"","multiTouch":0,"orderId":"","other":"","overhaul":"","overhaulName":"","proximitySenso":0,"regional":"","regionalName":"","screen":0,"screenProblem":"","screenProblemName":"","spiritLevel":0,"startingState":"","startingStateName":"","status":0,"system":"","type":"","vibrator":0,"warranty":"","warrantyName":"","water":"","waterName":"","wifi":0,"wirelessNetwork":"","wirelessNetworkName":""}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * battery : 0
         * bluetooth : 0
         * brandId : 
         * brandName : 
         * call : 0
         * camera : 0
         * capacity : 
         * capacityName : 
         * checkTime : 
         * colour : 
         * colourName : 
         * compass : 0
         * comprehensionAids : 0
         * estimatePrice : 0
         * facade : 
         * facadeName : 
         * fingerprint : 0
         * flashlight : 0
         * goodsId : 
         * goodsName : 
         * gravitySensor : 0
         * gyroscope : 0
         * id : 
         * imei : 
         * lightSensor : 0
         * location : 0
         * lockAccount : 
         * lockAccountName : 
         * loudspeaker : 0
         * memory : 
         * memoryName : 
         * microphone : 0
         * model : 
         * multiTouch : 0
         * orderId : 
         * other : 
         * overhaul : 
         * overhaulName : 
         * proximitySenso : 0
         * regional : 
         * regionalName : 
         * screen : 0
         * screenProblem : 
         * screenProblemName : 
         * spiritLevel : 0
         * startingState : 
         * startingStateName : 
         * status : 0
         * system : 
         * type : 
         * vibrator : 0
         * warranty : 
         * warrantyName : 
         * water : 
         * waterName : 
         * wifi : 0
         * wirelessNetwork : 
         * wirelessNetworkName : 
         */

        public int battery;
        public int bluetooth;
        public String brandId;
        public String brandName;
        public int call;
        public int camera;
        public String capacity;
        public String capacityName;
        public String checkTime;
        public String colour;
        public String colourName;
        public int compass;
        public int comprehensionAids;
        public int estimatePrice;
        public String facade;
        public String facadeName;
        public int fingerprint;
        public int flashlight;
        public String goodsId;
        public String goodsName;
        public int gravitySensor;
        public int gyroscope;
        public String id;
        public String imei;
        public int lightSensor;
        public int location;
        public String lockAccount;
        public String lockAccountName;
        public int loudspeaker;
        public String memory;
        public String memoryName;
        public int microphone;
        public String model;
        public int multiTouch;
        public String orderId;
        public String other;
        public String overhaul;
        public String overhaulName;
        public int proximitySenso;
        public String regional;
        public String regionalName;
        public int screen;
        public String screenProblem;
        public String screenProblemName;
        public int spiritLevel;
        public String startingState;
        public String startingStateName;
        public int status;
        public String system;
        public String type;
        public int vibrator;
        public String warranty;
        public String warrantyName;
        public String water;
        public String waterName;
        public int wifi;
        public String wirelessNetwork;
        public String wirelessNetworkName;
//    }


//    /**
//     * data : {"battery":0,"bluetooth":0,"brandId":"","brandName":"","call":0,"camera":0,"capacity":"","checkTime":"","colour":"","compass":0,"comprehensionAids":0,"estimatePrice":0,"facade":"","fingerprint":0,"flashlight":0,"goodsId":"","gravitySensor":0,"gyroscope":0,"id":"","imei":"","lightSensor":0,"location":0,"lockAccount":"","loudspeaker":0,"memory":"","microphone":0,"model":"","multiTouch":0,"orderId":"","other":"","overhaul":"","proximitySenso":0,"regional":"","screen":0,"screenProblem":"","spiritLevel":0,"startingState":"","status":0,"system":"","type":"","vibrator":0,"warranty":"","water":"","wifi":0,"wirelessNetwork":""}
//     */
//
//    public DataBean data;
//
//
//    public static class DataBean {
//        /**
//         * battery : 0
//         * bluetooth : 0
//         * brandId : 
//         * brandName : 
//         * call : 0
//         * camera : 0
//         * capacity : 
//         * checkTime : 
//         * colour : 
//         * compass : 0
//         * comprehensionAids : 0
//         * estimatePrice : 0
//         * facade : 
//         * fingerprint : 0
//         * flashlight : 0
//         * goodsId : 
//         * gravitySensor : 0
//         * gyroscope : 0
//         * id : 
//         * imei : 
//         * lightSensor : 0
//         * location : 0
//         * lockAccount : 
//         * loudspeaker : 0
//         * memory : 
//         * microphone : 0
//         * model : 
//         * multiTouch : 0
//         * orderId : 
//         * other : 
//         * overhaul : 
//         * proximitySenso : 0
//         * regional : 
//         * screen : 0
//         * screenProblem : 
//         * spiritLevel : 0
//         * startingState : 
//         * status : 0
//         * system : 
//         * type : 
//         * vibrator : 0
//         * warranty : 
//         * water : 
//         * wifi : 0
//         * wirelessNetwork : 
//         */
//
//        public int battery;
//        public int bluetooth;
//        public String brandId;
//        public String brandName;
//        public int call;
//        public int camera;
//        public String capacity;
//        public String capacityName;
//        public String checkTime;
//        public String colour;
//        public int compass;
//        public int comprehensionAids;
//        public int estimatePrice;
//        public String facade;
//        public int fingerprint;
//        public int flashlight;
//        public String goodsId;
//        public int gravitySensor;
//        public int gyroscope;
//        public String id;
//        public String imei;
//        public int lightSensor;
//        public int location;
//        public String lockAccount;
//        public int loudspeaker;
//        public String memory;
//        public String memoryName;
//        public int microphone;
//        public String model;
//        public int multiTouch;
//        public String orderId;
//        public String other;
//        public String overhaul;
//        public int proximitySenso;
//        public String regional;
//        public String regionalName;
//        public int screen;
//        public String screenProblem;
//        public int spiritLevel;
//        public String startingState;
//        public int status;
//        public String system;
//        public String type;
//        public int vibrator;
//        public String warranty;
//        public String water;
//        public int wifi;
//        public String wirelessNetwork;
//
//
        public DataBean(){}

        public DataBean(int battery, int bluetooth, String brandId, String brandName, int call, int camera, String capacity, String checkTime, String colour, int compass, int comprehensionAids, int estimatePrice, String facade, int fingerprint, int flashlight, String goodsId, int gravitySensor, int gyroscope, String id, String imei, int lightSensor, int location, String lockAccount, int loudspeaker, String memory, int microphone, String model, int multiTouch, String orderId, String other, String overhaul, int proximitySenso, String regional, int screen, String screenProblem, int spiritLevel, String startingState, int status, String system, String type, int vibrator, String warranty, String water, int wifi, String wirelessNetwork) {
            this.battery = battery;
            this.bluetooth = bluetooth;
            this.brandId = brandId;
            this.brandName = brandName;
            this.call = call;
            this.camera = camera;
            this.capacity = capacity;
            this.checkTime = checkTime;
            this.colour = colour;
            this.compass = compass;
            this.comprehensionAids = comprehensionAids;
            this.estimatePrice = estimatePrice;
            this.facade = facade;
            this.fingerprint = fingerprint;
            this.flashlight = flashlight;
            this.goodsId = goodsId;
            this.gravitySensor = gravitySensor;
            this.gyroscope = gyroscope;
            this.id = id;
            this.imei = imei;
            this.lightSensor = lightSensor;
            this.location = location;
            this.lockAccount = lockAccount;
            this.loudspeaker = loudspeaker;
            this.memory = memory;
            this.microphone = microphone;
            this.model = model;
            this.multiTouch = multiTouch;
            this.orderId = orderId;
            this.other = other;
            this.overhaul = overhaul;
            this.proximitySenso = proximitySenso;
            this.regional = regional;
            this.screen = screen;
            this.screenProblem = screenProblem;
            this.spiritLevel = spiritLevel;
            this.startingState = startingState;
            this.status = status;
            this.system = system;
            this.type = type;
            this.vibrator = vibrator;
            this.warranty = warranty;
            this.water = water;
            this.wifi = wifi;
            this.wirelessNetwork = wirelessNetwork;
        }
    }
}
