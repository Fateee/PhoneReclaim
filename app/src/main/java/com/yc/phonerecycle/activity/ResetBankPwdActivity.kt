package com.yc.phonerecycle.activity

import android.text.TextUtils
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.UserInfoUtils
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_reset_cash_pwd.*


class ResetBankPwdActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.resetWithdrawPasswordIV{

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_reset_cash_pwd

    override fun initView() {
    }

    override fun initDatas() {
        reset_cash_pwd_submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var old = reset_cash_pwd_old.mItemEdit.text.toString()
                var new = reset_cash_pwd.mItemEdit.text.toString()
                var confirm = reset_cash_pwd_comfirm.mItemEdit.text.toString()

                if (TextUtils.isEmpty(old)) {
                    ToastUtil.showShortToastCenter("请先填写旧密码")
                    return
                }
                if (TextUtils.isEmpty(new) || TextUtils.isEmpty(confirm)) {
                    ToastUtil.showShortToastCenter("请先填写新密码")
                    return
                }
                if (new.length != 6 || confirm.length != 6) {
                    ToastUtil.showShortToastCenter("密码需为6位数字")
                    return
                }
                if (!new.equals(confirm)) {
                    ToastUtil.showShortToastCenter("新密码不一致")
                    return
                }
                presenter.resetWithdrawPassword(old,new,UserInfoUtils.getUser().data?.userInfoVO?.id)
            }
        })
    }

    override fun resetWithdrawPasswordOK(data: BaseRep?) {
        if ((data as BaseRep).code == 0) {
            ToastUtil.showShortToastCenter("修改成功")
            finish()
        }
    }
}
