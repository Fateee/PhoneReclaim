package com.yc.phonerecycle.activity

import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import kotlinx.android.synthetic.main.activity_setting_list.*


class MySetListActivity : BaseActivity<EmptyPresenter>(){
    override fun createPresenter(): EmptyPresenter? = null


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_setting_list

    override fun initView() {
    }

    override fun initDatas() {
        setlist_phone.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, ChangePhoneActivity::class.java)
            }
        })
        setlist_pwd_change.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, ResetPwdActivity::class.java)
            }
        })
        setlist_bankpwd_change.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, ResetBankPwdActivity::class.java)
            }
        })
        setlist_cache_clear.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
            }
        })
        setlist_feedback.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, FeedbackActivity::class.java)
            }
        })
        setlist_about_us.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, AboutUsActivity::class.java)
            }
        })
        setlist_logout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, MySetListActivity::class.java)
            }
        })
    }

}
