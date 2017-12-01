//package com.qhc.customercloudshare.test;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.GridLayoutManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.github.jdsjlzx.ItemDecoration.SpacesItemDecoration;
//import com.github.jdsjlzx.interfaces.OnItemClickListener;
//import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
//import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
//import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
//import com.github.jdsjlzx.interfaces.OnRefreshListener;
//import com.github.jdsjlzx.progressindicator.AVLoadingIndicatorView;
//import com.github.jdsjlzx.recyclerview.LRecyclerView;
//import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
//import com.github.jdsjlzx.recyclerview.ProgressStyle;
//import com.github.jdsjlzx.view.CommonHeader;
//import com.qhc.customercloudshare.MyConstants;
//import com.qhc.customercloudshare.R;
//import com.qhc.customercloudshare.base.adapter.lrecycleview.ListBaseAdapter;
//import com.qhc.customercloudshare.base.adapter.lrecycleview.SuperViewHolder;
//import com.qhc.customercloudshare.bean.Customer;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Administrator on 2017/8/15.
// */
//
//public class LRecycleViewTest extends Activity {
//    // 服务器端一共多少条数据
//    private int totalCount = 0;
//    // 已经获取到多少条数据了
//    private int currentCount = 0;
//
//    private List<Customer> dataList = new ArrayList<>();
//
//    private DataAdapter dataAdapter;
//    private LRecyclerViewAdapter lRecyclerViewAdapter;
//    private LRecyclerView lRecyclerView;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_lrecycleview);
//        initView();
//    }
//
//    private void initView() {
//        for (int i = 0; i < 3; i++) {
//            Customer customer = new Customer();
//            dataList.add(customer);
//        }
//
//        lRecyclerView = (LRecyclerView) findViewById(R.id.recycleView);
//        // setLayoutManager 必须在setAdapter之前
//        GridLayoutManager manager = new GridLayoutManager(this, 2);
//        lRecyclerView.setLayoutManager(manager);
//        int spacing = getResources().getDimensionPixelSize(R.dimen.dp_10);
//        lRecyclerView.addItemDecoration(SpacesItemDecoration.newInstance(spacing, spacing, manager.getSpanCount(), Color.WHITE));
//        // setAdapter
//        dataAdapter = new DataAdapter(this);
//        dataAdapter.setDataList(dataList);
//        lRecyclerViewAdapter = new LRecyclerViewAdapter(dataAdapter);
//        lRecyclerView.setAdapter(lRecyclerViewAdapter);
//
//        /**
//         * 开启下拉刷新(默认开启)，关闭：lRecyclerView.setPullRefreshEnabled(false);
//         * 强制刷新：mRecyclerView.forceToRefresh();
//         */
//        lRecyclerView.setPullRefreshEnabled(true);
//        lRecyclerView.setLoadMoreEnabled(true);
//        // 添加一个HeaderView方法（FooterView同理）
////        lRecyclerViewAdapter.addHeaderView(View view);
//        // 或者
////        View header = LayoutInflater.from(this).inflate(R.layout.sample_header,(ViewGroup)findViewById(android.R.id.content), false);
////        lRecyclerViewAdapter.addHeaderView(header);
//        // 或者
////        CommonHeader headerView = new CommonHeader(this, R.layout.layout_home_header);
////        lRecyclerViewAdapter.addHeaderView(headerView);
//        // 去掉头部，注意：如果有两个以上的HeaderView，连续调用mLRecyclerViewAdapter.removeHeaderView()即可
////        lRecyclerViewAdapter.removeHeaderView();
//
//        // 设置头部加载颜色，第一个参数是转圈动画颜色，第二个是字体颜色，第三个是头部背景色
//        lRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.nhs_bg_gray, R.color.nhs_bg_white);
//        // 设置底部加载颜色
//        lRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.nhs_bg_gray, R.color.nhs_bg_white);
//        // 设置底部加载文字提示
//        lRecyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
//
//        // AVLoadingIndicatorView 库有多少效果，LRecyclerView就支持多少下拉刷新效果，当然你也可以自定义下拉刷新效果。
////        lRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); // 设置下拉刷新Progress的样式
////        lRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);           // 设置下拉刷新箭头
//
//        /**
//         * onScrollUp()——RecyclerView向上滑动的监听事件；
//         * onScrollDown()——RecyclerView向下滑动的监听事件；
//         * onScrolled()——RecyclerView正在滚动的监听事件；
//         * onScrollStateChanged(int state)——RecyclerView正在滚动的监听事件；
//         */
//        lRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
//            @Override
//            public void onScrollUp() {
//
//            }
//
//            @Override
//            public void onScrollDown() {
//
//            }
//
//            @Override
//            public void onScrolled(int distanceX, int distanceY) {
//
//            }
//
//            @Override
//            public void onScrollStateChanged(int state) {
//
//            }
//        });
//
//        /**
//         * 下拉刷新
//         */
//        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                dataAdapter.clear();
//                currentCount = 0;
//            }
//        });
//
//        /**
//         * 加载更多
//         */
//        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                if (currentCount < totalCount) {
//                    requestData();
//                } else {
//                    lRecyclerView.setNoMore(true);
//                }
//            }
//        });
//
//        /**
//         * 网络异常，点击重新加载
//         */
//        lRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
//            @Override
//            public void reload() {
//                requestData();
//            }
//        });
//
//        /**
//         * item点击事件
//         */
//        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//            }
//        });
//
//        /**
//         * item长按事件
//         */
//        lRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        });
//    }
//
//    /**
//     * 模拟请求网络
//     */
//    private void requestData() {
//
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    Thread.sleep(1000);
//                    runOnUiThread(() -> {
//                        lRecyclerView.refreshComplete(MyConstants.PAGE_SIZE);
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//
//    private class DataAdapter extends ListBaseAdapter<Customer> {
//        public DataAdapter(Context context) {
//            super(context);
//        }
//
//        @Override
//        public int getLayoutId() {
//            return R.layout.item_customer_query;
//        }
//
//        @Override
//        public void onBindItemHolder(SuperViewHolder holder, int position) {
//            Customer item = mDataList.get(position);
//            ImageView headView = holder.getView(R.id.imageView);
//        }
//    }
//
//}
