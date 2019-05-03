package com.yc.phonerecycle.activity.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.CheckResulttActivity
import com.yc.phonerecycle.activity.OrderDetailActivity
import com.yc.phonerecycle.activity.ReportDetailActivity
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.constant.UrlConst
import com.yc.phonerecycle.model.bean.biz.DetectionRep
import com.yc.phonerecycle.model.bean.biz.MyOrderListlRep
import com.yc.phonerecycle.utils.ActivityToActivity
import kotlinx.android.synthetic.main.item_list_common.view.*


import java.util.ArrayList

/**
 * Created by Administrator on 2018/2/4.
 */

class RecordListAdapter(private val mContext: Context) : RecyclerView.Adapter<RecordVH>() {
    private val mDataList: ArrayList<Any> = ArrayList()
    private var mItemWidth: Int = 0
    private var mImgId: Int = 0
    private var mCheckIndex: Int = 0
    private var mOnItemClick : OnItemClick? = null
//    fun startActivity(mContext: Context, pos: Int, mDataList: ArrayList<T>) {
//        val intent = Intent(mContext, PhotoViewActivity::class.java)
//        intent.putExtra(PhotoViewActivity.NOWPOS, pos)
//        intent.putExtra(PhotoViewActivity.DATALIST, mDataList)
//        mContext.startActivity(intent)
//    }

    fun setOnItemClickListener(onItemClick : OnItemClick) {
        mOnItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_list, null)
        return RecordVH(view)
    }


    override fun onBindViewHolder(holder: RecordVH, position: Int) {
        val temp = mDataList[position]
        when (temp) {
            is MyOrderListlRep.DataBean -> {
                holder.date.text = temp.createTime?: temp.dealTime
                holder.status.visibility = View.VISIBLE
                holder.divider.visibility = View.GONE
                holder.wait_ems.visibility = View.GONE
                when (temp.status) { //0、已完成 1、待寄出 2、待收货 3、已退回 4、验机 5、待打款
                    0 -> {
                        holder.status.text = "已完成"
                    }
                    1 -> {
                        holder.status.text = "待邮寄"
                        holder.divider.visibility = View.VISIBLE
                        holder.wait_ems.visibility = View.VISIBLE
                        holder.wait_ems.tag = temp
                        holder.wait_ems.setOnClickListener(object :View.OnClickListener{
                            override fun onClick(p0: View?) {
                                //立即邮寄
                                mOnItemClick?.onItemClick(position,holder.wait_ems.tag)
                            }
                        })
                    }
                    2 -> {
                        holder.status.text = "待收货"
                    }
                    3 -> {
                        holder.status.text = "已退回"
                    }
                    4 -> {
                        holder.status.text = "验机"
                    }
                    5 -> {
                        holder.status.text = "待打款"
                    }
                    6 -> {
                        holder.status.text = "待验机"
                    }
                }
                showLogo(temp.logo,holder.icon)
                var tip = StringBuilder()
                if (!TextUtils.isEmpty(temp.brandName)) {
                    tip.append(temp.brandName)
                }
                if (!TextUtils.isEmpty(temp.type)) {
                    if (!TextUtils.isEmpty(tip)) {
                        tip.append("-")
                    }
                    tip.append(temp.type)
                }
                if (!TextUtils.isEmpty(temp.capacityValue)) {
                    if (!TextUtils.isEmpty(tip)) {
                        tip.append("+")
                    }
                    tip.append(temp.capacityValue)
                }
                holder.name.text = tip.toString()
                holder.content.setTextColor(ContextCompat.getColor(BaseApplication.getAppContext(),R.color.ce84b2d))
                if (temp.status == 0 || temp.status == 5) {
                    holder.content.text = mContext.getString(R.string.order_price_value,temp.price)
                } else {
                    holder.content.text = mContext.getString(R.string.order_price_value,temp.estimatePrice)
                }
                holder.detail.visibility = View.GONE

                holder.itemView.tag = temp
                holder.itemView.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(p0: View?) {
                        //订单详情
                        var tmp = p0?.tag as MyOrderListlRep.DataBean
                        var map = HashMap<String,Any?>()
                        map["ord_id"] = tmp.id
                        map["order_bean"] = tmp
                        ActivityToActivity.toActivity(
                            mContext, OrderDetailActivity::class.java,map)
                    }
                })
            }
            is DetectionRep.DataBean -> {
                holder.date.text = temp.createTime?: temp.dealTime
                holder.status.visibility = View.VISIBLE
                holder.divider.visibility = View.GONE
                holder.wait_ems.visibility = View.GONE
                var tip = StringBuilder()
                if (!TextUtils.isEmpty(temp.brandName)) {
                    tip.append(temp.brandName)
                }
                if (!TextUtils.isEmpty(temp.type)) {
                    if (!TextUtils.isEmpty(tip)) {
                        tip.append("-")
                    }
                    tip.append(temp.type)
                }
                if (!TextUtils.isEmpty(temp.capacityValue)) {
                    if (!TextUtils.isEmpty(tip)) {
                        tip.append("+")
                    }
                    tip.append(temp.capacityValue)
                }
                holder.name.text = tip.toString()
                holder.content.text = temp.customerName
                when (temp.status) { //0、已完成 1、待寄出 2、待收货 3、已退回 4、验机 5、待打款
                    0 -> {
                        holder.status.text = "已完成"
                    }
                    1 -> {
                        holder.status.text = "待邮寄"
//                        holder.divider.visibility = View.VISIBLE
//                        holder.wait_ems.visibility = View.VISIBLE
//                        holder.wait_ems.tag = temp
//                        holder.wait_ems.setOnClickListener(object :View.OnClickListener{
//                            override fun onClick(p0: View?) {
//                                //立即邮寄
//                            }
//                        })
                    }
                    2 -> {
                        holder.status.text = "待收货"
                    }
                    3 -> {
                        holder.status.text = "已退回"
                    }
                    4 -> {
                        holder.status.text = "验机"
                    }
                    5 -> {
                        holder.status.text = "待打款"
                    }
                    6 -> {
                        holder.status.text = "待验机"
                    }
                }
                showLogo(temp.logo,holder.icon)
                holder.itemView.tag = temp
                holder.itemView.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(v: View?) {
                        //订单详情
                        var tag = v?.tag as DetectionRep.DataBean
                        var map = HashMap<String,Any?>()
                        map["recordid"] = tag.id
                        map["detection_bean"] = tag
                        if (tag.status == 3 || tag.status == 5) {
                            ActivityToActivity.toActivity(
                                mContext, ReportDetailActivity::class.java,map)
                        } else {
                            ActivityToActivity.toActivity(
                                mContext, CheckResulttActivity::class.java,map)
                        }
                    }
                })
            }
        }
    }

    private fun getCapacity(capacity: Int) {
    }

    private fun showLogo(logos: String?, icon: ImageView) {
        if (logos?.contains("@") == true) {
            var logonames = logos.split("@")
            if (logonames.size >0) {
                var url = UrlConst.FILE_DOWNLOAD_URL+logonames[0]
                Glide.with(mContext).load(url).into(icon)
            }
        } else {
            if (!TextUtils.isEmpty(logos)) {
                var url = UrlConst.FILE_DOWNLOAD_URL+logos
                Glide.with(mContext).load(url).into(icon)
            }
        }
    }

//    private fun getMapNameById(id: String?): String {
//        var i = HealthApp.ROOTMAP[id] as InitMenuForm
//        return i.name
//    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun refreshUI(list: List<Any>?, refresh: Boolean) {
        if (list == null) return
        if (refresh) {
            mDataList.clear()
        }
        mDataList.addAll(list)
        notifyDataSetChanged()
    }

    fun setItemWidth(mItemWidth: Int) {
        this.mItemWidth = mItemWidth
    }


    fun setProfileListId(mImgId: Int) {
        this.mImgId = mImgId
    }


}

class RecordVH(val mView: View) : RecyclerView.ViewHolder(mView) {
    val date: TextView = mView.date
    val status: TextView = mView.status
    val icon: ImageView = mView.icon
    val name: TextView = mView.name
    val content: TextView = mView.content
    val detail: TextView = mView.detail
    val divider: View = mView.divider
    val wait_ems: TextView = mView.wait_ems
}


interface OnRecorcItemClick {
    fun onItemClick(pos: Int, tag: Any)
}