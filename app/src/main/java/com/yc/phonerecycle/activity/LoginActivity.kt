package com.yc.phonerecycle.activity

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.umeng.socialize.UMShareAPI
import com.yc.phonerecycle.R
import com.yc.phonerecycle.constant.BaseConst
import com.yc.phonerecycle.model.bean.biz.LoginRep
import com.yc.phonerecycle.model.bean.biz.WxTokenRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.network.BaseRetrofit
import com.yc.phonerecycle.network.reqinterface.WeiXinRequest
import com.yc.phonerecycle.utils.ActivityToActivity
import com.yc.phonerecycle.utils.PhoneUtil
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.utils.UserInfoUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Response
import third.wx.SsoLoginType


class LoginActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.LoginViewIV,CommonBaseIV.ThirdLoginViewIV {

    var QQAppID: String = "1103278945"

    override fun createPresenter(): CommonPresenter? = CommonPresenter()

    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_login

    override fun initView() {
    }

    override fun initDatas() {
//        mTencent = Tencent.createInstance(QQAppID, applicationContext)
//        listener = object : BaseUIListener(this) {
//         override fun doComplete(values: JSONObject) {
//                Toast.makeText(this@LoginActivity, "QQ 登录成功-----" + values, Toast.LENGTH_SHORT).show();
//                initOpenidAndToken(values)
//            }
//        }
        tv_login_forget_pwd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@LoginActivity, ForgetPwdActivity::class.java)
            }
        })
        tv_sign_up.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    this@LoginActivity, SignUpActivity::class.java)
            }
        })
        tv_login_action.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (TextUtils.isEmpty(login_phone_et.text.toString())) {
                    ToastUtil.showShortToastCenter("请输入登录手机号码")
                    return
                }
                if (!PhoneUtil.isMobileNO(login_phone_et.text.toString())) {
                    ToastUtil.showShortToastCenter("手机号码格式不正确")
                }
                presenter.loginAction(login_phone_et?.text.toString(), login_pwd_et?.text.toString())
//                presenter.loginAction("admin", "123456")
            }
        })
        iv_login_wx.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
//                var openid = UserInfoUtils.getUserWxTokenRep().openid
//                if (TextUtils.isEmpty(openid)) {
//                    presenter.login(this@LoginActivity,SsoLoginType.WEIXIN)
//                } else {
//                    presenter.getThirdTokenByOpenId(this@LoginActivity,openid,SsoLoginType.WEIXIN)
//                }
                presenter.login(this@LoginActivity,SsoLoginType.WEIXIN)
            }
        })
        iv_login_qq.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
//                var openid = UserInfoUtils.getUserQQTokenRep().openid
//                if (TextUtils.isEmpty(openid)) {
//                    presenter.login(this@LoginActivity,SsoLoginType.QQ)
//                } else {
//                    presenter.getThirdTokenByOpenId(this@LoginActivity,openid,SsoLoginType.QQ)
//                }
                presenter.login(this@LoginActivity,SsoLoginType.QQ)
            }
        })
    }

    override fun loginResponse(data: Any?) {
        if (data is LoginRep) {
            if(data.code == 0) {
                UserInfoUtils.saveUser(data)
                ActivityToActivity.toActivity(
                    this@LoginActivity, MainActivity::class.java)
                finish()
            } else {
                if (!TextUtils.isEmpty(data.info))
                    ToastUtil.showShortToastCenter(data.info)
            }
        }
    }

    override fun goBindPhoneView(openID: String?, type: String?) {
        var map = HashMap<String,String?>()
        map["openid"] = openID
        map["type"] = type
        ActivityToActivity.toActivity(
            this@LoginActivity, BindPhoneForThirdActivity::class.java,map,BaseConst.REQUEST_BIND_PHONE)
    }

    override fun loginWX(
        accessToken: String,
        uId: String,
        expiresIn: Long,
        wholeData: String,
        body: MutableMap<String, Any>
    ) {
        var wxRequest = BaseRetrofit.getWxInstance().createRequest(WeiXinRequest::class.java)
        wxRequest.getAccessToken(BaseConst.WEIXIN_APPID,BaseConst.WEIXIN_SERCET, body["code"] as String?,BaseConst.WEIXIN_TYPE_AUTH_CODE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<WxTokenRep>> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {}
                override fun onNext(value: Response<WxTokenRep>) {
                    if (value.code() == 200 && value.body() != null) {
                        Log.e("huyi",value.body().toString()+" ")
                    }
                }
            })
    }

    override fun loginQQ(accessToken: String?, uId: String?, expiresIn: Long, wholeData: String?) {

    }

//    fun initOpenidAndToken(jsonObject: JSONObject) {
//        try {
//            val token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN)
//            val expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN)
//            val openId = jsonObject.getString(Constants.PARAM_OPEN_ID)
//            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
//                && !TextUtils.isEmpty(openId)) {
//                mTencent.setAccessToken(token, expires)
//                mTencent.openId = openId
//            }
//        } catch (e: Exception) {
//        }
//    }
//
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
////            Tencent.onActivityResultData(requestCode, resultCode, data, listener)
//            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//        }
        if (requestCode == BaseConst.REQUEST_BIND_PHONE && resultCode == BaseConst.REQUEST_BIND_PHONE) {
            ActivityToActivity.toActivity(
                this@LoginActivity, MainActivity::class.java)
            finish()
        } else {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
        }
    }

}
