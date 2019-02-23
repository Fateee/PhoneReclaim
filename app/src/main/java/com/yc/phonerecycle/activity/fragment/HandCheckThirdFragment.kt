package com.yc.phonerecycle.activity.fragment


import android.support.v4.app.Fragment
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.CheckResulttActivity
import com.yc.phonerecycle.activity.HandCheckActivity
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.utils.ActivityToActivity
import kotlinx.android.synthetic.main.activity_hand_check_stepthree.*
import kotlinx.android.synthetic.main.titleview.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HandCheckThirdFragment : BaseFragment<EmptyPresenter>() {
    override fun createPresenter(): EmptyPresenter? = null

    override fun getContentView(): Int = R.layout.activity_hand_check_stepthree

    override fun initViews(view: View?) {
    }

    override fun initData() {
        appraisal.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    activity, CheckResulttActivity::class.java)
            }
        })
        txt_left_title.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                (activity as HandCheckActivity).changeFragment((activity as HandCheckActivity).mSecondFragment)
            }
        })
    }
}
