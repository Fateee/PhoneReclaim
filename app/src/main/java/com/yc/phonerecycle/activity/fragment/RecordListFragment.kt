package com.yc.phonerecycle.activity.fragment


import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.adapter.OnItemClick
import com.yc.phonerecycle.activity.adapter.RecordListAdapter
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.biz.DetectionRep
import com.yc.phonerecycle.model.bean.biz.MyOrderListlRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.utils.UserInfoUtils
import com.yc.phonerecycle.widget.EmsDialog
import kotlinx.android.synthetic.main.common_mainlist.*
import kotlinx.android.synthetic.main.titleview.*


/**
 * A simple [Fragment] subclass.
 *
 */
class RecordListFragment : BaseFragment<CommonPresenter>(), CommonBaseIV.CommonIV{

    private var mStatus: String? = "-1"
    private var type: String? = "0"
    private var mEmsDialog: EmsDialog? = null
    private val mReefreshListener: SwipeRefreshLayout.OnRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            requestData()
        }
    }

    private val mLoadMoreListener = object : SwipeMenuRecyclerView.LoadMoreListener {
        override fun onLoadMore() {
            // 该加载更多啦。
            // 如果加载失败调用下面的方法，传入errorCode和errorMessage。
            // errorCode随便传，你自定义LoadMoreView时可以根据errorCode判断错误类型。
            // errorMessage是会显示到loadMoreView上的，用户可以看到。
            // mRecyclerView.loadMoreError(0, "请求网络失败");
        }
    }

    override fun createPresenter() = CommonPresenter()

    override fun getContentView(): Int = R.layout.common_mainlist

    private var isCheckList = false

    override fun initViews(view: View?) {
        mStatus = arguments?.getString("status")
        type = arguments?.getString("type")
        isCheckList = arguments?.getBoolean("checklist",false)!!
        simple_toolbar.visibility=View.GONE
        mEmsDialog = object : EmsDialog(activity as Context){}

        mEmsDialog?.setViewClickListener(object : EmsDialog.ViewClickListener {
            override fun click(company: String?, num: String?) {
                if (TextUtils.isEmpty(orderId)) {
                    ToastUtil.showShortToastCenter("订单id为空")
                    return
                }
                presenter.writeTracking(company,orderId,num)
            }
        })
    }

    private lateinit var mRecordListAdapter: RecordListAdapter

    private var orderId: String? = ""

    override fun initData() {
        if (context == null) return
        swipe_refresh_list.setOnRefreshListener(mReefreshListener)
        rv_list.useDefaultLoadMore()
        rv_list.setLoadMoreListener(mLoadMoreListener)
        val mGridLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_list.layoutManager = mGridLayoutManager

        mRecordListAdapter = RecordListAdapter(context!!)

        mRecordListAdapter.setOnItemClickListener(object : OnItemClick {
            override fun onItemClick(pos: Int, tag: Any) {
                if (tag is MyOrderListlRep.DataBean) {
                    orderId = tag.id
                    mEmsDialog?.show()
                }
            }
        })
        rv_list.adapter = mRecordListAdapter

        requestData()
    }

    private fun requestData() {
        var userId = UserInfoUtils.getUserId()
        when (type) {
            "1" -> {//-1 查询全部 0、已完成 1、待寄出 2、待收货 3、已退回 4、验机 5、待打款
                title_view.visibility = View.GONE
                presenter.getMyOrderList(userId,mStatus)
            }
            "4" -> {
                title_view.visibility = View.GONE
                if (isCheckList) {
                    presenter.getAssistantDetection(mStatus)
                } else {
                    presenter.getAssistantMyOrderList(mStatus)
                }
            }
        }
    }

    override fun getDataOK(rep: Any?) {
        swipe_refresh_list.isRefreshing = false
        if (rep is MyOrderListlRep) {
            mRecordListAdapter.refreshUI(rep.data,true)
        } else if (rep is DetectionRep) {
            mRecordListAdapter.refreshUI(rep.data,true)
        }else if (rep is BaseRep) {
            if (rep.code == 0) {
                orderId=""
                ToastUtil.showShortToastCenter("快递信息保存成功")
            }
        }
    }
}
