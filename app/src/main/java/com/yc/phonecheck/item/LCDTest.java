package com.yc.phonecheck.item;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import com.yc.phonerecycle.R;
import com.yc.phonerecycle.activity.AutoCheckActivity;

public class LCDTest extends BaseTest {

    private static final int[] COLORS = {
        R.drawable.bg_screen_one,
            R.drawable.bg_screen_two,
            R.drawable.bg_screen_three,
            R.drawable.bg_screen_four,
            R.drawable.bg_screen_five,
    };

    private int mColorIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_item, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(0,200);
    }

    @Override
    public void onHandleMessage(int i) {
        View vc = getView().findViewById(R.id.lcd_color);
        View vt = getView().findViewById(R.id.lcd_text);

        int index = mColorIndex++ % (COLORS.length + 1);

        if (index == 0) {
            mHandler.sendEmptyMessageDelayed(0,2000);
//            setButtonVisibility(false);
            vc.setVisibility(View.VISIBLE);
            vc.setBackgroundResource(COLORS[index]);
            vt.setVisibility(View.GONE);
        } else if (index == COLORS.length) {
//            setButtonVisibility(true);
            vc.setVisibility(View.GONE);
            View bg = getView().findViewById(R.id.screen_container);
            bg.setVisibility(View.VISIBLE);
            final CheckedTextView shadow = getView().findViewById(R.id.shadow);
            final CheckedTextView broken = getView().findViewById(R.id.broken);
            final CheckedTextView light = getView().findViewById(R.id.light);
            shadow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shadow.toggle();
                    if (shadow.isChecked()) {
                        ((AutoCheckActivity)getActivity()).checkResult.screenProblem = "1105";
                    }
                }
            });
            broken.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    broken.toggle();
                    if (broken.isChecked()) {
                        ((AutoCheckActivity)getActivity()).checkResult.screenProblem = "1106";
                    }
                }
            });
            light.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    light.toggle();
                    if (light.isChecked()) {
                        ((AutoCheckActivity)getActivity()).checkResult.screenProblem = "1104";
                    }
                }
            });
            getView().findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AutoCheckActivity)getActivity()).doCallTest();
                }
            });
        } else {
            mHandler.sendEmptyMessageDelayed(0,2000);
            vc.setBackgroundResource(COLORS[index]);
        }

    }

//    @Override
//    public boolean onSingleTapConfirmed(MotionEvent e) {
//        View vc = getView().findViewById(R.id.lcd_color);
//        View vt = getView().findViewById(R.id.lcd_text);
//
//        int index = mColorIndex++ % (COLORS.length + 1);
//
//        if (index == 0) {
//            setButtonVisibility(false);
//            vc.setVisibility(View.VISIBLE);
//            vc.setBackgroundResource(COLORS[index]);
//            vt.setVisibility(View.GONE);
//        } else if (index == COLORS.length) {
//            setButtonVisibility(true);
//            vc.setVisibility(View.GONE);
//            vt.setVisibility(View.VISIBLE);
//        } else {
//            vc.setBackgroundResource(COLORS[index]);
//        }
//        return true;
//    }

    @Override
    public String getTestName() {
        return getContext().getString(R.string.lcd_title);
    }

    @Override
    public boolean isNeedTest() {
        return hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)
                && getSystemProperties("lcd", true);
    }
}
