package com.yc.phonerecycle.activity

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.adapter.MenulistAdapter
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import kotlinx.android.synthetic.main.common_mainlist.*


class MyBankCardsActivity : BaseActivity<EmptyPresenter>(){

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

    override fun createPresenter(): EmptyPresenter? = null


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.common_mainlist

    override fun initView() {
    }

    private lateinit var mMenulistAdapter: MenulistAdapter

    override fun initDatas() {
        swipe_refresh_list.setOnRefreshListener(mReefreshListener)
        rv_list.useDefaultLoadMore()
        rv_list.setLoadMoreListener(mLoadMoreListener)
        val mGridLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_list.layoutManager = mGridLayoutManager
        mMenulistAdapter = MenulistAdapter(this)
        rv_list.adapter = mMenulistAdapter
    }

}
