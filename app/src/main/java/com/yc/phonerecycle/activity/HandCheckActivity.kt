package com.yc.phonerecycle.activity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
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
import android.os.Vibrator
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.widget.LinearLayout
import com.snail.antifake.deviceid.deviceid.DeviceIdUtil
import com.yc.phonerecycle.BaseCheckActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.fragment.*
import com.yc.phonerecycle.app.BaseApplication
import com.yc.phonerecycle.model.bean.biz.BrandGoodsRep
import com.yc.phonerecycle.model.bean.biz.ConfigPriceRep
import com.yc.phonerecycle.model.bean.biz.ConfigPriceTempRep
import com.yc.phonerecycle.model.bean.request.CheckReqBody
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.utils.*
import com.yc.phonerecycle.widget.SetItemLayout

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

    companion object {
        const val json = "{    \"id\": \"307164482558365696\",\n" +
                "        \"name\": \"华为估价体系\",\n" +
                "        \"remark\": \"华为估价体系 涉及到 Meta、荣耀系列和Nova \",\n" +
                "        \"configPriceSystemVOs\": [\n" +
                "        {\n" +
                "        \"id\": \"\",\n" +
                "        \"name\": \"网络制式\",\n" +
                "        \"priceConfigId\": \"\",\n" +
                "        \"childs\": [\n" +
                "        {\n" +
                "        \"id\": \"307164482575142913\",\n" +
                "        \"name\": \"全网通\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1290.0\",\n" +
                "        \"code\": \"4\",\n" +
                "        \"codeName\": \"网络制式\",\n" +
                "        \"type\": \"0\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"307164482604503040\",\n" +
                "        \"name\": \"联通\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1270.0\",\n" +
                "        \"code\": \"4\",\n" +
                "        \"codeName\": \"网络制式\",\n" +
                "        \"type\": \"0\"\n" +
                "        }\n" +
                "        ],\n" +
                "        \"price\": \"\",\n" +
                "        \"code\": \"4\",\n" +
                "        \"codeName\": \"网络制式\",\n" +
                "        \"type\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"\",\n" +
                "        \"name\": \"区域\",\n" +
                "        \"priceConfigId\": \"\",\n" +
                "        \"childs\": [\n" +
                "        {\n" +
                "        \"id\": \"307164482629668864\",\n" +
                "        \"name\": \"大陆\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1290.0\",\n" +
                "        \"code\": \"1\",\n" +
                "        \"codeName\": \"区域\",\n" +
                "        \"type\": \"0\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"307164482638057472\",\n" +
                "        \"name\": \"美版\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1270.0\",\n" +
                "        \"code\": \"1\",\n" +
                "        \"codeName\": \"区域\",\n" +
                "        \"type\": \"0\"\n" +
                "        }\n" +
                "        ],\n" +
                "        \"price\": \"\",\n" +
                "        \"code\": \"1\",\n" +
                "        \"codeName\": \"区域\",\n" +
                "        \"type\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"\",\n" +
                "        \"name\": \"内存\",\n" +
                "        \"priceConfigId\": \"\",\n" +
                "        \"childs\": [\n" +
                "        {\n" +
                "        \"id\": \"307164482654834689\",\n" +
                "        \"name\": \"64G\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1290.0\",\n" +
                "        \"code\": \"2\",\n" +
                "        \"codeName\": \"内存\",\n" +
                "        \"type\": \"0\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"307164482667417600\",\n" +
                "        \"name\": \"128G\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1270.0\",\n" +
                "        \"code\": \"2\",\n" +
                "        \"codeName\": \"内存\",\n" +
                "        \"type\": \"0\"\n" +
                "        }\n" +
                "        ],\n" +
                "        \"price\": \"\",\n" +
                "        \"code\": \"2\",\n" +
                "        \"codeName\": \"内存\",\n" +
                "        \"type\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"\",\n" +
                "        \"name\": \"颜色\",\n" +
                "        \"priceConfigId\": \"\",\n" +
                "        \"childs\": [\n" +
                "        {\n" +
                "        \"id\": \"307164482680000513\",\n" +
                "        \"name\": \"红色\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1290.0\",\n" +
                "        \"code\": \"5\",\n" +
                "        \"codeName\": \"颜色\",\n" +
                "        \"type\": \"0\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"307164482692583424\",\n" +
                "        \"name\": \"黑色\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1270.0\",\n" +
                "        \"code\": \"5\",\n" +
                "        \"codeName\": \"颜色\",\n" +
                "        \"type\": \"0\"\n" +
                "        }\n" +
                "        ],\n" +
                "        \"price\": \"\",\n" +
                "        \"code\": \"5\",\n" +
                "        \"codeName\": \"颜色\",\n" +
                "        \"type\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"\",\n" +
                "        \"name\": \"存储空间\",\n" +
                "        \"priceConfigId\": \"\",\n" +
                "        \"childs\": [\n" +
                "        {\n" +
                "        \"id\": \"307164482705166337\",\n" +
                "        \"name\": \"128G\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1290.0\",\n" +
                "        \"code\": \"3\",\n" +
                "        \"codeName\": \"存储空间\",\n" +
                "        \"type\": \"0\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"307164482713554944\",\n" +
                "        \"name\": \"256G\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1270.0\",\n" +
                "        \"code\": \"3\",\n" +
                "        \"codeName\": \"存储空间\",\n" +
                "        \"type\": \"0\"\n" +
                "        }\n" +
                "        ],\n" +
                "        \"price\": \"\",\n" +
                "        \"code\": \"3\",\n" +
                "        \"codeName\": \"存储空间\",\n" +
                "        \"type\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"\",\n" +
                "        \"name\": \"进水\",\n" +
                "        \"priceConfigId\": \"\",\n" +
                "        \"childs\": [\n" +
                "        {\n" +
                "        \"id\": \"307164482839384065\",\n" +
                "        \"name\": \"没有进水\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1290.0\",\n" +
                "        \"code\": \"9\",\n" +
                "        \"codeName\": \"进水\",\n" +
                "        \"type\": \"0\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"307164482851966976\",\n" +
                "        \"name\": \"有进水\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1270.0\",\n" +
                "        \"code\": \"9\",\n" +
                "        \"codeName\": \"进水\",\n" +
                "        \"type\": \"0\"\n" +
                "        }\n" +
                "        ],\n" +
                "        \"price\": \"\",\n" +
                "        \"code\": \"9\",\n" +
                "        \"codeName\": \"进水\",\n" +
                "        \"type\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"\",\n" +
                "        \"name\": \"账户锁\",\n" +
                "        \"priceConfigId\": \"\",\n" +
                "        \"childs\": [\n" +
                "        {\n" +
                "        \"id\": \"307164482872938497\",\n" +
                "        \"name\": \"有账户锁\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1290.0\",\n" +
                "        \"code\": \"11\",\n" +
                "        \"codeName\": \"账户锁\",\n" +
                "        \"type\": \"0\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"307164482877132800\",\n" +
                "        \"name\": \"没有账户锁\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1270.0\",\n" +
                "        \"code\": \"11\",\n" +
                "        \"codeName\": \"账户锁\",\n" +
                "        \"type\": \"0\"\n" +
                "        }\n" +
                "        ],\n" +
                "        \"price\": \"\",\n" +
                "        \"code\": \"11\",\n" +
                "        \"codeName\": \"账户锁\",\n" +
                "        \"type\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"\",\n" +
                "        \"name\": \"开机\",\n" +
                "        \"priceConfigId\": \"\",\n" +
                "        \"childs\": [\n" +
                "        {\n" +
                "        \"id\": \"307164482893910016\",\n" +
                "        \"name\": \"不能开机\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1290.0\",\n" +
                "        \"code\": \"12\",\n" +
                "        \"codeName\": \"开机\",\n" +
                "        \"type\": \"0\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"id\": \"307164482898104320\",\n" +
                "        \"name\": \"可以开机\",\n" +
                "        \"priceConfigId\": \"307164482558365696\",\n" +
                "        \"childs\": \"\",\n" +
                "        \"price\": \"1270.0\",\n" +
                "        \"code\": \"12\",\n" +
                "        \"codeName\": \"开机\",\n" +
                "        \"type\": \"0\"\n" +
                "        }\n" +
                "        ],\n" +
                "        \"price\": \"\",\n" +
                "        \"code\": \"12\",\n" +
                "        \"codeName\": \"开机\",\n" +
                "        \"type\": \"\"\n" +
                "        }\n" +
                "        ],\n" +
                "        \"hardwarePriceSystemVO\": {\n" +
                "        \"id\": \"\",\n" +
                "        \"priceConfig\": \"307164482558365696\",\n" +
                "        \"wirelessNetwork\": 0,\n" +
                "        \"wirelessNetworkType\": 0,\n" +
                "        \"gravitySensor\": 0,\n" +
                "        \"gravitySensorType\": 0,\n" +
                "        \"bluetooth\": 0,\n" +
                "        \"bluetoothType\": 0,\n" +
                "        \"proximitySenso\": 0,\n" +
                "        \"proximitySensoType\": 0,\n" +
                "        \"lightSensor\": 0,\n" +
                "        \"lightSensorType\": 0,\n" +
                "        \"compass\": 0,\n" +
                "        \"compassType\": 0,\n" +
                "        \"spiritLevel\": 0,\n" +
                "        \"spiritLevelType\": 0,\n" +
                "        \"location\": 0,\n" +
                "        \"locationType\": 0,\n" +
                "        \"call\": 0,\n" +
                "        \"callType\": 0,\n" +
                "        \"fingerprint\": 0,\n" +
                "        \"fingerprintType\": 0,\n" +
                "        \"comprehensionAids\": 0,\n" +
                "        \"comprehensionAidsType\": 0,\n" +
                "        \"loudspeaker\": 0,\n" +
                "        \"loudspeakerType\": 0,\n" +
                "        \"vibrator\": 0,\n" +
                "        \"vibratorType\": 0,\n" +
                "        \"microphone\": 0,\n" +
                "        \"microphoneType\": 0,\n" +
                "        \"\": 0,\n" +
                "        \"cameraType\": 0,\n" +
                "        \"flashlight\": 0,\n" +
                "        \"flashlightType\": 0,\n" +
                "        \"multiTouch\": 0,\n" +
                "        \"multiTouchType\": 0,\n" +
                "        \"battery\": 0,\n" +
                "        \"batteryType\": 0,\n" +
                "        \"screen\": 0,\n" +
                "        \"screenType\": 0\n" +
                "        }\n" +
                "        }"
    }
    var configRep: ConfigPriceRep.DataBean? = null
    var config: ConfigPriceTempRep? = null
    var configPageList: MutableList<MutableList<ConfigPriceRep.DataBean.ConfigPriceSystemVOsBean>>? = null

    override fun initBundle() {
        configRep = intent.getSerializableExtra("configRep") as ConfigPriceRep.DataBean?
        var goodbean = intent.getSerializableExtra("goodbean") as BrandGoodsRep.DataBean?
        var brandid = intent.getStringExtra("brandid")
        mCheckReqBody.brandId = goodbean?.brandId
        mCheckReqBody.goodsId = goodbean?.id
        mCheckReqBody.system = Build.VERSION.RELEASE
//        mCheckReqBody.brandName = Build.BRAND
        mCheckReqBody.brandName = goodbean?.brandName
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
        //todo huyi
//        var gsong = Gson()
//        config = gsong.fromJson(json,ConfigPriceTempRep::class.java) ?: ConfigPriceTempRep()
        configPageList = StringUtils.averageAssign(configRep?.configPriceSystemVOs,3)
    }

    override fun getContentView(): Int = R.layout.activity_hand_check

    override fun initView() {
    }

    override fun initDatas() {
        pm = applicationContext.packageManager
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mGRAVITY = mSensorManager!!.getDefaultSensor(Sensor.TYPE_GRAVITY)
        PROXIMITY = mSensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        LIGHT = mSensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        ORIENTATION = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        COMPASS = mSensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        changeFragment(mFirstFragment)
//        doWifiTest()
//        doBlueToothTest()
//        doGravitySensorTest()
//        doDistanceSensorTest()
//        doLightSensorTest()
//        doOrientationSensorTest()
//        doCompassTest()
//        doLocationTest()
//        doFingerTest()
//        doMicroTest()
//        doSpeakerTest()
//        checkOther()
//        doCameraTest()
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

    private var mSensorManager: SensorManager? = null
    private var mGRAVITY: Sensor? = null
    private var PROXIMITY: Sensor? = null
    private var LIGHT: Sensor? = null
    private var ORIENTATION: Sensor? = null
    private var COMPASS: Sensor? = null
    private var fingerprintManager: FingerprintManager? = null
    private var locationManager: LocationManager? = null

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


    private fun doWifiTest() {
        var ret = DeviceUtil.isWifiAvailable()
        mCheckReqBody.wifi = if (ret) { 0 } else { 1 }
    }


    private fun doBlueToothTest() {
        var ret = DeviceUtil.isBleAvailable()
        mCheckReqBody.bluetooth = if (ret) { 0 } else { 1 }
    }

    private fun doGravitySensorTest() {
        mCheckReqBody.gravitySensor = if (CameraUtils.isSupportGravity(BaseApplication.getAppContext())) {0} else {1}
//        mSensorManager.registerListener(this,mGRAVITY,SensorManager.SENSOR_DELAY_FASTEST)
    }


    private fun doDistanceSensorTest() {
        val hasProxSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY)
        if (!hasProxSensor) mCheckReqBody.proximitySenso = 1
        mSensorManager?.registerListener(this,PROXIMITY,SensorManager.SENSOR_DELAY_FASTEST)

    }

    private fun doLightSensorTest() {
        val hasLightSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT)
        if (!hasLightSensor) mCheckReqBody.lightSensor = 1
        mSensorManager?.registerListener(this,LIGHT,SensorManager.SENSOR_DELAY_FASTEST)
    }

    private fun doOrientationSensorTest() {
        mCheckReqBody.spiritLevel = 1
        mSensorManager?.registerListener(this,ORIENTATION,SensorManager.SENSOR_DELAY_FASTEST)
    }

    private fun doCompassTest() {
        val hasCompass = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)
        if (!hasCompass) mCheckReqBody.compass = 1
        mSensorManager?.registerListener(this,COMPASS,SensorManager.SENSOR_DELAY_FASTEST)
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
        var providerList = locationManager?.getProviders(true)
        // 测试一般都在室内，这里颠倒了书上的判断顺序
        var provider = when {
            providerList?.contains(LocationManager.NETWORK_PROVIDER)!! -> LocationManager.NETWORK_PROVIDER
            providerList?.contains(LocationManager.GPS_PROVIDER)!! -> LocationManager.GPS_PROVIDER
            else -> {
                // 当没有可用的位置提供器时，弹出Toast提示用户
                ToastUtil.showShortToast("Please Open Your GPS or Location Service")
                mCheckReqBody.location = 1
                return
            }
        }

        var location = locationManager?.getLastKnownLocation(provider)
        if(location!=null){
            //显示当前设备的位置信息
            mCheckReqBody.location = 0
            return
        }
        locationManager?.requestLocationUpdates(provider, 1000, 1f, locationListener)

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
                    if (fingerprintManager == null || !(fingerprintManager as FingerprintManager).isHardwareDetected()) {
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

        mCheckReqBody.multiTouch = if (CameraUtils.isSupportMultiTouch(applicationContext)) {0} else {1}

        val localVibrator = BaseApplication.getAppContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        mCheckReqBody.vibrator = if (localVibrator != null && localVibrator.hasVibrator()) {0} else {1}

        val hasFlash = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        if (!hasFlash) mCheckReqBody.flashlight = 1
    }

    private fun doCameraTest() {
        var ret = CameraUtils.HasBackCamera() !=2 || CameraUtils.HasFrontCamera() != 2
        mCheckReqBody.camera = if (ret) {0} else {1}
    }

    override fun onDestroy() {
        locationManager?.removeUpdates(locationListener)
        mSensorManager?.unregisterListener(this)
        super.onDestroy()
    }

    fun setCheckValue(config_container: LinearLayout): Boolean {
        if (config_container.childCount>0) {
            for (i in 0 until config_container.childCount) {
                var v = config_container.getChildAt(i)
                if (v is SetItemLayout) {
                    if (v.tag == null) {
                        ToastUtil.showShortToastCenter("必须全部选择后才能继续")
                        return false
                    }
                    var bean = v.tag as ConfigPriceRep.DataBean.ConfigPriceSystemVOsBean.ChildsBeanX
//                    var bean = v.tag as ConfigPriceTempRep.ConfigPriceSystemVOsBean.ChildsBean
                    when (bean.code) {
                        "1" -> mCheckReqBody.regional=bean.id
                        "2" -> mCheckReqBody.memory=bean.id
                        "3" -> mCheckReqBody.capacity=bean.id
                        "4" -> mCheckReqBody.wirelessNetwork=bean.id
                        "5" -> mCheckReqBody.colour=bean.id
                        "6" -> mCheckReqBody.warranty=bean.id
                        "7" -> mCheckReqBody.facade=bean.id
                        "8" -> mCheckReqBody.screenProblem = bean.id
                        "9" -> mCheckReqBody.water=bean.id
                        "10" -> mCheckReqBody.overhaul = bean.id
                        "11" -> mCheckReqBody.lockAccount=bean.id
                        "12" -> mCheckReqBody.startingState=bean.id

                        "13" -> mCheckReqBody.bluetooth=bean.id.toInt()
                        "14" -> mCheckReqBody.call=bean.id.toInt()
                        "15" -> mCheckReqBody.camera=bean.id.toInt()
                        "16" -> mCheckReqBody.compass=bean.id.toInt()
                        "17" -> mCheckReqBody.comprehensionAids=bean.id.toInt()
                        "18" -> mCheckReqBody.fingerprint=bean.id.toInt()
                        "19" -> mCheckReqBody.flashlight=bean.id.toInt()
                        "20" -> mCheckReqBody.gravitySensor=bean.id.toInt()
                        "21" -> mCheckReqBody.lightSensor=bean.id.toInt()
                        "22" -> mCheckReqBody.location=bean.id.toInt()
                        "23" -> mCheckReqBody.loudspeaker=bean.id.toInt()
                        "24" -> mCheckReqBody.microphone=bean.id.toInt()
                        "25" -> mCheckReqBody.multiTouch=bean.id.toInt()
                        "26" -> mCheckReqBody.proximitySenso=bean.id.toInt()
                        "27" -> mCheckReqBody.screen=bean.id.toInt()
                        "28" -> mCheckReqBody.spiritLevel=bean.id.toInt()
                        "29" -> mCheckReqBody.vibrator=bean.id.toInt()
                        "30" -> mCheckReqBody.wifi=bean.id.toInt()
                    }
                }
            }
            return true
        }
        return false
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
