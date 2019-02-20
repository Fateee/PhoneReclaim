package com.yc.phonerecycle.activity.fragment


import android.os.Build
import android.os.storage.StorageVolume
import android.support.v4.app.Fragment
import android.view.View
import com.snail.antifake.deviceid.deviceid.DeviceIdUtil
import com.snail.antifake.deviceid.macaddress.MacAddressUtils
import com.yc.phonerecycle.R
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.utils.MacUtils
import kotlinx.android.synthetic.main.fragment_main_home.*
import java.net.NetworkInterface


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseFragment<EmptyPresenter>() {

    override fun createPresenter(): EmptyPresenter? = null

    override fun getContentView(): Int = R.layout.fragment_main_home

    override fun initViews(view: View?) {
    }

    override fun initData() {
        phone_model.text = Build.MODEL
//        phone_storage.text = StorageVolume.EXTRA_STORAGE_VOLUME
        pingpai_value.text = Build.BRAND
        model_value.text = Build.MODEL
        val macAddress = android.provider.Settings.Secure.getString(context?.getContentResolver(), "bluetooth_address")
        bluetooth_mac.text = macAddress
        val wifiMacAddress=MacUtils.getWifiMacAddress()
        wifi_mac.text = MacAddressUtils.getMacAddress(context)
        os_version.text = Build.VERSION.RELEASE
        imei_value.text = DeviceIdUtil.getDeviceId(context)
    }


}