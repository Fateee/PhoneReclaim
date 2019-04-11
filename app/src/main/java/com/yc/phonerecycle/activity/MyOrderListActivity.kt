package com.yc.phonerecycle.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.adapter.MenuListPageAdapter
import com.yc.phonerecycle.activity.fragment.RecordListFragment
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.model.bean.biz.AboutUsRep
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_record_main.*
import kotlinx.android.synthetic.main.titleview.*


class MyOrderListActivity : BaseActivity<EmptyPresenter>(){

    override fun createPresenter() = EmptyPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_record_main

    override fun initView() {
    }

    private lateinit var listFragments: ArrayList<RecordListFragment>
    private var type: String? = "0"
    var list = ArrayList<String>()
    override fun initDatas() {
        txt_right_title.text = ""
        txt_main_title.text = "我的订单"
        type = UserInfoUtils.getUserType()
        listFragments = ArrayList<RecordListFragment>()
        when (type) {
            "1" -> {//-1 查询全部 0、已完成 1、待寄出 2、待收货 3、已退回 4、验机 5、待打款 6待验机
                createFragment("全部","-1")
                createFragment("待寄出","1")
                createFragment("待收货","2")
                createFragment("待验机","6")
                createFragment("已完成","0")
                createFragment("已退回","3")
                init()
            }
            "4" -> {
                txt_main_title.text = "回收记录"
                createFragment("全部订单","-1")
                createFragment("待收货","2")
                createFragment("待验机","6")
                createFragment("待打款","5")
                createFragment("已完成","0")
                init()
            }
        }

    }

    private fun createFragment(title: String,status: String) {
        list.add(title)
        var bundle = Bundle()
        bundle.putString("status",status)
        bundle.putString("type",type)
        var myFragment1 = RecordListFragment()
        myFragment1?.arguments = bundle
        listFragments.add(myFragment1)
    }

    private fun init() {
        val mAdapter = MenuListPageAdapter(supportFragmentManager)
        mAdapter.setTabText(list)
        mAdapter.setFragments(listFragments)
//        val mAdapter = MyFragmentPagerAdapter(supportFragmentManager,menuItems)
        view_pager.setAdapter(mAdapter)
        view_pager.setOffscreenPageLimit(1)
        tab_layout.setupWithViewPager(view_pager)//关联

        for (i in list.indices) {
            //mTitleIcons[i]和mTitleNames[i]是放图片和文字的资源的数组
//            tab_layout.getTabAt(i)!!.setIcon(mTitleIcons[i])//.setText(mTitleNames[i])
            //这个是设置选中和没选中的文字的颜色
            tab_layout.setTabTextColors(ContextCompat.getColor(BaseApplication.getAppContext(),R.color.c3c3c3c), ContextCompat.getColor(BaseApplication.getAppContext(),R.color.c0168b7))
        }
    }

}
