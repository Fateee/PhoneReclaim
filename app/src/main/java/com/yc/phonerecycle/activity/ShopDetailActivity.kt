package com.yc.phonerecycle.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.biz.NearByShopRep
import com.yc.phonerecycle.model.bean.biz.ShopDetailRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.MapUtil
import com.yc.phonerecycle.utils.PermissionUtils
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.widget.ListDialog
import kotlinx.android.synthetic.main.activity_shop_info_near.*
import kotlinx.android.synthetic.main.activity_shop_info_shopper.*
import java.util.*


class ShopDetailActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.CommonIV{
    private var listDialog: ListDialog? = null

    //声明AMapLocationClient类对象
//    var mLocationClient: AMapLocationClient? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var address: String? = ""
    override fun getDataOK(ret: Any?) {
        if (ret is ShopDetailRep) {
            if (ret?.code == 0) {
                var rep = ret?.data

                when (type) {
                    "1"-> {
                        Glide.with(this@ShopDetailActivity).load(rep.mainImage).apply(RequestOptions.bitmapTransform(CenterCrop()).placeholder(R.drawable.ic_shop_avatar)).into(shop_avatar)
                        shop_info_name.text = getString(R.string.shop_name,rep.name)
                        shop_info_addr.text = getString(R.string.shop_addr,rep.address)

                        shop_open_time.title = getString(R.string.shop_open_time,rep.businessHours)
                        var phoneNumber : String?
                        if (!TextUtils.isEmpty(rep.fixedLine)) {
                            shop_tele.title = getString(R.string.shop_tele, rep.fixedLine)
                            phoneNumber = rep.fixedLine
                        } else {
                            shop_tele.title = getString(R.string.shop_tele, rep.phone)
                            phoneNumber = rep.phone
                        }
                        shop_tele.setOnClickListener(object : View.OnClickListener{
                            override fun onClick(v: View?) {
                                callPhone(phoneNumber)
                            }
                        })
                        deal_num.title = getString(R.string.deal_num,rep.fixtureNumber.toString())
                        shop_addr.title = getString(R.string.shop_addr,rep.address)
                        address = rep.address
                        var pos = rep.longitudeLatitude.split("@")
                        if (pos.size>1) {
                            longitude = pos[0].toDouble()
                            latitude = pos[1].toDouble()
                        }
                        shop_addr.setOnClickListener(object : View.OnClickListener{
                            override fun onClick(v: View?) {
                                handleWithIconClick()
                            }
                        })
                    }
                    "4" -> {
                        Glide.with(this@ShopDetailActivity).load(rep.mainImage).into(shopper_avatar)
                        shop_info_name_shopper.text = getString(R.string.shop_name,rep.name)
                        shop_info_addr_shopper.text = getString(R.string.shop_addr,rep.address)

                        shopper_count.title = getString(R.string.shopper_count,rep.adminUser)
                        admin_count.title = getString(R.string.admin_count,rep.adminUser)
                    }
                    else -> {
                        shop_info_name.text = getString(R.string.shop_name,rep.name)
                        shop_info_addr.text = getString(R.string.shop_addr,rep.address)

                        shop_open_time.title = getString(R.string.shop_open_time,rep.businessHours)
                        if (!TextUtils.isEmpty(rep.fixedLine))
                            shop_tele.title =  getString(R.string.shop_tele,rep.fixedLine)
                        else
                            shop_tele.title =  getString(R.string.shop_tele,rep.phone)
                        deal_num.title = getString(R.string.deal_num,rep.fixtureNumber.toString())
                        shop_addr.title = getString(R.string.deal_num,rep.address)
                    }
                }
            }
        }
    }

    override fun createPresenter() = CommonPresenter()


    private var mId: String? = null
    private var type: String? = "1"
    override fun initBundle() {
        mId = intent.getStringExtra("id")
        type = intent.getStringExtra("type")
    }

    override fun getContentView(): Int {
        return when (type) {
            "1" -> {
                R.layout.activity_shop_info_near
            }
            "4" -> {
                R.layout.activity_shop_info_shopper
            }
            else -> {
                R.layout.activity_shop_info_near
            }
        }
    }

    override fun initView() {
    }

    override fun initDatas() {
    }

    override fun onResume() {
        super.onResume()
        when (type) {
            "1" -> {
                presenter.getStoreDetail(mId)
            }
            "4" -> {
                presenter.getMyStore()
            }
            else -> {
            }
        }
    }

    fun handleWithIconClick() {
        if (listDialog == null) {
            val itmes = arrayOf<String>(
                "高德地图",
                "百度地图",
                "腾讯地图",
                "取消"
            )
            listDialog = ListDialog(this@ShopDetailActivity, Arrays.asList(*itmes), "",
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    when (position) {
                        0 -> {
                            if (MapUtil.isGdMapInstalled()) {
                                MapUtil.openGaoDeNavi(this@ShopDetailActivity, 0.0, 0.0, null, latitude, longitude, address);
                            } else {
                                //这里必须要写逻辑，不然如果手机没安装该应用，程序会闪退，这里可以实现下载安装该地图应用
                                ToastUtil.showShortToast("尚未安装高德地图")
                            }

                        }
                        1 -> {
                            if (MapUtil.isBaiduMapInstalled()) {
                                MapUtil.openBaiDuNavi(this@ShopDetailActivity, 0.0, 0.0, null, latitude, longitude, address);
                            } else {
                                //这里必须要写逻辑，不然如果手机没安装该应用，程序会闪退，这里可以实现下载安装该地图应用
                                ToastUtil.showShortToast("尚未安装百度地图")
                            }
                        }
                        2 -> {
                            if (MapUtil.isTencentMapInstalled()) {
                                MapUtil.openTencentMap(this@ShopDetailActivity, 0.0, 0.0, null, latitude, longitude, address);
                            } else {
                                //这里必须要写逻辑，不然如果手机没安装该应用，程序会闪退，这里可以实现下载安装该地图应用
                                ToastUtil.showShortToast("尚未安装腾讯地图")
                            }
                        }
                    }
                    if (listDialog != null) listDialog!!.dismiss()
                })
            val window = listDialog!!.getWindow()
            val lp = WindowManager.LayoutParams()
            lp.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            window.setAttributes(lp)
            listDialog!!.setCanceledOnTouchOutside(true)
        }
        if (!listDialog!!.isShowing()) listDialog!!.show()
    }

    private val REQUEST_CALL = 2

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    fun callPhone(phoneNum: String) {
        PermissionUtils.checkCallPermission(this@ShopDetailActivity,object : PermissionUtils.Callback() {
            override fun onGranted() {
                val intent = Intent(Intent.ACTION_CALL)
                val data = Uri.parse("tel:$phoneNum")
                intent.data = data
                startActivity(intent)
            }

            override fun onRationale() {
                ToastUtil.showShortToast("请开启打电话权限才能正常使用")
            }

            override fun onDenied(context: Context) {
                showPermissionDialog("开启打电话权限","你还没有开启打电话权限，开启之后才可打电话")
            }
        })
    }
}
