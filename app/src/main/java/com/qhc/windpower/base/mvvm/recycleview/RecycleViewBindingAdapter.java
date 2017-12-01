package com.qhc.windpower.base.mvvm.recycleview;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.github.jdsjlzx.ItemDecoration.SpacesItemDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.qhc.windpower.R;
import com.qhc.windpower.base.adapter.lrecycleview.ListBaseAdapter;

/**
 * @author huhao on 2017/8/21.
 */
public class RecycleViewBindingAdapter {

    @BindingAdapter(value = {"adapter", "recycleAdapter", "columns", "canPullRefresh", "canLoadMore"}, requireAll = false)
    public static void initRecycleView(LRecyclerView lRecyclerView, ListBaseAdapter adapter, LRecyclerViewAdapter recycleAdapter, int columns,
                                       boolean canPullRefresh, boolean canLoadMore) {
        // setLayoutManager
        if (columns < 0) {
            Log.e("initRecycleView: ", "columns必须大于0");
            return;
        }
        if (columns == 0) {
            setLinearLayout(lRecyclerView);
        }
        if (columns > 0) {
            setGridLayout(lRecyclerView, columns);
        }
        // setAdapter
        lRecyclerView.setAdapter(new LRecyclerViewAdapter(adapter));
        // 是否可以下拉刷新
        lRecyclerView.setPullRefreshEnabled(canPullRefresh);
        // 是否可以加载更多
        lRecyclerView.setLoadMoreEnabled(canLoadMore);
        // 设置头部加载颜色，第一个参数是转圈动画颜色，第二个是字体颜色，第三个是头部背景色
        lRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.nhs_bg_gray, R.color.nhs_bg_white);
        // 设置底部加载颜色
        lRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.nhs_bg_gray, R.color.nhs_bg_white);
        // 设置底部加载文字提示
        lRecyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
    }

    /**
     * LinearLayoutManager
     */
    private static void setLinearLayout(LRecyclerView lRecyclerView) {
        LinearLayoutManager manager = new LinearLayoutManager(lRecyclerView.getContext());
        lRecyclerView.setLayoutManager(manager);
        int spacing = lRecyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.dp_10);
        lRecyclerView.addItemDecoration(SpacesItemDecoration.newInstance(0, spacing, 1, Color.WHITE));
    }

    /**
     * GridLayoutManager
     */
    private static void setGridLayout(LRecyclerView lRecyclerView, int gridCount) {
        GridLayoutManager manager = new GridLayoutManager(lRecyclerView.getContext(), gridCount);
        lRecyclerView.setLayoutManager(manager);
        int spacing = lRecyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.dp_10);
        lRecyclerView.addItemDecoration(SpacesItemDecoration.newInstance(spacing, spacing, manager.getSpanCount(), Color.WHITE));
    }
}
