package com.yc.phonerecycle.activity

import android.Manifest
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
import android.support.v4.app.ActivityCompat
import android.Manifest.permission
import android.Manifest.permission.WRITE_APN_SETTINGS
import android.Manifest.permission.SYSTEM_ALERT_WINDOW
import android.Manifest.permission.SET_DEBUG_APP
import android.Manifest.permission.READ_LOGS
import android.os.Build.VERSION
import android.os.Build


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
        if (Build.VERSION.SDK_INT >= 23) {
            val mPermissionList = arrayOf<String>(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS,
                Manifest.permission.USE_FINGERPRINT,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            )
            ActivityCompat.requestPermissions(this, mPermissionList, 123)
        }
    }

    override fun createPresenter(): EmptyPresenter? = null

    override fun initDatas() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame,mHomeFragment)
        transaction.commit()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
