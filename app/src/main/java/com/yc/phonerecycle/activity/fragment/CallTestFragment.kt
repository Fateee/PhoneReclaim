package com.yc.phonerecycle.activity.fragment


import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Message
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import com.yc.phonecheck.views.BorderTouchView
import com.yc.phonecheck.views.OnTouchChangedListener
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.AutoCheckActivity
import com.yc.phonerecycle.activity.HandCheckActivity
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.model.bean.biz.DictMapRep
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import kotlinx.android.synthetic.main.test_item.*


class CallTestFragment : BaseFragment<EmptyPresenter>(),View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.getId()) {
            R.id.refuse -> {
                (activity as AutoCheckActivity).checkResult.call = 1
                if ((activity as AutoCheckActivity).isAutoTabCheck) {
                    (activity as AutoCheckActivity).autoCheckTabDone()
                } else {
                    (activity as AutoCheckActivity).doHandCheck()
                }
            }
            R.id.grant -> callPhone("10086")
        }
    }

    private val REQUEST_CALL = 2
    private var refuseTV: View? = null
    private var grantTV: View? = null

    override fun createPresenter(): EmptyPresenter? = null

    override fun getContentView(): Int = R.layout.call

    private lateinit var vc: View
    private lateinit var vt: View

    override fun initViews(v: View?) {
        if(v == null) return
        refuseTV = v.findViewById(R.id.refuse)
        grantTV = v.findViewById(R.id.grant)
        refuseTV?.setOnClickListener(this)
        grantTV?.setOnClickListener(this)
    }

    override fun initData() {
    }

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    fun callPhone(phoneNum: String) {
        val intent = Intent(Intent.ACTION_CALL)
        val data = Uri.parse("tel:$phoneNum")
        intent.data = data
        startActivityForResult(intent, REQUEST_CALL)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CALL) {
            if ((activity as AutoCheckActivity).isAutoTabCheck) {
                (activity as AutoCheckActivity).autoCheckTabDone()
            } else {
                (activity as AutoCheckActivity).checkResult.call = 0
                (activity as AutoCheckActivity).doHandCheck()
            }
        }
    }
}
