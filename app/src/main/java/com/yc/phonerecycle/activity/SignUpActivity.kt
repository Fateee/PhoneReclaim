package com.yc.phonerecycle.activity

import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity<EmptyPresenter>() {
    override fun createPresenter(): EmptyPresenter? = null

    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_sign_up

    override fun initView() {
    }

    override fun initDatas() {
        tv_signup_action.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
            }
        })
    }

}
