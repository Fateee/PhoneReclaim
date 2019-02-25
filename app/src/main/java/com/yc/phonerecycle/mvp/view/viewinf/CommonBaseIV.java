package com.yc.phonerecycle.mvp.view.viewinf;

import com.yc.phonerecycle.model.bean.biz.UserInfoRep;
import com.yc.phonerecycle.mvp.presenter.base.BaseViewInf;

public interface CommonBaseIV extends BaseViewInf {
    void showLoading();
    void dismissLoading();

    interface LoginViewIV extends CommonBaseIV {
        void loginResponse(Object data);
    }

    interface SignUpIv extends CommonBaseIV {
        void requestCodeOK(Object data);
        void registerSuccess(Object data);
        void requestCodeError();
        void registerError(String message);
    }

    interface UserInfoIV extends CommonBaseIV {
        void userInfoSuccess(UserInfoRep body);

    }

    interface AddViewIV extends CommonBaseIV {
    }

    interface AddNewBabyVisitIV extends AddViewIV {
    }
}
