package com.wyjson.actionbardemo.utils;


import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Created by Wy on 2018/5/15.
 * <p>
 * 输入法解决方案(改良,增加是否监听调整)
 * https://github.com/Deepblue1996/AndroidBug5497Workaround
 */
public class AndroidBug5497Workaround {

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private int contentHeight;
    private boolean isFirst = true;
    private int statusBarHeight;

    private boolean have = true;

    public static void newInstance(Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private AndroidBug5497Workaround(Activity activity) {
        initWorkAround(activity);
    }

    public void initWorkAround(Activity activity) {
        //获取状态栏的高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        FrameLayout content = activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);

        //界面出现变动都会调用这个监听事件
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (have) {
                    if (isFirst) {
                        contentHeight = mChildOfContent.getHeight();//兼容华为等机型
                        isFirst = false;
                    }
                    possiblyResizeChildOfContent();
                }
            }
        };

        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    /**
     * 打开监听，自动调整布局
     */
    public void openOnGlobalLayoutListener() {
        have = true;
    }

    /**
     * 关闭监听，取消调整布局
     */
    public void closeOnGlobalLayoutListener() {
        have = false;
    }

    //重新调整跟布局的高度
    private void possiblyResizeChildOfContent() {

        int usableHeightNow = computeUsableHeight();

        //当前可见高度和上一次可见高度不一致 布局变动
        if (usableHeightNow != usableHeightPrevious) {
            //int usableHeightSansKeyboard2 = mChildOfContent.getHeight();//兼容华为等机型
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + statusBarHeight;
                } else {
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                }
            } else {
                frameLayoutParams.height = contentHeight;
            }

            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 计算mChildOfContent可见高度
     *
     * @return
     */
    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }
}