package com.administer.example.common_base.manager;


import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XHD on 2020/05/22
 */
public class FragmentManager {
    private static FragmentManager manager;
    private final List<Fragment> fragmentList = new ArrayList<>();

    public static FragmentManager getInstance() {
        if (manager == null) {
            manager = new FragmentManager();
        }
        return manager;
    }

    /**
     * 添加Fragment
     */
    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    /**
     * 移除Fragment
     */
    public void removeFragment(Fragment fragment) {
        fragmentList.remove(fragment);
    }

    //移除所有Fragment
    public void removeAllFragment() {
        fragmentList.clear();
    }




    public List<Fragment> getFragmentList() {
        return fragmentList;
    }
}
