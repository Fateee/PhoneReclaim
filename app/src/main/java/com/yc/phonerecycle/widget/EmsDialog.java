package com.yc.phonerecycle.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.yc.phonerecycle.R;

/**
 *describe: 支付密码的弹窗模式
 *author: Went_Gone
 *create on: 2016/10/24
 */
public class EmsDialog extends AlertDialog implements View.OnClickListener{
    private TextView tvSure;
    private ImageView tvCancle;
    private Context context;
    private EditText ems_company_et;
    private EditText ems_num_et;

    public EmsDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ems_layout);
        initViews();
        setListener();

        setShowStyle();
    }

    private void initViews() {
        ems_company_et = (EditText) findViewById(R.id.ems_company_et);
        ems_num_et = (EditText) findViewById(R.id.ems_num_et);
        tvCancle = (ImageView) findViewById(R.id.dialog_password_layout_TV_cancle);
        tvSure = (TextView) findViewById(R.id.dialog_password_layout_TV_sure);
    }

    private void setListener() {
        tvCancle.setOnClickListener(this);
        tvSure.setOnClickListener(this);
    }

    public void setShowStyle(){
        Window window = this.getWindow();
        // 可以在此设置显示动画
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams wl = window.getAttributes();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        wl.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;
        wl.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        // 设置显示位置
        this.onWindowAttributesChanged(wl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            this.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_password_layout_TV_cancle:
                dismiss();
                break;
            case R.id.dialog_password_layout_TV_sure:
                String company = ems_company_et.getText().toString();
                String num = ems_num_et.getText().toString();
                if (TextUtils.isEmpty(company) || TextUtils.isEmpty(num)) return;
                if (viewClickListener!=null){
                    viewClickListener.click(company,num);
                }
                dismiss();
                break;
        }
    }

//    public String getPassword(){
//        String password = null;
//        if (passwordLayout.getStrPassword()!=null && passwordLayout.getStrPassword().length()<=6){
//            password = passwordLayout.getStrPassword();
//        }
//        return password;
//    }

    private ViewClickListener viewClickListener;

    public void setViewClickListener(ViewClickListener viewClickListener) {
        this.viewClickListener = viewClickListener;
    }

    public interface ViewClickListener{
        void click(String company, String num);
    }
}
