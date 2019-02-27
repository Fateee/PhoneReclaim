//package com.yc.phonerecycle.widget;
//
//import android.content.Context;
//import android.graphics.*;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//import com.jiedian.developer.acitivity.screen.RefreshViewCallBack;
//import com.jm.jiediancabinet.baselib.util.DensityUtil;
//import com.jm.jiediancabinet.baselib.util.DeviceUtils;
//import com.jm.jiediancabinet.baselib.util.FileUtils;
//
//import java.io.File;
//
//import static com.jiedian.developer.acitivity.screen.ScreenDetectionActivity.*;
//
///**
// * Created by ycx on 2017/11/2.
// */
//
//public class ScreenDetectionView extends View {
//    public static final int[] COLORS = {Color.WHITE, Color.BLACK, Color.RED, Color.GREEN, Color.BLUE};
//    public static final String[] DEFINATION_IMG_PATHS = {"Screen_Definition_Test_1", "Screen_Definition_Test_2", "Screen_Definition_Test_3"};
//
//    private int currentImgIndex = 0;//当前使用的图片地址索引
//    private int currentColorIndex = 0;//当前使用的颜色测试索引
//
//    private int type = -1;
//    private int width;
//    private int height;
//
//    private int clickTime = 0;
//
//    private int CLICK_MAX_COUNT = 3;
//
//    private Paint paint;
//    private Paint linePaint;
//    private String baseDir;//文件存储的基本目录
//    private Path path;
//
//    private RefreshViewCallBack callBack;
//
//    private long lastClickTime = 0;
//
//    private float lastClickX = 0f;
//    private float lastClickY = 0f;
//
//    public ScreenDetectionView(Context context) {
//        this(context, null);
//    }
//
//    public ScreenDetectionView(Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public ScreenDetectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    private void init() {
//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.FILL);
//
//        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        linePaint.setColor(0xff999999);
//        linePaint.setStrokeWidth(1.5f);
//        linePaint.setStyle(Paint.Style.STROKE);
//
//        baseDir = DeviceUtils.getFactoryBaseDir() + File.separator;
//
//        path = new Path();
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasuredSpec, int heightMeasuredSpec) {
//        width = DensityUtil.getWidth();
//        height = DensityUtil.getHeight();
//        setMeasuredDimension(width, height);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        switch (type) {
//            case TYPE_COLOR:
//                drawForColor(canvas);
//                break;
//            case TYPE_LEAKER:
//                drawForLeaker(canvas);
//                break;
//            case TYPE_LINE:
//                drawForLine(canvas);
//            case TYPE_WRITE:
//                drawForWrite(canvas);
//                break;
//            case TYPE_DEFINATION:
//                drawForDefination(canvas);
//                break;
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 刷新颜色
//     */
//    public void refreshColor() {
//        if (type != TYPE_COLOR) return;
//        currentColorIndex++;
//        if (currentColorIndex < COLORS.length) invalidate();
//    }
//
//    /**
//     * 颜色测试
//     */
//    private void drawForColor(final Canvas canvas) {
//        paint.setColor(COLORS[currentColorIndex]);
//        canvas.drawRect(0, 0, width, height, paint);
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (callBack != null) callBack.refreshView();
//            }
//        }, 5 * 1000);
//    }
//
//    /**
//     * 漏光测试
//     *
//     * @param canvas
//     */
//    private void drawForLeaker(Canvas canvas) {
//        paint.setColor(0xff000000);
//        canvas.drawRect(0, 0, width, height, paint);
//    }
//
//    /**
//     * 绘画模式
//     *
//     * @param canvas
//     */
//    private void drawForWrite(Canvas canvas) {
//        paint.setColor(0xff0000ff);
//        paint.setStrokeWidth(10);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(path, paint);
//    }
//
//    /**
//     * 绘制虚线
//     *
//     * @param canvas
//     */
//    private void drawForLine(Canvas canvas) {
//        final int offsetX = (int) (width / 9f);
//        for (int i = offsetX; i < width; i = i + offsetX) {
//            canvas.drawLine(i, 0, i, height, linePaint);
//        }
//        final int offsetY = (int) (height / 16f);
//        for (int j = offsetY; j < height; j = j + offsetY) {
//            canvas.drawLine(0, j, width, j, linePaint);
//        }
//    }
//
//    /**
//     * 清晰度测试
//     *
//     * @param canvas
//     */
//    private void drawForDefination(Canvas canvas) {
//        String baseImgPath = baseDir + DEFINATION_IMG_PATHS[currentImgIndex];
//        if (FileUtils.isFileExist(baseImgPath + ".png")) {
//            baseImgPath = baseImgPath + ".png";
//        }
//        if (FileUtils.isFileExist(baseImgPath + ".jpg")) {
//            baseImgPath = baseImgPath + ".jpg";
//        }
//
//        Bitmap bitmap = BitmapFactory.decodeFile(baseImgPath);
//        if (bitmap != null) {
//            RectF rect = new RectF(0, 0, width, height);
//            canvas.drawBitmap(bitmap, null, rect, null);
//        }
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (type == TYPE_DEFINATION) {
//                    currentImgIndex += 1;
//                    if (currentImgIndex < DEFINATION_IMG_PATHS.length)
//                        invalidate();
//                }
//            }
//        }, 5 * 1000);
//    }
//
//    public void setType(int type) {
//        this.type = type;
//        this.currentImgIndex = 0;
//        this.currentColorIndex = 0;
//        this.lastClickTime = 0;
//        this.path.reset();
//        this.invalidate();
//    }
//
//    public void setType(int type, RefreshViewCallBack callBack) {
//        this.callBack = callBack;
//        setType(type);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (type == TYPE_LINE || type == TYPE_WRITE) {
//            float currentX = event.getX();
//            float currentY = event.getY();
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    path.moveTo(currentX, currentY);
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    path.lineTo(currentX, currentY);
//                    break;
//                case MotionEvent.ACTION_UP:
//                    if (checkDoubleClick(currentX, currentY)) {
//                        if (callBack != null) callBack.refreshView();
//                    } else {
//                        path.lineTo(currentX, currentY);
//                    }
//                    break;
//            }
//            invalidate();
//            return true;
//        } else {
//            return super.onTouchEvent(event);
//        }
//
//    }
//
//    /**
//     * 检测是不是双击
//     *
//     * @param curClickX
//     * @param curClickY
//     * @return
//     */
//    private boolean checkDoubleClick(float curClickX, float curClickY) {
//        final long curClickTime = System.currentTimeMillis();
//        if (Math.pow(lastClickX - curClickX, 2) + Math.pow(lastClickY - curClickY, 2) <= 50 * 50 && curClickTime - lastClickTime <= 500) {
//            clickTime++;
//        } else {
//            clickTime = 0;
//        }
//        if (clickTime >= CLICK_MAX_COUNT - 1) return true;
//        lastClickX = curClickX;
//        lastClickY = curClickY;
//        lastClickTime = curClickTime;
//        return false;
//    }
//}
