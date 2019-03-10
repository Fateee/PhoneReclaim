package com.yc.phonerecycle.activity.fragment


import android.content.DialogInterface
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


class TouchTestFragment : BaseFragment<EmptyPresenter>(), OnTouchChangedListener {
    private val TAG = "TouchTest"
    private val MSG_BORDER_TOUCH = 1
    private val MSG_CENTER_TOUCH = 2
    private val MSG_END = 3

    private var mBorderView: BorderTouchView? = null

    override fun onTouchFinish(v: View?) {
        if (v === mBorderView) {
            val msg = Message.obtain()
            msg.arg1 = MSG_END
            mHandler.sendMessageDelayed(msg, 1000)
        }
    }

    var mHandler: Handler = object : Handler() {

        override fun handleMessage(msg: Message) {
            onHandleMessage(msg.arg1)
        }
    }
    override fun createPresenter(): EmptyPresenter? = null

    override fun getContentView(): Int = R.layout.touch

    override fun initViews(view: View?) {
        mBorderView = view?.findViewById(R.id.touch_border) as BorderTouchView
    }

    override fun initData() {

    }

    fun onHandleMessage(index: Int) {
        when (index) {
            MSG_BORDER_TOUCH -> {
                Log.i(TAG, "MSG_BORDER_TOUCH")
                mBorderView?.setOnTouchChangedListener(this)
                mBorderView?.setVisibility(View.VISIBLE)
            }
            MSG_CENTER_TOUCH -> {
                Log.i(TAG, "MSG_CENTER_TOUCH")
                mBorderView?.setVisibility(View.GONE)
            }
            MSG_END -> {
                Log.i(TAG, "MSG_END")
                if (activity is AutoCheckActivity) {
                    (activity as AutoCheckActivity).checkResult.multiTouch = 0
                    (activity as AutoCheckActivity).doLCDTest()
                }
            }
        }
    }

}
