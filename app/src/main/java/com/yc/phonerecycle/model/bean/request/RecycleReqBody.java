package com.yc.phonerecycle.model.bean.request;

public class RecycleReqBody {
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

    public RecycleReqBody() {
    }

    public RecycleReqBody(String address, String addressId, String area, String consigneeName, String consigneePhone, String courierCompany, String dealTime, String dealUser, String estimatePrice, String goodsInstance, String id, String number, String orderOwner, String orderOwnerName, String orderType, int ownerType, String returnCourierCompany, String returnTrackingNumber, int status, String statusName, String trackingNumber) {
        this.address = address;
        this.addressId = addressId;
        this.area = area;
        this.consigneeName = consigneeName;
        this.consigneePhone = consigneePhone;
        this.courierCompany = courierCompany;
        this.dealTime = dealTime;
        this.dealUser = dealUser;
        this.estimatePrice = estimatePrice;
        this.goodsInstance = goodsInstance;
        this.id = id;
        this.number = number;
        this.orderOwner = orderOwner;
        this.orderOwnerName = orderOwnerName;
        this.orderType = orderType;
        this.ownerType = ownerType;
        this.returnCourierCompany = returnCourierCompany;
        this.returnTrackingNumber = returnTrackingNumber;
        this.status = status;
        this.statusName = statusName;
        this.trackingNumber = trackingNumber;
    }
}
