package com.yc.phonerecycle.activity.fragment


import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.storage.StorageVolume
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import com.snail.antifake.deviceid.deviceid.DeviceIdUtil
import com.snail.antifake.deviceid.macaddress.MacAddressUtils
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.*
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.mvp.view.BaseFragment
import com.yc.phonerecycle.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_main_home.*
import java.io.File
import java.net.NetworkInterface


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseFragment<EmptyPresenter>() {

    override fun createPresenter(): EmptyPresenter? = null

    override fun getContentView(): Int = R.layout.fragment_main_home

    private var cameraDialog: Dialog? = null

    override fun initViews(view: View?) {
    }

    override fun initData() {
        phone_model.text = Build.MODEL
        phone_storage.text = DeviceUtil.getTotalRomSize()
        pingpai_value.text = Build.BRAND
        model_value.text = Build.MODEL
        storage_value.text = DeviceUtil.getTotalRamSize()+"+"+DeviceUtil.getTotalRomSize()

        val macAddress = android.provider.Settings.Secure.getString(context?.getContentResolver(), "bluetooth_address")
        bluetooth_mac.text = macAddress
        wifi_mac.text = MacAddressUtils.getMacAddress(context)
        os_version.text = Build.VERSION.RELEASE
        Log.e("wifi",DeviceUtil.isWifiAvailable().toString()+" wifi "+DeviceUtil.isWifiConect())
//        Log.e("ble",DeviceUtil.isBleAvailable().toString()+" wifi "+DeviceUtil.isBleConect())
        PermissionUtils.checkPhoneStatePermission(activity as Context, object : PermissionUtils.Callback() {
            override fun onGranted() {
                imei_value.text = DeviceIdUtil.getDeviceId(context)
            }

            override fun onRationale() {
                ToastUtil.showShortToast("请开启电话权限才能正常使用")
            }

            override fun onDenied(context: Context) {
                (activity as BaseActivity<*>).showPermissionDialog("开启电话权限","你还没有开启电话权限，开启之后才可读取手机信息")
            }
        })


        hand_check.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var map = HashMap<String,String?>()
                map["checktype"] = "0"
                ActivityToActivity.toActivity(
                    activity, ChoosePhoneActivity::class.java,map)
            }
        })
        auto_check.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var map = HashMap<String,String?>()
                map["checktype"] = "1"
                ActivityToActivity.toActivity(
                    activity, ChoosePhoneActivity::class.java,map)
//                ActivityToActivity.toActivity(
//                    activity, AutoCheckActivity::class.java)
            }
        })
        to_detail.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    activity, AllParamsActivity::class.java)
            }
        })
    }

//    internal fun showPermissionDialog() {
//        if (cameraDialog == null) {
//            cameraDialog = DialogHelper.showDialog(
//                "1",
//                activity as Context,
//                null,
//                "",
//                "",
//                getString(R.string.open_camera_rights),
//                "你还没有开启电话权限，开启之后才可读取手机信息",
//                getString(R.string.cancel),
//                getString(R.string.setting),
//                "",
//                "",
//                object : BaseDialog.IClickListener {
//                    override fun click(dialog: Dialog) {
//                    }
//                },
//                object : BaseDialog.IClickListener {
//                    override fun click(dialog: Dialog) {
//                        PermissionUtils.openPermissionSettings(activity as Context)
//                    }
//                }
//            )
//            cameraDialog?.setCanceledOnTouchOutside(false)
//            cameraDialog?.setOnCancelListener(DialogInterface.OnCancelListener {
//            })
//        } else if (!cameraDialog?.isShowing()!!) {
//            cameraDialog?.show()
//        }
//    }
}