package com.qhc.windpower.base;

public class MainTabItem {
    public String title;
    public int imgSrcNormal;
    public int imgSrcSelected;
    public MainTabItem(String title, int imgSrcNormal, int imgSrcSelected) {
        this.title = title;
        this.imgSrcNormal = imgSrcNormal;
        this.imgSrcSelected = imgSrcSelected;
    }
}