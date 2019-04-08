package com.yc.phonerecycle.activity

import android.os.CountDownTimer
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.biz.StringDataRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.PhoneUtil
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import kotlinx.android.synthetic.main.titleview.*


class ForgetPwdActivity : BaseActivity<CommonPresenter>(),  CommonBaseIV.SignUpIv, CommonBaseIV.ResetPwdByPhoneIv{

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

    override fun getContentView(): Int = R.layout.activity_forget_pwd

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
                if (!PhoneUtil.isMobileLength(content)) {
                    ToastUtil.showShortToastCenter("手机号码格式不正确")
                    return
                }
                presenter.sendCode(2,content)
            }
        })
        txt_right_title.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var content = reset_pwd_phone.mItemEdit.text.toString()
                var code = reset_pwd_vercode.mItemEdit.text.toString()
                var pwd = reset_pwd_pwd.mItemEdit.text.toString()
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShortToastCenter("请先填写手机号码")
                    return
                }
                if (!PhoneUtil.isMobileLength(content)) {
                    ToastUtil.showShortToastCenter("手机号码格式不正确")
                    return
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShortToastCenter("请先填写验证码")
                    return
                }
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.showShortToastCenter("请先填写密码")
                    return
                }
                if (pwd.length !in 6..20) {
                    ToastUtil.showShortToastCenter("密码需为6-20位字母或数字")
                    return
                }
                presenter.restPasswordByPhone(code,pwd,content)
            }
        })
        reset_pwd_pwd.mItemSwitch.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                reset_pwd_pwd.mItemEdit.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                reset_pwd_pwd.mItemEdit.transformationMethod = PasswordTransformationMethod.getInstance()
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

    }

    override fun requestCodeError(message: String) {
        ToastUtil.showShortToastCenter("error : $message")
    }

    override fun registerError(message: String?) {
        ToastUtil.showShortToastCenter(message)
    }

    override fun resetPwdByPhoneOK(data: Any?) {
        if (data is StringDataRep) {
            if (data.code == 0) {
                ToastUtil.showShortToastCenter("修改成功")
                finish()
            } else {
                ToastUtil.showShortToastCenter(data.data)
            }
        }
    }
}
