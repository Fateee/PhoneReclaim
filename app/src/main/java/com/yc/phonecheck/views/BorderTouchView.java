package com.yc.phonecheck.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class BorderTouchView extends View {

    private Paint mPaint;

    private OnTouchChangedListener mListener;

    private boolean mDistanceValid;

    private int mMaxDistance;
    private int mX = 0;
    private int mY = 0;
    private int mRectWidth;
    private int mRectHeight;

    private Rect mAll[][];
    private boolean mAllFlags[][];
    private int yCount;
    private int xCount;

    public BorderTouchView(Context context) {
        this(context, null);
    }

    public BorderTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mRectWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 40, dm);
//        mRectWidth = (int) dm.density * 16;
        mRectHeight = mRectWidth;

        // Maximum distance between points
        mMaxDistance = (int) dm.density * 21;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
    }

    public void setOnTouchChangedListener(OnTouchChangedListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int l, r, t, b;
        yCount = h / mRectHeight;
        xCount = w / mRectWidth;
        mAll = new Rect[yCount][xCount];
        mAllFlags = new boolean[yCount][xCount];
        for (int i = 0; i < yCount; i++) {
            for (int j = 0; j < xCount; j++) {
                l = mRectWidth * j;
                r = (j == xCount - 1) ? w : l + mRectWidth;
                t = mRectHeight * i;
                b = (i == yCount -1) ? h : t + mRectHeight;
                mAll[i][j] = new Rect(l + 1, t + 1, r - 1, b - 1);
                mAllFlags[i][j] = false;
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        for (int i = 0; i < mAll.length; i++) {
            for (int j = 0; j < mAll[i].length; j++) {
                mPaint.setColor(mAllFlags[i][j] ? Color.GREEN : Color.WHITE);
                canvas.drawRect(mAll[i][j], mPaint);
            }
        }

    }

    private void touchDown(int x, int y) {
        mDistanceValid = true;
        mX = x;
        mY = y;
    }

    private void touchMove(int x, int y) {
        int dx = Math.abs(x - mX);
        int dy = Math.abs(y - mY);

        mX = x;
        mY = y;

        if (mDistanceValid) {
            mDistanceValid = dx < mMaxDistance && dy < mMaxDistance;
        }

        if (mDistanceValid) {
            setBorderFlag(x, y);
        }
    }

    private void touchUp() {
        if (mListener != null && checkBorders()) {
            mListener.onTouchFinish(this);
        }
    }

    private void setBorderFlag(int x, int y) {

        int yIndex = y / mRectHeight;
        if (yIndex > -1 && yIndex < mAll.length) {
            int xIndex = x / mRectWidth;
            if (xIndex > -1 && xIndex < mAll[yIndex].length) {
                mAllFlags[yIndex][xIndex] = true;
            }
        }
    }

    private boolean checkBorders() {
        for (int i = 0; i < mAllFlags.length; i++) {
            for (int j = 0; j < mAllFlags[i].length; j++) {
                if (!mAllFlags[i][j]) return false;
            }
        }
        return true;
    }
}
