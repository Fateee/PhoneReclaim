package com.yc.phonerecycle.activity

import android.view.View
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import kotlinx.android.synthetic.main.titleview.*


class EditUserInfoActivity : BaseActivity<EmptyPresenter>(){
    override fun createPresenter(): EmptyPresenter? = null


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_edit_nickname

    override fun initView() {
    }

    override fun initDatas() {
        txt_right_title.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
            }
        })
    }

}
