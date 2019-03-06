package com.yc.library.widget;


import com.yc.library.bean.City;
import com.yc.library.bean.County;
import com.yc.library.bean.Province;
import com.yc.library.bean.Street;
import com.yc.phonerecycle.model.bean.biz.DivisionRep;

public interface OnAddressSelectedListener {
    void onAddressSelected(DivisionRep.DataBean.VoListBean province, DivisionRep.DataBean.VoListBean city, DivisionRep.DataBean.VoListBean county, DivisionRep.DataBean.VoListBean street, DivisionRep.DataBean.VoListBean village);
}
