package com.yc.phonerecycle.activity.fragment


import android.support.v4.app.Fragment
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.*
import com.yc.phonerecycle.activity.settlist.MySetListActivity
import com.yc.phonerecycle.model.bean.biz.NearByShopRep
import com.yc.phonerecycle.model.bean.biz.UserInfoRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
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
        uc_shop_nearby.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var type = UserInfoUtils.getUser().data?.userInfoVO?.type
                when (type) {
                    "1" -> {
                        ActivityToActivity.toActivity(
                            activity, MyNearShopActivity::class.java)
                    }
                    "4" -> {
                        var map = HashMap<String,String>()
                        map["type"] = "4"
                        ActivityToActivity.toActivity(
                            activity, ShopDetailActivity::class.java,map)
                    }
                }

            }
        })
        item1_layout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    activity, MyOrderListActivity::class.java)
            }
        })
        item3_layout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var type = UserInfoUtils.getUser().data?.userInfoVO?.type
                when (type) {
                    "1" -> {
                        ActivityToActivity.toActivity(
                            activity, MyCheckListActivity::class.java)
                    }
                    "4" -> {
                        var map = HashMap<String,String>()
                        map["type"] = "4"
                        ActivityToActivity.toActivity(
                            activity, ShopperCheckListActivity::class.java,map)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        presenter.getInfo()
    }

    override fun userInfoSuccess(body: UserInfoRep?) {
        UserInfoUtils.saveUserInfo(body)
        item_name.text = body?.data?.name
        item_sign.text = body?.data?.signature
        if (UserInfoUtils.getUserType() == "1") {
            item1_num.text = body?.data?.orderCount.toString()
            item1_name.text = "订单"

            item2_name.text = "钱包"

            item3_num.text = body?.data?.instance.toString()
            item3_name.text = "评估记录"
        } else if (UserInfoUtils.getUserType() == "4") {
            item1_num.text = body?.data?.instance.toString()
            item1_name.text = "回收记录"

            item2_name.text = "余额"

            item3_num.text = body?.data?.testCount.toString()
            item3_name.text = "检测记录"
            uc_my_user.visibility = View.VISIBLE

            uc_my_user.title = getString(R.string.my_user_count,body?.data?.myUser)
        }

        item2_num.text = body?.data?.money.toString()
    }

}
