package com.yc.phonerecycle.activity

import android.view.View
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.UserInfoRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_edit_userinfo.*


class UserInfoActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.UserInfoIV{

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_edit_userinfo

    override fun initView() {
    }

    override fun initDatas() {
        userinfo_nick.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var map = HashMap<String,String>()
                map["title"] = userinfo_nick.getTitle()
                map["type"] = "0"
                ActivityToActivity.toActivity(
                    this@UserInfoActivity, EditUserInfoActivity::class.java,map)
            }
        })
        userinfo_sign.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var map = HashMap<String,String>()
                map["title"] = userinfo_sign.getTitle()
                map["type"] = "1"
                ActivityToActivity.toActivity(
                    this@UserInfoActivity, EditUserInfoActivity::class.java,map)
            }
        })
    }

    override fun onResume() {
       super.onResume()
        presenter.getInfo()
    }

    override fun userInfoSuccess(body: UserInfoRep?) {
        UserInfoUtils.saveUserInfo(body)
        userinfo_nick.setSubTitle(UserInfoUtils.getUserInfo().data?.name)
        userinfo_sign.setSubTitle(UserInfoUtils.getUserInfo().data?.signature)
    }
}
