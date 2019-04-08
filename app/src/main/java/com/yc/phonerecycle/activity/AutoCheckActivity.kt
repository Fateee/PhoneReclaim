package com.yc.phonerecycle.activity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.hardware.*
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.fingerprint.FingerprintManager
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.*
import android.os.Build
import android.support.v4.app.ActivityCompat
import com.yc.phonerecycle.R
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import kotlinx.android.synthetic.main.activity_auto_check.*
import android.location.LocationManager
import android.location.Location
import android.location.LocationListener
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.google.gson.Gson
import com.snail.antifake.deviceid.deviceid.DeviceIdUtil
import com.yc.phonecheck.item.CallTest
import com.yc.phonecheck.item.LCDTest
import com.yc.phonecheck.item.TouchTest
import com.yc.phonerecycle.BaseCheckActivity
import com.yc.phonerecycle.activity.fragment.CallTestFragment
import com.yc.phonerecycle.activity.fragment.HandCheckThirdFragment
import com.yc.phonerecycle.activity.fragment.LcdTestFragment
import com.yc.phonerecycle.activity.fragment.TouchTestFragment
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.model.bean.biz.BrandGoodsRep
import com.yc.phonerecycle.model.bean.biz.ConfigPriceRep
import com.yc.phonerecycle.model.bean.biz.ConfigPriceTempRep
import com.yc.phonerecycle.model.bean.request.CheckReqBody
import com.yc.phonerecycle.utils.*


class AutoCheckActivity : BaseCheckActivity<CommonPresenter>(), SensorEventListener {

    var index = 0
    @JvmField
    var checkResult = CheckReqBody()
    var check_array: Array<String> =
        arrayOf("无线网络","蓝牙","重力感应器","距离感应器","光线感应器","水平仪","指南针","定位","指纹","麦克风","扬声器","闪光灯","振动器","摄像头")

    var configRep: ConfigPriceRep.DataBean? = null
    var config: ConfigPriceTempRep? = null

    var mHandler = object:Handler() {}

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private lateinit var pm : PackageManager

    private val REQUEST_CODE: Int = 10

    override fun createPresenter() = CommonPresenter()


    var isAutoTabCheck: Boolean = false

    override fun initBundle() {
//        var gsong = Gson()
//        config = gsong.fromJson(HandCheckActivity.json,ConfigPriceTempRep::class.java) ?: ConfigPriceTempRep()
        configRep = intent.getSerializableExtra("configRep") as ConfigPriceRep.DataBean?
        var goodbean = intent.getSerializableExtra("goodbean") as BrandGoodsRep.DataBean?
        var brandid = intent.getStringExtra("brandid")
        isAutoTabCheck = intent.getBooleanExtra("isAutoTabCheck",false)
        checkResult.brandId = goodbean?.brandId
        checkResult.goodsId = configRep?.goodsId ?: goodbean?.id
        checkResult.system = Build.VERSION.RELEASE
        checkResult.brandName = Build.BRAND
        checkResult.type = Build.MODEL
//        checkResult.capacity = "0201"
//        var totleSize = DeviceUtil.getTotalRomSize()
//        if (totleSize.contains("GB")) {
//            var temp = totleSize.removeSuffix("GB")
//            var totle = temp.trimEnd().toDouble()
//            if (totle < 16) {
//                checkResult.capacity = "0201"
//            } else if (totle < 32) {
//                checkResult.capacity = "0202"
//            } else if (totle < 64) {
//                checkResult.capacity = "0203"
//            } else if (totle < 128) {
//                checkResult.capacity = "0204"
//            } else if (totle < 256) {
//                checkResult.capacity = "0205"
//            } else if (totle < 512) {
//                checkResult.capacity = "0206"
//            } else {
//                checkResult.capacity = "0201"
//            }
//        }
//
//        var totleRamSize = DeviceUtil.getTotalRamSize()
//        var totleRam :Double = 0.0
//        if (totleRamSize.contains("GB")) {
//            var temp = totleSize.removeSuffix("GB")
//            totleRam = temp.trimEnd().toDouble()
//        }
//        if (totleRamSize.contains("MB")) {
//            var temp = totleSize.removeSuffix("MB")
//            totleRam = temp.trimEnd().toDouble()
//        }
//        if (totleRam < 1) {
//            checkResult.memory = "0101"
//        } else if (totleRam < 2) {
//            checkResult.memory = "0102"
//        } else if (totleRam < 3) {
//            checkResult.memory = "0103"
//        } else if (totleRam < 4) {
//            checkResult.memory = "0104"
//        } else if (totleRam < 6) {
//            checkResult.memory = "0105"
//        } else if (totleRam < 8) {
//            checkResult.memory = "0106"
//        } else if (totleRam < 10) {
//            checkResult.memory = "0107"
//        } else if (totleRam < 12) {
//            checkResult.memory = "0108"
//        } else {
//            checkResult.memory = "0101"
//        }
        PermissionUtils.checkPhoneStatePermission(this@AutoCheckActivity, object : PermissionUtils.Callback() {
            override fun onGranted() {
                checkResult.imei = DeviceIdUtil.getDeviceId(this@AutoCheckActivity)
            }

            override fun onRationale() {
                ToastUtil.showShortToast("请开启电话权限才能正常使用")
            }

            override fun onDenied(context: Context) {
                showPermissionDialog("开启电话权限","你还没有开启电话权限，开启之后才可读取手机信息")
            }
        })
    }

    override fun getContentView(): Int = R.layout.activity_auto_check

    override fun initView() {
        index++
        auto_index.text = index.toString()+"/"+check_array.size.toString()
        auto_tip.text = getString(R.string.auto_check_text,check_array[index-1])
    }

    private lateinit var mSensorManager: SensorManager
    private var mGRAVITY: Sensor? = null
    private var PROXIMITY: Sensor? = null
    private var LIGHT: Sensor? = null
    private var ORIENTATION: Sensor? = null
    private var COMPASS: Sensor? = null
    private lateinit var fingerprintManager: FingerprintManager
    private lateinit var locationManager: LocationManager

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_GRAVITY){
//            ToastUtil.showShortToast("当前重力感应值: "+event.values[0])
            checkResult.gravitySensor = 0
        }else if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
//            ToastUtil.showShortToast("当前距离感应值: "+event.values[0])
            checkResult.proximitySenso = 0
        } else if (event?.sensor?.type == Sensor.TYPE_LIGHT ) {
//            ToastUtil.showShortToast("当前光线强度为: "+event.values[0])
            checkResult.lightSensor = 0
        } else if (event?.sensor?.type == Sensor.TYPE_ORIENTATION) {
//            ToastUtil.showShortToast("spiritLevel: "+event.values[0])
            checkResult.spiritLevel = 0
        } else if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
//            ToastUtil.showShortToast("Compass: "+event.values[0])
            checkResult.compass = 0
        }
    }

    //arrayOf("无线网络","蓝牙","重力感应器","距离感应器","光线感应器","水平仪","指南针","定位","指纹","麦克风","扬声器","闪光灯","振动器","摄像头")
    override fun initDatas() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        pm = applicationContext.packageManager
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mGRAVITY = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        PROXIMITY = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        LIGHT = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        ORIENTATION = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        COMPASS = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
//        var sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//        for (sensor in sensorList) {
//            Log.i("sensor", "onResume: "+sensor.getName());
//        }
        doWifiTest()
//        doGravitySensorTest()
    }

    private fun doWifiTest() {
        mHandler.postDelayed({
            var list = BaseApplication.mOptionMap.get("16")
            var ret = DeviceUtil.isWifiAvailable()
            checkResult.wifi = if (ret) { 0 } else { 1 }
            initView()
            doBlueToothTest()
        },2000)
    }


    private fun doBlueToothTest() {
        mHandler.postDelayed({
            var list = BaseApplication.mOptionMap.get("16")
            var ret = DeviceUtil.isBleAvailable()
            checkResult.bluetooth = if (ret) { 0 } else { 1 }
            initView()
            doGravitySensorTest()
        },2000)
    }

    private fun doGravitySensorTest() {
        mHandler.postDelayed({
            initView()
            doDistanceSensorTest()
        },2500)
//        checkResult.gravitySensor = if (CameraUtils.isSupportGravity(BaseApplication.getAppContext())) {0} else {1}
//        if (!hasProxSensor) checkResult.proximitySenso = 1
        checkResult.gravitySensor = 1
        mSensorManager.registerListener(this,mGRAVITY,SensorManager.SENSOR_DELAY_FASTEST)
    }


    private fun doDistanceSensorTest() {
        mHandler.postDelayed({
            initView()
            doLightSensorTest()},2500)
        val hasProxSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY)
        if (!hasProxSensor) checkResult.proximitySenso = 1
        mSensorManager.registerListener(this,PROXIMITY,SensorManager.SENSOR_DELAY_FASTEST)

    }

    private fun doLightSensorTest() {
        mHandler.postDelayed({ initView()
            doOrientationSensorTest()},3000)
        val hasLightSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT)
        if (!hasLightSensor) checkResult.lightSensor = 1
        mSensorManager.registerListener(this,LIGHT,SensorManager.SENSOR_DELAY_FASTEST)
    }

    private fun doOrientationSensorTest() {
        mHandler.postDelayed({ initView()
            doCompassTest()},2500)
        checkResult.spiritLevel = 1
        mSensorManager.registerListener(this,ORIENTATION,SensorManager.SENSOR_DELAY_FASTEST)
    }

    private fun doCompassTest() {
        mHandler.postDelayed({ initView()
            doLocationTest()},2500)
        val hasCompass = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)
        if (!hasCompass) checkResult.compass = 1
        mSensorManager.registerListener(this,COMPASS,SensorManager.SENSOR_DELAY_FASTEST)
    }

    private fun checkLocationPermission() : Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    }


    private fun doLocationTest() {
        mHandler.postDelayed({
            initView()
            locationManager?.removeUpdates(locationListener)
            doFingerTest()},3000)
        val haslocation = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION)
        if (!haslocation) checkResult.location = 1
        if (Build.VERSION.SDK_INT >= 23) {// android6 执行运行时权限
            if (checkLocationPermission()) {
                var string_array: Array<String> =
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                ActivityCompat.requestPermissions(this, string_array, REQUEST_CODE)
                return
            }
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager == null) {
            checkResult.location = 1
            return
        }
        // 获取所有可用的位置提供器
        var providerList = locationManager.getProviders(true);
        // 测试一般都在室内，这里颠倒了书上的判断顺序
        var provider = when {
            providerList.contains(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
            providerList.contains(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
            else -> {
                // 当没有可用的位置提供器时，弹出Toast提示用户
                ToastUtil.showShortToast("Please Open Your GPS or Location Service")
                checkResult.location = 1
                return
            }
        }

        var location = locationManager.getLastKnownLocation(provider)
        if(location!=null){
            //显示当前设备的位置信息
            checkResult.location = 0
            return
        }
        locationManager.requestLocationUpdates(provider, 1000, 1f, locationListener)

    }
    private fun doFingerTest() {
        mHandler.postDelayed({ initView()
            doMicroTest()},3000)
        val hasFINGERPRINT = pm.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
        if (!hasFINGERPRINT) checkResult.fingerprint = 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
            if (fingerprintManager == null) {
                checkResult.fingerprint = 1
            }
            PermissionUtils.checkFingerPermission(this@AutoCheckActivity, object : PermissionUtils.Callback() {
                override fun onGranted() {
                    if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
                        checkResult.fingerprint = 1
                    } else {
                        checkResult.fingerprint = 0
                    }
                }
                override fun onRationale() {
                    ToastUtil.showShortToast("请开启指纹权限才能正常使用")
                    checkResult.fingerprint = 1
                }
                override fun onDenied(context: Context) {
                    showPermissionDialog("开启指纹权限","你还没有开启指纹权限，开启之后才可读取指纹信息")
                    checkResult.fingerprint = 1
                }
            })
        } else {
            checkResult.fingerprint = 1
        }
    }
    var mAudioRecoderUtils = AudioRecoderUtils()
    private fun doMicroTest() {
        mHandler.postDelayed({ initView()
            mAudioRecoderUtils.stopRecord()
            doSpeakerTest()},5000)
        val hasMicrophone = microTest()
        if (hasMicrophone) {
            PermissionUtils.checkRecordPermission(this@AutoCheckActivity, object : PermissionUtils.Callback() {
                override fun onGranted() {
                    ToastUtil.showShortToast("正在录音...")
                    var ret = mAudioRecoderUtils.startRecord()
                    checkResult.microphone = if (ret) {0} else {1}
                }
                override fun onRationale() {
                    ToastUtil.showShortToast("请开启录音权限才能正常使用")
                    checkResult.microphone = 1
                }
                override fun onDenied(context: Context) {
                    showPermissionDialog("开启录音权限","你还没有开启录音权限，开启之后才可录音")
                    checkResult.microphone = 1
                }
            })
        } else {
            checkResult.microphone = 1
        }
    }
    private fun doSpeakerTest() {
        mHandler.postDelayed({ initView()
            mAudioRecoderUtils.playEndOrFail(false)
            doFlashLightTest()},5000)
        ToastUtil.showShortToast("正在播放录音...")
        var ret = mAudioRecoderUtils.startPlay()
//        var ret = CheckPhoneUtil.doSpeakerTest()
        checkResult.loudspeaker = if (ret) {0} else {1}
        checkOther()
    }

    private fun checkOther() {
        val hasGYROSCOPE = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE)
        if (!hasGYROSCOPE) checkResult.gyroscope = 1

    }

    private fun doFlashLightTest() {
        mHandler.postDelayed({ initView()
            changeFlashLight(false)
            doVibratorTest()},2500)
        checkResult.flashlight = 1
        PermissionUtils.checkCameraPermission(this@AutoCheckActivity, object : PermissionUtils.Callback() {
            override fun onGranted() {
                var ret = changeFlashLight(true)
                checkResult.flashlight = if (ret) {0} else {1}
            }
            override fun onRationale() {
                ToastUtil.showShortToast("请开启相机权限才能正常使用")
                checkResult.flashlight = 1
            }
            override fun onDenied(context: Context) {
                showPermissionDialog("开启相机权限","你还没有开启相机权限，开启之后才可读取相机信息")
                checkResult.flashlight = 1
            }
        })
    }
    private fun doVibratorTest() {
        mHandler.postDelayed({ initView()
            doCameraTest()},2500)
        var ret = isVibratorGood()
        checkResult.vibrator = if (ret) {0} else {1}
    }
    private fun doCameraTest() {
        mHandler.postDelayed({
//            doLCDTest()
            touchTest()
        },2500)
        var ret = CameraUtils.HasBackCamera() !=2 && CameraUtils.HasFrontCamera() != 2
        checkResult.camera = if (ret) {0} else {1}
    }

//    private var mTouchTest = TouchTest()
    private var mTouchTest = TouchTestFragment()
    fun touchTest() {
        ic_rorato.visibility = View.GONE
        checkResult.multiTouch = 1
        mHandler.postDelayed(lcdTestRunnable,75*1000)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.screen_check_layout,mTouchTest)
        transaction.commitAllowingStateLoss()
    }

    var lcdTestRunnable = object : Runnable{
        override fun run() {
            doLCDTest()
        }
    }

    private var mLCDTest = LcdTestFragment()

    fun doLCDTest() {
        DeviceUtil.setIsFullScreen(this@AutoCheckActivity,true)
        mHandler.removeCallbacks(lcdTestRunnable)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.screen_check_layout,mLCDTest)
        transaction.commitAllowingStateLoss()
    }

    private var mCallTest = CallTestFragment()
    fun doCallTest() {
        DeviceUtil.setIsFullScreen(this@AutoCheckActivity,false)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.screen_check_layout,mCallTest)
        transaction.commitAllowingStateLoss()
    }

    var mThirdFragment = HandCheckThirdFragment()
    fun doHandCheck() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.screen_check_layout,mThirdFragment)
        transaction.commitAllowingStateLoss()
    }

    //LocationListener 用于当位置信息变化时由 locationManager 调用
    private var locationListener : LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            //更新当前设备的位置信息
            checkResult.location = 0
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
            checkResult.location = 0
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted准许
                doLocationTest()
            } else {
                // Permission Denied拒绝
                doLocationTest()
            }
        }
    }

    override fun onDestroy() {
        mHandler.removeCallbacks(lcdTestRunnable)
        mHandler.removeCallbacksAndMessages(null)
        mSensorManager?.unregisterListener(this)
        super.onDestroy()
    }

    fun autoCheckTabDone() {
        var map = HashMap<String, Any>()
        map["checkbean"] = checkResult
//        map["recordid"] = data.data
        ActivityToActivity.toActivity(
            this@AutoCheckActivity, CheckResulttActivity::class.java,map)
        finish()
    }

    private var camera: Camera? = null

    fun changeFlashLight(openOrClose: Boolean) :Boolean {
        var ret = false
        //判断API是否大于24（安卓7.0系统对应的API）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                //获取CameraManager
                val mCameraManager =
                    BaseApplication.getAppContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager
                //获取当前手机所有摄像头设备ID
                val ids = mCameraManager.cameraIdList
                for (id in ids) {
                    val c = mCameraManager.getCameraCharacteristics(id)
                    //查询该摄像头组件是否包含闪光灯
                    val flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
                    val lensFacing = c.get(CameraCharacteristics.LENS_FACING)
                    if (flashAvailable != null && flashAvailable
                        && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        //打开或关闭手电筒
                        mCameraManager.setTorchMode(id, openOrClose)
                        ret = true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            try {
                camera = Camera.open()
                val parameters = camera?.parameters
                //打开闪光灯
                parameters?.flashMode =
                        if (openOrClose) Camera.Parameters.FLASH_MODE_TORCH else Camera.Parameters.FLASH_MODE_OFF//开启//关闭
                camera?.parameters = parameters
                if (!openOrClose) camera?.release()
                ret = true
            } catch (e: Exception) {

            }
        }
        return ret
    }
}
