package com.yc.phonerecycle.activity

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
import java.text.SimpleDateFormat
import java.util.*


class MyNearShopActivity : BaseActivity<CommonPresenter>(),CommonBaseIV.CommonIV{

    //声明AMapLocationClient类对象
    var mLocationClient: AMapLocationClient? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    //声明定位回调监听器
    var mLocationListener = object : AMapLocationListener{
        override fun onLocationChanged(amapLocation: AMapLocation?) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。
                    amapLocation.locationType//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    latitude = amapLocation.latitude//获取纬度
                    longitude = amapLocation.longitude//获取经度
                    amapLocation.accuracy//获取精度信息
                    amapLocation.address//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.country//国家信息
                    amapLocation.province//省信息
                    amapLocation.city//城市信息
                    amapLocation.district//城区信息
                    amapLocation.street//街道信息
                    amapLocation.streetNum//街道门牌号信息
                    amapLocation.cityCode//城市编码
                    amapLocation.adCode//地区编码
                    amapLocation.aoiName//获取当前定位点的AOI信息
                    amapLocation.buildingId//获取当前室内定位的建筑物Id
                    amapLocation.floor//获取当前室内定位的楼层
                    amapLocation.gpsAccuracyStatus//获取GPS的当前状态
//获取定位时间
                    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val date = Date(amapLocation.time)
                    df.format(date)
                    requestShops()
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    }

    private val mReefreshListener: SwipeRefreshLayout.OnRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
        }
    }

    private val mLoadMoreListener = object : SwipeMenuRecyclerView.LoadMoreListener {
        override fun onLoadMore() {
            // 该加载更多啦。
            // 如果加载失败调用下面的方法，传入errorCode和errorMessage。
            // errorCode随便传，你自定义LoadMoreView时可以根据errorCode判断错误类型。
            // errorMessage是会显示到loadMoreView上的，用户可以看到。
            // mRecyclerView.loadMoreError(0, "请求网络失败");
        }
    }

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {

    }

    override fun getContentView(): Int = R.layout.common_mainlist

    override fun initView() {
        //初始化定位
        mLocationClient = AMapLocationClient(applicationContext);
        //初始化AMapLocationClientOption对象
        var mLocationOption = AMapLocationClientOption()
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //设置定位回调监听
        mLocationClient?.setLocationListener(mLocationListener);
        //给定位客户端对象设置定位参数
        mLocationClient?.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient?.startLocation();
    }

    private lateinit var mMenulistAdapter: MenulistAdapter

    override fun initDatas() {
        txt_right_title.text = ""
        txt_main_title.text = "附近商家"
        swipe_refresh_list.setOnRefreshListener(mReefreshListener)
        rv_list.useDefaultLoadMore()
        rv_list.setLoadMoreListener(mLoadMoreListener)
        val mGridLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_list.layoutManager = mGridLayoutManager
        mMenulistAdapter = MenulistAdapter(this)
        rv_list.adapter = mMenulistAdapter

    }

    override fun onResume() {
        super.onResume()
        requestShops()
    }

    private fun requestShops() {
        if (longitude == 0.0 && latitude == 0.0) return
        presenter.getNearbyShop(longitude.toString(),latitude.toString())
    }

    override fun getDataOK(rep: Any?) {
        if (rep is NearByShopRep) {
            mMenulistAdapter.refreshUI(rep.data,true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationClient?.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient?.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

}
