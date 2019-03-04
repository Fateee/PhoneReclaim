package com.yc.phonerecycle.activity

import android.graphics.Color
import android.os.Bundle
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.adapter.MenuListPageAdapter
import com.yc.phonerecycle.activity.fragment.RecordListFragment
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
    override fun initDatas() {
        txt_right_title.text = ""
        txt_main_title.text = "我的订单"
        type = UserInfoUtils.getUser().data?.userInfoVO?.type
        var list = ArrayList<String>()
        listFragments = ArrayList<RecordListFragment>()
        when (type) {
            "1" -> {//-1 查询全部 0、已完成 1、待寄出 2、待收货 3、已退回 4、验机 5、待打款
                list.add("全部订单")
                list.add("待寄出")
                list.add("待收货")
                list.add("已完成")
                list.add("已退回")
                createFragment("-1")
                createFragment("1")
                createFragment("2")
                createFragment("0")
                createFragment("3")
                init(list)
            }
            "4" -> {
                list.add("全部订单")
                list.add("待收货")
                list.add("待打款")
                list.add("已完成")
                createFragment("-1")
                createFragment("2")
                createFragment("5")
                createFragment("0")
                init(list)
            }
        }

    }

    private fun createFragment(status: String) {
        var bundle = Bundle()
        bundle.putString("status",status)
        bundle.putString("type",type)
        var myFragment1 = RecordListFragment()
        myFragment1?.arguments = bundle
        listFragments.add(myFragment1)
    }

    private fun init(list: ArrayList<String>) {
        val mAdapter = MenuListPageAdapter(supportFragmentManager)
        mAdapter.setTabText(list)
        mAdapter.setFragments(listFragments)
//        val mAdapter = MyFragmentPagerAdapter(supportFragmentManager,menuItems)
        view_pager.setAdapter(mAdapter)
        view_pager.setOffscreenPageLimit(2)
        tab_layout.setupWithViewPager(view_pager)//关联

        for (i in list.indices) {
            //mTitleIcons[i]和mTitleNames[i]是放图片和文字的资源的数组
//            tab_layout.getTabAt(i)!!.setIcon(mTitleIcons[i])//.setText(mTitleNames[i])
            //这个是设置选中和没选中的文字的颜色
            tab_layout.setTabTextColors(getColor(R.color.c3c3c3c), getColor(R.color.c0168b7))
        }
    }

}
