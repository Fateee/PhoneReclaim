package com.yc.phonerecycle.activity.fragment


import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.HandCheckActivity
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.model.bean.biz.DictMapRep
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.widget.SetItemLayout
import kotlinx.android.synthetic.main.activity_hand_check_stepone.*
import kotlinx.android.synthetic.main.titleview.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HandCheckFirstFragment : BaseFragment<EmptyPresenter>() {
    override fun createPresenter(): EmptyPresenter? = null

    override fun getContentView(): Int = R.layout.activity_hand_check_stepone

    override fun initViews(view: View?) {
    }

    override fun initData() {
        next.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                (activity as HandCheckActivity).changeFragment((activity as HandCheckActivity).mSecondFragment)
            }
        })
        txt_left_title.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                activity?.finish()
            }
        })
        ram.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(ram,"1")
            }
        })
        rom.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(rom,"2")
            }
        })
        color.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(color,"3")
            }
        })
        net_type.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(net_type,"5")
            }
        })
        version.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(version,"4")
            }
        })
        account_lock.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialogChoice(account_lock,"15")
            }
        })
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
        }
    }
}
