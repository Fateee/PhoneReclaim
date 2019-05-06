package com.yc.phonerecycle.activity.fragment


import android.content.DialogInterface
import android.os.Handler
import android.os.Message
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.CheckedTextView
import android.widget.ImageSwitcher
import android.widget.TextView
import com.yc.phonecheck.views.BorderTouchView
import com.yc.phonecheck.views.OnTouchChangedListener
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.AutoCheckActivity
import com.yc.phonerecycle.activity.HandCheckActivity
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.model.bean.biz.DictMapRep
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.utils.ToastUtil


class LcdTestFragment : BaseFragment<EmptyPresenter>(), View.OnTouchListener {
    private var downX: Float=0f

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (!showLast) return true
        when (event?.getAction()) {
            MotionEvent.ACTION_DOWN ->{
                //手指按下的X坐标
                downX = event.getX();
            }
            MotionEvent.ACTION_UP -> {
                var lastX = event.getX();
                //抬起的时候的X坐标大于按下的时候就显示上一张图片
                if(lastX > downX){
                    if(mColorIndex > 0){
                        //设置动画，这里的动画比较简单，不明白的去网上看看相关内容
//                        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
//                        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                        mColorIndex --
                        screen_contain.setVisibility(View.GONE)
                        vc.setBackgroundResource(COLORS[mColorIndex])
                    }else{
                        ToastUtil.showShortToastCenter("已经是第一张");
                    }
                }

                if(lastX < downX){
                    if(mColorIndex < COLORS.size - 1){
//                        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_in));
//                        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.lift_out));
                        mColorIndex ++ ;
                        if(mColorIndex == COLORS.size - 1) {
                            screen_contain.setVisibility(View.VISIBLE)
                        }
                        vc.setBackgroundResource(COLORS[mColorIndex])
                    }else{
                        ToastUtil.showShortToastCenter("已经是最后一张");
                    }
                }
            }
        }

        return true;

    }

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
    var showLast : Boolean = false

    private lateinit var screen_contain: View

    private lateinit var shadow: CheckedTextView
    private lateinit var broken: CheckedTextView
    private lateinit var light: CheckedTextView
    private lateinit var next: TextView

    override fun initViews(view: View?) {
        if(view == null) return
        vc = view?.findViewById(R.id.lcd_color)
        vt = view?.findViewById(R.id.lcd_text)
        screen_contain = view?.findViewById(R.id.screen_container)
        shadow = view?.findViewById(R.id.shadow)
        broken = view?.findViewById(R.id.broken)
        light = view?.findViewById(R.id.light)
        next = view?.findViewById(R.id.next)
        vc.setOnTouchListener(this@LcdTestFragment)
    }

    override fun initData() {
        mHandler.sendEmptyMessageDelayed(0, 200)
    }

    fun onHandleMessage(index: Int) {
        val index = mColorIndex++ % (COLORS.size + 1)

        when (index) {
            0 -> {
                mHandler.sendEmptyMessageDelayed(0, 3000)
                //            setButtonVisibility(false);
                vc.visibility = View.VISIBLE
                vc.setBackgroundResource(COLORS[index])
                vt.visibility = View.GONE
            }
            COLORS.size -> {
                //            setButtonVisibility(true);
//                vc.visibility = View.GONE
                mColorIndex = COLORS.size - 1
                showLast = true
                screen_contain.visibility = View.VISIBLE
                shadow.setOnClickListener {
                    shadow.toggle()
                    if (shadow.isChecked()) {
                        (activity as AutoCheckActivity).checkResult.screen = 1
                    }
                }
                broken.setOnClickListener {
                    broken.toggle()
                    if (broken.isChecked()) {
                        (activity as AutoCheckActivity).checkResult.screen = 1
                    }
                }
                light.setOnClickListener {
                    light.toggle()
                    if (light.isChecked()) {
                        (activity as AutoCheckActivity).checkResult.screen = 1
                    }
                }
                next.setOnClickListener({ (activity as AutoCheckActivity).doCallTest() })
            }
            else -> {
                mHandler.sendEmptyMessageDelayed(0, 3000)
                vc.setBackgroundResource(COLORS[index])
            }
        }
    }

}
