package com.yc.phonerecycle.model.bean.request;

public class SaveBankReqBody {
    /**
     * acount : 
     * cardholder : 
     * id : 
     * openingBank : 
     * userId : 
     */

    public String acount;
    public String cardholder;
    public String id;
    public String openingBank;
    public String userId;

    public SaveBankReqBody(String acount, String cardholder, String id, String openingBank, String userId) {
        this.acount = acount;
        this.cardholder = cardholder;
        this.id = id;
        this.openingBank = openingBank;
        this.userId = userId;
    }
}
