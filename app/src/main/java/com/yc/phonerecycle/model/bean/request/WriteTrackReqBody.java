package com.yc.phonerecycle.model.bean.request;

public class WriteTrackReqBody {
    /**
     * courierCompany :
     * orderId :
     * trackingNumber :
     */

    public String courierCompany;
    public String orderId;
    public String trackingNumber;

    public WriteTrackReqBody(String courierCompany, String orderId, String trackingNumber) {
        this.courierCompany = courierCompany;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
    }
}
