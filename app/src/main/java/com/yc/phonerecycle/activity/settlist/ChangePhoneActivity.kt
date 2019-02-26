package com.yc.phonerecycle.activity.settlist

import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.PhoneUtil
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_reset_phone.*


class ChangePhoneActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.SignUpIv,CommonBaseIV.changePhoneIV{

    var timer = object : CountDownTimer(60*1000,1000) {
        override fun onFinish() {
            reset_pwd_vercode.mVerfyCode.isEnabled = true
            reset_pwd_vercode.mVerfyCode.text = "重发验证码"
        }

        override fun onTick(seconds: Long) {
            reset_pwd_vercode.mVerfyCode.isEnabled = false
            reset_pwd_vercode.mVerfyCode.text = (seconds/1000).toString()+"秒后重发"
        }

    }

    override fun createPresenter() = CommonPresenter()

    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_reset_phone

    override fun initView() {
    }

    override fun initDatas() {
        reset_pwd_vercode.mVerfyCode.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var content = reset_pwd_phone.mItemEdit.text.toString()
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShortToastCenter("请先填写手机号码")
                    return
                }
                if (!PhoneUtil.isMobileNO(content)) {
                    ToastUtil.showShortToastCenter("手机号码格式不正确")
                }
                presenter.sendCode(content)
            }
        })
        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var content = reset_pwd_phone.mItemEdit.text.toString()
                var code = reset_pwd_vercode.mItemEdit.text.toString()

                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShortToastCenter("请先填写手机号码")
                    return
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShortToastCenter("请先填写验证码")
                    return
                }
                presenter.changePhone(code,content)
            }
        })
    }

    override fun requestCodeOK(data: Any?) {
        if ((data as BaseRep).code == 0) {
            ToastUtil.showShortToastCenter("短信验证码已发送")
            timer.start()
        }
    }

    override fun registerSuccess(data: Any?) {

    }

    override fun requestCodeError() {
    }

    override fun registerError(message: String?) {
        ToastUtil.showShortToastCenter(message)
    }

    override fun changePhone(data: BaseRep?) {
        if ((data as BaseRep).code == 0) {
            ToastUtil.showShortToastCenter("修改成功")
            var temp = UserInfoUtils.getUser()
            temp.data?.userInfoVO?.phone = reset_pwd_phone.mItemEdit.text.toString()
            UserInfoUtils.saveUser(temp)
            finish()
        }
    }
}
