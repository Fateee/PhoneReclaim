package com.yc.phonerecycle.activity

import android.text.TextUtils
import android.view.View
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.utils.UserInfoUtils
import kotlinx.android.synthetic.main.activity_edit_nickname.*
import kotlinx.android.synthetic.main.activity_edit_userinfo.*
import kotlinx.android.synthetic.main.titleview.*


class EditUserInfoActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.EditUserInfoIV{

    override fun createPresenter() = CommonPresenter()


    private var mTitle: String? = null

    private var mType: String? = "-1"

    override fun initBundle() {
        mTitle = intent.getStringExtra("title")
        mType = intent.getStringExtra("type")
    }

    override fun getContentView(): Int = R.layout.activity_edit_nickname

    override fun initView() {
    }

    override fun initDatas() {
        txt_main_title.text = mTitle
        when(mType) {
            "0" -> {
                item_edit.setText(UserInfoUtils.getUserInfo().data?.name)
            }
            "1" -> {
                item_edit.setText(UserInfoUtils.getUserInfo().data?.signature)
            }
        }

        txt_right_title.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var content = item_edit.text.toString()
                when(mType) {
                    "0" -> {
                        if (TextUtils.isEmpty(content)) return
                        presenter.changeName(content, UserInfoUtils.getUser().data?.userInfoVO?.id)
                    }
                    "1" -> {
                        presenter.changeSignature( UserInfoUtils.getUser().data?.userInfoVO?.id,content)
                    }
                }
            }
        })
    }

    override fun editNickNameSuccess(data: BaseRep?) {
        if (data?.code == 0) {
            ToastUtil.showShortToastCenter("保存成功")
            finish()
        } else {
            if (!TextUtils.isEmpty(data?.info))
                ToastUtil.showShortToastCenter(data?.info)
        }
    }

    override fun editUserSignSuccess(data: BaseRep?) {
        if (data?.code == 0) {
            ToastUtil.showShortToastCenter("保存成功")
            finish()
        } else {
            if (!TextUtils.isEmpty(data?.info))
                ToastUtil.showShortToastCenter(data?.info)
        }
    }
}
