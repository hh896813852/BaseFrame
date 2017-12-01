package com.qhc.windpower;

import android.app.Application;
import android.text.TextUtils;

import com.qhc.windpower.bean.User;
import com.qhc.windpower.helper.AppPreferences;
import com.qhc.windpower.utils.JsonUtil;


/**
 * Created by any on 17/6/14.
 */

public class MyApplication extends Application {
    private User user;
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static MyApplication getInstance() {
        return application;
    }

    public User getUser() {
        if (user == null) {
            String json = AppPreferences.getUserInfo();
            if (!TextUtils.isEmpty(json)) {
                user = JsonUtil.fromJson(json, User.class);
            }
        }
        return user;
    }

    public void updateUserToDisk() {
        setUser(getUser());
    }

    public void reSaveUser() {
        String json = JsonUtil.toJson(this.user);
        // 保存
        AppPreferences.setUserInfo(json);
    }

    public void setUser(User user) {
        if (user != null) {
            if (this.user == null) {
                this.user = user;
            }
            String json = JsonUtil.toJson(this.user);
            // 保存
            AppPreferences.setUserInfo(json);
        } else {
            this.user = null;
            // 清除
            AppPreferences.clearUserInfo();
        }
    }

    public boolean isLogin() {
        return getUser() != null;
    }
}
