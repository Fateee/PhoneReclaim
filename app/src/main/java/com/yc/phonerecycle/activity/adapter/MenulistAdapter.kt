package com.yc.phonerecycle.activity.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.BankCardListRep
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

                holder.name.text = temp.cardholder
                holder.bank_name.text = temp.openingBank
                holder.bank_account.text = temp.acount
            }
//            is ResidentRepBean.DataBean -> {
//                holder.item1.text = temp.code
//                holder.item2.text = temp.name
//                var sexStr = "未知"
//                if (!"0".equals(temp.sex)){
//                    sexStr = getMapNameById(temp.sex)
//                }
//                holder.item3.text = sexStr
//                holder.item4.text = temp.birthday
//                holder.item5.text = temp.identityNumber
//                var censusType = getMapNameById(temp.censusType)
//                holder.item6.text = censusType
//                var floatingPopulation = getMapNameById(temp.floatingPopulation)
//                holder.item7.text = floatingPopulation
//                var relationshipType = getMapNameById(temp.relationshipType)
//                holder.item8.text = relationshipType
//                holder.item9parent.visibility = View.GONE
//                holder.mView.tag = temp
//                temp.tag = mType
//                holder.mView.setOnClickListener(mPeopleDetailListener)
//            }
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
    val name: TextView = mView.name
    val bank_name: TextView = mView.bank_name
    val bank_account: TextView = mView.bank_account
}