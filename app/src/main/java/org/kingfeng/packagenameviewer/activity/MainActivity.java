package org.kingfeng.packagenameviewer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.kingfeng.packagenameviewer.R;
import org.kingfeng.packagenameviewer.adapter.FragmentAdapter;
import org.kingfeng.packagenameviewer.fragment.AppAllFragment;
import org.kingfeng.packagenameviewer.fragment.AppSysFragment;
import org.kingfeng.packagenameviewer.fragment.AppUserFragment;
import org.kingfeng.packagenameviewer.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: HomePage
 *
 * @author Jinfeng Lee
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentAdapter mFragmentAdapter;

    private ViewPager mPageVp;
    private TextView mTabAllTv, mTabContactsTv, mTabFriendTv;
    private LinearLayout llAllApp;
    private LinearLayout llSysApp;
    private LinearLayout llUserApp;

    /*** Tab的那个引导线 */
    private ImageView ivTabLine;

    private AppAllFragment appAllFragment;
    private AppUserFragment appUserFragment;
    private AppSysFragment appSysFragment;

    /*** ViewPager的当前选中页*/
    private int currentIndex;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        init();
        initTabLineWidth();
        setListener();
    }

    private void setListener() {
        llUserApp.setOnClickListener(this);
        llSysApp.setOnClickListener(this);
        llAllApp.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_about) {
            CommonUtil.showDialog(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        llAllApp = (LinearLayout) findViewById(R.id.id_tab_all_app_ll);
        llSysApp = (LinearLayout) findViewById(R.id.id_tab_sys_app_ll);
        llUserApp = (LinearLayout) findViewById(R.id.id_tab_user_app_ll);


        mTabContactsTv = (TextView) this.findViewById(R.id.id_contacts_tv);
        mTabAllTv = (TextView) this.findViewById(R.id.id_all_app_tv);
        mTabAllTv.setTextColor(Color.RED);
        mTabFriendTv = (TextView) this.findViewById(R.id.id_friend_tv);
        ivTabLine = (ImageView) this.findViewById(R.id.id_tab_line_iv);

        mPageVp = (ViewPager) this.findViewById(R.id.id_page_vp);
    }

    private void init() {
        appAllFragment = new AppAllFragment();
        appSysFragment= new AppSysFragment(this);
        appUserFragment = new AppUserFragment(this);

        mFragmentList.add(appAllFragment);
        mFragmentList.add(appSysFragment);
        mFragmentList.add(appUserFragment);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        mPageVp.setAdapter(mFragmentAdapter);
        mPageVp.setCurrentItem(0);

        mPageVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
               // 有三种状态（0，1，2） 0：什么都没做 1：正在滑动 2：滑动完毕
            }

            /**
             * position :当前页面，及你点击滑动的页面
             * offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ivTabLine.getLayoutParams();

                Log.d(TAG, "offset:" + offset );
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距
                 */
                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3)
                            + currentIndex * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3)
                            +  currentIndex * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3)
                            +  currentIndex * (screenWidth / 3));
                }
                ivTabLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextViewColor();

                switch (position) {
                    case 0:
                        mTabAllTv.setTextColor(Color.RED);
                        break;
                    case 1:
                        mTabFriendTv.setTextColor(Color.RED);
                        break;
                    case 2:
                        mTabContactsTv.setTextColor(Color.RED);
                        break;
                    default:
                        break;
                }

                currentIndex = position;
            }
        });
    }

    /**
     * 设置滑动条的宽度为屏幕的1/3
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ivTabLine.getLayoutParams();
        lp.width = screenWidth / 3;
        ivTabLine.setLayoutParams(lp);
    }

    private void resetTextViewColor() {
        mTabAllTv.setTextColor(Color.BLACK);
        mTabFriendTv.setTextColor(Color.BLACK);
        mTabContactsTv.setTextColor(Color.BLACK);
    }

    @Override
    public void onClick(View view) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ivTabLine.getLayoutParams();

        if(view.getId() == R.id.id_tab_user_app_ll) {
            mPageVp.setCurrentItem(2);
            lp.leftMargin = (int) (screenWidth * 2.0 / 3);

        } else if(view.getId() == R.id.id_tab_sys_app_ll){
            mPageVp.setCurrentItem(1);
            lp.leftMargin = (int) (screenWidth * 1.0 / 3);
        } else if(view.getId() == R.id.id_tab_all_app_ll) {
            mPageVp.setCurrentItem(0);
            lp.leftMargin = 0;
        }

        ivTabLine.setLayoutParams(lp);
    }
}
