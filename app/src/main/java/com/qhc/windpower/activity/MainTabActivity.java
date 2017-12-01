package com.qhc.windpower.activity;

import android.os.Bundle;

import com.qhc.windpower.R;
import com.qhc.windpower.base.BaseFragment;
import com.qhc.windpower.base.BaseFragmentActivity;
import com.qhc.windpower.base.MagicIndicatorBuilder;
import com.qhc.windpower.base.MagicIndicatorPageListener;
import com.qhc.windpower.base.MainTabItem;
import com.qhc.windpower.base.adapter.CommonViewPagerAdapter;
import com.qhc.windpower.base.annotation.Layout;
import com.qhc.windpower.base.annotation.Translucent;
import com.qhc.windpower.core.ActivityManager;
import com.qhc.windpower.databinding.ActivityMainTabBinding;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

@Layout(value = R.layout.activity_main_tab)
@Translucent
public class MainTabActivity extends BaseFragmentActivity<ActivityMainTabBinding> {

//    private HomeFragment homeFragment = new HomeFragment();                         // 工作台
//    private NewCustomerFragment newCustomerFragment = new NewCustomerFragment();    // 新增客户
//    private MeFragment meFragment = new MeFragment();                               // 个人中心

    private MainTabItem[] mainTabItems = {
            new MainTabItem("工作台", R.drawable.icon_tab_main_normal, R.drawable.icon_tab_main_selected)};

    private BaseFragment[] fragments = {};

    private long pressBackButtonTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 清除该Activity下面的所有Activity
        ActivityManager.clearBottomActivitiesUntil(this);
        // init ViewPager
        getDataBinding().vpMain.setOffscreenPageLimit(3);
        getDataBinding().vpMain.setAdapter(new CommonViewPagerAdapter(getSupportFragmentManager(), fragments));
        getDataBinding().vpMain.addOnPageChangeListener(new MagicIndicatorPageListener(getDataBinding().mainTabIndicator));
        // set MagicIndicator
        CommonNavigator commonNavigator = MagicIndicatorBuilder.buildCommonBottomTabNavigator(this, mainTabItems, index -> {
            getDataBinding().vpMain.setCurrentItem(index, false);
        });

        getDataBinding().mainTabIndicator.setNavigator(commonNavigator);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        // 两秒内点击两次返回键
        if ((System.currentTimeMillis() - pressBackButtonTime) > 2000) {
            showToast("再按一次退出程序");
            pressBackButtonTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
