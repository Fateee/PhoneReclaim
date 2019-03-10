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
import kotlinx.android.synthetic.main.test_item.*


class LcdTestFragment : BaseFragment<EmptyPresenter>() {
    private val COLORS = intArrayOf(
        R.drawable.bg_screen_one,
        R.drawable.bg_screen_two,
        R.drawable.bg_screen_three,
        R.drawable.bg_screen_four,
        R.drawable.bg_screen_five
    )

    private var mColorIndex: Int = 0


    var mHandler: Handler = object : Handler() {

        override fun handleMessage(msg: Message) {
            onHandleMessage(msg.arg1)
        }
    }
    override fun createPresenter(): EmptyPresenter? = null

    override fun getContentView(): Int = R.layout.test_item

    private lateinit var vc: View
    private lateinit var vt: View

    override fun initViews(view: View?) {
        if(view == null) return
        vc = view?.findViewById(R.id.lcd_color)
        vt = view?.findViewById(R.id.lcd_text)
    }

    override fun initData() {
        mHandler.sendEmptyMessageDelayed(0, 200)
    }

    fun onHandleMessage(index: Int) {
        val index = mColorIndex++ % (COLORS.size + 1)

        when (index) {
            0 -> {
                mHandler.sendEmptyMessageDelayed(0, 1500)
                //            setButtonVisibility(false);
                vc.visibility = View.VISIBLE
                vc.setBackgroundResource(COLORS[index])
                vt.visibility = View.GONE
            }
            COLORS.size -> {
                //            setButtonVisibility(true);
                vc.visibility = View.GONE
                screen_container.setVisibility(View.VISIBLE)
                shadow.setOnClickListener {
                    shadow.toggle()
                    if (shadow.isChecked()) {
                        (activity as AutoCheckActivity).checkResult.screenProblem = "1105"
                    }
                }
                broken.setOnClickListener {
                    broken.toggle()
                    if (broken.isChecked()) {
                        (activity as AutoCheckActivity).checkResult.screenProblem = "1106"
                    }
                }
                light.setOnClickListener {
                    light.toggle()
                    if (light.isChecked()) {
                        (activity as AutoCheckActivity).checkResult.screenProblem = "1104"
                    }
                }
                next.setOnClickListener({ (activity as AutoCheckActivity).doCallTest() })
            }
            else -> {
                mHandler.sendEmptyMessageDelayed(0, 1500)
                vc.setBackgroundResource(COLORS[index])
            }
        }
    }

}
