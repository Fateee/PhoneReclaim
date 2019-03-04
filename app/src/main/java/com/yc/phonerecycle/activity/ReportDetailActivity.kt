package com.yc.phonerecycle.activity

import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.AboutUsRep
import com.yc.phonerecycle.model.bean.biz.MyOrderDetailRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV


class ReportDetailActivity : BaseActivity<CommonPresenter>(){

    private lateinit var goodsInstanceVO: MyOrderDetailRep.DataBean.GoodsInstanceVOBean

    override fun createPresenter() = CommonPresenter()

    override fun initBundle() {
        goodsInstanceVO = intent?.getSerializableExtra("obj") as MyOrderDetailRep.DataBean.GoodsInstanceVOBean
    }

    override fun getContentView(): Int = R.layout.activity_check_result_detail

    override fun initView() {
    }

    override fun initDatas() {
    }

}
