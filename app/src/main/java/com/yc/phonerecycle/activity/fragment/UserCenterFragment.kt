package com.yc.phonerecycle.activity.fragment


import android.support.v4.app.Fragment
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.UserInfoActivity
import com.yc.phonerecycle.activity.MyBankCardsActivity
import com.yc.phonerecycle.activity.MySetListActivity
import com.yc.phonerecycle.activity.MyWalletActivity
import com.yc.phonerecycle.model.bean.biz.UserInfoRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.UserInfoUtils
import kotlinx.android.synthetic.main.fragment_main_usercenter.*


/**
 * A simple [Fragment] subclass.
 *
 */
class UserCenterFragment : BaseFragment<CommonPresenter>(),CommonBaseIV.UserInfoIV {

    override fun createPresenter() = CommonPresenter()

    override fun getContentView(): Int = R.layout.fragment_main_usercenter

    override fun initViews(view: View?) {
    }

    override fun initData() {
        presenter.getInfo()
        item_name.text = UserInfoUtils.getUser().data?.userInfoVO?.userName
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

        avatar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    activity, UserInfoActivity::class.java)
            }
        })
        item_name.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    activity, UserInfoActivity::class.java)
            }
        })
        item_sign.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    activity, UserInfoActivity::class.java)
            }
        })

        uc_bank_card.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    activity, MyBankCardsActivity::class.java)
            }
        })
    }

    override fun userInfoSuccess(body: UserInfoRep?) {
        item_name.text = body?.data?.name
    }

}
