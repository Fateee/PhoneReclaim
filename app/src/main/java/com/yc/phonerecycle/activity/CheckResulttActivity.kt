package com.yc.phonerecycle.activity

import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.UserInfoUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yc.phonerecycle.R
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.constant.UrlConst
import com.yc.phonerecycle.model.bean.biz.DetectionRep
import com.yc.phonerecycle.model.bean.biz.DictMapRep
import com.yc.phonerecycle.model.bean.biz.MyOrderListlRep
import com.yc.phonerecycle.model.bean.biz.OrderDetailRep
import com.yc.phonerecycle.model.bean.request.CheckReqBody
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_check_result_detail.*
import kotlinx.android.synthetic.main.item_check_result.view.*
import kotlinx.android.synthetic.main.item_check_result_container.view.*
import kotlinx.android.synthetic.main.titleview.*


class CheckResulttActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.CommonIV{
    override fun getDataOK(rep: Any?) {
        if (rep is OrderDetailRep) {
            refreshView(rep?.data)
        }
    }

    override fun createPresenter() = CommonPresenter()

    var mCheckReqBody : CheckReqBody? = null

    private var result_type: String? = ""

    private var recordid: String? = ""
    private var order_bean: MyOrderListlRep.DataBean? = null
    private var detection_bean: DetectionRep.DataBean? = null
    override fun initBundle() {
        mCheckReqBody = intent.getSerializableExtra("checkbean") as CheckReqBody?
        result_type = intent.getStringExtra("result_type")
        recordid = intent.getStringExtra("recordid")
        order_bean = intent?.getSerializableExtra("order_bean") as MyOrderListlRep.DataBean?
        detection_bean = intent?.getSerializableExtra("detection_bean") as DetectionRep.DataBean?
    }

    override fun getContentView(): Int = R.layout.activity_check_result_detail

    override fun initView() {

    }

    private fun refreshView(mCheckReqBody: OrderDetailRep.DataBean?) {
        if (order_bean != null) {
            showLogo(order_bean?.logo,icon)
            name.text = order_bean?.brandName+"  "+order_bean?.type
            var memoryForm = getFromDict("1", mCheckReqBody?.memory)
            content.text = memoryForm?.value+"+"+order_bean?.capacityValue+"GB"
        } else if (detection_bean != null) {
            showLogo(detection_bean?.logo,icon)
            name.text = detection_bean?.brandName+"  "+detection_bean?.type
            var memoryForm = getFromDict("1", mCheckReqBody?.memory)
            content.text = memoryForm?.value+"+"+detection_bean?.capacityValue+"GB"
        } else {
            name.text = mCheckReqBody?.brandName+" "+mCheckReqBody?.type+" "+mCheckReqBody?.regional
            var memoryForm = getFromDict("1", mCheckReqBody?.memory)
            var romForm = getFromDict("2", mCheckReqBody?.memory)
            if (memoryForm == null) {
                name.postDelayed({memoryForm= getFromDict("1", mCheckReqBody?.memory)
                    content.text = memoryForm?.value+"+"+romForm?.name},2000)
            }
            if (romForm == null) {
                name.postDelayed({romForm = getFromDict("2", mCheckReqBody?.memory)
                    content.text = memoryForm?.value+"+"+romForm?.name},2000)
            }
            content.text = memoryForm?.value+"+"+romForm?.name
        }

        price.text = getString(R.string.order_price_value,mCheckReqBody?.estimatePrice.toString())
        //1 有 0无
        addRowView("无线网络",mCheckReqBody?.wifi == 1601,"","距离感应器",mCheckReqBody?.proximitySenso ==0,"")
        addRowView("蓝牙",mCheckReqBody?.bluetooth == 0,"","光线感应器",mCheckReqBody?.lightSensor==0,"")
        addRowView("扬声器",mCheckReqBody?.loudspeaker == 0,"","重力感应器",mCheckReqBody?.gravitySensor==0,"")
        addRowView("麦克风",mCheckReqBody?.microphone == 0,"","水平仪",mCheckReqBody?.spiritLevel==0,"")
        addRowView("闪光灯",mCheckReqBody?.flashlight == 0,"","指南针",mCheckReqBody?.compass==0,"")

        addRowView("震动器",mCheckReqBody?.vibrator == 0,"","定位",mCheckReqBody?.location==0,"")
        addRowView("摄像头",mCheckReqBody?.camera == 0,"","指纹",mCheckReqBody?.fingerprint==0,"")
        addRowView("屏幕触控",mCheckReqBody?.multiTouch == 0,"","拨打电话",mCheckReqBody?.call==0,"")
        addRowView("屏幕坏点",mCheckReqBody?.screen == 0,"","语音助手",mCheckReqBody?.comprehensionAids==0,"")
        addRowView("电池状态",false,"83%","",true,"")
        if(this@CheckResulttActivity.mCheckReqBody == null) {
            custom_phone.visibility = View.VISIBLE
            custom_phone.text = getString(R.string.check_time,mCheckReqBody?.checkTime)
        } else {
            custom_phone.visibility = View.VISIBLE
            //todo huyi
            custom_phone.text = getString(R.string.custom_phone,mCheckReqBody?.checkTime)
        }
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

    override fun initDatas() {
        presenter.getGoodsInstanceById(recordid)
        if(mCheckReqBody != null) {
            submit.visibility = View.VISIBLE
            var type = UserInfoUtils.getUser().data?.userInfoVO?.type
            when (type) {
                "1" -> {
                    submit.text="立即回收"
                    custom_phone.visibility = View.VISIBLE
                }
                "4" -> {
                    submit.text="返回主页"
                }
            }
            submit.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    when (type) {
                        "1" -> {
                            var map = HashMap<String, Any?>()
                            map["recordid"] = recordid
                            ActivityToActivity.toActivity(
                                this@CheckResulttActivity, RecycleInputUserInfoActivity::class.java,map)
                        }
                        "4" -> {
                            finish()
                        }
                    }
                }
            })
        } else{
            txt_main_title.text = "检测详情"
        }
    }

    private fun showLogo(logos: String?, icon: ImageView) {
        if (logos?.contains("@") == true) {
            var logonames = logos.split("@")
            if (logonames.size >0) {
                var url = UrlConst.FILE_DOWNLOAD_URL+logonames[0]
                Glide.with(this@CheckResulttActivity).load(url).into(icon)
            }
        } else {
            if (!TextUtils.isEmpty(logos)) {
                var url = UrlConst.FILE_DOWNLOAD_URL+logos
                Glide.with(this@CheckResulttActivity).load(url).into(icon)
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
