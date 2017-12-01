package com.qhc.windpower.base;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;


import com.qhc.windpower.R;
import com.qhc.windpower.utils.DensityUtil;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by any on 16/7/26.
 */
public class MagicIndicatorBuilder {

    public interface OnNavigatorClickListener {
        void onNavigatorClickListener(int index);
    }

    public static class MagicIndicatorConfiguration {
        public String[] labels;
        public int labelTextSize = 14;
        public int titleNormalColor;
        public int titleSelectedColor;
        /**
         * LinePagerIndicator.MODE_MATCH_EDGE 直线宽度 == 均分
         * LinePagerIndicator.MODE_WRAP_CONTENT  直线宽度 == title宽度
         * LinePagerIndicator.MODE_EXACTLY 直线宽度 == mLineWidth
         */
        public int lineMode = LinePagerIndicator.MODE_MATCH_EDGE;
        public String lineColor = "#f72a40";
        public int lineHeight;
        public boolean isAdjustMode = true;

        public MagicIndicatorConfiguration (Context context) {
            this.titleNormalColor = R.color.nhs_text_gray;
            this.titleSelectedColor = R.color.l4_main;
            this.lineHeight = DensityUtil.dip2px(context, 1.5f);
        }
    }

    /**
     * 采用的默认的 MagicIndicatorConfiguration 进行构建
     * @param context
     * @param labels
     * @param listener
     * @return
     */
    public static CommonNavigator buildCommonNavigator(Context context,
                                                       String[] labels,
                                                       OnNavigatorClickListener listener) {
        MagicIndicatorConfiguration configuration = new MagicIndicatorConfiguration(context);
        configuration.labels = labels;
        return buildCommonNavigator(context, configuration, listener);
    }

    public static CommonNavigator buildCommonNavigator(Context context,
                                                       MagicIndicatorConfiguration configuration,
                                                       OnNavigatorClickListener listener) {
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdjustMode(configuration.isAdjustMode);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return configuration.labels == null ? 0 : configuration.labels.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setText(configuration.labels[index]);
                colorTransitionPagerTitleView.setTextSize(configuration.labelTextSize);
                colorTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(context, configuration.titleNormalColor));
                colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(context, configuration.titleSelectedColor));
                colorTransitionPagerTitleView.setOnClickListener(view -> listener.onNavigatorClickListener(index));
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(configuration.lineMode);
                indicator.setLineHeight(configuration.lineHeight);
                List<String> colorList = new ArrayList<>();
                colorList.add(configuration.lineColor);
                indicator.setColorList(colorList);
                return indicator;
            }
        });
        return commonNavigator;
    }

    public static CommonNavigator buildCommonBottomTabNavigator(Context context, MainTabItem[] mainTabItems, OnNavigatorClickListener listener) {
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mainTabItems.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.view_main_tab_item);

                // 初始化
                final ImageView titleImg = (ImageView) commonPagerTitleView.findViewById(R.id.imgvTabItem);
                final TextView txtvTabItem = (TextView) commonPagerTitleView.findViewById(R.id.txtvTabItem);
                txtvTabItem.setText(mainTabItems[index].title);


                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleImg.setImageResource(mainTabItems[index].imgSrcSelected);
                        txtvTabItem.setTextColor(context.getResources().getColor(R.color.l4_main));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleImg.setImageResource(mainTabItems[index].imgSrcNormal);
                        txtvTabItem.setTextColor(context.getResources().getColor(R.color.nhs_text_gray));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });

                commonPagerTitleView.setOnClickListener(view -> listener.onNavigatorClickListener(index));

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        return commonNavigator;
    }
}