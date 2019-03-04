package com.yc.phonerecycle.activity.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.HandCheckActivity
import com.yc.phonerecycle.constant.UrlConst
import com.yc.phonerecycle.model.bean.biz.BrandGoodsRep
import com.yc.phonerecycle.model.bean.biz.BrandRep
import com.yc.phonerecycle.utils.ActivityToActivity
import kotlinx.android.synthetic.main.brand_listitem.view.*
import kotlinx.android.synthetic.main.phone_listitem.view.*


import java.util.ArrayList

/**
 * Created by Administrator on 2018/2/4.
 */

class BandAdapter(private val mContext: Context, private val mType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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

    public fun setOnItemClickListener(onItemClick : OnItemClick) {
        mOnItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (mType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.brand_listitem, null)
            return BrandMenuVH(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.phone_listitem, null)
            return PhoneVH(view)
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val temp = mDataList[position]
        when (temp) {
            is BrandRep.DataBean -> {
                if (holder is BrandMenuVH) {
                    if (mCheckIndex == position) {
                        holder.name.setTextColor(mContext.getColor(R.color.c0168b7))
                        holder.right_divider.visibility = View.GONE
                    } else {
                        holder.name.setTextColor(mContext.getColor(R.color.c323232))
                        holder.right_divider.visibility = View.VISIBLE
                    }
                    holder.name.text = temp.name
                    holder.itemView.tag = temp
                    holder.itemView.setOnClickListener(object :View.OnClickListener{
                        override fun onClick(p0: View?) {
                            mCheckIndex = position
                            mOnItemClick?.onItemClick(position,holder.itemView.tag)
                            notifyDataSetChanged()
                        }
                    })
                }
            }
            is BrandGoodsRep.DataBean -> {
                if (holder is PhoneVH) {
                    holder.phone_name.text = temp.type
                    var url = UrlConst.FILE_DOWNLOAD_URL+temp.logo
                    Glide.with(mContext).load(url).centerCrop().into(holder.phone_logo)

                    holder.itemView.tag = temp
                    holder.itemView.setOnClickListener(object :View.OnClickListener{
                        override fun onClick(p0: View?) {
//                            var tmp = p0?.tag as NearByShopRep.DataBean
//                            var map = HashMap<String,String>()
//                            map["id"] = tmp.id
//                            map["type"] = "1"
                            ActivityToActivity.toActivity(
                                mContext, HandCheckActivity::class.java)
                        }
                    })
                }
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

class BrandMenuVH(val mView: View) : RecyclerView.ViewHolder(mView) {
    val name: TextView = mView.brand_name
    val right_divider: View = mView.right_divider
}

class PhoneVH(val mView: View) : RecyclerView.ViewHolder(mView) {
    val phone_logo: ImageView = mView.phone_logo
    val phone_name: TextView = mView.phone_name
}

interface OnItemClick {
    fun onItemClick(pos: Int, tag: Any)
}