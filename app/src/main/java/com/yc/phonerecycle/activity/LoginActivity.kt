package com.yc.phonerecycle.activity

import android.text.TextUtils
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.LoginRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.PhoneUtil
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.LoginViewIV {

    override fun createPresenter(): CommonPresenter? = CommonPresenter()

    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_login

    override fun initView() {
    }

    override fun initDatas() {
        tv_login_forget_pwd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@LoginActivity, ForgetPwdActivity::class.java)
            }
        })
        tv_sign_up.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@LoginActivity, SignUpActivity::class.java)
            }
        })
        tv_login_action.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (TextUtils.isEmpty(login_phone_et.text.toString())) {
                    ToastUtil.showShortToastCenter("请输入登录手机号码")
                    return
                }
                if (!PhoneUtil.isMobileNO(login_phone_et.text.toString())) {
                    ToastUtil.showShortToastCenter("手机号码格式不正确")
                }
                presenter.loginAction(login_phone_et?.text.toString(), login_pwd_et?.text.toString())
//                presenter.loginAction("admin", "123456")
            }
        })
        iv_login_wx.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                //todo huyi
            }
        })
        iv_login_qq.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                //todo huyi
            }
        })
    }

    override fun loginResponse(data: Any?) {
        if (data is LoginRep) {
            if(data.code == 0) {
                UserInfoUtils.saveUser(data)
                ActivityToActivity.toActivity(
                    this@LoginActivity, MainActivity::class.java)
                finish()
            } else {
                if (!TextUtils.isEmpty(data.info))
                    ToastUtil.showShortToastCenter(data.info)
            }
        }
    }
}
