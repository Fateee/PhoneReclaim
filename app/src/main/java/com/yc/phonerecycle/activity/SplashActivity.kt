package com.yc.phonerecycle.activity

import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_splash.*
import android.widget.LinearLayout
import android.view.View
import android.widget.ImageView
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.adapter.GuidePageAdapter
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter


class SplashActivity : BaseActivity<EmptyPresenter>(){
    override fun createPresenter(): EmptyPresenter? = null

    private val loadingTime: Long = 1500

    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_splash

    override fun initView() {
    }

    override fun initDatas() {
        if (UserInfoUtils.isGuideShown()) {
            splash_img.postDelayed({
                toNextActivity()
            },loadingTime)
        } else {
            initViewPager()
        }
    }


    /**
     * 加载图片ViewPager
     */
    private fun initViewPager() {
        guide_vp.visibility = View.VISIBLE
        splash_img.visibility = View.GONE
        //实例化图片资源
        var imageIdArray = intArrayOf(R.drawable.guide1, R.drawable.guide2, R.drawable.guide3)
        var viewList = ArrayList<ImageView>()
        //获取一个Layout参数，设置为全屏
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )

        //循环创建View并加入到集合中
        val len = imageIdArray.size
        for (i in 0 until len) {
            //new ImageView并设置全屏和图片资源
            val imageView = ImageView(this)
            imageView.setLayoutParams(params)
            imageView.setBackgroundResource(imageIdArray[i])
            if (i == len-1) {
                imageView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        UserInfoUtils.saveGuideShown()
                        toNextActivity()
                    }
                })
            }
            //将ImageView加入到集合中
            viewList.add(imageView)
        }

        //View集合初始化好后，设置Adapter
        guide_vp.setAdapter(GuidePageAdapter(viewList as List<View>?))
    }

    private fun toNextActivity() {
        if (UserInfoUtils.isLogged()) {
            ActivityToActivity.toActivity(
                this@SplashActivity, MainActivity::class.java)
        } else {
            ActivityToActivity.toActivity(
                this@SplashActivity, LoginActivity::class.java)
        }
        finish()
    }

}
