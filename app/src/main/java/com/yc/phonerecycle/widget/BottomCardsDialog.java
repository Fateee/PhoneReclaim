package com.yc.phonerecycle.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yc.library.utils.Dev;
import com.yc.library.widget.AddressSelector;
import com.yc.library.widget.OnAddressSelectedListener;
import com.yc.phonerecycle.R;
import com.yc.phonerecycle.activity.BindCardActivity;
import com.yc.phonerecycle.activity.adapter.MenulistAdapter;
import com.yc.phonerecycle.interfaces.OnBankClickListener;
import com.yc.phonerecycle.model.bean.biz.BankCardListRep;
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter;
import com.yc.phonerecycle.utils.ActivityToActivity;


/**
 * Created by smartTop on 2016/10/19.
 */

public class BottomCardsDialog extends Dialog implements CommonPresenter.BankcardListener {

    private CommonPresenter mCommonPresenter;
    private TextView cash_money_tip;
    private ImageView iv_colse;
    private MenulistAdapter mMenulistAdapter;
    private TextView add_bank_card;

    public BottomCardsDialog(Context context) {
        super(context, R.style.bottom_dialog);
        init(context);
    }

    public BottomCardsDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public BottomCardsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(final Context context) {
        mCommonPresenter = new CommonPresenter();
        mCommonPresenter.setBankcardListener(this);
        View view = LayoutInflater.from(context).inflate(R.layout.cardlist_for_dialog, null);
        setContentView(view);
        this.cash_money_tip = (TextView) view.findViewById(R.id.cash_money_tip);//省份
        this.iv_colse = (ImageView) view.findViewById(R.id.iv_colse);
        this.add_bank_card = (TextView) view.findViewById(R.id.add_bank_card);
        add_bank_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityToActivity.toActivity(
                        context, BindCardActivity.class);
                dismiss();
            }
        });
        iv_colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        SwipeMenuRecyclerView rv_list = (SwipeMenuRecyclerView) view.findViewById(R.id.rv_list);
        rv_list.useDefaultLoadMore();
        LinearLayoutManager mGridLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(mGridLayoutManager);
        mMenulistAdapter = new MenulistAdapter(context);
        mMenulistAdapter.setViewType(10);
        rv_list.setAdapter(mMenulistAdapter);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = Dev.dp2px(context, 300);
        window.setAttributes(params);

        window.setGravity(Gravity.BOTTOM);


    }

    public void show(String moneyValue, OnBankClickListener mOnBankClickListener) {
        mMenulistAdapter.mOnBankClickListener = mOnBankClickListener;
        mCommonPresenter.getUserBankCard();
        cash_money_tip.setText(getContext().getString(R.string.this_cash_value,moneyValue));
        super.show();
    }

    public static BottomCardsDialog show(Context context) {
        return show(context, null);
    }

    public static BottomCardsDialog show(Context context, OnAddressSelectedListener listener) {
        BottomCardsDialog dialog = new BottomCardsDialog(context, R.style.bottom_dialog);
        dialog.show();
        return dialog;
    }

    @Override
    public void onBankcardGetOk(BankCardListRep dataBean) {
        if (dataBean != null && dataBean.data != null) {
            mMenulistAdapter.refreshUI(dataBean.data,true);
        }
    }
}
