package com.yc.phonerecycle.mvp.view.viewinf;

import com.yc.phonerecycle.model.bean.base.BaseRep;
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

    interface ResetPwdByPhoneIv extends CommonBaseIV {
        void resetPwdByPhoneOK(Object data);
    }

    interface UserInfoIV extends CommonBaseIV {
        void userInfoSuccess(UserInfoRep body);
    }

    interface EditUserNameIV extends CommonBaseIV {
        void editNickNameSuccess(BaseRep data);
    }

    interface EditUserSignIV extends CommonBaseIV {
        void editUserSignSuccess(BaseRep data);
    }

    interface resetWithdrawPasswordIV extends CommonBaseIV {
        void resetWithdrawPasswordOK(BaseRep data);
    }
    interface resetPasswordIV extends CommonBaseIV {
        void resetPasswordOK(BaseRep data);
    }
    interface createWithdrawPasswordIV extends CommonBaseIV {
        void createWithdrawPassword(BaseRep data);
    }
    interface changePhoneIV extends CommonBaseIV {
        void changePhone(BaseRep data);
    }
}
