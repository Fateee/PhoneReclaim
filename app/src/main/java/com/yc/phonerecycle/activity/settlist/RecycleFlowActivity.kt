package com.yc.phonerecycle.activity.settlist

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.AboutUsRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.PermissionUtils
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_recycle_flow.*


class RecycleFlowActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.AboutUsIV{

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_recycle_flow

    override fun initView() {
    }

    override fun initDatas() {
        contact_custom.setOnClickListener { callPhone(getString(R.string.custom_tele)) }
        //todo huyi custom phone
        custom_phone.text = getString(R.string.custom_phone,getString(R.string.custom_tele))
    }

    override fun getAboutUsOK(data: AboutUsRep?) {
    }
    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    fun callPhone(phoneNum: String) {
        PermissionUtils.checkCallPermission(this@RecycleFlowActivity,object : PermissionUtils.Callback() {
            override fun onGranted() {
                val intent = Intent(Intent.ACTION_CALL)
                val data = Uri.parse("tel:$phoneNum")
                intent.data = data
                startActivity(intent)
            }

            override fun onRationale() {
                ToastUtil.showShortToast("请开启打电话权限才能正常使用")
            }

            override fun onDenied(context: Context) {
                showPermissionDialog("开启打电话权限","你还没有开启打电话权限，开启之后才可打电话")
            }
        })
    }
}
