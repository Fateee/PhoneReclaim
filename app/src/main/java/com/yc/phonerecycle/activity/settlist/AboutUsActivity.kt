package com.yc.phonerecycle.activity.settlist

import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.AboutUsRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV


class AboutUsActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.AboutUsIV{

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_about_us

    override fun initView() {
    }

    override fun initDatas() {
        presenter.getAboutUsVO()
    }

    override fun getAboutUsOK(data: AboutUsRep?) {
    }
}
