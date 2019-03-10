package com.yc.phonerecycle.activity.fragment


import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import com.yc.phonecheck.views.OnTouchChangedListener
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.AutoCheckActivity
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import kotlinx.android.synthetic.main.touch.*


class TouchTestFragment : BaseFragment<EmptyPresenter>(), OnTouchChangedListener {
    private val TAG = "TouchTestFragment"
    private val MSG_BORDER_TOUCH = 1
    private val MSG_CENTER_TOUCH = 2
    private val MSG_END = 3

    override fun onTouchFinish(v: View?) {
        if (v?.id == R.id.touch_border) {
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
        touch_border?.setOnTouchChangedListener(this)
    }

    override fun initData() {

    }

    fun onHandleMessage(index: Int) {
        when (index) {
            MSG_BORDER_TOUCH -> {
                Log.i(TAG, "MSG_BORDER_TOUCH")
                touch_border?.setVisibility(View.VISIBLE)
            }
            MSG_CENTER_TOUCH -> {
                Log.i(TAG, "MSG_CENTER_TOUCH")
                touch_border?.setVisibility(View.GONE)
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
