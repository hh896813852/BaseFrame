package com.qhc.windpower.business;

import com.qhc.windpower.MyApplication;
import com.qhc.windpower.bean.base.Message;
import com.qhc.windpower.helper.ToastHelper;

/**
 * Created by any on 17/6/14.
 */

public class MyDataListener<T> implements DataListener<T> {

    public void onSuccess(T data) {

    }

    public void onFail(int status, String failMessage) {
        ToastHelper.showToast(MyApplication.getInstance().getApplicationContext(), failMessage);
    }

    public void onMessage(Message<T> message) {
        if (message.status == 1) {
            onSuccess(message.data);
        } else {
            onFail(message.status, message.msg);
        }
    }
}
