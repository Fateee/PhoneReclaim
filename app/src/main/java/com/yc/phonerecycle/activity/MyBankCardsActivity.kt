package com.yc.phonerecycle.activity

import android.app.Dialog
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.adapter.MenulistAdapter
import com.yc.phonerecycle.interfaces.OnBankClickListener
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.biz.BankCardListRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.DialogHelper
import com.yc.phonerecycle.utils.PermissionUtils
import kotlinx.android.synthetic.main.common_mainlist.*
import kotlinx.android.synthetic.main.titleview.*


class MyBankCardsActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonIV{

    private val mReefreshListener: SwipeRefreshLayout.OnRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            presenter.getUserBankCard()
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


    override fun initBundle() {

    }

    override fun getContentView(): Int = R.layout.common_mainlist

    override fun initView() {
    }

    private lateinit var mMenulistAdapter: MenulistAdapter

    override fun initDatas() {
        txt_right_title.setOnClickListener {
            ActivityToActivity.toActivity(
                this@MyBankCardsActivity, BindCardActivity::class.java)
        }
        swipe_refresh_list.setOnRefreshListener(mReefreshListener)
        rv_list.useDefaultLoadMore()
        rv_list.setLoadMoreListener(mLoadMoreListener)
        val mGridLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_list.layoutManager = mGridLayoutManager
        mMenulistAdapter = MenulistAdapter(this)
        mMenulistAdapter.setViewType(11)
        mMenulistAdapter.mOnBankClickListener = object : OnBankClickListener {
            override fun OnBankClick(dataBean: BankCardListRep.DataBean?) {
                showDeleteDialog("删除银行卡","确定要删除银行卡吗？",dataBean)
            }
        }
        rv_list.adapter = mMenulistAdapter

    }

    override fun onResume() {
        super.onResume()
        presenter.getUserBankCard()
    }

    override fun getDataOK(rep: Any?) {
        swipe_refresh_list.isRefreshing = false
        if (rep is BankCardListRep) {
            mMenulistAdapter.refreshUI(rep.data,true)
        } else if (rep is BaseRep) {
            presenter.getUserBankCard()
        }
    }

    private var deleteDialog: Dialog? = null

    fun showDeleteDialog(
        title: String,
        msg: String,
        dataBean: BankCardListRep.DataBean?
    ) {
        if (deleteDialog == null) {
            deleteDialog = DialogHelper.showDialog(
                "1",
                this@MyBankCardsActivity, null,
                "",
                "",
                title,
                msg,
                getString(R.string.cancel),
                getString(R.string.sure),
                "",
                "0168b7",
                { },
                { presenter.deleteCardbyId(dataBean?.id) })
            deleteDialog?.setCanceledOnTouchOutside(false)
            deleteDialog?.setOnCancelListener { }
        } else if (deleteDialog?.isShowing == false) {
            deleteDialog?.show()
        }
    }
}
