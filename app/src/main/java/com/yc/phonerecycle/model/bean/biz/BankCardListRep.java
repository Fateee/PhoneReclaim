package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.util.List;

public class BankCardListRep extends BaseRep {


    public List<DataBean> data;

    public static class DataBean {
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

    }
}
