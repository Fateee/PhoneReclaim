//package com.yc.phonerecycle.mvp.presenter.biz;
//
//import com.yc.phonerecycle.network.BaseRetrofit;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class LoginPresenter extends BasePresenter<LoginViewIV/*,LoginRepBean*/> implements Callback<LoginRepBean> {
//
//    private final LoginRequest mLoginRequest;
//
//    public LoginPresenter() {
//        mLoginRequest = BaseRetrofit.getInstance().createRequest(LoginRequest.class);
//    }
//
//    public void loginAction(String loginName, String pwd) {
//        if (getView() != null) getView().showLoading();
//        LoginReqBody body = new LoginReqBody(loginName,pwd);
//        Call<LoginRepBean> loginTask = mLoginRequest.loginRequestAction(body);
//        loginTask.enqueue(this);
//    }
//
////    @Override
////    public LoginRepBean getModel() {
////        return null;
////    }
//
//    @Override
//    public void onResponse(Call<LoginRepBean> call, Response<LoginRepBean> response) {
//        int status = response.body() != null ? response.body().status : 0;
//        if (0 != status && "登陆成功".equals(response.body().message)) return;
//        LoginViewIV loginView = getView();
//        if (loginView != null && response.body().data != null) {
//            //保存数据
//            loginView.dismissLoading();
//            loginView.loginSuccess(response.body().data);
//        }
//    }
//
//    @Override
//    public void onFailure(Call<LoginRepBean> call, Throwable t) {
//        if (getView() != null) getView().dismissLoading();
//    }
//}
