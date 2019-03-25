package com.yc.phonerecycle.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yc.phonerecycle.R;


/**
 * 基本搜索页面－包含取消按钮
 * @author 胡毅
 */
public class CommonSearchView extends RelativeLayout implements
        OnFocusChangeListener, TextWatcher {

    /**
     * 搜索编辑框
     */
    public EditText mSearchET;

    public Context mContext;
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;


//    private TextView mSearchTipTV;

    public RecyclerView mRecyclerView;

    private ProgressBar mLoadingProBar;
    /**
     * 退出搜索
     */
    public TextView mSearchCancelTV;


//    private Animation mVisbleAnimation;
//
//    private Animation mInVisbleAnimation;

    public TextView mSearchListNoContent;
    /**
     * 没有内容的文本视图
     */
    public TextView mSearchNoContentTv;

    /**
     * 软键盘
     */
    protected InputMethodManager mInputMethodManager;
    /**
     * 没有网络的文本视图
     */
    private LinearLayoutManager mLayoutManager;
    public RelativeLayout mSearchTitleLayoutRL;
//    public RecyclerView.OnScrollListener mRvScrollListener = new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
////                if (isRecycleViewTop() && isRecycleViewBottom()) {
////                    mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
////                    mSwipyRefreshLayout.setEnabled(true);
////                } else if (isRecycleViewTop()) {
////                    mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
////                    mSwipyRefreshLayout.setEnabled(true);
////                } else if (mSearchNoNet.isShown() || mSearchListNoContent.isShown()) {
////                    mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
////                    mSwipyRefreshLayout.setEnabled(true);
////                } else
//            if (isRecycleViewBottom()) {
//                mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
//                mSwipyRefreshLayout.setEnabled(true);
//            } else {
//                mSwipyRefreshLayout.setEnabled(false);
//            }
//        }
//    };

    public CommonSearchView(Context context, AttributeSet attrs,
                            int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater.from(context).inflate(
                R.layout.view_search_common, this);
        initView();
    }

    public CommonSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(
                R.layout.view_search_common, this);
        initView();
    }

    public CommonSearchView(Context context) {
        super(context);
        mContext = context;
        LayoutInflater.from(context).inflate(
                R.layout.view_search_common, this);
        initView();
    }

    /**
     * 初始化界面
     */
    public void initView() {
        mSearchTitleLayoutRL = (RelativeLayout) findViewById(R.id.rl_search_title_layout);
        mSearchET = (EditText) findViewById(R.id.et_search_edittext);
//        mSearchTipTV = (TextView) findViewById(R.id.tv_search_empty_view);
        mSearchCancelTV = (TextView) findViewById(R.id.tv_search_cancel);
        mLoadingProBar = (ProgressBar) findViewById(R.id.pb_search_loading_data);
        mSearchListNoContent = (TextView) findViewById(R.id.mSearchNoContent);
//        mSearchNoNet = (RelativeLayout) findViewById(R.id.view_search_nonetwork);
//        mSearchNoContentTv = (TextView) mSearchListNoContent.findViewById(R.id.mTvViewNoContentInfo);
//        mVisbleAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right);
//        mInVisbleAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_right);

        mInputMethodManager=(InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = mSearchET.getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_search_clear_text, mContext.getTheme());
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());

        setClearIconVisible(false);

//        mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.srl_search_refresh);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_search_listview);
        //初始化recycleview的方向
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        initViewListener();
    }


    /**
     * 初始化listener
     */
    private void initViewListener() {
        mSearchET.setOnFocusChangeListener(this);
        mSearchET.addTextChangedListener(this);
        mSearchET.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    closeInputMethod(mSearchET,true);
                    if (null == onSearchListener) return false;
                    //开始搜索
                    String keywords = v.getText().toString();
                    //嗯，那没有输入的时候，点搜索没反应就好
                    if (keywords.length() > 0) {
                        //先清除list的结果
                        onSearchListener.clearSearchResult();
                        onSearchListener.loadSearchResultByKeyword(keywords);
                    }
                    return true;
                }
                return false;
            }
        });
        mSearchET.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View paramView, MotionEvent event) {
                if (mSearchET.getCompoundDrawables()[2] != null) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        boolean touchable = event.getX() > (mSearchET
                                .getWidth() - mSearchET.getPaddingRight() - mClearDrawable
                                .getIntrinsicWidth())
                                && (event.getX() < ((mSearchET.getWidth() - mSearchET
                                .getPaddingRight())));
                        if (touchable) {
                            mSearchET.setText("");
                        }
                        setClearIconVisible(!TextUtils.isEmpty(mSearchET.getText().toString()));
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        boolean touchable = event.getX() > (mSearchET
                                .getWidth() - mSearchET.getPaddingRight() - mClearDrawable
                                .getIntrinsicWidth())
                                && (event.getX() < ((mSearchET.getWidth() - mSearchET
                                .getPaddingRight())));
                        if (touchable) {
                            mSearchET.setCompoundDrawables(
                                    mSearchET.getCompoundDrawables()[0],
                                    mSearchET.getCompoundDrawables()[1], mClearDrawable,
                                    mSearchET.getCompoundDrawables()[3]);
                        }
                    }
                }
                return false;
            }
        });

//        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh(SwipyRefreshLayoutDirection direction) {
//                if (null == onSearchListener) return;
//                String keywords = mSearchET.getText().toString();
//                if (TextUtils.isEmpty(keywords)) {
//                    mSwipyRefreshLayout.setRefreshing(false);
//                    return;
//                }
//                //下拉刷新
//                if (direction == SwipyRefreshLayoutDirection.TOP) {
//                    onSearchListener.refreshSearchResult();
//                } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {//上拉刷新加载更多
//                    onSearchListener.loadSearchResultMore();
//                }
//            }
//        });

        mSearchCancelTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSearchView();
            }
        });

//        mRecyclerView.addOnScrollListener(mRvScrollListener);
    }

    /**
     * 是否是在recyclerview的顶部
     *
     * @return
     */
    public boolean isRecycleViewTop() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter != null && mLayoutManager != null) {
            if (adapter.getItemCount() > 0) {
                return mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
            }
        }
        return false;
    }

    /**
     * 是否是在recyclerview的顶部
     *
     * @return
     */
    public boolean isRecycleViewBottom() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter != null && mLayoutManager != null) {
            if (adapter.getItemCount() > 0) {
                return mLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount()-1;
            }
        }
        return false;
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;

        mSearchET.setCompoundDrawables(
                mSearchET.getCompoundDrawables()[0],
                mSearchET.getCompoundDrawables()[1], right,
                mSearchET.getCompoundDrawables()[3]);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(mSearchET.getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        setClearIconVisible(s.length() > 0);
    }

    /**
     * 当输入框里面内容发生变化之前的时候回调的方法
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    /**
     * 当输入框里面内容发生变化之后的时候回调的方法
     */
    @Override
    public void afterTextChanged(Editable s) {
//        if (null == onSearchListener) return;
//        onSearchListener.clearKeywords();
//        if (s.length() > 0) {
//            onSearchListener.loadSearchResultByKeyword(s.toString());
//        }
    }

//    private SearchListener onSearchListener;
    protected SearchListener onSearchListener;

    public void setOnSearchListener(SearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    /**
     * 执行搜索的监听器
     */
    public interface SearchListener {

        void loadSearchResultByKeyword(String keywords);

        void clearSearchResult();

        void loadSearchResultMore();

        void refreshSearchResult();
    }

    /**
     * 显示正在loading的界面
     * @param visble
     */
    protected void showLoadingView(boolean visble) {
        mSearchListNoContent.setVisibility(View.GONE);
        if (visble) {
            mLoadingProBar.setVisibility(VISIBLE);
        } else {
            mLoadingProBar.setVisibility(GONE);
        }
    }

    /**
     * 清除编辑框的焦点和文字
     */
    public void clearSearchEditText() {
        mSearchET.clearFocus();
        mSearchET.setText("");
    }

    /**
     * 显示当前界面
     */
    public void showSearchView() {
        //弹出搜索框
        if (!isShown()) {
            setVisibility(View.VISIBLE);
//            setAnimation(mVisbleAnimation);
//            startAnimation(mVisbleAnimation);
            mSearchET.requestFocus();

            closeInputMethod(mSearchET,false);
        }
    }

    /**
     * 隐藏搜索视图
     */
    public void hideSearchView() {
//        closeInputMethod(mSearchET,true);
        if (isShown()) {
//            setAnimation(mInVisbleAnimation);
//            startAnimation(mInVisbleAnimation);
            setVisibility(View.GONE);
            clearSearchEditText();
        }
    }

    public void hideSearchViewNoCloseKeyboard() {
        if (isShown()) {
//            setAnimation(mInVisbleAnimation);
//            startAnimation(mInVisbleAnimation);
            setVisibility(View.GONE);
            clearSearchEditText();
        }
    }

    /**
     * 关闭/显示键盘
     *
     * @param view
     */
    private void closeInputMethod(View view,boolean close) {
        if (close) {
            if (mInputMethodManager.isActive()) {
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } else {
            if (mInputMethodManager.isActive()) {
                mInputMethodManager.showSoftInput(view, 0);
            }
        }
    }

    /**
     * 设置编辑框左边的图片
     * @param resId
     */
    public void setEditDrawableLeft(int resId) {
        Drawable mLeftDrawable = ResourcesCompat.getDrawable(mContext.getResources(), resId, mContext.getTheme());
        mLeftDrawable.setBounds(0, 0, mLeftDrawable.getIntrinsicWidth(), mLeftDrawable.getIntrinsicHeight());

        mSearchET.setCompoundDrawables(
                mLeftDrawable,
                mSearchET.getCompoundDrawables()[1], mSearchET.getCompoundDrawables()[2],
                mSearchET.getCompoundDrawables()[3]);
    }

    public void setEditHint(@StringRes int strId)
    {
        mSearchET.setHint(strId);
    }
}
