package com.qhc.windpower.helper;


import com.qhc.windpower.BuildConfig;

/**
 * @author any
 */
public class AppPreferences extends BasePreferences {
    /**
     * 是否展示引导页
     */
    public static final String IS_FIRST_OPEN_APP = "nhs.is_first_open_app_" + BuildConfig.VERSION_CODE;

    public static final String USER_INFO = "nhs.user_info_v20";

    public static final String CONFIG_INFO = "nhs.config_info";

    public static final String HOME_DATA = "nhs.home_data";//缓存首页最后更新数据

    public static final String CATEGORY_DATA = "nhs.category_data";//缓存分类数据

    public static final String LOCATION_DATA = "nhs.location_data";//缓存首页最后更新数据

    public static final String APP_UPDATE_ID = "nhs.app_update_id";//app更新下载id（DownloadManager）

    public static final String PAY_SECURITY_VERIFICATION_TIP_CODE = "nhs.pay_security_verification_tip_code";// 0表示第一次，1表示不是第一次

    public static final String APP_FORCE_UPDATE = "nhs.app_force_update";//是否强制更新app

    public static final String HISTORY_SEARCH = "nhs.history_search";//历史搜索

    public static final String SHOP_HISTORY_SEARCH = "nhs.shop_history_search";//商家历史搜索

    public static final String APP_HOST_DATA = "nhs.app_host_data";//App地址环境类型(debug模式使用)


    public static boolean isFirstOpenApp() {
        return getBoolean(IS_FIRST_OPEN_APP, true);
    }

    public static void finishFirstOpenApp() {
        setBoolean(IS_FIRST_OPEN_APP, false);
    }

    public static String getUserInfo() {
        return getString(USER_INFO, "");
    }

    public static String getConfigInfo() {
        return getString(CONFIG_INFO, "");
    }

    public static void setUserInfo(String userInfo) {
        setString(USER_INFO, userInfo);
    }

    public static void clearUserInfo() {
        clear(USER_INFO);
    }

    public static void setConfigInfo(String configInfo) {
        setString(CONFIG_INFO, configInfo);
    }

    public static String getHomeData() {
        return getString(HOME_DATA, "");
    }

    public static void setHomeData(String homeData) {
        setString(HOME_DATA, homeData);
    }

    public static String getCategoryData() {
        return getString(CATEGORY_DATA, "");
    }

    public static void setCategoryData(String categoryData) {
        setString(CATEGORY_DATA, categoryData);
    }

    public static String getLocationData() {
        return getString(LOCATION_DATA, "");
    }

    public static void setLocationData(String locationData) {
        setString(LOCATION_DATA, locationData);
    }

    public static void setAppUpdateId(long id) {
        setLong(APP_UPDATE_ID, id);
    }

    public static long getAppUpdateId() {
        return getLong(APP_UPDATE_ID, -1l);
    }

    public static boolean isForceUpdateAPP() {
        return getBoolean(APP_FORCE_UPDATE, false);
    }

    public static void setAppForceUpdate(boolean isForce) {
        setBoolean(APP_FORCE_UPDATE, isForce);
    }

    public static int getPaySecurityVerificationTipCode() {
        return getInt(PAY_SECURITY_VERIFICATION_TIP_CODE, 0);
    }

    public static void setPaySecurityVerificationTipCode(int paySecurityVerificationTipCode) {
        setInt(PAY_SECURITY_VERIFICATION_TIP_CODE, paySecurityVerificationTipCode);
    }
}
