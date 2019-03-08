package com.yc.phonerecycle.activity

import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.AboutUsRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV


class AllParamsActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.AboutUsIV{

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_phone_all_params

    override fun initView() {
    }

    override fun initDatas() {
        presenter.getAboutUsVO()
    }

    override fun getAboutUsOK(data: AboutUsRep?) {
    }
}
