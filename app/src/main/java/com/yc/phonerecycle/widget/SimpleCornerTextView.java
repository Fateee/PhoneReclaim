package com.yc.phonerecycle.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.yc.phonerecycle.R;



public class SimpleCornerTextView extends android.support.v7.widget.AppCompatTextView{

    public SimpleCornerTextView(Context context) {
        this(context, null);
        loadShapeAttribute(context, null);
    }

    public SimpleCornerTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadShapeAttribute(context, attrs);
    }

    public SimpleCornerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadShapeAttribute(context, attrs);
    }

    private void loadShapeAttribute(Context context, AttributeSet attrs){
        if (attrs == null){

        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        this.setBackgroundDrawable(gradientDrawable);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleCornerTextView);
        if (a != null){
            float defDimen = context.getResources().getDimension(R.dimen.common2);
            int bgColor = a.getColor(R.styleable.SimpleCornerTextView_bgColor, Color.BLACK);
            int borderColor = a.getColor(R.styleable.SimpleCornerTextView_borderColor, Color.WHITE);

            float borderWidth = a.getDimension(R.styleable.SimpleCornerTextView_borderWidth, 0);

            float radius = a.getDimension(R.styleable.SimpleCornerTextView_radius, defDimen);
            if (radius == 0){
                radius = defDimen;
            }
            float topLeftRadius = a.getDimension(R.styleable.SimpleCornerTextView_topLeftRadius, radius);
            float topRightRadius = a.getDimension(R.styleable.SimpleCornerTextView_topRightRadius, radius);
            float bottomRightRadius = a.getDimension(R.styleable.SimpleCornerTextView_bottomRightRadius, radius);
            float bottomLeftRadius = a.getDimension(R.styleable.SimpleCornerTextView_bottomLeftRadius, radius);
            GradientDrawable drawable = (GradientDrawable) this.getBackground();
            drawable.setCornerRadii(new float[]{topLeftRadius,topLeftRadius,topRightRadius,topRightRadius,
                    bottomRightRadius,bottomRightRadius, bottomLeftRadius,bottomLeftRadius});
            drawable.setColor(bgColor);
            drawable.setStroke((int)borderWidth, borderColor);
        }
    }

    public void setBackgroundColor(int bgColor){
        GradientDrawable drawable = (GradientDrawable) this.getBackground();
        drawable.setColor(bgColor);
    }
}
