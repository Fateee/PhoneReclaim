package com.yc.phonerecycle.activity

import android.text.TextUtils
import android.view.View
import com.yc.library.widget.AddressSelector
import com.yc.library.widget.BottomDialog
import com.yc.library.widget.OnAddressSelectedListener
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.constant.BaseConst
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.biz.AboutUsRep
import com.yc.phonerecycle.model.bean.biz.DivisionRep
import com.yc.phonerecycle.model.bean.biz.RecycleAddrRep
import com.yc.phonerecycle.model.bean.biz.StringDataRep
import com.yc.phonerecycle.model.bean.request.RecycleReqBody
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_recycle_userinfo.*


class RecycleInputUserInfoActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonIV,CommonBaseIV.SaveAddrIV,
    OnAddressSelectedListener,
    AddressSelector.OnDialogCloseListener, AddressSelector.onSelectorAreaPositionListener{
    override fun saveAddrOK(data: BaseRep?) {
        if (data is StringDataRep) {
            if (data?.code == 0) {
                ToastUtil.showShortToastCenter("地址提交成功")

                var orderVO = RecycleReqBody()
                orderVO.goodsInstance = recordid
                orderVO.area = addressVO.address
                orderVO.addressId = data?.data
                orderVO.status = BaseConst.ORDER_WAIT_EMS
                presenter.addOrder(orderVO)
            }
        }
    }

    override fun getDataOK(rep: Any?) {
        if (rep is BaseRep) {
            if (rep?.code == 0) {
                ToastUtil.showShortToastCenter("订单提交成功")
                var map = HashMap<String, Any?>()
                map["recordid"] = recordid
                ActivityToActivity.toActivity(
                    this@RecycleInputUserInfoActivity, OrderSubmitSuccessActivity::class.java,map)
                finish()
            }
        }
    }

    override fun createPresenter() = CommonPresenter()

    var addressVO = RecycleAddrRep.DataBean()
    private var recordid: String? = ""
    private var address: String? = ""
    private var dialog : BottomDialog? = null

    override fun initBundle() {
        recordid = intent.getStringExtra("recordid")
    }

    override fun getContentView(): Int = R.layout.activity_recycle_userinfo

    override fun initView() {
    }

    override fun initDatas() {
        recycle_addr_defalut.mItemSwitch.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                addressVO.defaultAdd = 1
            } else {
                addressVO.defaultAdd = 0
            }
        }
        recycle_addr_city.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                addrDialog()
            }
        })
        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var username = recycle_username.mItemEdit.text.toString()
                var phone_num= recycle_phone.mItemEdit.text.toString()
//                var city = recycle_addr_city.mItemEdit.text.toString()
//                var area = recycle_addr_area.mItemEdit.text.toString()
                var addr_detail = recycle_addr_detail.text.toString()

                if (TextUtils.isEmpty(username)) {
                    ToastUtil.showShortToastCenter("请输入您的姓名")
                    return
                }
                if (TextUtils.isEmpty(phone_num)) {
                    ToastUtil.showShortToastCenter("请输入您的手机号码")
                    return
                }
                if (TextUtils.isEmpty(address)) {
                    ToastUtil.showShortToastCenter("请输入地址")
                    return
                }
                addressVO.name = username
                addressVO.phone = phone_num
                addressVO.address = addrcode
                addressVO.addressDetail = addr_detail
                presenter.saveOrUpdateAddress(addressVO)
            }
        })
    }

    override fun selectorAreaPosition(
        provincePosition: Int,
        cityPosition: Int,
        countyPosition: Int,
        streetPosition: Int
    ) {

    }

    override fun dialogclose() {
        dialog?.dismiss()
    }

    private var addrcode: String?=""

    override fun onAddressSelected(
        province: DivisionRep.DataBean.VoListBean?,
        city: DivisionRep.DataBean.VoListBean?,
        county: DivisionRep.DataBean.VoListBean?,
        street: DivisionRep.DataBean.VoListBean?,
        village: DivisionRep.DataBean.VoListBean?
    ) {
        address = province?.provinceName+city?.cityName+county?.countyName
        addrcode = county?.countyCode
        recycle_addr_city.setSubTitle(address)
    }

    public fun addrDialog() {
        if (dialog != null) {
            dialog?.show();
        } else {
            dialog = BottomDialog(this@RecycleInputUserInfoActivity)
            dialog?.setOnAddressSelectedListener(this@RecycleInputUserInfoActivity)
            dialog?.setDialogDismisListener(this@RecycleInputUserInfoActivity)
            dialog?.setTextSize(14f);//设置字体的大小
            dialog?.setIndicatorBackgroundColor(android.R.color.holo_orange_light);//设置指示器的颜色
            dialog?.setTextSelectedColor(android.R.color.holo_orange_light);//设置字体获得焦点的颜色
            dialog?.setTextUnSelectedColor(android.R.color.holo_blue_light);//设置字体没有获得焦点的颜色
//            dialog.setDisplaySelectorArea("31",1,"2704",1,"2711",0,"15582",1);//设置已选中的地区
            dialog?.setSelectorAreaPositionListener(this@RecycleInputUserInfoActivity);
            dialog?.show();
        }
    }
}
