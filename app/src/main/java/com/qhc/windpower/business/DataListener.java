package com.qhc.windpower.business;


import com.qhc.windpower.bean.base.Message;

/**
 * Created by any on 17/6/14.
 */

public interface DataListener<T> {

    void onMessage(Message<T> message);
}
