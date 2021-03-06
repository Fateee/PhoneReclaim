package com.yc.phonerecycle.activity.fragment


import android.support.v4.app.Fragment
import android.view.View
import android.widget.LinearLayout
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.HandCheckActivity
import com.yc.phonerecycle.model.bean.biz.ConfigPriceRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.utils.DensityUtil
import com.yc.phonerecycle.widget.SetItemLayout
import kotlinx.android.synthetic.main.activity_hand_check_stepone.*
import kotlinx.android.synthetic.main.titleview.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HandCheckFirstFragment : BaseFragment<CommonPresenter>() {
    override fun createPresenter() = CommonPresenter()

    override fun getContentView(): Int = R.layout.activity_hand_check_stepone

    override fun initViews(view: View?) {
    }

    override fun initData() {
        next.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(!(activity as HandCheckActivity).setCheckValue(config_container)) return
                (activity as HandCheckActivity).changeFragment((activity as HandCheckActivity).mSecondFragment)
            }
        })
        txt_left_title.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                activity?.finish()
            }
        })

        addConfigView()

//        ram.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(ram,"1")
//            }
//        })
//        rom.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(rom,"2")
//            }
//        })
//        color.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(color,"3")
//            }
//        })
//        net_type.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(net_type,"5")
//            }
//        })
//        version.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(version,"4")
//            }
//        })
//        account_lock.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                dialogChoice(account_lock,"15")
//            }
//        })
    }

    private fun addConfigView() {
        var pageOneList = (activity as HandCheckActivity).configPageList?.get(0)
        if (pageOneList != null) {
            for (temp in pageOneList) {
                var setItemLayout = SetItemLayout(activity)
                var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(50f))
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
////        if (ram.tag == null || rom.tag == null || color.tag == null || net_type.tag == null || version.tag == null || account_lock.tag == null) return false
////        return if (activity is HandCheckActivity) {
////            (activity as HandCheckActivity).mCheckReqBody.memory = (ram.tag as DictMapRep.DataBean).id
////            (activity as HandCheckActivity).mCheckReqBody.capacity = (rom.tag as DictMapRep.DataBean).id
////            (activity as HandCheckActivity).mCheckReqBody.colour = (color.tag as DictMapRep.DataBean).id
////            (activity as HandCheckActivity).mCheckReqBody.wirelessNetwork = (net_type.tag as DictMapRep.DataBean).id
////            (activity as HandCheckActivity).mCheckReqBody.regional = (version.tag as DictMapRep.DataBean).id
////            (activity as HandCheckActivity).mCheckReqBody.lockAccount = (account_lock.tag as DictMapRep.DataBean).id
////            true
////        } else{
////            false
////        }
//    }


//    /**
//     * 单选
//     */
//    private fun dialogChoice(layout: SetItemLayout, dicTypeId: ConfigPriceTempRep.ConfigPriceSystemVOsBean) {
//        if (context == null) return
////        var listData = BaseApplication.mOptionMap.get(dicTypeId)
//        var listData = dicTypeId.childs
//        if (listData != null && !listData.isEmpty()) {
//            var chosedData = layout.tag
//            var chooseIndex = 0
//            var items: Array<String?> = arrayOfNulls(listData.size)
//            for (i in listData.indices) {
//                items[i] = listData[i].name
//                if (chosedData != null && (chosedData as ConfigPriceTempRep.ConfigPriceSystemVOsBean.ChildsBean).id == listData[i].id)
//                    chooseIndex = i
//            }
//            var builder =  AlertDialog.Builder(context!!,0)
//            builder.setTitle(layout.mItemName.text.toString())
//            builder.setSingleChoiceItems(items, chooseIndex,
//                object : DialogInterface.OnClickListener {
//                    override fun onClick(dialog: DialogInterface?, which: Int) {
//                        chooseIndex = which
//                        var chosedData = listData.get(which)
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
////            presenter.getDictMappingByType(dicTypeId)
//        }
//    }
}
