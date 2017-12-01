package com.qhc.windpower.base.mvvm.SwipeRefreshLayout;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Administrator on 2017/8/10.
 */

public class SwipeRefreshLayoutBindingAdapter {

    @BindingAdapter({"onRefreshListener"})
    public static void onRefreshListener(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    @BindingAdapter({"refreshing"})
    public static void refreshing(SwipeRefreshLayout swipeRefreshLayout, Boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }
}
