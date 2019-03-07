package com.yc.phonerecycle.activity

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.adapter.BandAdapter
import com.yc.phonerecycle.activity.adapter.OnItemClick
import com.yc.phonerecycle.model.bean.biz.BrandGoodsRep
import com.yc.phonerecycle.model.bean.biz.BrandRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import kotlinx.android.synthetic.main.activity_choose_phone.*


class ChoosePhoneActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonTypeIV{

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_choose_phone

    override fun initView() {
    }

    private lateinit var mBandAdapter: BandAdapter

    private lateinit var mPhoneAdapter: BandAdapter

    override fun initDatas() {
        presenter.getBrandSelect(0)
        val mLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        brand_list.layoutManager = mLinearLayoutManager
        mBandAdapter = BandAdapter(this,0)
        brand_list.adapter = mBandAdapter
        mBandAdapter.setOnItemClickListener(object : OnItemClick{
            override fun onItemClick(pos: Int, tag: Any) {
                if (tag is BrandRep.DataBean) {
                    presenter.getGoodsByBrandId(1,tag.id)
                }
            }
        })


        val mGridLayoutManager = GridLayoutManager(this, 2)
        brand_detail_list.layoutManager = mGridLayoutManager
        mPhoneAdapter = BandAdapter(this,1)
        brand_detail_list.adapter = mPhoneAdapter

    }

    override fun getDataOK(rep: Any?, type: Int) {
        if (type == 0 && rep is BrandRep) {
            mBandAdapter.refreshUI(rep.data,true)
            presenter.getGoodsByBrandId(1,rep.data[0].id)
        } else if (type == 1 && rep is BrandGoodsRep) {
            mPhoneAdapter.refreshUI(rep.data,true)
        }
    }
}