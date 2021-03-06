package com.yc.phonerecycle.activity

import android.bluetooth.BluetoothManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
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
import kotlinx.android.synthetic.main.activity_phone_all_params.*
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import android.os.BatteryManager
import android.provider.MediaStore.Images.ImageColumns.ORIENTATION
import android.text.format.Formatter
import com.snail.antifake.deviceid.deviceid.DeviceIdUtil
import com.yc.phonerecycle.utils.*


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
        PermissionUtils.checkCameraPermission(this@AllParamsActivity, object : PermissionUtils.Callback() {
            override fun onGranted() {
                main_camera.text = CameraUtils.getCameraPixels(CameraUtils.HasBackCamera())+"像素"
                back_camera.text = CameraUtils.getCameraPixels(CameraUtils.HasBackCamera())+"像素"
                front_camera.text = CameraUtils.getCameraPixels(CameraUtils.HasFrontCamera())+"像素"
            }

            override fun onRationale() {
                ToastUtil.showShortToast("请开启摄像头权限才能正常使用")
            }

            override fun onDenied(context: Context) {
                showPermissionDialog("开启摄像头权限","你还没有开启摄像头权限，开启之后才可读取摄像头信息")
            }
        })


        rom.text = DeviceUtil.getAvailRomSize()+"/"+DeviceUtil.getTotalRomSize()
        ram.text = DeviceUtil.getAvailRamSize()+"/"+DeviceUtil.getTotalRamSize()

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

        video_record.text = "4K"

        network_model.text = DeviceUtil.getNetWorkString()
        gps_value.text = "内置A-GPS，支持GLONASS"
        wifi_value.text = "wi-fi802.11a/b/g/n/ac"
        bluetooth_value.text = "蓝牙4.2"
        battery_value.text = DeviceUtil.getBatteryLevel().toString()+"%"
        os_value.text = Build.VERSION.RELEASE

        var mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        var mGRAVITY = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        var PROXIMITY = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        var LIGHT = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        var COMPASS = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        gravity_value.text = if (mGRAVITY!=null) {
            "支持"
        } else {
            "不支持"
        }
        accl_value.text = if (CameraUtils.isSupportAccelerometer()) {
            "支持"
        } else {
            "不支持"
        }
        light_value.text = if (CameraUtils.isSupportSensorLight(applicationContext)|| LIGHT != null) {
            "支持"
        } else {
            "不支持"
        }
        compass_value.text = if (CameraUtils.isSupportCompass(applicationContext) || COMPASS!=null) {
            "支持"
        } else {
            "不支持"
        }
        distance_value.text = if (CameraUtils.isSupportDistance() || PROXIMITY!=null) {
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