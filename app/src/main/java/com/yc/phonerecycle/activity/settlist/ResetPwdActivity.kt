package com.yc.phonerecycle.activity.settlist

import android.text.TextUtils
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_splash.*
import android.widget.LinearLayout
import android.view.View
import android.widget.ImageView
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_reset_pwd.*


class ResetPwdActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.resetPasswordIV{

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_reset_pwd

    override fun initView() {
    }

    override fun initDatas() {
        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var old = reset_pwd_old.mItemEdit.text.toString()
                var new = reset_pwd_new.mItemEdit.text.toString()
                var confirm = reset_pwd_confirm.mItemEdit.text.toString()

                if (TextUtils.isEmpty(old)) {
                    ToastUtil.showShortToastCenter("请先填写旧密码")
                    return
                }
                if (TextUtils.isEmpty(new) || TextUtils.isEmpty(confirm)) {
                    ToastUtil.showShortToastCenter("请先填写新密码")
                    return
                }
                if (new.length !in 6..20 || confirm.length !in 6..20) {
                    ToastUtil.showShortToastCenter("密码需为6-20位字母或数字")
                    return
                }
                if (!new.equals(confirm)) {
                    ToastUtil.showShortToastCenter("新密码不一致")
                    return
                }
                presenter.resetPassword(old,new,UserInfoUtils.getUser().data?.userInfoVO?.id)
            }
        })
    }

    override fun resetPasswordOK(data: BaseRep?) {
        if ((data as BaseRep).code == 0) {
            ToastUtil.showShortToastCenter("修改成功")
            finish()
        }
    }
}
