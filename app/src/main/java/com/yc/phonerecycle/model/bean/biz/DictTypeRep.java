package com.yc.phonerecycle.model.bean.biz;


import com.yc.phonerecycle.model.bean.base.BaseRep;

import java.io.Serializable;
import java.util.List;

/**
 * Describe：公共码表
 */
public class DictTypeRep extends BaseRep implements Serializable {

    /**
     * data : [{"id":"1","typeName":"手机内存","key":"1"},{"id":"2","typeName":"存储容量","key":"2"},{"id":"3","typeName":"机身颜色","key":"3"},{"id":"4","typeName":"区域版本","key":"4"},{"id":"5","typeName":"网络制式","key":"5"},{"id":"6","typeName":"估价体系-标记类型","key":"6"},{"id":"7","typeName":"订单状态","key":"7"},{"id":"8","typeName":"短信业务类型","key":"8"},{"id":"9","typeName":"手机评估码值-保修情况","key":"9"},{"id":"10","typeName":"手机评估码值-外观","key":"10"},{"id":"11","typeName":"手机评估码值-屏幕问题","key":"11"},{"id":"12","typeName":"手机评估码值-拆修问题","key":"12"},{"id":"13","typeName":"手机评估码值-有无进水","key":"13"},{"id":"14","typeName":"手机评估码值-开机情况","key":"14"},{"id":"15","typeName":"手机评估-账户锁","key":"15"},{"id":"16","typeName":"手机评估码值-wifi情况","key":"16"},{"id":"17","typeName":"手机评估码值-陀螺仪问题","key":"17"}]
     */

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 1
         * typeName : 手机内存
         * key : 1
         */

        public String id;
        public String typeName;
        public String key;

    }
}
