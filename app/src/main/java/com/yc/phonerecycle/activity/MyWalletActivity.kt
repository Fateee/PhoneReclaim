package com.yc.phonerecycle.activity

import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import kotlinx.android.synthetic.main.activity_my_wallet.*


class MyWalletActivity : BaseActivity<EmptyPresenter>(){
    override fun createPresenter(): EmptyPresenter? = null


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_my_wallet

    override fun initView() {
    }

    override fun initDatas() {
        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MyWalletActivity, CashWxBankActivity::class.java)
            }
        })
    }

}