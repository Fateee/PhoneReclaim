package com.yc.phonerecycle.activity

import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.AboutUsRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV


class MyCheckListActivity : BaseActivity<CommonPresenter>(){

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_my_check_list

    override fun initView() {
    }

    override fun initDatas() {
    }

}
