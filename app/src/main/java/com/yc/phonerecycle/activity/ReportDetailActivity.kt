package com.yc.phonerecycle.activity

import android.text.TextUtils
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.MyOrderDetailRep
import com.yc.phonerecycle.model.bean.biz.OrderDetailRep
import com.yc.phonerecycle.model.bean.biz.PhoneReportRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import kotlinx.android.synthetic.main.activity_check_result_detail.*
import kotlinx.android.synthetic.main.item_check_result.view.*
import kotlinx.android.synthetic.main.item_check_result_container.view.*


class ReportDetailActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonIV{
    override fun getDataOK(rep: Any?) {
        if (rep is PhoneReportRep) {
            refreshView(rep.data)
        }
    }

    private lateinit var goodsInstanceVO: MyOrderDetailRep.DataBean.GoodsInstanceVOBean
    private var recordid: String? = ""

    override fun createPresenter() = CommonPresenter()

    override fun initBundle() {
        goodsInstanceVO = intent?.getSerializableExtra("obj") as MyOrderDetailRep.DataBean.GoodsInstanceVOBean
        recordid = intent.getStringExtra("recordid")
    }

    override fun getContentView(): Int = R.layout.activity_check_result_detail

    override fun initView() {
    }

    override fun initDatas() {
        presenter.getGoodsInstanceReport(recordid)
    }

    private fun refreshView(mCheckReqBody: PhoneReportRep.DataBean) {
        name.text = mCheckReqBody?.brandName+" "+mCheckReqBody?.type+" "+mCheckReqBody?.regional
        content.text = mCheckReqBody?.memory+" "+mCheckReqBody?.capacity
        //1 有 0无
        addRowView("无线网络",mCheckReqBody?.wifi == 1602,"","距离感应器",true,"")
        addRowView("蓝牙",mCheckReqBody?.bluetooth == 1,"","光线感应器",true,"")
        addRowView("扬声器",mCheckReqBody?.loudspeaker == 1,"","重力感应器",true,"")
        addRowView("麦克风",mCheckReqBody?.microphone == 1,"","水平仪",true,"")
        addRowView("闪光灯",mCheckReqBody?.flashlight == 1,"","指南针",false,"")

        addRowView("震动器",mCheckReqBody?.vibrator == 1,"","定位",true,"")
        addRowView("摄像头",mCheckReqBody?.camera == 1,"","指纹",true,"")
        addRowView("屏幕触控",mCheckReqBody?.multiTouch == 1,"","拨打电话",true,"")
        addRowView("屏幕坏点",mCheckReqBody?.screen == 1,"","语音助手",true,"")
        addRowView("电池状态",false,"83%","",true,"")
    }

    private fun addRowView(leftTitle: String, leftOk: Boolean, leftValue: String,rightTitle: String, rightOk: Boolean, rightValue: String) {
        var rowView = layoutInflater.inflate(R.layout.item_check_result_container, detail_container,false)
        if (!TextUtils.isEmpty(leftTitle)) {
            rowView.left_view.check_name.text = leftTitle
            if (TextUtils.isEmpty(leftValue)) {
                if (leftOk) {
                    rowView.left_view.ic_check_value.setImageResource(R.drawable.ic_right)
                } else {
                    rowView.left_view.ic_check_value.setImageResource(R.drawable.ic_error)
                }
            } else {
                rowView.left_view.check_value.text = leftValue
            }
        }
        if (!TextUtils.isEmpty(rightTitle)) {
            rowView.right_view.check_name.text = rightTitle
            if (TextUtils.isEmpty(rightValue)) {
                if (rightOk) {
                    rowView.right_view.ic_check_value.setImageResource(R.drawable.ic_right)
                } else {
                    rowView.right_view.ic_check_value.setImageResource(R.drawable.ic_error)
                }
            } else {
                rowView.right_view.check_value.text = rightValue
            }
        }
        detail_container.addView(rowView,detail_container.childCount)
    }

}
