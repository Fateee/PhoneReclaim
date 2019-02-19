package com.yc.phonerecycle.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.yc.phonerecycle.R;

/**
 * Created by ycx on 2017/11/1.
 */

public class SetItemLayout extends LinearLayout {
    private ImageView mItemIcon;
    private EditText mItemEdit;
    private TextView mSubTitle1;
    private View mDivider;
    private TextView mVerfyCode;

    public interface OnSwitchListener {
        void onSwitch(View v, boolean isOpen);
    }

    TextView mItemName;
    TextView mSubTitle;
    SwitchCompat mItemSwitch;
    ImageView mItemTip;

    public SetItemLayout(Context context) {
        this(context, null);
    }

    public SetItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_setting_item, this);
        initView();

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.setItemLayout, defStyleAttr, 0);

        int iconId = a.getResourceId(R.styleable.setItemLayout_icon,0);
        if (iconId != 0) {
            setIcon(iconId);
        }
        String title = a.getString(R.styleable.setItemLayout_title);
        if (!TextUtils.isEmpty(title)) setTitle(title);

        String hint = a.getString(R.styleable.setItemLayout_edit_hint);
        if (!TextUtils.isEmpty(hint)) setHint(hint);

        String subTitle = a.getString(R.styleable.setItemLayout_sub_title);
        if (!TextUtils.isEmpty(subTitle)) setSubTitle(subTitle);

        String subTitleCode = a.getString(R.styleable.setItemLayout_sub_title_code);
        if (!TextUtils.isEmpty(subTitleCode)) setSubTitleCode(subTitleCode);

        String subTitle1 = a.getString(R.styleable.setItemLayout_sub_title1);
        if (!TextUtils.isEmpty(subTitle1)) setSubTitle1(subTitle1);

        boolean isShowSwitch = a.getBoolean(R.styleable.setItemLayout_is_show_switch, false);
        boolean switchValue = a.getBoolean(R.styleable.setItemLayout_show_switch_value, false);
        if (isShowSwitch) showSwitchButton(switchValue, null);

        boolean isShowTip = a.getBoolean(R.styleable.setItemLayout_is_show_image_tip, false);
        if (isShowTip) showImageItemTip();

        boolean hideDivider = a.getBoolean(R.styleable.setItemLayout_hide_divider, false);
        if (hideDivider) hideDivider();
        a.recycle();
    }

    private void initView() {
//        View view =
        mItemIcon = (ImageView) findViewById(R.id.item_icon);
        mItemName = (TextView) findViewById(R.id.item_name);
        mItemEdit = (EditText) findViewById(R.id.item_edit);

        mSubTitle = (TextView) findViewById(R.id.sub_title);
        mVerfyCode = (TextView) findViewById(R.id.signup_verfy_code);
        mSubTitle1 = (TextView) findViewById(R.id.sub_title1);
        mItemSwitch = (SwitchCompat) findViewById(R.id.item_switch);
        mItemTip = (ImageView) findViewById(R.id.iv_detail_arrow);
        mDivider = findViewById(R.id.divider);
    }

    public void showSwitchButton(boolean isOpen, @Nullable final OnSwitchListener onSwitchListener) {
        mItemSwitch.setVisibility(VISIBLE);
        mItemSwitch.setChecked(isOpen);
        mItemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onSwitchListener != null) {
                    onSwitchListener.onSwitch(mItemSwitch, isChecked);
                }
            }
        });
//        mItemSwitch.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isInClickEvent) {
//                    return;
//                }
//                isInClickEvent = true;
//                SingleContainer.getMainHandler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        isInClickEvent = false;
//                    }
//                }, 200);
//                if (!mItemSwitch.isOpen()) {
//                    mItemSwitch.setOpen();
//                } else {
//                    mItemSwitch.setClose();
//                }
//
//                if (onSwitchListener != null) {
//                    onSwitchListener.onSwitch(mItemSwitch, mItemSwitch.isOpen());
//                }
//            }
//        });

    }

    public void setTitle(String title) {
        mItemName.setText(title);
    }

    public void setSubTitle(String title) {
        mSubTitle.setText(title);
        mSubTitle.setVisibility(VISIBLE);
    }

    public void showImageItemTip() {
        mItemTip.setVisibility(VISIBLE);
    }

    private void setSubTitle1(String subTitle1) {
        mSubTitle1.setText(subTitle1);
        mSubTitle1.setVisibility(VISIBLE);
    }

    private void setHint(String hint) {
        mItemEdit.setVisibility(VISIBLE);
        mItemEdit.setHint(hint);
    }

    private void setIcon(int resid) {
        mItemEdit.setVisibility(VISIBLE);
        mItemIcon.setImageResource(resid);
    }

    private void hideDivider() {
        mDivider.setVisibility(GONE);
    }

    private void setSubTitleCode(String subTitleCode) {
        mVerfyCode.setText(subTitleCode);
        mVerfyCode.setVisibility(VISIBLE);
    }
}
