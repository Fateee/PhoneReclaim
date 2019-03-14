package com.yc.phonerecycle.activity

import android.os.CountDownTimer
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.PhoneUtil
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.SignUpIv {

    var timer = object : CountDownTimer(60*1000,1000) {
        override fun onFinish() {
            signup_verfy_code.isEnabled = true
            signup_verfy_code.text = "重发验证码"
        }

        override fun onTick(seconds: Long) {
            signup_verfy_code.isEnabled = false
            signup_verfy_code.text = (seconds/1000).toString()+"秒后重发"
        }

    }
    override fun createPresenter(): CommonPresenter? = CommonPresenter()

    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_sign_up

    override fun initView() {
    }

    override fun initDatas() {

        tv_signup_action.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var pwd = signup_pwd_et.text.toString()
                if (TextUtils.isEmpty(signup_phone_et.text.toString()) ||
                    TextUtils.isEmpty(signup_verfy_code_et.text.toString())
                    || TextUtils.isEmpty(pwd)) {
                    return
                }
                if (pwd.length !in 6..20) {
                    ToastUtil.showShortToastCenter("密码需为6-20位字母或数字")
                    return
                }
                presenter.register(signup_verfy_code_et.text.toString(),signup_pwd_et.text.toString(),
                    signup_phone_et.text.toString(),signup_recommend_phone_et.text.toString())
            }
        })
        signup_verfy_code.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (TextUtils.isEmpty(signup_phone_et.text.toString())) {
                    ToastUtil.showShortToastCenter("请先填写手机号码")
                    return
                }
                if (!PhoneUtil.isMobileNO(signup_phone_et.text.toString())) {
                    ToastUtil.showShortToastCenter("手机号码格式不正确")
                }
                presenter.sendCode(1,signup_phone_et.text.toString())
            }
        })
        signup_pwd_switch.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                signup_pwd_et.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                signup_pwd_et.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    override fun requestCodeOK(data: Any?) {
        if ((data as BaseRep).code == 0) {
            ToastUtil.showShortToastCenter("短信验证码已发送")
            timer.start()
        } else {
            ToastUtil.showShortToastCenter(data.info)
        }
    }

    override fun registerSuccess(data: Any?) {
        if ((data as BaseRep).code == 0) {
            ToastUtil.showShortToastCenter("注册成功")
            finish()
        }
    }

    override fun requestCodeError(message: String) {
        ToastUtil.showShortToastCenter("error : $message")
    }

    override fun registerError(message: String?) {
        ToastUtil.showShortToastCenter(message)
    }
}
