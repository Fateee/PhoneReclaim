package com.yc.phonerecycle.activity

import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.constant.UrlConst
import com.yc.phonerecycle.model.bean.biz.UserMoneyRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import kotlinx.android.synthetic.main.activity_my_wallet.*


class MyWalletActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonIV{
    override fun getDataOK(rep: Any?) {
        if (rep is UserMoneyRep) {
            money_value.text = rep.data.toString()
            if(rep.data > 0 || !UrlConst.realease) submit.visibility = View.VISIBLE else submit.visibility = View.GONE
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
                var map = HashMap<String,String>()
                map["money"] = money_value.text.toString()
                ActivityToActivity.toActivity(
                    this@MyWalletActivity, CashWxBankActivity::class.java,map)
            }
        })

        presenter.getUserMoney()
    }

}
