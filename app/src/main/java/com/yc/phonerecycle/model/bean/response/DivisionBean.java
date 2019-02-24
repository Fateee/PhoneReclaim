package com.yc.phonerecycle.model.bean.response;

import java.util.List;

public class DivisionBean {
    public List<ChildDivisionListBean> childDivisionList;

    public static class ChildDivisionListBean {
        /**
         * ID : 110000
         * Name : 北京
         * FullName : 北京
         */

        public int ID;
        public String Name;
        public String FullName;

        @Override
        public String toString() {
            return Name;
        }
    }
}
