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
import android.view.View
import com.snail.antifake.deviceid.deviceid.DeviceIdUtil
import com.snail.antifake.deviceid.macaddress.MacAddressUtils
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.AllParamsActivity
import com.yc.phonerecycle.activity.ChoosePhoneActivity
import com.yc.phonerecycle.activity.HandCheckActivity
import com.yc.phonerecycle.activity.SignUpActivity
import com.yc.phonerecycle.mvp.presenter.biz.EmptyPresenter
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
//        phone_storage.text = StorageVolume.EXTRA_STORAGE_VOLUME
        pingpai_value.text = Build.BRAND
        model_value.text = Build.MODEL
        val macAddress = android.provider.Settings.Secure.getString(context?.getContentResolver(), "bluetooth_address")
        bluetooth_mac.text = macAddress
        val wifiMacAddress=MacUtils.getWifiMacAddress()
        wifi_mac.text = MacAddressUtils.getMacAddress(context)
        os_version.text = Build.VERSION.RELEASE

        PermissionUtils.checkPhoneStatePermission(activity as Context, object : PermissionUtils.Callback() {
            override fun onGranted() {
                imei_value.text = DeviceIdUtil.getDeviceId(context)
            }

            override fun onRationale() {
                ToastUtil.showShortToast("请开启电话权限才能正常使用")
            }

            override fun onDenied(context: Context) {
                showPermissionDialog()
            }
        })


        hand_check.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    activity, ChoosePhoneActivity::class.java)
            }
        })
        to_detail.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityToActivity.toActivity(
                    activity, AllParamsActivity::class.java)
            }
        })
    }

    internal fun showPermissionDialog() {
        if (cameraDialog == null) {
            cameraDialog = DialogHelper.showDialog(
                "1",
                activity as Context,
                null,
                "",
                "",
                getString(R.string.open_camera_rights),
                "你还没有开启电话权限，开启之后才可读取手机信息",
                getString(R.string.cancel),
                getString(R.string.setting),
                "",
                "",
                object : BaseDialog.IClickListener {
                    override fun click(dialog: Dialog) {
                    }
                },
                object : BaseDialog.IClickListener {
                    override fun click(dialog: Dialog) {
                        PermissionUtils.openPermissionSettings(activity as Context)
                    }
                }
            )
            cameraDialog?.setCanceledOnTouchOutside(false)
            cameraDialog?.setOnCancelListener(DialogInterface.OnCancelListener {
            })
        } else if (!cameraDialog?.isShowing()!!) {
            cameraDialog?.show()
        }
    }
}