package com.yc.phonerecycle.mvp.view.viewinf;

import com.yc.phonerecycle.mvp.presenter.base.BaseViewInf;

public interface CommonBaseIV extends BaseViewInf {
    void showLoading();
    void dismissLoading();

    interface MainMenuIV extends CommonBaseIV {
    }

    interface ChildMenuIV extends CommonBaseIV {
    }

    interface AddViewIV extends CommonBaseIV {
    }

    interface AddNewBabyVisitIV extends AddViewIV {
    }
}
