package com.yc.phonerecycle.activity

import android.graphics.BitmapFactory
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.activity.adapter.MenulistAdapter
import com.yc.phonerecycle.model.bean.biz.BankCardListRep
import com.yc.phonerecycle.model.bean.biz.NearByShopRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.ActivityToActivity
import kotlinx.android.synthetic.main.common_mainlist.*
import kotlinx.android.synthetic.main.titleview.*
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClientOption
import android.icu.util.ULocale.getCountry
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.WindowManager
import android.widget.AdapterView
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.yc.phonerecycle.utils.MapUtil
import com.yc.phonerecycle.utils.ToastUtil
import com.yc.phonerecycle.widget.ListDialog
import kotlinx.android.synthetic.main.activity_shop_in_map.*
import java.text.SimpleDateFormat
import java.util.*




class ShopInMapActivity : BaseActivity<CommonPresenter>(){

    private var listDialog: ListDialog? = null

    //声明AMapLocationClient类对象
    var mLocationClient: AMapLocationClient? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private lateinit var mShopBean: NearByShopRep.DataBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapview.onCreate(savedInstanceState);
        var pos = mShopBean.longitudeLatitude.split("@")
        if (pos.size>1) {
            longitude = pos[0].toDouble()
            latitude = pos[1].toDouble()
        }
        //初始化定位
        var latLng = LatLng(latitude,longitude);
        var markerOptions = MarkerOptions().position(latLng).title(mShopBean.name).snippet(mShopBean.address).icon(
            BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.drawable.ic_locate_point)))
        var marker = mapview.map.addMarker(markerOptions);
        marker.showInfoWindow()
        mapview.map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))

        var myLocationStyle = MyLocationStyle()//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true)
        mapview.map.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //mapview.map.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mapview.map.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        shop_map_name.text = getString(R.string.shop_name_inmap,mShopBean.name)
        shop_map_addr.text = getString(R.string.shop_addr,mShopBean.address)
        ic_guide.visibility=View.VISIBLE
        ic_guide.setOnClickListener { handleWithIconClick() }
        click_for_more.setOnClickListener { finish() }
    }


    override fun createPresenter() = CommonPresenter()

    override fun initBundle() {
        mShopBean = intent.getSerializableExtra("shopbean") as NearByShopRep.DataBean
    }

    override fun getContentView(): Int = R.layout.activity_shop_in_map

    override fun initView() {
    }

    override fun initDatas() {

    }

    override fun onResume() {
        super.onResume()
        mapview.onResume();
    }

    override fun onPause() {
        super.onPause()
        mapview.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationClient?.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient?.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        mapview.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapview.onSaveInstanceState(outState)
    }

    fun handleWithIconClick() {
        if (listDialog == null) {
            val itmes = arrayOf<String>(
                "高德地图",
                "百度地图",
                "腾讯地图",
                "取消"
            )
            listDialog = ListDialog(this@ShopInMapActivity, Arrays.asList(*itmes), "",
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    when (position) {
                        0 -> {
                            if (MapUtil.isGdMapInstalled()) {
                                MapUtil.openGaoDeNavi(this@ShopInMapActivity, 0.0, 0.0, null, latitude, longitude, mShopBean.address);
                            } else {
                                //这里必须要写逻辑，不然如果手机没安装该应用，程序会闪退，这里可以实现下载安装该地图应用
                                ToastUtil.showShortToast("尚未安装高德地图")
                            }

                        }
                        1 -> {
                            if (MapUtil.isBaiduMapInstalled()) {
                                MapUtil.openBaiDuNavi(this@ShopInMapActivity, 0.0, 0.0, null, latitude, longitude, mShopBean.address);
                            } else {
                                //这里必须要写逻辑，不然如果手机没安装该应用，程序会闪退，这里可以实现下载安装该地图应用
                                ToastUtil.showShortToast("尚未安装百度地图")
                            }
                        }
                        2 -> {
                            if (MapUtil.isTencentMapInstalled()) {
                                MapUtil.openTencentMap(this@ShopInMapActivity, 0.0, 0.0, null, latitude, longitude, mShopBean.address);
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

}
