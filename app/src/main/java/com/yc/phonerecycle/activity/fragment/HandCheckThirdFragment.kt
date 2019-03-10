package com.yc.phonerecycle.activity.fragment


import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.AutoCheckActivity
import com.yc.phonerecycle.activity.CheckResulttActivity
import com.yc.phonerecycle.activity.HandCheckActivity
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.biz.DictMapRep
import com.yc.phonerecycle.model.bean.biz.SaveRecordRep
import com.yc.phonerecycle.model.bean.request.CheckReqBody
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.widget.SetItemLayout
import kotlinx.android.synthetic.main.activity_hand_check_stepthree.*
import kotlinx.android.synthetic.main.titleview.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HandCheckThirdFragment : BaseFragment<CommonPresenter>(),CommonBaseIV.saveOrUpdateIV {
    override fun saveOrUpdate(data: BaseRep?) {
        if (data is SaveRecordRep) {
            if (data?.code == 0) {
                ToastUtil.showShortToastCenter("添加检测记录成功")
                var map = HashMap<String, Any>()
                if (activity is HandCheckActivity) {
                    map["checkbean"] = (activity as HandCheckActivity).mCheckReqBody
                } else if (activity is AutoCheckActivity) {
                    map["checkbean"] = (activity as AutoCheckActivity).checkResult
                }
                map["recordid"] = data.data
                ActivityToActivity.toActivity(
                    activity, CheckResulttActivity::class.java,map)
            }
        }
    }

    override fun createPresenter() = CommonPresenter()

    override fun getContentView(): Int = R.layout.activity_hand_check_stepthree

    override fun initViews(view: View?) {
    }

    override fun initData() {
        appraisal.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(!setCheckValue()) return

            }
        })
        txt_left_title.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (activity is HandCheckActivity) {
                    (activity as HandCheckActivity).changeFragment((activity as HandCheckActivity).mSecondFragment)
                } else if (activity is AutoCheckActivity) {
                    activity?.finish()
//                    (activity as AutoCheckActivity).changeFragment((activity as HandCheckActivity).mSecondFragment)
                }
            }
        })
        phone_color.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(phone_color,"3")
            }
        })
        fix_condition.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(fix_condition,"9")
            }
        })
        surface.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(surface,"10")
            }
        })
        screen.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(screen,"11")
            }
        })
        overhaul.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(overhaul,"12")
            }
        })
        in_water.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(in_water,"13")
            }
        })
    }

    private fun setCheckValue(): Boolean {
        if (phone_color.tag == null || fix_condition.tag == null ||
            surface.tag == null || screen.tag == null || overhaul.tag == null ||
            in_water.tag == null) return false
        return if (activity is HandCheckActivity) {
            (activity as HandCheckActivity).mCheckReqBody.colour = (phone_color.tag as DictMapRep.DataBean).id
            (activity as HandCheckActivity).mCheckReqBody.warranty = (fix_condition.tag as DictMapRep.DataBean).id
            (activity as HandCheckActivity).mCheckReqBody.facade = (surface.tag as DictMapRep.DataBean).id
            (activity as HandCheckActivity).mCheckReqBody.screenProblem = (screen.tag as DictMapRep.DataBean).id
            (activity as HandCheckActivity).mCheckReqBody.overhaul = (overhaul.tag as DictMapRep.DataBean).id
            (activity as HandCheckActivity).mCheckReqBody.water = (in_water.tag as DictMapRep.DataBean).id
            (activity as HandCheckActivity).mCheckReqBody.other = remark_edit.getText().toString()
            presenter.saveOrUpdate((activity as HandCheckActivity).mCheckReqBody)
            true
        } else if (activity is AutoCheckActivity) {
            (activity as AutoCheckActivity).checkResult.colour = (phone_color.tag as DictMapRep.DataBean).id
            (activity as AutoCheckActivity).checkResult.warranty = (fix_condition.tag as DictMapRep.DataBean).id
            (activity as AutoCheckActivity).checkResult.facade = (surface.tag as DictMapRep.DataBean).id
            (activity as AutoCheckActivity).checkResult.screenProblem = (screen.tag as DictMapRep.DataBean).id
            (activity as AutoCheckActivity).checkResult.overhaul = (overhaul.tag as DictMapRep.DataBean).id
            (activity as AutoCheckActivity).checkResult.water = (in_water.tag as DictMapRep.DataBean).id
            presenter.saveOrUpdate((activity as AutoCheckActivity).checkResult)
            true
        } else{
            false
        }
    }

    /**
     * 单选
     */
    private fun dialogChoice(layout: SetItemLayout, dicTypeId: String) {
        if (context == null) return
        var listData = BaseApplication.mOptionMap.get(dicTypeId)
        if (listData != null) {
            var chosedData = layout.tag
            var chooseIndex = 0
            var items: Array<String?> = arrayOfNulls(listData.size)
            for (i in listData.indices) {
                items[i] = listData[i].name
                if (chosedData != null && (chosedData as DictMapRep.DataBean).id == listData[i].id)
                    chooseIndex = i
            }
            var builder =  AlertDialog.Builder(context!!,0)
            builder.setTitle(layout.mItemName.text.toString())
            builder.setSingleChoiceItems(items, chooseIndex,
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        chooseIndex = which
                    }
                })
            builder.setPositiveButton("确定", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var chosedData = listData.get(chooseIndex)
                    layout.setSubTitle(chosedData.name)
                    layout.tag = chosedData
                }
            })
            builder.create().show()
        } else {
            ToastUtil.showShortToastCenter("获取选项中...请稍后点击重试")
            presenter.getDictMappingByType(dicTypeId)
        }
    }
}
