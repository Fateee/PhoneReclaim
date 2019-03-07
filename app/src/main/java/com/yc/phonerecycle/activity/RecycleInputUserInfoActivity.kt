package com.yc.phonerecycle.activity

import android.text.TextUtils
import android.view.View
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.biz.AboutUsRep
import com.yc.phonerecycle.model.bean.request.RecycleReqBody
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_recycle_userinfo.*


class RecycleInputUserInfoActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonIV{
    override fun getDataOK(rep: Any?) {
        if (rep is BaseRep) {
            if (rep?.code == 0) {
                ToastUtil.showShortToastCenter("订单提交成功")
//                finish()
            }
        }
    }

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_recycle_userinfo

    override fun initView() {
    }

    override fun initDatas() {
        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var username = recycle_username.mItemEdit.text.toString()
                var phone_num= recycle_phone.mItemEdit.text.toString()
                var city = recycle_addr_city.mItemEdit.text.toString()
                var area = recycle_addr_area.mItemEdit.text.toString()
                var addr_detail = recycle_addr_detail.text.toString()

                if (TextUtils.isEmpty(username)) {
                    ToastUtil.showShortToastCenter("请输入您的姓名")
                    return
                }
                if (TextUtils.isEmpty(phone_num)) {
                    ToastUtil.showShortToastCenter("请输入您的手机号码")
                    return
                }
                var orderVO = RecycleReqBody()

                presenter.addOrder(orderVO)
            }
        })
    }

}
