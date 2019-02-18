package com.yc.phonerecycle.mvp.presenter.base;


import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/1/18.
 */

public abstract class BasePresenter<V extends BaseViewInf/*, M extends BaseModelInf*/> {

    public WeakReference<V> weakReference;

//    public M model;

    public BasePresenter() {
    }

    public void attach(V v) {
        weakReference = new WeakReference<V>(v);
//        model = getModel();
    }

//    public abstract M getModel();

    public void deAttach() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
    }

    public boolean isViewAttached() {
        return weakReference != null && weakReference.get() != null;
    }

    public V getView() {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }
}
