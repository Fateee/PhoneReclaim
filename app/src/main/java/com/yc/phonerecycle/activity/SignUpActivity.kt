package com.yc.phonerecycle.activity

import android.icu.util.TimeUnit
import android.os.CountDownTimer
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity<EmptyPresenter>() {

    var timer = object : CountDownTimer(60*1000,1000) {
        override fun onFinish() {
            signup_verfy_code.isEnabled = true
            signup_verfy_code.text = "重新获取"
        }

        override fun onTick(seconds: Long) {
            signup_verfy_code.isEnabled = false
            signup_verfy_code.text = (seconds/1000).toString()+"秒"
        }

    }
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
        signup_verfy_code.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                timer.start()
            }
        })
    }

}
