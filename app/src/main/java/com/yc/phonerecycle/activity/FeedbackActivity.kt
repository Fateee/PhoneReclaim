package com.yc.phonerecycle.activity

import android.text.TextUtils
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.UserInfoUtils
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_feedback.*


class FeedbackActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.FeedbakcIV{

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_feedback

    override fun initView() {
    }

    override fun initDatas() {
        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var content = feedback_edit.text.toString()

                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShortToastCenter("内容不能为空")
                    return
                }
                presenter.addFeedback(UserInfoUtils.getUser().data?.userInfoVO?.id,content)
            }
        })
    }

    override fun addFeedbackOK(data: BaseRep?) {
        if (data?.code == 0) {
            ToastUtil.showShortToastCenter("反馈成功")
            finish()
        }
    }
}
