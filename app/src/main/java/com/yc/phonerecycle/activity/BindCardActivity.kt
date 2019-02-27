package com.yc.phonerecycle.activity

import android.text.TextUtils
import com.yc.phonerecycle.mvp.view.BaseActivity
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_card_bind.*
import kotlinx.android.synthetic.main.titleview.*


class BindCardActivity : BaseActivity<CommonPresenter>(){
    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_card_bind

    override fun initView() {
    }

    override fun initDatas() {
        txt_right_title.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var bank_name = card_bind_bank_name.mItemEdit.text.toString()
                var bind_owner= card_bind_owner.mItemEdit.text.toString()
                var card_num = card_bind_card_num.mItemEdit.text.toString()

                if (TextUtils.isEmpty(bank_name)) {
                    ToastUtil.showShortToastCenter("请输入银行名称")
                    return
                }
                if (TextUtils.isEmpty(bind_owner)) {
                    ToastUtil.showShortToastCenter("请输入持卡人姓名")
                    return
                }
                if (TextUtils.isEmpty(card_num)) {
                    ToastUtil.showShortToastCenter("请输入银行卡号")
                    return
                }
//                presenter.saveBankCard(old,new,UserInfoUtils.getUser().data?.userInfoVO?.id)
            }
        })
    }

}
