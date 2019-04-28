package com.yc.phonerecycle.model.bean.request;

import com.yc.phonerecycle.model.bean.BaseBean;
import com.yc.phonerecycle.model.bean.biz.OrderDetailRep;
import org.jetbrains.annotations.NotNull;

public class CheckReqBody extends BaseBean {

    /**
     * battery : 0
     * bluetooth : 0
     * brandId : 
     * brandName : 
     * call : 0
     * camera : 0
     * capacity : 
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
    public String colour;
    public int compass;
    public int comprehensionAids;
    public float estimatePrice;
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
    public String model;
    public String checkTime;
    @NotNull
    public OrderDetailRep.DataBean cloneToOrderDetailRepDataBean() {
        return new OrderDetailRep.DataBean(battery, bluetooth, brandId, brandName, call, camera, capacity, checkTime, colour, compass, comprehensionAids, estimatePrice, facade, fingerprint, flashlight, goodsId, gravitySensor, gyroscope, id, imei, lightSensor, location, lockAccount, loudspeaker, memory, microphone, model, multiTouch, orderId, other, overhaul, proximitySenso, regional, screen, screenProblem, spiritLevel, startingState, status, system, type, vibrator, warranty, water, wifi, wirelessNetwork);
    }
}
