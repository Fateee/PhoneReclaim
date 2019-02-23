package com.yc.phonerecycle.activity.fragment


import android.support.v4.app.Fragment
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.MySetListActivity
import com.yc.phonerecycle.activity.MyWalletActivity
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.utils.ActivityToActivity
import kotlinx.android.synthetic.main.fragment_main_usercenter.*


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
        iv_to_setlist.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    activity, MySetListActivity::class.java)
            }
        })
        my_wallet_layout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    activity, MyWalletActivity::class.java)
            }
        })
    }
}
