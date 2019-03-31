package com.yc.phonerecycle.activity

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.adapter.BandAdapter
import com.yc.phonerecycle.activity.adapter.OnItemClick
import com.yc.phonerecycle.model.bean.biz.BrandGoodsRep
import com.yc.phonerecycle.model.bean.biz.BrandRep
import com.yc.phonerecycle.model.bean.biz.ConfigPriceRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_choose_phone.*


class ChoosePhoneActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonTypeIV,CommonBaseIV.CommonIV{
    override fun getDataOK(rep: Any?) {
        if (rep is ConfigPriceRep) {
            if (rep.data == null) {
                ToastUtil.showShortToastCenter("没有匹配到该机型的配置")
            } else {
                when(checktype) {
                    "0" -> {
                        rep.data.addHardwareToConfigList()
                        goodMap["configRep"] = rep.data
                        ActivityToActivity.toActivity(
                            this@ChoosePhoneActivity, HandCheckActivity::class.java,goodMap)
                    }
                    "1" -> {
                        goodMap["configRep"] = rep.data
                        ActivityToActivity.toActivity(
                            this@ChoosePhoneActivity, AutoCheckActivity::class.java,goodMap)
                    }
                }
//                finish()
            }
        }
    }

    override fun createPresenter() = CommonPresenter()


    private var checktype: String? = "-1"

    override fun initBundle() {
        checktype = intent.getStringExtra("checktype")
    }

    override fun getContentView(): Int = R.layout.activity_choose_phone

    override fun initView() {
    }

    private lateinit var mBandAdapter: BandAdapter

    private lateinit var mPhoneAdapter: BandAdapter

    private lateinit var goodMap: HashMap<String, Any?>

    override fun initDatas() {
        search_phone_view.checktype = checktype
        search_et.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                search_phone_view.showSearchView()
            }
        })
        presenter.getBrandSelect("",0)
        val mLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        brand_list.layoutManager = mLinearLayoutManager
        mBandAdapter = BandAdapter(this,0)
        brand_list.adapter = mBandAdapter
        mBandAdapter.setOnItemClickListener(object : OnItemClick{
            override fun onItemClick(pos: Int, tag: Any) {
                if (tag is BrandRep.DataBean) {
                    mPhoneAdapter.mBrandId = tag.id
                    presenter.getGoodsByBrandId(1,tag.id)
                }
            }
        })


        val mGridLayoutManager = GridLayoutManager(this, 2)
        brand_detail_list.layoutManager = mGridLayoutManager
        mPhoneAdapter = BandAdapter(this,1)
        mPhoneAdapter.mCheckType = checktype
        brand_detail_list.adapter = mPhoneAdapter
        mPhoneAdapter.setOnItemClickListener(object : OnItemClick{
            override fun onItemClick(pos: Int, tag: Any) {
                if (tag is HashMap<*, *>) {
                    goodMap = tag as HashMap<String,Any?>
//                    map["goodbean"] = tmp
//                    map["brandid"] = mBrandId
                    var good = goodMap["goodbean"] as BrandGoodsRep.DataBean
                    presenter.getConfigPriceSystemById(good.id)
                }
            }
        })
    }

    override fun getDataOK(rep: Any?, type: Int) {
        if (type == 0 && rep is BrandRep) {
            mBandAdapter.refreshUI(rep.data,true)
            if (rep.data.size > 0)
                presenter.getGoodsByBrandId(1,rep.data[0].id)
        } else if (type == 1 && rep is BrandGoodsRep) {
            mPhoneAdapter.refreshUI(rep.data,true)
        }
    }

    override fun onBackPressed() {
        if (search_phone_view.isShown()) {
            search_phone_view.hideSearchView()
        } else {
            super.onBackPressed()
        }
    }
}
