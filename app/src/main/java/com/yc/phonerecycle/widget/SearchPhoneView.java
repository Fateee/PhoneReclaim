package com.yc.phonerecycle.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.yc.phonerecycle.activity.HandCheckActivity;
import com.yc.phonerecycle.activity.adapter.BandAdapter;
import com.yc.phonerecycle.activity.adapter.OnItemClick;
import com.yc.phonerecycle.model.bean.biz.BrandGoodsRep;
import com.yc.phonerecycle.model.bean.biz.BrandRep;
import com.yc.phonerecycle.model.bean.biz.ConfigPriceRep;
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter;
import com.yc.phonerecycle.utils.ActivityToActivity;
import com.yc.phonerecycle.utils.KeyboardUtil;
import com.yc.phonerecycle.utils.ToastUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchPhoneView extends CommonSearchView implements CommonSearchView.SearchListener{
    /**
     * 搜索用户的manager
     */
    private CommonPresenter mSearchUserManager;
    /**
     * 搜索的关键字
     */
    private String mKeywords;

    /**
     * 搜索结果
     */
    private List<BrandRep.DataBean> mSearchUserlist = new ArrayList<>();

    private BandAdapter mSearchUserAdapter;
    private TextView mNoNetTv;
    private boolean mBackKeyToGoneSearchView;
    private HashMap<String, Object> goodMap = new HashMap<>();

    public SearchPhoneView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SearchPhoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchPhoneView(Context context) {
        super(context);
    }

    @Override
    public void initView() {
        super.initView();
        mSearchUserlist = new ArrayList<>();
        mSearchUserAdapter = new BandAdapter(mContext,2);
        mSearchUserManager = new CommonPresenter();
        setOnSearchListener(this);
        mRecyclerView.setAdapter(mSearchUserAdapter);
        mSearchUserAdapter.setOnItemClickListener(new OnItemClick() {
            @Override
            public void onItemClick(int pos, @NotNull Object tag) {
                if (tag instanceof HashMap) {
                    goodMap = (HashMap<String, Object>) tag;
                    String brandid = (String) goodMap.get("brandid");
                    mSearchUserManager.getConfigPriceSystemById(brandid);
                }
            }
        });
        initSearchClubListener();
    }


    private void initSearchClubListener() {
        mSearchUserManager.setClubCallback(new CommonPresenter.GetClubCallback() {
            @Override
            public void onGetDataSuccess(int type, Object result) {
                showLoadingView(false);
                if (type == 0) {
                    BrandRep bean = (BrandRep) result;
                    if (bean.data == null || bean.data.size()==0) {
                        showEmptyView();
                    }
                    mSearchUserlist.addAll(bean.data);
                    mSearchUserAdapter.refreshUI(mSearchUserlist,true);
                    mSearchUserAdapter.notifyDataSetChanged();
//                    for (BrandRep.DataBean temp : bean.data) {
//                        mSearchUserManager.getGoodsByBrandId(1,temp.id);
//                    }
                }
//                else if (type == 1){
//                    BrandGoodsRep mSearchClubResult = (BrandGoodsRep) result;
//                    mSearchUserlist.addAll(mSearchClubResult.data);
//                    mSearchUserAdapter.refreshUI(mSearchUserlist,true);
//                    mSearchUserAdapter.notifyDataSetChanged();
//                }
                else if (type == 3){
                    if (result instanceof ConfigPriceRep) {
                        if (((ConfigPriceRep)result).data == null) {
                            ToastUtil.showShortToastCenter("没有匹配到该机型的配置");
                        } else {
                            goodMap.put("configRep",((ConfigPriceRep)result).data);
                            ActivityToActivity.toActivity(
                                    mContext, HandCheckActivity.class,goodMap);
                        }
                    }
                }
            }

            @Override
            public void onGetDataFailNetErr(String msg) {

            }

            @Override
            public void onGetDataFailDataErr(String msg) {

            }
        });
    }

    @Override
    public void loadSearchResultByKeyword(String keywords) {
        showLoadingView(true);
        mSearchUserlist.clear();
        //执行搜索的方法
        mSearchUserManager.search(keywords,0);
    }

    @Override
    public void clearSearchResult() {
//        mSearchListNoContent.setVisibility(GONE);
//        mSearchNoNet.setVisibility(GONE);
        //清除adapter的数据并刷新界面
        mSearchUserAdapter.clearAdapter();
    }

    @Override
    public void loadSearchResultMore() {
    }

    @Override
    public void refreshSearchResult() {
//        //清除adapter的数据并刷新界面
//        clearSearchResult();
    }


    @Override
    public void showSearchView() {
        showLoadingView(false);
        //清除adapter的数据并刷新界面
        clearSearchResult();
        super.showSearchView();
    }


    @Override
    public void hideSearchView() {
        KeyboardUtil.hideKeyboard((Activity) mContext);
        super.hideSearchView();
    }


    /**
     * true 按返回键让Searchview消失 false 执行默认
     */
    public void setBackKeyToGoneSearchView(boolean mBackKeyToGoneSearchView) {
        this.mBackKeyToGoneSearchView = mBackKeyToGoneSearchView;
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mBackKeyToGoneSearchView && event.getAction() == KeyEvent.ACTION_DOWN && isShown()) {
                hideSearchView();
                return true;
            } else {
                return false;
            }

        }
        return super.dispatchKeyEventPreIme(event);
    }
}
