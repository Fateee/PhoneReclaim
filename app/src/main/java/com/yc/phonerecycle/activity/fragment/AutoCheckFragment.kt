package com.yc.phonerecycle.activity.fragment


import android.support.v4.app.Fragment
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.AutoCheckActivity
import com.yc.phonerecycle.activity.ChoosePhoneActivity
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.utils.ActivityToActivity
import kotlinx.android.synthetic.main.fragment_main_check.*


/**
 * A simple [Fragment] subclass.
 *
 */
class AutoCheckFragment : BaseFragment<EmptyPresenter>() {
    override fun createPresenter(): EmptyPresenter? = null

    override fun getContentView(): Int = R.layout.fragment_main_check

    override fun initViews(view: View?) {
    }

    override fun initData() {
        check_click.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var map = HashMap<String,Boolean?>()
                map["isAutoTabCheck"] = true
//                ActivityToActivity.toActivity(
//                    activity, ChoosePhoneActivity::class.java,map)
                ActivityToActivity.toActivity(
                    activity, AutoCheckActivity::class.java,map)
            }
        })
    }
}
