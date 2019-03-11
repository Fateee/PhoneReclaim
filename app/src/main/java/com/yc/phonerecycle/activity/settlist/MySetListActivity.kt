package com.yc.phonerecycle.activity.settlist

import android.app.Dialog
import com.yc.phonerecycle.mvp.view.BaseActivity
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.LoginActivity
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.model.bean.biz.BankCardListRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.*
import kotlinx.android.synthetic.main.activity_setting_list.*


class MySetListActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.LoginViewIV{
    override fun loginWX(
        accessToken: String?,
        uId: String?,
        expiresIn: Long,
        wholeData: String?,
        body: MutableMap<String, Any>?
    ) {
    }

    override fun loginQQ(accessToken: String?, uId: String?, expiresIn: Long, wholeData: String?) {
    }

    override fun createPresenter(): CommonPresenter? = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_setting_list

    override fun initView() {
    }

    override fun initDatas() {
        setlist_phone.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, ChangePhoneActivity::class.java)
            }
        })
        setlist_pwd_change.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, ResetPwdActivity::class.java)
            }
        })
        setlist_bankpwd_change.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, ResetBankPwdActivity::class.java)
            }
        })
        initCacheSize()
        setlist_cache_clear.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                showDeleteDialog("清除缓存","确定要清除缓存吗？")
            }
        })
        recycle_flow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, RecycleFlowActivity::class.java)
            }
        })
        setlist_feedback.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, FeedbackActivity::class.java)
            }
        })
        setlist_about_us.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@MySetListActivity, AboutUsActivity::class.java)
            }
        })
        setlist_logout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                presenter.logout()
                UserInfoUtils.cleanUser()
                ActivityToActivity.toActivity(
                    this@MySetListActivity, LoginActivity::class.java)
                finish()
            }
        })
    }

    private fun initCacheSize() {
        var size = DataCleanManager.getTotalCacheSize(BaseApplication.getAppContext())
        setlist_cache_clear.setSubTitle(size)
    }

    override fun loginResponse(data: Any?) {
    }

    override fun onResume() {
        super.onResume()
        var phone = PhoneUtil.hidePhoneNum(UserInfoUtils.getUser().data?.userInfoVO?.phone)
        setlist_phone.setSubTitle(phone)
    }

    private var deleteDialog: Dialog? = null

    fun showDeleteDialog(
        title: String,
        msg: String) {
        if (deleteDialog == null) {
            deleteDialog = DialogHelper.showDialog(
                "1",
                this@MySetListActivity, null,
                "",
                "",
                title,
                msg,
                getString(R.string.cancel),
                getString(R.string.sure),
                "",
                "0168b7",
                { },
                { DataCleanManager.clearAllCache(BaseApplication.getAppContext())
                    initCacheSize()})
            deleteDialog?.setCanceledOnTouchOutside(false)
            deleteDialog?.setOnCancelListener { }
        } else if (deleteDialog?.isShowing == false) {
            deleteDialog?.show()
        }
    }
}
