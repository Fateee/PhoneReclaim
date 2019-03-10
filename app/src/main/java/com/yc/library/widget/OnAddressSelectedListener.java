package com.yc.library.widget;


import com.yc.library.bean.City;
import com.yc.library.bean.County;
import com.yc.library.bean.Province;
import com.yc.library.bean.Street;
import com.yc.phonerecycle.model.bean.biz.DivisionRep;

public interface OnAddressSelectedListener {
    void onAddressSelected(DivisionRep.DataBean province, DivisionRep.DataBean city, DivisionRep.DataBean county, DivisionRep.DataBean street, DivisionRep.DataBean village);
}
