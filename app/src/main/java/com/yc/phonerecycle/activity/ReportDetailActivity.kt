package com.yc.phonerecycle.activity

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.constant.UrlConst
import com.yc.phonerecycle.model.bean.biz.*
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import kotlinx.android.synthetic.main.activity_check_result_detail.*
import kotlinx.android.synthetic.main.item_check_result.view.*
import kotlinx.android.synthetic.main.item_check_result_container.view.*
import kotlinx.android.synthetic.main.titleview.*


class ReportDetailActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonIV{
    override fun getDataOK(rep: Any?) {
        if (rep is PhoneReportRep) {
            refreshView(rep?.data)
        }
    }

    private var goodsInstanceVO: MyOrderDetailRep.DataBean.GoodsInstanceVOBean? = null
    private var recordid: String? = ""
    private var order_bean: MyOrderListlRep.DataBean? = null
    private var detection_bean: DetectionRep.DataBean? = null

    override fun createPresenter() = CommonPresenter()

    override fun initBundle() {
        goodsInstanceVO = intent?.getSerializableExtra("obj") as MyOrderDetailRep.DataBean.GoodsInstanceVOBean?
        recordid = intent.getStringExtra("recordid")
        order_bean = intent?.getSerializableExtra("order_bean") as MyOrderListlRep.DataBean?
        detection_bean = intent?.getSerializableExtra("detection_bean") as DetectionRep.DataBean?
    }

    override fun getContentView(): Int = R.layout.activity_check_result_detail

    override fun initView() {
    }

    override fun initDatas() {
        presenter.getGoodsInstanceReport(recordid)
        txt_main_title.text = "验机报告"
    }

    private fun refreshView(mCheckReqBody: PhoneReportRep.DataBean?) {
        var tip = StringBuilder()
        name.text = mCheckReqBody?.brandName+" "+mCheckReqBody?.type+" "+mCheckReqBody?.regional
//        content.text = mCheckReqBody?.memory+" "+mCheckReqBody?.capacity
        content.text = mCheckReqBody?.memoryName+"+"+mCheckReqBody?.capacityName
        if (order_bean != null) {
            showLogo(order_bean?.logo,icon)
            if (!TextUtils.isEmpty(order_bean?.brandName)) {
                tip.append(order_bean?.brandName+"  ")
            }
            if (!TextUtils.isEmpty(order_bean?.type)) {
                tip.append(order_bean?.type)
            }
        } else if (detection_bean != null) {
            showLogo(detection_bean?.logo,icon)
            if (!TextUtils.isEmpty(detection_bean?.brandName)) {
                tip.append(detection_bean?.brandName+"  ")
            }
            if (!TextUtils.isEmpty(detection_bean?.type)) {
                tip.append(detection_bean?.type)
            }
        } else {
            if (!TextUtils.isEmpty(mCheckReqBody?.brandName)) {
                tip.append(mCheckReqBody?.brandName+"  ")
            }
            if (!TextUtils.isEmpty(mCheckReqBody?.type)) {
                tip.append(mCheckReqBody?.type+" ")
            }
        }
        if (!TextUtils.isEmpty(mCheckReqBody?.remark)) {
            check_report.visibility = View.VISIBLE
            report_remark.text = mCheckReqBody?.remark
        }
        if (!TextUtils.isEmpty(mCheckReqBody?.regionalName)) {
            tip.append("  "+mCheckReqBody?.regionalName)
        }
        name.text = tip.toString()
        if (mCheckReqBody?.price != 0) {
            price.text = getString(R.string.order_price_value,mCheckReqBody?.price.toString())
            price_tip.text = "(最终回收价)"
        } else {
            price.text = getString(R.string.order_price_value,mCheckReqBody?.estimatePrice.toString())
        }
        //1 有 0无
        addRowView("无线网络",mCheckReqBody?.wifi == 0,"","距离感应器",mCheckReqBody?.proximitySenso ==0,"")
        addRowView("蓝牙",mCheckReqBody?.bluetooth == 0,"","光线感应器",mCheckReqBody?.lightSensor==0,"")
        addRowView("扬声器",mCheckReqBody?.loudspeaker == 0,"","重力感应器",mCheckReqBody?.gravitySensor==0,"")
        addRowView("麦克风",mCheckReqBody?.microphone == 0,"","水平仪",mCheckReqBody?.spiritLevel==0,"")
        addRowView("闪光灯",mCheckReqBody?.flashlight == 0,"","指南针",mCheckReqBody?.compass==0,"")

        addRowView("震动器",mCheckReqBody?.vibrator == 0,"","定位",mCheckReqBody?.location==0,"")
        addRowView("摄像头",mCheckReqBody?.camera == 0,"","指纹",mCheckReqBody?.fingerprint==0,"")
        addRowView("屏幕触控",mCheckReqBody?.multiTouch == 0,"","拨打电话",mCheckReqBody?.call==0,"")
        addRowView("屏幕坏点",mCheckReqBody?.screen == 0,"","语音助手",mCheckReqBody?.comprehensionAids==0,"")
//        addRowView("电池状态",false,"83%","",true,"")
        custom_phone.visibility = View.VISIBLE
        custom_phone.text = getString(R.string.yanji_time,mCheckReqBody?.checkTime)
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

    private fun showLogo(logos: String?, icon: ImageView) {
        if (logos?.contains("@") == true) {
            var logonames = logos.split("@")
            if (logonames.size >0) {
                var url = UrlConst.FILE_DOWNLOAD_URL+logonames[0]
                Glide.with(this@ReportDetailActivity).load(url).into(icon)
            }
        } else {
            if (!TextUtils.isEmpty(logos)) {
                var url = UrlConst.FILE_DOWNLOAD_URL+logos
                Glide.with(this@ReportDetailActivity).load(url).into(icon)
            }
        }
    }

    private fun getFromDict(dicTypeId: String, value :String?): DictMapRep.DataBean? {
        if (TextUtils.isEmpty(value)) return null
        var listData = BaseApplication.mOptionMap.get(dicTypeId)
        if (listData != null && !listData.isEmpty()) {
            for (i in listData.indices) {
                if (value == listData[i].id) {
                    return listData[i]
                }
            }
        } else {
            presenter.getDictMappingByType(dicTypeId)
        }
        return null
    }
}
