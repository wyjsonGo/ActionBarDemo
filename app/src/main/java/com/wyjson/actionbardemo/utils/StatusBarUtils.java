package com.wyjson.actionbardemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.wyjson.actionbardemo.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarUtils {

    /**
     * 是否是光亮主题 true:光亮/false:暗黑
     */
    public static boolean isLightTheme = true;

    /**
     * 设置状态栏高度
     *
     * @param activity
     * @return
     */
    public static View setStatusBarHeight(Activity activity) {
        View statusBar = activity.findViewById(R.id.v_status_bar);
        return setStatusBarHeight(activity, statusBar);
    }

    /**
     * 设置状态栏高度
     *
     * @param activity
     * @param statusBar
     * @return
     */
    public static View setStatusBarHeight(Activity activity, View statusBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams lp = statusBar.getLayoutParams();
            lp.height = getStatusBarHeight(activity);
            statusBar.setLayoutParams(lp);
            return statusBar;
        }
        return null;
    }

    public static void setTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);
        setRootView(activity);
    }

    public static void setStatusBarMode(Activity activity) {
        if (isLightTheme) {
            setLightMode(activity);
        } else {
            setDarkMode(activity);
        }
    }

    public static void setLightMode(Activity activity) {
        setMIUIStatusBarDarkIcon(activity, true);
        setMeiZuStatusBarDarkIcon(activity, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public static void setDarkMode(Activity activity) {
        setMIUIStatusBarDarkIcon(activity, false);
        setMeiZuStatusBarDarkIcon(activity, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * 修改 MIUI V6  以上状态栏颜色
     *
     * @param activity
     * @param darkIcon 文字颜色,true:黑色,false:白色
     */
    private static void setMIUIStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkIcon ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
        }
    }

    /**
     * 修改魅族状态栏字体颜色 Flyme 4.0
     */
    private static void setMeiZuStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkIcon) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
        } catch (Exception e) {
        }
    }

    /**
     * 设置根布局参数
     */
    private static void setRootView(Activity activity) {
        ViewGroup parent = activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    /**
     * 使状态栏透明
     */
    private static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            // https://www.jianshu.com/p/11a2b780fd9b
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        } catch (Exception e) {
        }
        if (statusBarHeight == 0) {
            try {
                Rect frame = new Rect();
                ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                statusBarHeight = frame.top;
            } catch (Exception e) {
            }
        }
        if (statusBarHeight == 0) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
            }
        }
        return statusBarHeight;
    }
}