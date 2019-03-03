package com.yc.phonerecycle.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.yc.phonerecycle.R;


import java.util.List;

/**
 *describe: 支付密码的弹窗模式
 *author: Went_Gone
 *create on: 2016/10/24
 */
public class PasswordDialog extends AlertDialog implements View.OnClickListener{
    private String[] numbers = {"1","2","3","4","5","6","7","8","9",null,"0",null};
    private TextView tvSure;
    private ImageView tvCancle;
    private Context context;
    private PasswordInputView mPasswordInputView;

    public PasswordDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_password_layout);
        initViews();
        setListener();

        setShowStyle();
    }

    private void initViews() {
        mPasswordInputView = (PasswordInputView) findViewById(R.id.input_view);
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
                String pwd = mPasswordInputView.getText().toString();
                if (TextUtils.isEmpty(pwd) || pwd.length() < 6) return;
                if (viewClickListener!=null){
                    viewClickListener.click(pwd);
                }
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
        void click(String pwd);
    }
}
