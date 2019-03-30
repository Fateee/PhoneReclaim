package com.yc.phonerecycle.activity.fragment


import android.support.v4.app.Fragment
import android.view.View
import android.widget.LinearLayout
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.AutoCheckActivity
import com.yc.phonerecycle.activity.CheckResulttActivity
import com.yc.phonerecycle.activity.HandCheckActivity
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.biz.ConfigPriceRep
import com.yc.phonerecycle.model.bean.biz.SaveRecordRep
import com.yc.phonerecycle.model.bean.request.CheckReqBody
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.DensityUtil
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.utils.UserInfoUtils
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
                activity?.finish()
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
        if (UserInfoUtils.getUserType() == "4") {
            appraisal.text = "下一步"
        }
        addConfigView()
//        phone_color.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(phone_color,"3")
//            }
//        })
//        fix_condition.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(fix_condition,"9")
//            }
//        })
//        surface.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(surface,"10")
//            }
//        })
//        screen.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(screen,"11")
//            }
//        })
//        overhaul.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(overhaul,"12")
//            }
//        })
//        in_water.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(in_water,"13")
//            }
//        })
    }

    private var pageOneList: MutableList<ConfigPriceRep.DataBean.ConfigPriceSystemVOsBean>? = null

    private fun addConfigView() {
        if (activity is HandCheckActivity) {
            pageOneList = (activity as HandCheckActivity).configPageList?.get(2)
        } else if(activity is AutoCheckActivity) {
            pageOneList = (activity as AutoCheckActivity).configRep?.configPriceSystemVOs
        }
        for (temp in pageOneList!!) {
            var setItemLayout = SetItemLayout(activity)
            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(50f))
            params.topMargin = DensityUtil.dip2px(10f)
            setItemLayout.layoutParams = params
            setItemLayout.setBackgroundResource(R.drawable.hand_check_bg)
            setItemLayout.title = temp.name
            setItemLayout.setSubTitle("请选择")
            setItemLayout.showImageItemTip()
            setItemLayout.hideDivider()
            setItemLayout.setObj(temp)
            setItemLayout.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
//                        var tem = p0?.getTag() as ConfigPriceRep.DataBean.ConfigPriceSystemVOsBean
                    dialogChoice(setItemLayout,setItemLayout.temp)
                }
            })
            config_container.addView(setItemLayout,config_container.childCount)
        }
    }

    private fun setCheckValue(): Boolean {
        if (config_container.childCount>0) {

            return if (activity is HandCheckActivity) {
                var ret = setValue((activity as HandCheckActivity).mCheckReqBody)
                if (!ret) return false
                (activity as HandCheckActivity).mCheckReqBody.other = remark_edit.getText().toString()
                presenter.saveOrUpdate((activity as HandCheckActivity).mCheckReqBody)
                true
            } else if (activity is AutoCheckActivity) {
                var ret =setValue((activity as AutoCheckActivity).checkResult)
                if (!ret) return false
                (activity as AutoCheckActivity).checkResult.other = remark_edit.getText().toString()
                presenter.saveOrUpdate((activity as AutoCheckActivity).checkResult)
                true
            } else{
                false
            }
        }
        return false
//        if (phone_color.tag == null || fix_condition.tag == null ||
//            surface.tag == null || screen.tag == null || overhaul.tag == null ||
//            in_water.tag == null) return false
//        return if (activity is HandCheckActivity) {
//            (activity as HandCheckActivity).mCheckReqBody.colour = (phone_color.tag as DictMapRep.DataBean).id
//            (activity as HandCheckActivity).mCheckReqBody.warranty = (fix_condition.tag as DictMapRep.DataBean).id
//            (activity as HandCheckActivity).mCheckReqBody.facade = (surface.tag as DictMapRep.DataBean).id
//            (activity as HandCheckActivity).mCheckReqBody.screenProblem = (screen.tag as DictMapRep.DataBean).id
//            (activity as HandCheckActivity).mCheckReqBody.overhaul = (overhaul.tag as DictMapRep.DataBean).id
//            (activity as HandCheckActivity).mCheckReqBody.water = (in_water.tag as DictMapRep.DataBean).id
//            (activity as HandCheckActivity).mCheckReqBody.other = remark_edit.getText().toString()
//            presenter.saveOrUpdate((activity as HandCheckActivity).mCheckReqBody)
//            true
//        } else if (activity is AutoCheckActivity) {
//            (activity as AutoCheckActivity).checkResult.colour = (phone_color.tag as DictMapRep.DataBean).id
//            (activity as AutoCheckActivity).checkResult.warranty = (fix_condition.tag as DictMapRep.DataBean).id
//            (activity as AutoCheckActivity).checkResult.facade = (surface.tag as DictMapRep.DataBean).id
//            (activity as AutoCheckActivity).checkResult.screenProblem = (screen.tag as DictMapRep.DataBean).id
//            (activity as AutoCheckActivity).checkResult.overhaul = (overhaul.tag as DictMapRep.DataBean).id
//            (activity as AutoCheckActivity).checkResult.water = (in_water.tag as DictMapRep.DataBean).id
//            presenter.saveOrUpdate((activity as AutoCheckActivity).checkResult)
//            true
//        } else{
//            false
//        }
    }

    private fun setValue(checkResult: CheckReqBody): Boolean {
        for (i in 0 until config_container.childCount) {
            var v = config_container.getChildAt(i)
            if (v is SetItemLayout) {
                if (v.tag == null) return false
                var bean = v.tag as ConfigPriceRep.DataBean.ConfigPriceSystemVOsBean.ChildsBeanX
//                    var bean = v.tag as ConfigPriceTempRep.ConfigPriceSystemVOsBean.ChildsBean
                when (bean.code) {
                    "1" -> checkResult.regional=bean.id
                    "2" -> checkResult.memory=bean.id
                    "3" -> checkResult.capacity=bean.id
                    "4" -> checkResult.wirelessNetwork=bean.id
                    "5" -> checkResult.colour=bean.id
                    "6" -> checkResult.warranty=bean.id
                    "7" -> checkResult.facade=bean.id
                    "8" -> checkResult.screenProblem = bean.id
                    "9" -> checkResult.water=bean.id
                    "10" -> checkResult.overhaul = bean.id
                    "11" -> checkResult.lockAccount=bean.id
                    "12" -> checkResult.startingState=bean.id
                }
            }
        }
        return true
    }

//    /**
//     * 单选
//     */
//    private fun dialogChoice(layout: SetItemLayout, dicTypeId: String) {
//        if (context == null) return
//        var listData = BaseApplication.mOptionMap.get(dicTypeId)
//        if (listData != null) {
//            var chosedData = layout.tag
//            var chooseIndex = 0
//            var items: Array<String?> = arrayOfNulls(listData.size)
//            for (i in listData.indices) {
//                items[i] = listData[i].name
//                if (chosedData != null && (chosedData as DictMapRep.DataBean).id == listData[i].id)
//                    chooseIndex = i
//            }
//            var builder =  AlertDialog.Builder(context!!,0)
//            builder.setTitle(layout.mItemName.text.toString())
//            builder.setSingleChoiceItems(items, chooseIndex,
//                object : DialogInterface.OnClickListener {
//                    override fun onClick(dialog: DialogInterface?, which: Int) {
//                        chooseIndex = which
//                        var chosedData = listData.get(chooseIndex)
//                        layout.setSubTitle(chosedData.name)
//                        layout.tag = chosedData
//                        dialog?.dismiss()
//                    }
//                })
////            builder.setPositiveButton("确定", object : DialogInterface.OnClickListener {
////                override fun onClick(dialog: DialogInterface?, which: Int) {
////                    var chosedData = listData.get(chooseIndex)
////                    layout.setSubTitle(chosedData.name)
////                    layout.tag = chosedData
////                }
////            })
//            builder.create().show()
//        } else {
//            ToastUtil.showShortToastCenter("获取选项中...请稍后点击重试")
//            presenter.getDictMappingByType(dicTypeId)
//        }
//    }
}
