package com.yc.phonecheck.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yc.phonerecycle.R;

public class DistancdSensorTest extends BaseTest implements SensorEventListener {

    private static final int MSG_CHECK  = 1;
    private static final int MSG_REMOVE = 2;
    private static final float MIN_VALUE     = 3f;
    private static final float MAX_VALUE     = 10f;
    private static final float COMPARE_VALUE = 6f;
    private static final String TAG = "ProxSensorTest";
    private SensorManager mSensorManager;
    private Sensor mSensor;

    private int mSignalValue = 0;

    private boolean mCovered;
    private PowerManager.WakeLock mWakeLock;
    private PowerManager mPowerManager;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        setTimerTask(MSG_CHECK, 6000);
        mCovered = false;

        //息屏设置
        mPowerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        if (mPowerManager != null) {
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,
                    TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.proxsensor, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        //释放息屏
        if (mWakeLock.isHeld())
            mWakeLock.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWakeLock = null;
        mPowerManager = null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not to do!
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
//            mSignalValue = (int) event.values[0];
//            setSignalText(mSignalValue);
//            onDistanceChanged(event.values[0]);
//        }
        if (event.values[0] == 0.0) {
            //关闭屏幕
            if (!mWakeLock.isHeld())
                mWakeLock.acquire();

        } else {
            //唤醒设备
            if (mWakeLock.isHeld())
                mWakeLock.release();
        }
    }

    @Override
    public void onHandleMessage(final int index) {
        if (index == MSG_CHECK && mSignalValue <= 0) {
            clickFailButton();
        } else if (index == MSG_REMOVE && mCovered) {
            clickPassButton();
        }
    }

    @Override
    public String getTestName() {
        return getContext().getString(R.string.proxsensor_title);
    }

    @Override
    public boolean isNeedTest() {
        PackageManager pm = getContext().getPackageManager();
        boolean hasProxSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY);
        return hasProxSensor && getSystemProperties("proxsensor", true);
    }

//    private void onDistanceChanged(float distance) {
//        if (distance < MIN_VALUE) {
//            distance = MIN_VALUE;
//        } else if (distance > MAX_VALUE) {
//            distance = MAX_VALUE;
//        }
//
//        if (distance <= COMPARE_VALUE) {
//            setStepText(R.string.proxsensor_remove);
//            mCovered = true;
//        } else {
//            setStepText(R.string.proxsensor_cover);
//            setTimerTask(MSG_REMOVE, 300);
//        }
//    }

//    private void setStepText(int resId) {
//        ((TextView) getView().findViewById(R.id.proxsensor_text)).setText(resId);
//    }
//
//    private void setSignalText(int value) {
//        TextView v = (TextView) getView().findViewById(R.id.proxsensor_signal);
//        v.setText(getString(R.string.proxsensor_signal, value));
//    }
}
