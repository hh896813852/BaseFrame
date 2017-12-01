package com.qhc.windpower.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.qhc.windpower.R;


/**
 * Created by haungyin on 2015/4/28.
 */
public class CycleView extends View {
    Paint paint;
    int color = Color.BLACK;

    public CycleView(Context context) {
        super(context);
        init(null);
    }

    public CycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    private void init(AttributeSet attrs) {
        color = getResources().getColor(R.color.nhs_bg_light_blue);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

//        if (attrs != null) {
//            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CycleView, 0, 0);
//            color = a.getColor(R.styleable.CycleView_cycleColor, color);
//            a.recycle();
//        }
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.min(getHeight(),getWidth()) / 2, paint);
    }
}
