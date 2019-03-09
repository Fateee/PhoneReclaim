package com.yc.phonerecycle.activity.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.ShopDetailActivity
import com.yc.phonerecycle.activity.ShopInMapActivity
import com.yc.phonerecycle.interfaces.OnBankClickListener
import com.yc.phonerecycle.model.bean.BaseBean
import com.yc.phonerecycle.model.bean.biz.BankCardListRep
import com.yc.phonerecycle.model.bean.biz.NearByShopRep
import com.yc.phonerecycle.utils.ActivityToActivity
import kotlinx.android.synthetic.main.commont_listitem.view.*


import java.util.ArrayList

/**
 * Created by Administrator on 2018/2/4.
 */

class MenulistAdapter(private val mContext: Context) : RecyclerView.Adapter<ChildMenuVH>() {
    //    private val mPlaceHolders = intArrayOf(R.color.CBBDEFB, R.color.CC5CAE9, R.color.CD8D8D8, R.color.CDCEDC8, R.color.CFDEFBA)
    private val mDataList: ArrayList<Any> = ArrayList()
    private var mItemWidth: Int = 0
    private var mType: Int = 0
    private var mImgId: Int = 0
    lateinit var mOnBankClickListener : OnBankClickListener
//    fun startActivity(mContext: Context, pos: Int, mDataList: ArrayList<T>) {
//        val intent = Intent(mContext, PhotoViewActivity::class.java)
//        intent.putExtra(PhotoViewActivity.NOWPOS, pos)
//        intent.putExtra(PhotoViewActivity.DATALIST, mDataList)
//        mContext.startActivity(intent)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildMenuVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.commont_listitem, null)
        return ChildMenuVH(view)
    }

//    private val mFamilyDetailListener = object : View.OnClickListener{
//        override fun onClick(v: View?) {
//            var mainIntent = Intent(mContext, AddFamilyActivity::class.java)
//            var title = "家庭详情"
//            mainIntent.putExtra("TITLE",title)
//            mainIntent.putExtra(BaseConst.DETAIL_KEY,v?.tag as FamilyListRepBean.DataBean)
//            mContext.startActivity(mainIntent)
//        }
//    }
//
//    private val mFamilyMemberListener = object : View.OnClickListener{
//        override fun onClick(v: View?) {
//            var mainIntent = Intent(mContext, MenuChildListActivity::class.java)
//            var title = "家庭成员管理"
//            mainIntent.putExtra("TITLE",title)
//            mainIntent.putExtra(BaseConst.EXTRA_FAMILY_OBJ_KEY,v?.tag as FamilyListRepBean.DataBean)
//            mContext.startActivity(mainIntent)
//        }
//    }
//
//    private val mPeopleDetailListener = object : View.OnClickListener{
//        override fun onClick(v: View?) {
//            //detail
//            var mainIntent :Intent? = null
//            var title =""
//            var obj =  v?.tag
//            if(obj is ResidentRepBean.DataBean) {
//                when(obj.tag) {
//                    2-> {
//                        title = "居民详情"
//                        mainIntent = Intent(mContext, AddPeopleActivity::class.java)
//                        mainIntent.putExtra(BaseConst.DETAIL_KEY,obj)
//                    }
//                    3-> {
//                        title = "添加随访"
//                        mainIntent = Intent(mContext, AddNewBabyVisitActivity::class.java)
//                        mainIntent.putExtra(BaseConst.DETAIL_KEY,obj)
//                    }
//                }
//            }
//            if (mainIntent == null) return
//            mainIntent.putExtra("TITLE",title)
//            mContext.startActivity(mainIntent)
//        }
//    }

    override fun onBindViewHolder(holder: ChildMenuVH, position: Int) {
        val temp = mDataList[position]
        when (temp) {
            is BankCardListRep.DataBean -> {
                holder.name.text = temp.cardholder?.replaceRange(0,1,"*")
                holder.bank_name.text = temp.openingBank
                holder.bank_account.text = temp.acount
                holder.header_one.visibility = View.VISIBLE
                holder.header_two.visibility = View.GONE
                holder.header_one.tag = temp
                if (mType == 10) {
                    holder.header_one.setOnClickListener(object :View.OnClickListener{
                        override fun onClick(v: View?) {
                            mOnBankClickListener.OnBankClick(v?.tag as BankCardListRep.DataBean)
                        }
                    })
                }
            }
            is NearByShopRep.DataBean -> {
                holder.header_one.visibility = View.GONE
                holder.header_two.visibility = View.VISIBLE
                holder.shop_name.text = temp.name
                holder.shop_addr.text = temp.address
                holder.contact_shop.tag = temp
                holder.contact_shop.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(p0: View?) {
                        var tmp = p0?.tag as NearByShopRep.DataBean
                        var map = HashMap<String,String?>()
                        map["id"] = tmp.id
                        map["type"] = "1"
                        ActivityToActivity.toActivity(
                            mContext, ShopDetailActivity::class.java,map)
                    }
                })
                holder.map_detail.tag = temp
                holder.map_detail.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(p0: View?) {
                        var tmp = p0?.tag as NearByShopRep.DataBean
                        var map = HashMap<String,BaseBean?>()
                        map["shopbean"] = tmp
                        ActivityToActivity.toActivity(
                            mContext, ShopInMapActivity::class.java,map)
                    }
                })
                holder.header_two.tag = temp
                holder.header_two.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(p0: View?) {
                        var tmp = p0?.tag as NearByShopRep.DataBean
                        var map = HashMap<String,String?>()
                        map["id"] = tmp.id
                        map["type"] = "1"
                        ActivityToActivity.toActivity(
                            mContext, ShopDetailActivity::class.java,map)
                    }
                })
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

    fun setViewType(mViewType: Int) {
        mType = mViewType
    }

}

class ChildMenuVH(val mView: View) : RecyclerView.ViewHolder(mView) {
    val header_one: RelativeLayout = mView.header_one
    val name: TextView = mView.name
    val bank_name: TextView = mView.bank_name
    val bank_account: TextView = mView.bank_account

    val header_two: RelativeLayout = mView.header_two

    val shop_name: TextView = mView.shop_name
    val shop_addr: TextView = mView.shop_addr
    val contact_shop: TextView = mView.contact_shop
    val map_detail: TextView = mView.map_detail
}