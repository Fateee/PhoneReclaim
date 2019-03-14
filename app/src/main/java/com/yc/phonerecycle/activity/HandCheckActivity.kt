package com.yc.phonerecycle.activity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.fingerprint.FingerprintManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import com.snail.antifake.deviceid.deviceid.DeviceIdUtil
import com.yc.phonecheck.item.MicrophoneTest
import com.yc.phonerecycle.BaseCheckActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.fragment.*
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.model.bean.biz.BrandGoodsRep
import com.yc.phonerecycle.model.bean.request.CheckReqBody
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.CameraUtils
import com.yc.phonerecycle.utils.CheckPhoneUtil
import com.yc.phonerecycle.utils.PermissionUtils
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_auto_check.*
import kotlinx.android.synthetic.main.fragment_main_home.*

class HandCheckActivity : BaseCheckActivity<EmptyPresenter>() , SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    var mFirstFragment = HandCheckFirstFragment()
//    var mFirstFragment = MicrophoneTest()
    var mSecondFragment = HandCheckSecondFragment()
    var mThirdFragment = HandCheckThirdFragment()

    var mCheckReqBody = CheckReqBody()

    private lateinit var mWifiManager: WifiManager

    private lateinit var mBluetooth: BluetoothAdapter

    private lateinit var pm : PackageManager

    private val REQUEST_CODE: Int = 10

    override fun createPresenter(): EmptyPresenter? = null


    override fun initBundle() {
        var goodbean = intent.getSerializableExtra("goodbean") as BrandGoodsRep.DataBean?
        var brandid = intent.getStringExtra("brandid")
        mCheckReqBody.brandId = brandid
        mCheckReqBody.goodsId = goodbean?.id
        mCheckReqBody.system = Build.VERSION.RELEASE
        mCheckReqBody.brandName = Build.BRAND
        mCheckReqBody.type = Build.MODEL
        PermissionUtils.checkPhoneStatePermission(this@HandCheckActivity, object : PermissionUtils.Callback() {
            override fun onGranted() {
                mCheckReqBody.imei = DeviceIdUtil.getDeviceId(this@HandCheckActivity)
            }

            override fun onRationale() {
                ToastUtil.showShortToast("请开启电话权限才能正常使用")
            }

            override fun onDenied(context: Context) {
                showPermissionDialog("开启电话权限","你还没有开启电话权限，开启之后才可读取手机信息")
            }
        })
    }

    override fun getContentView(): Int = R.layout.activity_hand_check

    override fun initView() {
    }

    override fun initDatas() {
        pm = applicationContext.packageManager
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mGRAVITY = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        PROXIMITY = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        LIGHT = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        ORIENTATION = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        COMPASS = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        changeFragment(mFirstFragment)
        doGravitySensorTest()
        doDistanceSensorTest()
        doLightSensorTest()
        doOrientationSensorTest()
        doCompassTest()
        doLocationTest()
        doFingerTest()
        doMicroTest()
        doSpeakerTest()
        checkOther()
    }

    fun changeFragment(mfragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.hand_check_container,mfragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (mFirstFragment.isVisible) {
            finish()
        } else if (mSecondFragment.isVisible) {
            changeFragment(mFirstFragment)
        } else if (mThirdFragment.isVisible) {
            changeFragment(mSecondFragment)
        } else {
            super.onBackPressed()
        }
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
//            ToastUtil.showShortToast("TYPE_GRAVITY: "+event.values[0])
            mCheckReqBody.gravitySensor = 0
        }else if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
//            ToastUtil.showShortToast("Distance: "+event.values[0])
            mCheckReqBody.proximitySenso = 0
        } else if (event?.sensor?.type == Sensor.TYPE_LIGHT ) {
//            ToastUtil.showShortToast("Light: "+event.values[0])
            mCheckReqBody.lightSensor = 0
        } else if (event?.sensor?.type == Sensor.TYPE_ORIENTATION) {
//            ToastUtil.showShortToast("spiritLevel: "+event.values[0])
            mCheckReqBody.spiritLevel = 0
        } else if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
//            ToastUtil.showShortToast("Compass: "+event.values[0])
            mCheckReqBody.compass = 0
        }
    }


    private fun doGravitySensorTest() {
        mCheckReqBody.gravitySensor = if (CameraUtils.isSupportGravity(BaseApplication.getAppContext())) {0} else {1}
//        mSensorManager.registerListener(this,mGRAVITY,SensorManager.SENSOR_DELAY_FASTEST)
    }


    private fun doDistanceSensorTest() {
        val hasProxSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY)
        if (!hasProxSensor) mCheckReqBody.proximitySenso = 1
        mSensorManager.registerListener(this,PROXIMITY,SensorManager.SENSOR_DELAY_FASTEST)

    }

    private fun doLightSensorTest() {
        val hasLightSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT)
        if (!hasLightSensor) mCheckReqBody.lightSensor = 1
        mSensorManager.registerListener(this,LIGHT,SensorManager.SENSOR_DELAY_FASTEST)
    }

    private fun doOrientationSensorTest() {
        mCheckReqBody.spiritLevel = 1
        mSensorManager.registerListener(this,ORIENTATION,SensorManager.SENSOR_DELAY_FASTEST)
    }

    private fun doCompassTest() {
        val hasCompass = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)
        if (!hasCompass) mCheckReqBody.compass = 1
        mSensorManager.registerListener(this,COMPASS,SensorManager.SENSOR_DELAY_FASTEST)
    }

    private fun checkLocationPermission() : Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    }


    private fun doLocationTest() {
        val haslocation = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION)
        if (!haslocation) mCheckReqBody.location = 1
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
            mCheckReqBody.location = 1
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
                mCheckReqBody.location = 1
                return
            }
        }

        var location = locationManager.getLastKnownLocation(provider)
        if(location!=null){
            //显示当前设备的位置信息
            mCheckReqBody.location = 0
            return
        }
        locationManager.requestLocationUpdates(provider, 1000, 1f, locationListener)

    }

    private fun doFingerTest() {
        val hasFINGERPRINT = pm.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
        if (!hasFINGERPRINT) mCheckReqBody.fingerprint = 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
            if (fingerprintManager == null) {
                mCheckReqBody.fingerprint = 1
            }
            PermissionUtils.checkFingerPermission(this@HandCheckActivity, object : PermissionUtils.Callback() {
                override fun onGranted() {
                    if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
                        mCheckReqBody.fingerprint = 1
                    } else {
                        mCheckReqBody.fingerprint = 0
                    }
                }
                override fun onRationale() {
                    ToastUtil.showShortToast("请开启指纹权限才能正常使用")
                    mCheckReqBody.fingerprint = 1
                }
                override fun onDenied(context: Context) {
                    showPermissionDialog("开启指纹权限","你还没有开启指纹权限，开启之后才可读取指纹信息")
                    mCheckReqBody.fingerprint = 1
                }
            })
        } else {
            mCheckReqBody.fingerprint = 1
        }
    }
    private fun doMicroTest() {
        val hasMicrophone = microTest()
        mCheckReqBody.microphone = if (hasMicrophone) {0} else {1}
    }
    private fun doSpeakerTest() {
        var ret = CheckPhoneUtil.doSpeakerTest()
        mCheckReqBody.loudspeaker = if (ret) {0} else {1}
    }

    private fun checkOther() {
        val hasGYROSCOPE = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE)
        if (!hasGYROSCOPE) mCheckReqBody.gyroscope = 1

    }

    override fun onDestroy() {
        locationManager?.removeUpdates(locationListener)
        mSensorManager?.unregisterListener(this)
        super.onDestroy()
    }

    private var locationListener : LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            //更新当前设备的位置信息
            mCheckReqBody.location = 0
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
            mCheckReqBody.location = 0
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }
}
