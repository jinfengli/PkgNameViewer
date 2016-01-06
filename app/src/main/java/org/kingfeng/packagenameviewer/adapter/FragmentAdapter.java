package org.kingfeng.packagenameviewer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: FragmentAdapter
 *
 * @author lijf
 */
public class FragmentAdapter  extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<Fragment>();

    public FragmentAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

//
//    @Override
//    public int getItemPosition(Object object) {
//        return PagerAdapter.POSITION_NONE; // 触发销毁对象以及重建对象
//    }
}
