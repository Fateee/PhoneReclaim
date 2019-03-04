package com.yc.phonerecycle.activity.fragment


import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.adapter.RecordListAdapter
import com.yc.phonerecycle.model.bean.biz.DetectionRep
import com.yc.phonerecycle.model.bean.biz.MyOrderListlRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.UserInfoUtils
import kotlinx.android.synthetic.main.common_mainlist.*
import kotlinx.android.synthetic.main.titleview.*


/**
 * A simple [Fragment] subclass.
 *
 */
class CheckListFragment : BaseFragment<CommonPresenter>(), CommonBaseIV.CommonIV{

    private var mStatus: String? = "-1"
    private var type: String? = "0"

    private val mReefreshListener: SwipeRefreshLayout.OnRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
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
        isCheckList = arguments?.getBoolean("checklist",false) ?: false
        simple_toolbar.visibility=View.GONE
    }

    private lateinit var mRecordListAdapter: RecordListAdapter

    override fun initData() {
        if (context == null) return
        swipe_refresh_list.setOnRefreshListener(mReefreshListener)
        rv_list.useDefaultLoadMore()
        rv_list.setLoadMoreListener(mLoadMoreListener)
        val mGridLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_list.layoutManager = mGridLayoutManager

        mRecordListAdapter = RecordListAdapter(context!!)
        rv_list.adapter = mRecordListAdapter

        var type = UserInfoUtils.getUser().data?.userInfoVO?.type
        when (type) {
            "1" -> {//-1 查询全部 0、已完成 1、待寄出 2、待收货 3、已退回 4、验机 5、待打款
                presenter.getAssistantDetection()
            }
        }

    }

    override fun getDataOK(rep: Any?) {
        if (rep is DetectionRep) {
            mRecordListAdapter.refreshUI(rep.data,true)
        }
    }
}
