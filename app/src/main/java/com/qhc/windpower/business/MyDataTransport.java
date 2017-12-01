package com.qhc.windpower.business;


import com.qhc.windpower.MyApplication;
import com.qhc.windpower.bean.User;

/**
 * 与后台进行数据传入的工具
 * 一般用法:
 * MyDataTransport.create(URL)
 *   .addParam(key1, value1)
 *   .addParam(key2, value2)
 *   .addDefaultProgressing()
 *   .execute(DataListener, String.class);
 */
public class MyDataTransport extends DataTransport {
    protected MyDataTransport() {
    }
    public static MyDataTransport create(String url) {
        MyDataTransport dataChangeBiz = new MyDataTransport();
        dataChangeBiz.addUrl(url);
        User loginUser = MyApplication.getInstance().getUser();
        if (loginUser != null) {
            dataChangeBiz.addParam("token", loginUser.token);
        }
        return dataChangeBiz;
    }
}
