package com.yc.phonecheck.item;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yc.phonecheck.views.BorderTouchView;
import com.yc.phonecheck.views.OnTouchChangedListener;
import com.yc.phonerecycle.R;


public class TouchTest extends BaseTest implements OnTouchChangedListener {

    private static final String TAG = "TouchTest";
    private static final int MSG_BORDER_TOUCH = 1;
    private static final int MSG_CENTER_TOUCH = 2;
    private static final int MSG_END          = 3;

    private BorderTouchView mBorderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.touch, container, false);
        mBorderView = (BorderTouchView) v.findViewById(R.id.touch_border);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTimerTask(MSG_BORDER_TOUCH, 0);
    }

    @Override
    public void onHandleMessage(final int index) {
        switch (index) {
        case MSG_BORDER_TOUCH:
            Log.i(TAG, "MSG_BORDER_TOUCH");
            mBorderView.setOnTouchChangedListener(this);
            mBorderView.setVisibility(View.VISIBLE);
            break;
        case MSG_CENTER_TOUCH:
            Log.i(TAG, "MSG_CENTER_TOUCH");
            mBorderView.setVisibility(View.GONE);
            break;
        case MSG_END:
            Log.i(TAG, "MSG_END");
            clickPassButton();
            break;
        }
    }

    @Override
    public void onTouchFinish(View v) {
        if (v == mBorderView) {
            setTimerTask(MSG_END, 1000);
        }
    }

    @Override
    public String getTestName() {
        return getContext().getString(R.string.touch_title);
    }

    @Override
    public boolean isNeedTest() {
        return getSystemProperties("touch", true);
    }
}
