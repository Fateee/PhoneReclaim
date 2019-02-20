package com.yc.phonerecycle.activity.fragment


import android.support.v4.app.Fragment
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment


/**
 * A simple [Fragment] subclass.
 *
 */
class UserCenterFragment : BaseFragment<EmptyPresenter>() {
    override fun createPresenter(): EmptyPresenter? = null

    override fun getContentView(): Int = R.layout.fragment_main_usercenter

    override fun initViews(view: View?) {
    }

    override fun initData() {
    }
}
