package com.qhc.windpower.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.qhc.windpower.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author any on 2016/7/16.
 */
public class CommonViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    public CommonViewPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragmentList) {
        super(fm);
        this.fragments = fragmentList;
    }

    public CommonViewPagerAdapter(FragmentManager fm, BaseFragment... fragmentsArray) {
        super(fm);
        fragments = new ArrayList<>();
        for (BaseFragment baseFragment : fragmentsArray) {
            fragments.add(baseFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}