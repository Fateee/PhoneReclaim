package com.yc.phonerecycle.activity.fragment


import android.support.v4.app.Fragment
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.HandCheckActivity
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import kotlinx.android.synthetic.main.activity_hand_check_steptwo.*
import kotlinx.android.synthetic.main.titleview.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HandCheckSecondFragment : BaseFragment<EmptyPresenter>() {
    override fun createPresenter(): EmptyPresenter? = null

    override fun getContentView(): Int = R.layout.activity_hand_check_steptwo

    override fun initViews(view: View?) {
    }

    override fun initData() {
        last.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                (activity as HandCheckActivity).changeFragment((activity as HandCheckActivity).mFirstFragment)
            }
        })
        next.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                (activity as HandCheckActivity).changeFragment((activity as HandCheckActivity).mThirdFragment)
            }
        })
        txt_left_title.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                (activity as HandCheckActivity).changeFragment((activity as HandCheckActivity).mFirstFragment)
            }
        })
    }
}
