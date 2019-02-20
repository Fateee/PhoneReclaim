package com.yc.phonerecycle.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.fragment.AutoCheckFragment
import com.yc.phonerecycle.activity.fragment.HomeFragment
import com.yc.phonerecycle.activity.fragment.UserCenterFragment
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<EmptyPresenter>() {
    //继承Activity 不会显示APP头上的标题
    private var mHomeFragment = HomeFragment()
    private var mAutoCheckFragment = AutoCheckFragment()
    private var mUserCenterFragment = UserCenterFragment()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_home -> {
                transaction.replace(R.id.frame,mHomeFragment)
            }
            R.id.navigation_dashboard -> {
                transaction.replace(R.id.frame,mAutoCheckFragment)
            }
            R.id.navigation_notifications -> {
                transaction.replace(R.id.frame,mUserCenterFragment)
            }
        }
        transaction.commit()
        true
    }

    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_main

    override fun initView() {

    }

    override fun createPresenter(): EmptyPresenter? = null

    override fun initDatas() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,mHomeFragment)
        transaction.commit()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
