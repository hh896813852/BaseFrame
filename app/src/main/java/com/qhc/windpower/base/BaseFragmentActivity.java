package com.qhc.windpower.base;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.qhc.windpower.MyApplication;
import com.qhc.windpower.R;
import com.qhc.windpower.base.annotation.Layout;
import com.qhc.windpower.base.annotation.Translucent;
import com.qhc.windpower.bean.User;
import com.qhc.windpower.core.ActivityManager;
import com.qhc.windpower.helper.StatusBarUtil;
import com.qhc.windpower.helper.ToastHelper;
import com.qhc.windpower.utils.LogUtil;

import java.lang.reflect.Field;

/**
 * 所有Activity页面的抽象父类
 * @param <DataBinding> 数据绑定者
 */
public abstract class BaseFragmentActivity<DataBinding extends ViewDataBinding> extends FragmentActivity {

    private DataBinding dataBinding;

    private boolean autoChangeStatusBar = true;

    public void setAutoChangeStatusBar(boolean autoChangeStatusBar) {
        this.autoChangeStatusBar = autoChangeStatusBar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        Layout layout = getClass().getAnnotation(Layout.class);
        if (layout != null) {
            dataBinding = DataBindingUtil.setContentView(this, layout.value());
            TextView titleView = (TextView)findViewById(R.id.actionbarTitle);
            if (titleView != null) {
                titleView.setText(layout.title());
            }
            TextView rightButtonTextView = (TextView)findViewById(R.id.textButton);
            if (rightButtonTextView != null) {
                rightButtonTextView.setText(layout.rightButton());
                rightButtonTextView.setOnClickListener(v -> onRightButtonClick());
            }
        } else {
            LogUtil.w("DataBinding警告->需要使用数据绑定功能必须使用@Layout注解注入布局");
        }
        //
        bindViewModelToUI();

        // 透明状态栏
        Translucent translucentAnnotation = getClass().getAnnotation(Translucent.class);
        if (translucentAnnotation != null && translucentAnnotation.value()) {
            StatusBarUtil.setTranslucent(this);
        } else {
            // 非透明状态栏采用黑色风格+白色statusBar
            if (autoChangeStatusBar && StatusBarUtil.setDarkStyleTitleBar(this)) {
                StatusBarUtil.setTranslucent(this);
                StatusBarUtil.setColorWithColorIntValue(this, Color.TRANSPARENT);
                StatusBarUtil.setDarkStyleTitleBar(this);

                View rootView = ((getWindow().getDecorView().findViewById(android.R.id.content)));
                rootView.setBackgroundColor(getResources().getColor(R.color.nhs_bg_white));
                rootView.setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
            }
        }
    }

    public void onRightButtonClick() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    /**
     * 获取数据绑定对象
     * @return
     */
    public DataBinding getDataBinding () {
        return dataBinding;
    }

    /**
     * 打开一个页面
     * @param activityClass
     */
    public void startActivity(Class<? extends Activity> activityClass) {
        startActivity(activityClass, false);
    }

    /**
     * 打开一个页面
     * 并且设置成是否关闭当前页面
     * @param activityClass
     * @param finishSelf
     */
    public void startActivity(Class<? extends Activity> activityClass, boolean finishSelf) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        if (finishSelf) {
            finish();
        }
    }

    /**
     * 使得XML文件中可以用viewModel
     */
    private void bindViewModelToUI() {
        if (dataBinding != null) {
            // 绑定两者
            Field[] declaredFields = dataBinding.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.getType().equals(getClass())) {
                    declaredField.setAccessible(true);
                    try {
                        declaredField.set(dataBinding, this);
                    } catch (IllegalAccessException e) {
                    }
                    declaredField.setAccessible(false);
                    break;
                }
            }
        }
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public User getLoginUser() {
        return getMyApplication().getUser();
    }

    /**
     * 生成附言
     * @return
     */
    public String getRemarkKeys() {
        String keys = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer stringBuffer = new StringBuffer(4);
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 36);
            stringBuffer.append(keys.charAt(random));
        }
        return stringBuffer.toString();
    }

    /**
     * 返回控件被点击
     * @param view
     */
    public void onBackButtonClick(View view) {
        finish();
    }

    /**
     * 返回当前对象本身,简化 XXXActivity.this
     * @return
     */
    public Activity self() {
        return this;
    }

    /**
     * 获取当前应用程序的全局变量
     * @return
     */
    public MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    /**
     * 弹出一个提示信息框
     * @param message
     */
    public void showToast(String message) {
        ToastHelper.showToast(this, message);
    }

}
