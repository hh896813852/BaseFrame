package com.qhc.windpower.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * @author any on 2017/2/24.
 */
public class GuideViewPager extends ViewPager {
    private OnScrollListener onScrollListener;

    public GuideViewPager(Context context) {
        super(context);
    }

    public GuideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置滑动接口
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (onScrollListener != null) {
            onScrollListener.onScroll(x);
        }
    }

    /**
     * 滚动的回调接口
     *
     * @author xiaanming
     */
    public interface OnScrollListener {
        /**
         * 回调方法， 返回GuideViewPager滑动的X方向距离
         *
         * @param scrollX
         */
        void onScroll(int scrollX);
    }
}
