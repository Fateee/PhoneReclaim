package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.BaseBean;
import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.util.List;

public class NearByShopRep extends BaseRep {
    public List<DataBean> data;

    public static class DataBean extends BaseBean {
        /**
         * address : 
         * adminUser : 
         * adminUserName : 
         * area : 
         * businessHours : 
         * distance : 0
         * email : 
         * fixedLine : 
         * fixtureNumber : 0
         * id : 
         * images : 
         * longitudeLatitude : 
         * mainImage : 
         * merchantsId : 
         * merchantsName : 
         * name : 
         * password : 
         * phone : 
         * remark : 
         * status : 0
         */

        public String address;
        public String adminUser;
        public String adminUserName;
        public String area;
        public String businessHours;
        public double distance;
        public String email;
        public String fixedLine;
        public int fixtureNumber;
        public String id;
        public String images;
        public String longitudeLatitude;
        public String mainImage;
        public String merchantsId;
        public String merchantsName;
        public String name;
        public String password;
        public String phone;
        public String remark;
        public int status;
    }
}
