package com.yc.phonerecycle.mvp.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.yc.phonerecycle.model.bean.biz.ConfigPriceRep;
import com.yc.phonerecycle.model.bean.biz.ConfigPriceTempRep;
import com.yc.phonerecycle.mvp.presenter.base.BasePresenter;
import com.yc.phonerecycle.mvp.presenter.base.BaseViewInf;
import com.yc.phonerecycle.utils.NetUtil;
import com.yc.phonerecycle.utils.ToastUtil;
import com.yc.phonerecycle.widget.SetItemLayout;

import java.util.List;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseViewInf {

//    private P mP;
    public static final int LATEST_TYPE = 1;
    public static final int CATEGORY_TYPE = 2;
    public static final int CHOSEN_TYPE = 3;
    private ProgressDialog loadingDialog;
    BasePresenter mPresenter;
    protected boolean isInit = false;
    protected boolean isLoad = false;

    public P getPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
            if (mPresenter != null) mPresenter.attach(this);
        }
        if (mPresenter != null && mPresenter.getView() == null) mPresenter.attach(this);
        return (P) mPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
        getPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
        initData();
    }

    protected abstract P createPresenter();

    protected abstract int getContentView();

    protected abstract void initViews(View view);

    protected abstract void initData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    protected void stopLoad() {

    }

    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
        if (getPresenter() == null) return;
        getPresenter().deAttach();
    }
    public void showLoading() {
        if (!NetUtil.checkNetworkState()) {
            ToastUtil.showShortToastCenter("无网络连接");
        }
        if (loadingDialog != null && loadingDialog.isShowing()) {
            return;
        }
        if (getActivity()!=null && getActivity().isFinishing()) return;
        loadingDialog = ProgressDialog.show(getContext(),"","",false,true);
    }

    public void dismissLoading() {
        loadingDialog.dismiss();
    }

    /**
     * 单选
     */
    public void dialogChoice(final SetItemLayout layout , ConfigPriceRep.DataBean.ConfigPriceSystemVOsBean dicTypeId ) {
        if (getActivity() == null) return;
//        var listData = BaseApplication.mOptionMap.get(dicTypeId)
        final List<ConfigPriceRep.DataBean.ConfigPriceSystemVOsBean.ChildsBeanX> listData = dicTypeId.childs;
        if (listData != null && !listData.isEmpty()) {
            Object chosedData = layout.getTag();
            int chooseIndex = 0;
            String[] items = new String[listData.size()];
            for (int i = 0; i <listData.size() ; i++) {
                items[i] = listData.get(i).name;
//                if (chosedData != null && (chosedData instanceof ConfigPriceTempRep.ConfigPriceSystemVOsBean.ChildsBean)) {
                if (chosedData != null && (chosedData instanceof ConfigPriceRep.DataBean.ConfigPriceSystemVOsBean.ChildsBeanX)) {
                    if (((ConfigPriceRep.DataBean.ConfigPriceSystemVOsBean.ChildsBeanX)chosedData).id == listData.get(i).id) {
                        chooseIndex = i;
                    }
                }
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),0);
            builder.setTitle(layout.mItemName.getText().toString());
            builder.setSingleChoiceItems(items, chooseIndex,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            layout.setSubTitle(listData.get(which).name);
                            layout.setTag(listData.get(which));
                            dialog.dismiss();
                        }
            });
            builder.create().show();
        } else {
            ToastUtil.showShortToastCenter("获取选项中...请稍后点击重试");
//            presenter.getDictMappingByType(dicTypeId)
        }
    }
}
