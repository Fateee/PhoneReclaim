package com.yc.phonerecycle.utils

import android.Manifest
import android.content.Context
import com.yanzhenjie.permission.AndPermission

/**
 * Created on 2018/12/1.

 * @author lx
 */

object PermissionUtils {

    abstract class Callback {

        open fun onGranted() {}

        open fun onRationale() {}

        open fun onDenied(context: Context) {}

    }

    @JvmStatic
    fun checkPermission(context: Context, callback: Callback?, vararg permission: String) {
        AndPermission.with(context)
                .runtime()
                .permission(permission)
                .onGranted {
                    callback?.onGranted()
                }
                .rationale { _, _, executor ->
                    callback?.onRationale()
                    executor.execute()
                }
                .onDenied { permissions ->
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        callback?.onDenied(context)
                    }
                }
                .start()
    }

    @JvmStatic
    fun checkCameraPermission(context: Context, callback: Callback?) {
        checkPermission(context, callback, Manifest.permission.CAMERA)
    }

    @JvmStatic
    fun checkLocationPermission(context: Context, callback: Callback?) {
        checkPermission(context, callback, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @JvmStatic
    fun checkStoragePermission(context: Context, callback: Callback?) {
        checkPermission(context, callback, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    @JvmStatic
    fun checkPhoneStatePermission(context: Context, callback: Callback?) {
        checkPermission(context, callback, Manifest.permission.READ_PHONE_STATE)
    }

    @JvmStatic
    fun openPermissionSettings(context: Context) {
        AndPermission.with(context).runtime().setting().start()
    }

}
