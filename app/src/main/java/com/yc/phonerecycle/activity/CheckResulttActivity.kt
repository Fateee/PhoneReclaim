package com.yc.phonerecycle.activity

import android.text.TextUtils
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.UserInfoUtils
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.OrderDetailRep
import com.yc.phonerecycle.model.bean.request.CheckReqBody
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import kotlinx.android.synthetic.main.activity_check_result_detail.*
import kotlinx.android.synthetic.main.item_check_result.view.*
import kotlinx.android.synthetic.main.item_check_result_container.view.*


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

    override fun initBundle() {
        mCheckReqBody = intent.getSerializableExtra("checkbean") as CheckReqBody
        result_type = intent.getStringExtra("result_type")
        recordid = intent.getStringExtra("recordid")
    }

    override fun getContentView(): Int = R.layout.activity_check_result_detail

    override fun initView() {

    }

    private fun refreshView(mCheckReqBody: OrderDetailRep.DataBean?) {
        name.text = mCheckReqBody?.brandName+" "+mCheckReqBody?.type+" "+mCheckReqBody?.regional
        content.text = mCheckReqBody?.memory+" "+mCheckReqBody?.capacity
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
        }
    }

}
