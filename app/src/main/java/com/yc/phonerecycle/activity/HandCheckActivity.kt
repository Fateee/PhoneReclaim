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
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import com.yc.phonecheck.item.MicrophoneTest
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.fragment.*
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.model.bean.request.CheckReqBody
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_auto_check.*

class HandCheckActivity : BaseActivity<EmptyPresenter>() , SensorEventListener {

//    var mFirstFragment = HandCheckFirstFragment()
    var mFirstFragment = MicrophoneTest()
    var mSecondFragment = HandCheckSecondFragment()
    var mThirdFragment = HandCheckThirdFragment()

    var mCheckReqBody = CheckReqBody()

    private lateinit var mWifiManager: WifiManager

    private lateinit var mBluetooth: BluetoothAdapter

    private lateinit var pm : PackageManager

    private val REQUEST_CODE: Int = 10

    override fun createPresenter(): EmptyPresenter? = null

    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_hand_check

    override fun initView() {
    }

    override fun initDatas() {
        changeFragment(mFirstFragment)
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

    private fun doWifiTest() {
        registerWifiBroadcast()
        mWifiManager.isWifiEnabled = !mWifiManager.isWifiEnabled
        ic_circle.postDelayed({ initView()
            doBlueToothTest()},6000)
    }


    private fun doBlueToothTest() {
        registerBluetoothBroadcast()
        if(!mBluetooth.isEnabled) {
            mBluetooth.enable()
        } else {
            mBluetooth.disable()
        }
        ic_circle.postDelayed({ initView()
            doGravitySensorTest()},6000)
    }

    private fun doGravitySensorTest() {
        val hasGSensor = pm.hasSystemFeature(PackageManager.FEATURE_SCREEN_PORTRAIT) && pm.hasSystemFeature(
            PackageManager.FEATURE_SCREEN_LANDSCAPE)
        var ret =  hasGSensor && getSystemProperties("gsensor", true)
        if (ret) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        } else {
            //无重力感应
            mCheckReqBody.gravitySensor = 0
        }
        ic_circle.postDelayed({ initView()
            doDistanceSensorTest()},6000)
    }


    private fun doDistanceSensorTest() {
        val hasProxSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY)
        var ret = hasProxSensor && getSystemProperties("proxsensor", true)
        if (ret) {
            var mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            var mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
            ic_circle.postDelayed({ mSensorManager.unregisterListener(this)},5000)
        } else {
            mCheckReqBody.proximitySenso = 0
        }
        ic_circle.postDelayed({
            initView()
            doLightSensorTest()},6000)
    }

    private fun doLightSensorTest() {
        val hasLightSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT)
        var ret = hasLightSensor && getSystemProperties("lightsensor", true)
        if (ret) {
            var mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            var mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
            ic_circle.postDelayed({ mSensorManager.unregisterListener(this)},5000)
        } else {
            mCheckReqBody.lightSensor = 0
        }
        ic_circle.postDelayed({ initView()
            doCompassTest()},6000)
    }
    private fun doCompassTest() {
        val hasCompass = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)
        var ret =  hasCompass && getSystemProperties("compass", true)
        if (ret) {
            var mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            var mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            mSensorManager.registerListener(this, mAccSensor, SensorManager.SENSOR_DELAY_UI)
            ic_circle.postDelayed({ mSensorManager.unregisterListener(this)},5000)
        } else {
            mCheckReqBody.compass = 0
        }
        ic_circle.postDelayed({ initView()
            doLocationTest()},6000)
    }

    private fun checkLocationPermission() : Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    }

    private fun doLocationTest() {
        if (Build.VERSION.SDK_INT >= 23) {// android6 执行运行时权限
            if (checkLocationPermission()) {
                // TODO: Consider calling
                var string_array: Array<String> =
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                ActivityCompat.requestPermissions(this, string_array, REQUEST_CODE)
                return
            }
        }

        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 获取所有可用的位置提供器
        var providerList = locationManager.getProviders(true);
        // 测试一般都在室内，这里颠倒了书上的判断顺序
        var provider = when {
            providerList.contains(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
            providerList.contains(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
            else -> {
                // 当没有可用的位置提供器时，弹出Toast提示用户
                ToastUtil.showShortToast("Please Open Your GPS or Location Service")
                mCheckReqBody.location = 0
                return
            }
        }

        var location = locationManager.getLastKnownLocation(provider)
        if(location!=null){
            //显示当前设备的位置信息
            mCheckReqBody.location = 1
            return
        }
        locationManager.requestLocationUpdates(provider, 1000, 1f, locationListener)
        ic_circle.postDelayed({
            initView()
            locationManager?.removeUpdates(locationListener) },6000)

    }
    private fun doFingerTest() {

    }
    private fun doMicroTest() {

    }
    private fun doSpeakerTest() {

    }
    private fun doFlashLightTest() {

    }
    private fun doVibratorTest() {

    }
    private fun doCameraTest() {

    }

    private fun registerWifiBroadcast() {
        mWifiManager = applicationContext
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        var filter = IntentFilter()
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiReceiver, filter)
    }



    private fun registerBluetoothBroadcast() {
        mBluetooth = BluetoothAdapter.getDefaultAdapter()
        var filter = IntentFilter()
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(blueReceiver, filter)
    }

    private val wifiReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == WifiManager.WIFI_STATE_CHANGED_ACTION) {
                var list = BaseApplication.mOptionMap.get("16")
                when (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)) {
                    WifiManager.WIFI_STATE_DISABLED -> {
                        ToastUtil.showShortToast("WiFi disabled")
                        mWifiManager.isWifiEnabled = true
                        mCheckReqBody.wifi = list?.get(0)?.id?.toInt() ?: 1602
                    }
                    WifiManager.WIFI_STATE_DISABLING -> {
                        ToastUtil.showShortToast("WiFi disabling")
                        mCheckReqBody.wifi = list?.get(0)?.id?.toInt() ?: 1602
                    }
                    WifiManager.WIFI_STATE_ENABLED -> {
                        ToastUtil.showShortToast("WiFi enabled")
                        mCheckReqBody.wifi = list?.get(0)?.id?.toInt() ?: 1602
                    }
                    WifiManager.WIFI_STATE_ENABLING -> {
                        ToastUtil.showShortToast("WiFi enabling")
                        mCheckReqBody.wifi = list?.get(0)?.id?.toInt() ?: 1602
                    }
                    WifiManager.WIFI_STATE_UNKNOWN -> {
                        ToastUtil.showShortToast("WiFi state unknown")
                        mCheckReqBody.wifi = list?.get(1)?.id?.toInt() ?: 1601
                    }
                }
            }
        }
    }

    private val blueReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)) {
                    BluetoothAdapter.STATE_OFF -> {
                        ToastUtil.showShortToast("蓝牙已经关闭")
                        mWifiManager.isWifiEnabled = true
                        mCheckReqBody.bluetooth = 1
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                        ToastUtil.showShortToast("蓝牙正在关闭中")
                        mCheckReqBody.bluetooth = 1
                    }
                    BluetoothAdapter.STATE_ON -> {
                        ToastUtil.showShortToast("蓝牙已经打开")
                        mCheckReqBody.bluetooth = 1
                    }
                    BluetoothAdapter.STATE_TURNING_ON -> {
                        ToastUtil.showShortToast("蓝牙正在打开中")
                        mCheckReqBody.bluetooth = 1
                    }
                    0 -> {
                        ToastUtil.showShortToast("蓝牙 state unknown")
                        mCheckReqBody.bluetooth = 0
                    }
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        when (newConfig?.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                //重力测试成功
                mCheckReqBody.gravitySensor = 1
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                //重力测试成功
                mCheckReqBody.gravitySensor = 1
            }
        }
    }


    //LocationListener 用于当位置信息变化时由 locationManager 调用
    private var locationListener : LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            //更新当前设备的位置信息
            mCheckReqBody.location = 1
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT ) {
            ToastUtil.showShortToast("Light: "+event.values[0])
            mCheckReqBody.lightSensor = 1
        } else if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            ToastUtil.showShortToast("Distance: "+event.values[0])
            mCheckReqBody.proximitySenso = 1
        } else if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            ToastUtil.showShortToast("Compass: "+event.values[0])
            mCheckReqBody.compass = 1
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

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}
