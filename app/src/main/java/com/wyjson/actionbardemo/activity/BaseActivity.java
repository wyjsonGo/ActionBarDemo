package com.wyjson.actionbardemo.activity;

import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.utils.StatusBarUtils;
import com.wyjson.actionbardemo.view.SuperActionBar;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isStatusBarEnabled())
            initStatusBar();

        if (isNavigationBarColorEnabled() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.theme_color));
        }

    }

    public SuperActionBar getSuperActionBar() {
        return findViewById(R.id.action_bar);
    }

    /**
     * 初始化沉浸式
     */
    protected void initStatusBar() {
        StatusBarUtils.setTransparent(this);
        StatusBarUtils.setDarkMode(this);
//        StatusBarUtils.setLightMode(this);
    }

    /**
     * 是否在Activity使用
     *
     * @return the boolean
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 是否在Activity使用
     *
     * @return the boolean
     */
    protected boolean isNavigationBarColorEnabled() {
        return true;
    }

}