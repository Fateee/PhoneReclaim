package com.yc.phonerecycle.activity

import android.os.CountDownTimer
import android.text.TextUtils
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.biz.LoginRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.PhoneUtil
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import kotlinx.android.synthetic.main.titleview.*


class BindPhoneForThirdActivity : BaseActivity<CommonPresenter>(),  CommonBaseIV.SignUpIv,CommonBaseIV.ThirdBindIV {
    override fun loginResponse(data: Any?) {
    }

    override fun loginWX(
        accessToken: String?,
        uId: String?,
        expiresIn: Long,
        wholeData: String?,
        body: MutableMap<String, Any>?
    ) {
    }

    override fun loginQQ(accessToken: String?, uId: String?, expiresIn: Long, wholeData: String?) {
    }

    override fun thirdBindOKGetSystemTokenResponse(data: Any?) {
        if (data is LoginRep) {
            if(data.code == 0) {
                ActivityToActivity.toActivity(
                    this@BindPhoneForThirdActivity, MainActivity::class.java)
                finish()
            } else {
                if (!TextUtils.isEmpty(data.info))
                    ToastUtil.showShortToastCenter(data.info)
            }
        }
    }

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

    private var type: String?= null

    private var openid: String? = null

    override fun initBundle() {
        openid = intent.getStringExtra("openid")
        type = intent.getStringExtra("type")
    }

    override fun getContentView(): Int = R.layout.activity_forget_pwd

    override fun initView() {
    }

    override fun initDatas() {
        txt_main_title.text = "绑定手机号"
        reset_pwd_pwd.visibility = View.GONE
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
                presenter.sendCode(1,content)
            }
        })
        txt_right_title.setOnClickListener(object : View.OnClickListener {
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

                presenter.saveUserPhone(code,openid,content)
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

//    override fun resetPwdByPhoneOK(data: Any?) {
//        if ((data as BaseRep).code == 0) {
//            ToastUtil.showShortToastCenter("修改成功")
//            finish()
//        }
//    }
}
