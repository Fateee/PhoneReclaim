package com.yc.phonerecycle.activity

import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_splash.*
import android.widget.LinearLayout
import android.view.View
import android.widget.ImageView
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.adapter.GuidePageAdapter
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter


class CheckResulttActivity : BaseActivity<EmptyPresenter>(){
    override fun createPresenter(): EmptyPresenter? = null


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_check_result_detail

    override fun initView() {
    }

    override fun initDatas() {
    }

}
