package com.yc.phonerecycle.model.bean.request;

public class FeedbackReqBody {
    /**
     * id :
     * remark :
     */

    public String id;
    public String remark;

    public FeedbackReqBody(String id, String remark) {
        this.id = id;
        this.remark = remark;
    }
}
