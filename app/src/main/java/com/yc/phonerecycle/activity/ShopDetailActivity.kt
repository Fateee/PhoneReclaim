package com.yc.phonerecycle.activity

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.NearByShopRep
import com.yc.phonerecycle.model.bean.biz.ShopDetailRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import kotlinx.android.synthetic.main.activity_shop_info_near.*
import kotlinx.android.synthetic.main.activity_shop_info_shopper.*


class ShopDetailActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.CommonIV{
    override fun getDataOK(ret: Any?) {
        if (ret is ShopDetailRep) {
            if (ret?.code == 0) {
                var rep = ret?.data

                when (type) {
                    "1"-> {
                        Glide.with(this@ShopDetailActivity).load(rep.mainImage).into(shop_avatar)
                        shop_info_name.text = getString(R.string.shop_name,rep.name)
                        shop_info_addr.text = getString(R.string.shop_addr,rep.address)

                        shop_open_time.title = getString(R.string.shop_open_time,rep.businessHours)
                        if (!TextUtils.isEmpty(rep.fixedLine))
                            shop_tele.title =  getString(R.string.shop_tele,rep.fixedLine)
                        else
                            shop_tele.title =  getString(R.string.shop_tele,rep.phone)
                        deal_num.title = getString(R.string.deal_num,rep.fixtureNumber.toString())
                        shop_addr.title = getString(R.string.deal_num,rep.address)
                    }
                    "4" -> {
                        Glide.with(this@ShopDetailActivity).load(rep.mainImage).into(shopper_avatar)
                        shop_info_name_shopper.text = getString(R.string.shop_name,rep.name)
                        shop_info_addr_shopper.text = getString(R.string.shop_addr,rep.address)

                        shopper_count.title = getString(R.string.shopper_count,rep.adminUser)
                        admin_count.title = getString(R.string.admin_count,rep.adminUser)
                    }
                    else -> {
                        shop_info_name.text = getString(R.string.shop_name,rep.name)
                        shop_info_addr.text = getString(R.string.shop_addr,rep.address)

                        shop_open_time.title = getString(R.string.shop_open_time,rep.businessHours)
                        if (!TextUtils.isEmpty(rep.fixedLine))
                            shop_tele.title =  getString(R.string.shop_tele,rep.fixedLine)
                        else
                            shop_tele.title =  getString(R.string.shop_tele,rep.phone)
                        deal_num.title = getString(R.string.deal_num,rep.fixtureNumber.toString())
                        shop_addr.title = getString(R.string.deal_num,rep.address)
                    }
                }
            }
        }
    }

    override fun createPresenter() = CommonPresenter()


    private var mId: String? = null
    private var type: String? = "1"
    override fun initBundle() {
        mId = intent.getStringExtra("id")
        type = intent.getStringExtra("type")
    }

    override fun getContentView(): Int {
        return when (type) {
            "1" -> {
                R.layout.activity_shop_info_near
            }
            "4" -> {
                R.layout.activity_shop_info_shopper
            }
            else -> {
                R.layout.activity_shop_info_near
            }
        }
    }

    override fun initView() {
    }

    override fun initDatas() {
    }

    override fun onResume() {
        super.onResume()
        when (type) {
            "1" -> {
                presenter.getStoreDetail(mId)
            }
            "4" -> {
                presenter.getMyStore()
            }
            else -> {
            }
        }

    }
}
