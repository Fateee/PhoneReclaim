package com.yc.phonerecycle.activity

import android.view.View
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_recycle_success.*


class OrderSubmitSuccessActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonIV{
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

    override fun getContentView(): Int = R.layout.activity_recycle_success

    override fun initView() {
    }

    override fun initDatas() {
        back_home.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                ActivityToActivity.toActivity(
                    this@OrderSubmitSuccessActivity, MainActivity::class.java)
            }
        })
        order_detail.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                ActivityToActivity.toActivity(
                    this@OrderSubmitSuccessActivity, MyOrderListActivity::class.java)
            }
        })
    }

}
