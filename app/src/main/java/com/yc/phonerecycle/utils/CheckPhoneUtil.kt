package com.yc.phonerecycle.utils

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.media.AudioManager
import android.os.Build
import com.yc.phonerecycle.BaseCheckActivity
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.mvp.view.BaseActivity

object CheckPhoneUtil {

    @JvmStatic
    fun doSpeakerTest(): Boolean{
        try {
            var localAudioManager = BaseApplication.getAppContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
            if (!localAudioManager.isSpeakerphoneOn()) {
                localAudioManager.setSpeakerphoneOn(true);
                localAudioManager.setStreamVolume(0, localAudioManager.getStreamMaxVolume(0), 0);
            }
            return true
        } catch (localException:Exception) {
            localException.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun doFingerTest(context : Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var fingerprintManager = context.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
            if (fingerprintManager == null) {
                return
            }
            PermissionUtils.checkFingerPermission(context, object : PermissionUtils.Callback() {
                override fun onGranted() {
                    if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
//                        return true
                    }
                }
                override fun onRationale() {
                    ToastUtil.showShortToast("请开启指纹权限才能正常使用")
                }
                override fun onDenied(context: Context) {
                    if (context is BaseActivity<*>) {
                        context.showPermissionDialog("开启指纹权限","你还没有开启指纹权限，开启之后才可读取指纹信息")
                    } else if (context is BaseCheckActivity<*>) {
                        context.showPermissionDialog("开启指纹权限","你还没有开启指纹权限，开启之后才可读取指纹信息")
                    }
                }
            })
        } else {
//            checkResult.fingerprint = 1
            return
        }
    }


}