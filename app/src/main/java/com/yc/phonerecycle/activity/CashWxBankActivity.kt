package com.yc.phonerecycle.activity

import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.settlist.CreateBankPwdActivity
import com.yc.phonerecycle.constant.BaseConst
import com.yc.phonerecycle.constant.UrlConst
import com.yc.phonerecycle.interfaces.OnBankClickListener
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.biz.BankCardListRep
import com.yc.phonerecycle.model.bean.biz.LoginRep
import com.yc.phonerecycle.model.bean.request.CashAccountReqBody
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.utils.UserInfoUtils
import com.yc.phonerecycle.widget.BottomCardsDialog
import com.yc.phonerecycle.widget.PasswordDialog
import kotlinx.android.synthetic.main.activity_cash_wx_bank.*
import third.wx.SsoLoginType


class CashWxBankActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.MoneyIV, OnBankClickListener,CommonBaseIV.ThirdAuthIV {
    override fun thirdAuthResponse(success: Boolean) {
        if (success) {
            mPayDialog?.show()
        }
    }

    override fun loginResponse(data: Any?) {
//        if (data is LoginRep) {
//            if(data.code == 0) {
//                mPayDialog?.show()
//            } else {
//                if (!TextUtils.isEmpty(data.info))
//                    ToastUtil.showShortToastCenter(data.info)
//            }
//        }
    }

//    override fun loginWX(
//        accessToken: String?,
//        uId: String?,
//        expiresIn: Long,
//        wholeData: String?,
//        body: MutableMap<String, Any>?
//    ) {
//    }
//
//    override fun loginQQ(accessToken: String?, uId: String?, expiresIn: Long, wholeData: String?) {
//    }

//    override fun goBindPhoneView(openID: String?, type: String?) {
//        var map = HashMap<String,String?>()
//        map["openid"] = openID
//        map["type"] = type
//        ActivityToActivity.toActivity(this@CashWxBankActivity, BindPhoneForThirdActivity::class.java,map, BaseConst.REQUEST_BIND_PHONE)
//    }

    private var mDataBean: BankCardListRep.DataBean? = null


    override fun OnBankClick(dataBean: BankCardListRep.DataBean?) {
        mDataBean = dataBean
        mBankDialog?.dismiss()
        submit.postDelayed({mPayDialog?.show()},500)

    }

    private var mAccount: String = ""

    override fun cashPwdOK(rep: BaseRep?) {
        if ((rep as BaseRep).code == 0) {
            var writeTrackingVO = CashAccountReqBody()
            writeTrackingVO.userId = UserInfoUtils.getUserId()
            writeTrackingVO.userType = UserInfoUtils.getUserType()
            writeTrackingVO.meoney = mMoneyStr.toFloat()
            if (mCashType == 0) {
                var openid = UserInfoUtils.getUserWxTokenRep().openid
//                presenter.saveWXBankCard(openid)
                writeTrackingVO.account = openid
                writeTrackingVO.type = 0
            } else if (mCashType == 1) {
                writeTrackingVO.account = mDataBean?.acount
                writeTrackingVO.bankName = mDataBean?.openingBank
                writeTrackingVO.type = 1
            }
            mAccount = writeTrackingVO.account
            presenter.saveUserMoney(writeTrackingVO)
        } else if (rep.code == 17) {
            ActivityToActivity.toActivity(
                this@CashWxBankActivity, CreateBankPwdActivity::class.java)
        } else {
            ToastUtil.showShortToastCenter(rep.info)
        }
    }

    override fun saveMoneyBankOK(rep: BaseRep?) {
        var map = HashMap<String,String?>()
        map["money"] = moneyValue
        map["mCashType"] = mCashType.toString()
        map["account"] = mAccount
        ActivityToActivity.toActivity(
            this@CashWxBankActivity, CashSuccessActivity::class.java,map)
        finish()
    }

    override fun saveMoneyWXOK(rep: BaseRep?) {

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
        mBankDialog = object : BottomCardsDialog(this@CashWxBankActivity){}
    }

    private var mPayDialog: PasswordDialog? = null
    private var mBankDialog: BottomCardsDialog? = null

    private var moneyValue: String=""

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
                mPayDialog?.dismiss()
                presenter.inputWithdrawPassword(pwd)
            }
        })
        money_et.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                moneyValue = money_et.text.toString()
//                all_cash_out_tip.text = getString(R.string.all_cash_out_tip,moneyValue)
            }
        })
        submit.setOnClickListener(object : View.OnClickListener {

            override fun onClick(p0: View?) {
                money_et.clearFocus()
                if (TextUtils.isEmpty(moneyValue)) return
                if (moneyValue.toDouble() > mMoneyStr.toDouble()) {
                    ToastUtil.showShortToastCenter(getString(R.string.all_cash_out_tip,mMoneyStr))
                    return
                }
                //验证提现密码，提现密码接口
                //调用提现接口,成功后跳转
                if (mCashType == 0) {
                    presenter.login(this@CashWxBankActivity, SsoLoginType.WEIXIN)
                } else if (mCashType == 1) {
                    mBankDialog?.show(moneyValue,this@CashWxBankActivity)
                }
            }
        })
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == BaseConst.REQUEST_BIND_PHONE && resultCode == BaseConst.REQUEST_BIND_PHONE) {
//            mPayDialog?.show()
//        }
//    }
}
