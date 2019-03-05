package com.yc.phonerecycle.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yancongxian on 2017/5/20.
 */

public class DialogHelper {
    /**
     * 显示dialog,type = 1时,resId = -1
     *
     * @param type
     * @param context
     * @param url
     * @param title
     * @param msg
     * @param leftBtnStr
     * @param rightBtnStr
     * @param okListener
     * @param cancelListener
     */
    public static Dialog showDialog(String type, final Context context, final String url, String width, String height, String title, String msg, String leftBtnStr, String rightBtnStr, String leftBtnColor, String rightBtnColor, final BaseDialog.IClickListener okListener, final BaseDialog.IClickListener cancelListener) {
        Dialog dialog = null;
        switch (type) {
            case "1":
                dialog = showTextDialog(context, title, msg, leftBtnStr, rightBtnStr, leftBtnColor, rightBtnColor, okListener, cancelListener);
                break;
//            case "2":
//                dialog = showCenterImgDialog(context, R.drawable.dialog_icon_succ, url, title, msg, leftBtnStr, rightBtnStr, leftBtnColor, rightBtnColor, okListener, cancelListener);
//                break;
//            case "3":
//                dialog = showFillImgDialog(context, R.drawable.cash_refund_bg, url, width, height,title, msg, leftBtnStr, rightBtnStr, leftBtnColor, rightBtnColor, okListener, cancelListener);
//                break;
        }
        return dialog;
    }

    /**
     * type=1 文本Dialog
     *
     * @param mContext
     * @param title
     * @param msg
     * @param leftBtnStr
     * @param rightBtnStr
     * @param leftBtnListener
     * @param rightBtnListener
     */
    public static Dialog showTextDialog(final Context mContext, String title, String msg, String leftBtnStr, String rightBtnStr, String leftBtnColor, String rightBtnColor, final BaseDialog.IClickListener leftBtnListener, final BaseDialog.IClickListener rightBtnListener) {
        if (!TextUtils.isEmpty(leftBtnColor)) {
            leftBtnColor = "#" + leftBtnColor;
        }
        if (!TextUtils.isEmpty(rightBtnColor)) {
            rightBtnColor = "#" + rightBtnColor;
        }
        final BaseDialog.Builder dialogBuilder = new BaseDialog.Builder(mContext).setTitle(TextUtils.isEmpty(title) ? "" : title).setMessage(TextUtils.isEmpty(msg) ? "" : msg);
        if (!TextUtils.isEmpty(leftBtnStr)) {
            dialogBuilder.setLeftButtonStr(leftBtnStr);
            if (leftBtnListener != null) dialogBuilder.setLeftClickListener(leftBtnListener);
        }
        if (!TextUtils.isEmpty(rightBtnStr)) {

            dialogBuilder.setRightButtonStr(rightBtnStr);
            if (rightBtnListener != null) dialogBuilder.setRightClickListener(rightBtnListener);
        }
        dialogBuilder.setLeftButtonColor(leftBtnColor);
        dialogBuilder.setRightButtonColor(rightBtnColor);
        final BaseDialog dialog = dialogBuilder.create();
        dialog.show();
        return dialog;
    }

}
