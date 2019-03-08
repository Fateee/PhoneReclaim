package com.yc.phonerecycle.activity

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.Display
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.AboutUsRep
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.CameraUtils
import com.yc.phonerecycle.utils.CpuUtils
import kotlinx.android.synthetic.main.activity_phone_all_params.*
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import android.os.BatteryManager






class AllParamsActivity : BaseActivity<CommonPresenter>() {

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_phone_all_params

    private lateinit var phone: TelephonyManager

    private lateinit var wifi: WifiManager

    private lateinit var display: Display

    private lateinit var metrics: DisplayMetrics

    override fun initView() {
        phone = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        wifi = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
        display = getWindowManager().getDefaultDisplay();
        metrics = getResources().getDisplayMetrics();
    }

    override fun initDatas() {

        val book = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(book)

        brandname.text = Build.BRAND
        typename.text = Build.MODEL
        os_version.text = Build.VERSION.RELEASE
        cpu_type.text = CameraUtils.getCpuName()
        main_camera.text = CameraUtils.getCameraPixels(CameraUtils.HasBackCamera())+"像素"

//        rom.text =
//        ram.text =

        cpu_cpu_type.text = CameraUtils.getCpuName()
        cpu_core.text = CpuUtils.getNumCpuCores().toString()
        var list = CpuUtils.getCpuCurFreq()
        if (list.size > 0) {
            cpu_feq.text = list.get(0).toString()+"KHZ"
        }

        screen_size.text = CameraUtils.getPingMuSize(applicationContext).toString()+"英寸"
        screen_texing.text = Build.BOARD
        screen_fenbianlv.text = display.width.toString()+"*"+display.height.toString()+"像素"
        screen_midu.text = book.densityDpi.toString()+"dpi"
        screen_touch.text = if (CameraUtils.isSupportMultiTouch(applicationContext)) {
            "支持"
        } else {
            "不支持"
        }

//        video_record.text
        back_camera.text = CameraUtils.getCameraPixels(CameraUtils.HasBackCamera())+"像素"
        front_camera.text = CameraUtils.getCameraPixels(CameraUtils.HasFrontCamera())+"像素"

        network_model.text = phone.getNetworkType().toString()
//        gps_value.text = android.os.Build.FINGERPRINT
//        wifi_value.text = android.os.Build.DISPLAY
        bluetooth_value.text
        battery_value.text
        os_value.text = Build.VERSION.RELEASE

        gravity_value.text = if (CameraUtils.isSupportGravity(applicationContext)) {
            "支持"
        } else {
            "不支持"
        }
        accl_value.text = if (CameraUtils.isSupportAccelerometer()) {
            "支持"
        } else {
            "不支持"
        }
        light_value.text = if (CameraUtils.isSupportSensorLight(applicationContext)) {
            "支持"
        } else {
            "不支持"
        }
        compass_value.text = if (CameraUtils.isSupportCompass(applicationContext)) {
            "支持"
        } else {
            "不支持"
        }
        distance_value.text = if (CameraUtils.isSupportDistance()) {
            "支持"
        } else {
            "不支持"
        }
        tuoluoyi_value.text= if (CameraUtils.isSupportGyroscope()) {
            "支持"
        } else {
            "不支持"
        }
    }

}