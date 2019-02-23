package com.yc.phonerecycle.activity

import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<EmptyPresenter>() {
    override fun createPresenter(): EmptyPresenter? = null

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
            }
        })
    }
}
