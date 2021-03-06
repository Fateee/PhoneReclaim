package com.yc.phonerecycle.activity

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.constant.UrlConst
import com.yc.phonerecycle.model.bean.BaseBean
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.biz.MyOrderDetailRep
import com.yc.phonerecycle.model.bean.biz.MyOrderListlRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.widget.EmsDialog
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.item_list_common.*


class OrderDetailActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonIV{

    private var ord_id: String? = ""
    private var mEmsDialog: EmsDialog? = null
    override fun getDataOK(rep: Any?) {
        if (rep is MyOrderDetailRep) {
            when(rep.data.status) {//0、已完成 1、待寄出 2、待收货 3、已退回 4、验机 5、待打款
                0->{
                    order_done.visibility = View.VISIBLE
                    order_sendby.visibility = View.GONE
                    order_getby.visibility = View.GONE
                    order_return.visibility = View.GONE
                    order_waitcash.visibility = View.GONE
                    order_price_tip.text = "最终回收价："
                }
                1->{
                    order_done.visibility = View.GONE
                    order_sendby.visibility = View.VISIBLE
                    order_getby.visibility = View.GONE
                    order_return.visibility = View.GONE
                    order_waitcash.visibility = View.GONE
                    order_userinfo_tip.text = "收货地址"
                }
                2->{
                    order_done.visibility = View.GONE
                    order_sendby.visibility = View.GONE
                    order_getby.visibility = View.VISIBLE
                    order_return.visibility = View.GONE
                    order_waitcash.visibility = View.GONE
                }
                3->{
                    order_done.visibility = View.GONE
                    order_sendby.visibility = View.GONE
                    order_getby.visibility = View.GONE
                    order_return.visibility = View.VISIBLE
                    order_waitcash.visibility = View.GONE

                    order_ems_tip.text = "退回物流信息"
//                    order_price_tip.text = "最终回收价："
                }
                4->{
                }
                5->{
                    order_done.visibility = View.GONE
                    order_sendby.visibility = View.GONE
                    order_getby.visibility = View.GONE
                    order_return.visibility = View.GONE
                    order_waitcash.visibility = View.VISIBLE
                    order_price_tip.text = "最终回收价："
                }
                6->{
                    order_done.visibility = View.VISIBLE
                    order_done.text = "待验机"
                    order_sendby.visibility = View.GONE
                    order_getby.visibility = View.GONE
                    order_return.visibility = View.GONE
                    order_waitcash.visibility = View.GONE
                }
            }
            order_userinfo_name.text = rep.data.consigneeName
            order_userinfo_address.text = rep.data.address
            order_userinfo_phone.text = rep.data.consigneePhone
            if (rep.data.status != 1) {
                order_ems_content.visibility = View.VISIBLE
                order_userinfo_content.visibility = View.GONE
                order_send_now.visibility = View.GONE
                order_ems_name.text = getString(R.string.order_ems_name,rep.data.courierCompany)
                order_ems_address.text = getString(R.string.order_ems_address,rep.data.trackingNumber)
                order_ems_change.visibility = View.GONE
                if (rep.data.status == 2) {
                    order_ems_change.visibility = View.VISIBLE
                    order_ems_change.setOnClickListener{
                        mEmsDialog?.show()
                    }
                }
            } else {
                order_ems_content.visibility = View.GONE
                order_userinfo_content.visibility = View.VISIBLE
                order_send_now.visibility = View.VISIBLE
                order_send_now.setOnClickListener{
                    mEmsDialog?.show()
                }
            }
            if (TextUtils.isEmpty(rep.data.createTime)) {
                if (order_bean != null) {
                    date.text = order_bean?.createTime?:order_bean?.dealTime
                } else {
                    date.text = rep.data.dealTime
                }
            } else {
                date.text = rep.data.createTime
            }
            var tip = StringBuilder()
            if (order_bean != null) {
                showLogo(order_bean?.logo,icon)
                if (!TextUtils.isEmpty(order_bean?.brandName)) {
                    tip.append(order_bean?.brandName)
                }
                if (!TextUtils.isEmpty(order_bean?.type)) {
                    if (!TextUtils.isEmpty(tip)) {
                        tip.append("-")
                    }
                    tip.append(order_bean?.type)
                }
                if (!TextUtils.isEmpty(order_bean?.capacityValue)) {
                    if (!TextUtils.isEmpty(tip)) {
                        tip.append("+")
                    }
                    tip.append(order_bean?.capacityValue)
                }
                name.text = tip.toString()
//                name.text = order_bean?.brandName+"-"+order_bean?.type+"+"+order_bean?.capacityValue+"G"
            } else {
                if (!TextUtils.isEmpty(rep.data.goodsInstanceVO.brandName)) {
                    tip.append(rep.data.goodsInstanceVO.brandName)
                }
                if (!TextUtils.isEmpty(rep.data.goodsInstanceVO.type)) {
                    if (!TextUtils.isEmpty(tip)) {
                        tip.append("-")
                    }
                    tip.append(rep.data.goodsInstanceVO.type)
                }
                if (!TextUtils.isEmpty(rep.data.goodsInstanceVO.capacity)) {
                    if (!TextUtils.isEmpty(tip)) {
                        tip.append("+")
                    }
                    tip.append(rep.data.goodsInstanceVO.capacity)
                }
                name.text = tip.toString()
//                name.text = rep.data.goodsInstanceVO.brandName+"-"+rep.data.goodsInstanceVO.type+"+"+rep.data.goodsInstanceVO.capacity
            }
            detail.tag = rep.data
            detail.setOnClickListener {
                var tag = detail.tag as MyOrderDetailRep.DataBean
                var map = HashMap<String,Any?>()
                map["recordid"] = tag.goodsInstanceVO?.id
                map["order_bean"] = order_bean
                if (tag.status == 0 || tag.status == 5) {
                    ActivityToActivity.toActivity(
                        this@OrderDetailActivity, ReportDetailActivity::class.java,map)
                } else {
                    ActivityToActivity.toActivity(
                        this@OrderDetailActivity, CheckResulttActivity::class.java,map)
                }
            }
            if (rep.data.status == 0 || rep.data.status == 5) {
                if (TextUtils.isEmpty(rep.data.price)) {
                    order_price_value.text = getString(R.string.order_price_value,order_bean?.price)
                } else {
                    order_price_value.text = getString(R.string.order_price_value,rep.data.price)
                }
            } else {
                order_price_value.text = getString(R.string.order_price_value,rep.data.estimatePrice)
            }
        } else if (rep is BaseRep) {
            if (rep.code == 0) {
                presenter.getOrderDetailbyId(ord_id)
                ToastUtil.showShortToastCenter("快递信息保存成功")
            }
        }
    }

    override fun createPresenter() = CommonPresenter()

    private var order_bean: MyOrderListlRep.DataBean? = null

    override fun initBundle() {
        ord_id = intent?.getStringExtra("ord_id")
        order_bean = intent?.getSerializableExtra("order_bean") as MyOrderListlRep.DataBean?
    }

    override fun getContentView(): Int = R.layout.activity_order_detail

    override fun initView() {
        mEmsDialog = object : EmsDialog(this@OrderDetailActivity){}
    }

    override fun initDatas() {
        presenter.getOrderDetailbyId(ord_id)

        mEmsDialog?.setViewClickListener(object : EmsDialog.ViewClickListener {
            override fun click(company: String?, num: String?) {
                presenter.writeTracking(company,ord_id,num)
            }
        })
    }

    private fun showLogo(logos: String?, icon: ImageView) {
        if (logos?.contains("@") == true) {
            var logonames = logos.split("@")
            if (logonames.size >0) {
                var url = UrlConst.FILE_DOWNLOAD_URL+logonames[0]
                Glide.with(this@OrderDetailActivity).load(url).into(icon)
            }
        } else {
            if (!TextUtils.isEmpty(logos)) {
                var url = UrlConst.FILE_DOWNLOAD_URL+logos
                Glide.with(this@OrderDetailActivity).load(url).into(icon)
            }
        }
    }
}
