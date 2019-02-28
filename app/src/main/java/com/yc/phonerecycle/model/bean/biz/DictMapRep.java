package com.yc.phonerecycle.model.bean.biz;

import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.io.Serializable;
import java.util.List;

public class DictMapRep extends BaseRep implements Serializable {

    public List<DataBean> data;

    public static class DataBean implements Serializable{
        /**
         * id : 0401
         * typeId : 4
         * name : 大陆
         * value : 大陆
         */

        public String id;
        public String typeId;
        public String name;
        public String value;

    }
}
