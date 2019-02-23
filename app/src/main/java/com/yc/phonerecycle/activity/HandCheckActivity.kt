package com.yc.phonerecycle.activity

import android.support.v4.app.Fragment
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.fragment.*
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity

class HandCheckActivity : BaseActivity<EmptyPresenter>() {

    var mFirstFragment = HandCheckFirstFragment()
    var mSecondFragment = HandCheckSecondFragment()
    var mThirdFragment = HandCheckThirdFragment()

    override fun createPresenter(): EmptyPresenter? = null

    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_hand_check

    override fun initView() {
    }

    override fun initDatas() {
        changeFragment(mFirstFragment)
    }

    fun changeFragment(mfragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.hand_check_container,mfragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (mFirstFragment.isVisible) {
            finish()
        } else if (mSecondFragment.isVisible) {
            changeFragment(mFirstFragment)
        } else if (mThirdFragment.isVisible) {
            changeFragment(mSecondFragment)
        } else {
            super.onBackPressed()
        }
    }


}
