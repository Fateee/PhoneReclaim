package com.yc.phonerecycle.activity.fragment


import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.LinearLayout
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.HandCheckActivity
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.model.bean.biz.ConfigPriceRep
import com.yc.phonerecycle.model.bean.biz.ConfigPriceTempRep
import com.yc.phonerecycle.model.bean.biz.DictMapRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.utils.DensityUtil
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.widget.SetItemLayout
import kotlinx.android.synthetic.main.activity_hand_check_steptwo.*
import kotlinx.android.synthetic.main.titleview.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HandCheckSecondFragment : BaseFragment<CommonPresenter>() {
    override fun createPresenter() = CommonPresenter()

    override fun getContentView(): Int = R.layout.activity_hand_check_steptwo

    override fun initViews(view: View?) {
    }

    override fun initData() {
        last.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                (activity as HandCheckActivity).changeFragment((activity as HandCheckActivity).mFirstFragment)
            }
        })
        next.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(!(activity as HandCheckActivity).setCheckValue(config_container)) return
                (activity as HandCheckActivity).changeFragment((activity as HandCheckActivity).mThirdFragment)
            }
        })
        txt_left_title.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                (activity as HandCheckActivity).changeFragment((activity as HandCheckActivity).mFirstFragment)
            }
        })
        addConfigView()
//        start_up.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(start_up,"14")
//            }
//        })
//        fingerprint.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(fingerprint,"-1")
//            }
//        })
//        wifi_fault.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(wifi_fault,"16")
//            }
//        })
//        bluetooth_fault.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(bluetooth_fault,"-1")
//            }
//        })
//        call_fault.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(call_fault,"-1")
//            }
//        })
//        camrea_fault.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(camrea_fault,"-1")
//            }
//        })
//        gyro_fault.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(gyro_fault,"17")
//            }
//        })
//        gradienter_fault.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(gradienter_fault,"-1")
//            }
//        })
    }

    private fun addConfigView() {
        var pageOneList = (activity as HandCheckActivity).configPageList?.get(1)
        if (pageOneList != null) {
            for (temp in pageOneList) {
                var setItemLayout = SetItemLayout(activity)
                var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(50f))
//                params.width = LinearLayout.LayoutParams.MATCH_PARENT
//                params.height = DensityUtil.dip2px(50f)
                params.topMargin = DensityUtil.dip2px(10f)
                setItemLayout.setTitleWidth(DensityUtil.dip2px(110f).toFloat())
                setItemLayout.layoutParams = params
                setItemLayout.setBackgroundResource(R.drawable.hand_check_bg)
                setItemLayout.title = temp.name
                var code = temp.code.toInt()
                if (code > 12) {
                    setItemLayout.setSubTitle(temp.childs[0].name)
                    setItemLayout.tag = temp.childs[0]
                } else {
                    setItemLayout.setSubTitle("请选择")
                }
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
    }

//    private fun setCheckValue(): Boolean {
//        if (config_container.childCount>0) {
//            for (i in 0 until config_container.childCount) {
//                var v = config_container.getChildAt(i)
//                if (v is SetItemLayout) {
//                    if (v.tag == null) return false
//                    var bean = v.tag as ConfigPriceRep.DataBean.ConfigPriceSystemVOsBean.ChildsBeanX
////                    var bean = v.tag as ConfigPriceTempRep.ConfigPriceSystemVOsBean.ChildsBean
//                    when (bean.code) {
//                        "1" -> (activity as HandCheckActivity).mCheckReqBody.regional=bean.id
//                        "2" -> (activity as HandCheckActivity).mCheckReqBody.memory=bean.id
//                        "3" -> (activity as HandCheckActivity).mCheckReqBody.capacity=bean.id
//                        "4" -> (activity as HandCheckActivity).mCheckReqBody.wirelessNetwork=bean.id
//                        "5" -> (activity as HandCheckActivity).mCheckReqBody.colour=bean.id
//                        "6" -> (activity as HandCheckActivity).mCheckReqBody.warranty=bean.id
//                        "7" -> (activity as HandCheckActivity).mCheckReqBody.facade=bean.id
//                        "8" -> (activity as HandCheckActivity).mCheckReqBody.screenProblem = bean.id
//                        "9" -> (activity as HandCheckActivity).mCheckReqBody.water=bean.id
//                        "10" -> (activity as HandCheckActivity).mCheckReqBody.overhaul = bean.id
//                        "11" -> (activity as HandCheckActivity).mCheckReqBody.lockAccount=bean.id
//                        "12" -> (activity as HandCheckActivity).mCheckReqBody.startingState=bean.id
//                    }
//                }
//            }
//            return true
//        }
//        return false
////        if (start_up.tag == null || fingerprint.tag == null ||
////            wifi_fault.tag == null || bluetooth_fault.tag == null || call_fault.tag == null ||
////            camrea_fault.tag == null|| gyro_fault.tag == null || gradienter_fault.tag == null) return false
////        return if (activity is HandCheckActivity) {
////            (activity as HandCheckActivity).mCheckReqBody.startingState = (start_up.tag as DictMapRep.DataBean).id
////            (activity as HandCheckActivity).mCheckReqBody.fingerprint = (fingerprint.tag as DictMapRep.DataBean).id.toInt()
////            (activity as HandCheckActivity).mCheckReqBody.wifi = (wifi_fault.tag as DictMapRep.DataBean).id.toInt()
////            (activity as HandCheckActivity).mCheckReqBody.bluetooth = (bluetooth_fault.tag as DictMapRep.DataBean).id.toInt()
////            (activity as HandCheckActivity).mCheckReqBody.call = (call_fault.tag as DictMapRep.DataBean).id.toInt()
////            (activity as HandCheckActivity).mCheckReqBody.camera = (camrea_fault.tag as DictMapRep.DataBean).id.toInt()
////            (activity as HandCheckActivity).mCheckReqBody.gyroscope = (gyro_fault.tag as DictMapRep.DataBean).id.toInt()
////            (activity as HandCheckActivity).mCheckReqBody.spiritLevel = (gradienter_fault.tag as DictMapRep.DataBean).id.toInt()
////            true
////        } else{
////            false
////        }
//    }

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
