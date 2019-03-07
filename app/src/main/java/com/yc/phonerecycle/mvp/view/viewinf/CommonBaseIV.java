package com.yc.phonerecycle.mvp.view.viewinf;

import com.yc.phonerecycle.model.bean.base.BaseRep;
import com.yc.phonerecycle.model.bean.biz.AboutUsRep;
import com.yc.phonerecycle.model.bean.biz.BankCardListRep;
import com.yc.phonerecycle.model.bean.biz.UserInfoRep;
import com.yc.phonerecycle.mvp.presenter.base.BaseViewInf;

import java.util.Map;

public interface CommonBaseIV extends BaseViewInf {
    void showLoading();
    void dismissLoading();

    interface LoginViewIV extends CommonBaseIV {
        void loginResponse(Object data);
        void loginWX(String accessToken, String uId, long expiresIn, final String wholeData, Map<String, Object> body);
        void loginQQ(String accessToken, String uId, long expiresIn, final String wholeData);
    }
    interface ThirdLoginViewIV extends LoginViewIV {
        void goBindPhoneView(String openID, String type);
    }

    interface ThirdBindIV extends LoginViewIV {
        void thirdBindOKGetSystemTokenResponse(Object data);
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

    interface EditUserInfoIV extends CommonBaseIV {
        void editNickNameSuccess(BaseRep data);
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

    interface saveOrUpdateIV extends CommonBaseIV {
        void saveOrUpdate(BaseRep data);
    }

    interface FeedbakcIV extends CommonBaseIV {
        void addFeedbackOK(BaseRep data);
    }

    interface AboutUsIV extends CommonBaseIV {
        void getAboutUsOK(AboutUsRep data);
    }
    interface saveBankCardIV extends CommonBaseIV {
        void saveBankCardOK(BaseRep data);
    }

    interface SaveAddrIV extends CommonBaseIV {
        void saveAddrOK(BaseRep data);
    }

    interface CommonIV extends CommonBaseIV{
        void getDataOK(Object rep);
    }
    interface CommonTypeIV extends CommonBaseIV{
        void getDataOK(Object rep,int type);
    }
    interface MoneyIV extends CommonBaseIV{
        void cashPwdOK(BaseRep rep);
        void saveMoneyBankOK(BaseRep rep);
        void saveMoneyWXOK(BaseRep rep);
    }
}
