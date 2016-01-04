package org.kingfeng.packagenameviewer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * TODO: RecyclerView Divlder
 *
 * @author Jinfeng Lee
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = DividerItemDecoration.class.getSimpleName();

    private Drawable mDividerDrawable;

    public DividerItemDecoration(Context mContext) {
        TypedArray ta = mContext.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        mDividerDrawable = ta.getDrawable(0);
        ta.recycle();
    }

    /**
     * 主要实现绘制分隔线
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVerticalDivider(c, parent);
        drawHorizontalDivider(c, parent);
    }

    /**
     * 画垂直方向的分隔线，也就是水平线
     *
     * @param c
     * @param parent
     */
    private void drawVerticalDivider(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            int left =childView.getLeft();
            int top = 0;
            int right = childView.getRight();
            int bottom = 0;
            top = childView.getBottom();//子控件的底是分隔线的顶
                       bottom = top + mDividerDrawable.getIntrinsicHeight();//后者取得Drawable在屏幕上的高度，不同分比率手机获取的值是不一样的
            mDividerDrawable.setBounds(left, top, right, bottom);//设置边界，也就是绘制的范围
            mDividerDrawable.draw(c);
        }
    }

    /**
     * 画垂直方向的分隔线，也就是水平线
     *
     * @param c
     * @param parent
     */
    private void drawHorizontalDivider(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            int left = childView.getRight();
            int top = childView.getTop();
            int right = left + mDividerDrawable.getIntrinsicWidth();
            int bottom = childView.getBottom();// 后者取得Drawable在屏幕上的高度，不同分比率手机获取的值是不一样的
            mDividerDrawable.setBounds(left, top, right, bottom);//设置边界，也就是绘制的范围
            mDividerDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    }
}
