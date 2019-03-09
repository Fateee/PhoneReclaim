package com.yc.phonerecycle.activity

import com.yc.phonerecycle.mvp.view.BaseActivity
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import kotlinx.android.synthetic.main.activity_cash_success.*


class CashSuccessActivity : BaseActivity<EmptyPresenter>(){
    override fun createPresenter(): EmptyPresenter? = null

    private lateinit var mMoneyStr: String
    private lateinit var mCashType : String
    private lateinit var mAccount: String

    override fun initBundle() {
        mMoneyStr = intent.getStringExtra("money")
        mCashType = intent.getStringExtra("mCashType")
        mAccount = intent.getStringExtra("account")
    }

    override fun getContentView(): Int = R.layout.activity_cash_success

    override fun initView() {
        money_value.text = mMoneyStr
        cash_account.text = getString(R.string.cash_account,mAccount)
        when(mCashType) {
            "0"-> {
                tip.text = "请在我的钱包零钱中查看余额"
            }
        }
    }

    override fun initDatas() {
        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                finish()
            }
        })
    }

}
