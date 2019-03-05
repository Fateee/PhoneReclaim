package com.yc.phonerecycle.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import com.yc.phonerecycle.app.BaseApplication;

/**
 * 做尺寸转换的工具类。在dp/sp/px之间自由转换
 */
public class DensityUtil {

    static Context context = BaseApplication.getAppContext();

    public static int dip2px(float dipValue) {
        float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int dip2px(float dipValue, float toscale) {
        float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) ((dipValue * scale + 0.5f) * toscale);
    }

    public static int px2dip(float pxValue) {
        float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(float spValue) {
        float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    public static int getWidth() {
        initScreen();
        if (screen != null) {
            return screen.widthPixels;
        }
        return 0;
    }

    public static int getHeight() {
        initScreen();
        if (screen != null) {
            return screen.heightPixels;
        }
        return 0;
    }
    public static int getNavigationBarHeight() {
        int result = 0;
        try {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    static void initScreen() {
        if (screen == null) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);
            if (dm.widthPixels > dm.heightPixels) {
                screen = new Screen(dm.heightPixels, dm.widthPixels);
            } else {
                screen = new Screen(dm.widthPixels, dm.heightPixels);
            }
        }
    }

    public static int getStatusHeight() {
        if (STATUS_HEIGHT <= 0) {
            try {
                int resourceId = context.getResources()
                        .getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    STATUS_HEIGHT = context.getResources()
                            .getDimensionPixelSize(resourceId);
                }
            } catch (Exception ex) {
            }
        }
        return STATUS_HEIGHT;
    }

    static Screen screen = null;
    static int STATUS_HEIGHT = 0;

    public static class Screen {
        public int widthPixels;
        public int heightPixels;

        public Screen() {
        }

        public Screen(int widthPixels, int heightPixels) {
            this.widthPixels = widthPixels;
            this.heightPixels = heightPixels;
        }

        @NonNull
        @Override
        public String toString() {
            return "(" + widthPixels + "," + heightPixels + ")";
        }

    }
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getRealHeight(final Window window){//去掉状态栏和虚拟按键的高度
        Rect frame = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(frame);
        return px2dip(frame.bottom - frame.top);
    }
}
