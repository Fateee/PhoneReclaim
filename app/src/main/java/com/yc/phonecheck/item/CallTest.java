package com.yc.phonecheck.item;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.yc.phonerecycle.R;


public class CallTest extends BaseTest implements View.OnClickListener {

    private static final int REQUEST_CALL = 2;
    private View refuseTV;
    private View grantTV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.call, container, false);
        refuseTV = v.findViewById(R.id.refuse);
        grantTV = v.findViewById(R.id.grant);
        refuseTV.setOnClickListener(this);
        grantTV.setOnClickListener(this);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        setButtonVisibility(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    @Override
    public String getTestName() {
        return getContext().getString(R.string.call_title);
    }

    @Override
    public boolean isNeedTest() {
        return true;
    }

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
        startActivityForResult(intent, REQUEST_CALL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refuse:

                break;
            case R.id.grant:
                callPhone("10086");
                break;
        }
    }
}
