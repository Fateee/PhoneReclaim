package com.yc.phonerecycle.activity

import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.request.CashAccountReqBody
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.widget.PasswordDialog
import kotlinx.android.synthetic.main.activity_cash_wx_bank.*


class CashWxBankActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.MoneyIV{
    override fun cashPwdOK(rep: BaseRep?) {
        if ((rep as BaseRep).code == 0) {
            if (mCashType == 0) {
                presenter.saveWXBankCard("")
            } else if (mCashType == 1) {
//                var writeTrackingVO = CashAccountReqBody()
//                presenter.saveUserMoney()
            }
        }
    }

    override fun saveMoneyBankOK(rep: BaseRep?) {
        ActivityToActivity.toActivity(
            this@CashWxBankActivity, CashSuccessActivity::class.java)
        finish()
    }

    override fun saveMoneyWXOK(rep: BaseRep?) {
        ActivityToActivity.toActivity(
            this@CashWxBankActivity, CashSuccessActivity::class.java)
        finish()
    }

    override fun createPresenter() = CommonPresenter()

    private lateinit var mMoneyStr: String
    private var mCashType = 0

    override fun initBundle() {
        mMoneyStr = intent.getStringExtra("money")
    }

    override fun getContentView(): Int = R.layout.activity_cash_wx_bank

    override fun initView() {
        all_cash_out_tip.text = getString(R.string.all_cash_out_tip,mMoneyStr)
        mPayDialog = object : PasswordDialog(this@CashWxBankActivity){}
    }

    private var mPayDialog: PasswordDialog? = null

    override fun initDatas() {
        all_cash_out.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                money_et.setText(mMoneyStr)
            }
        })

        cash_to_wx.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                mCashType = 0
                cash_to_wx.setRightIcon(R.drawable.ic_cash_checked)
                cash_to_bank.setRightIcon(R.drawable.ic_cash_uncheck)
            }
        })
        cash_to_bank.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                mCashType = 1
                cash_to_bank.setRightIcon(R.drawable.ic_cash_checked)
                cash_to_wx.setRightIcon(R.drawable.ic_cash_uncheck)
            }
        })

        mPayDialog?.setViewClickListener(object : PasswordDialog.ViewClickListener {
            override fun click(pwd: String?) {
                presenter.inputWithdrawPassword(pwd)
            }
        })

        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                money_et.clearFocus()
                //验证提现密码，提现密码接口
                //调用提现接口,成功后跳转

                mPayDialog?.show()
            }
        })
    }

}
