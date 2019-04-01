package com.yc.phonerecycle.activity

import android.support.v4.content.ContextCompat
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.settlist.CreateBankPwdActivity
import com.yc.phonerecycle.constant.UrlConst
import com.yc.phonerecycle.model.bean.biz.ExistDrawPasswordRep
import com.yc.phonerecycle.model.bean.biz.UserMoneyRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import kotlinx.android.synthetic.main.activity_my_wallet.*


class MyWalletActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonIV{
    override fun getDataOK(rep: Any?) {
        if (rep is UserMoneyRep) {
            money_value.text = rep.data.toString()
//            if(rep.data > 0 || !UrlConst.realease) submit.visibility = View.VISIBLE else submit.visibility = View.GONE
            if (rep.data > 0) {
                submit.setBackgroundColor(resources.getColor(R.color.c0168b7))
                submit.isEnabled = true
//                submit.isClickable = true
            }
        } else if (rep is ExistDrawPasswordRep) {
            if (rep.data) {
                var map = HashMap<String,String>()
                map["money"] = money_value.text.toString()
                ActivityToActivity.toActivity(
                    this@MyWalletActivity, CashWxBankActivity::class.java,map)
            } else {
                ActivityToActivity.toActivity(
                    this@MyWalletActivity, CreateBankPwdActivity::class.java)
            }
        }
    }

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_my_wallet

    override fun initView() {
    }

    override fun initDatas() {
        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                presenter.existDrawPassword()
            }
        })
        presenter.getUserMoney()
    }

}
