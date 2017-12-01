package com.qhc.windpower.utils;

public class RequestCodeUtil {
    private static int currentCode = 1;

    public synchronized static int next() {
        return currentCode ++;
    }
}