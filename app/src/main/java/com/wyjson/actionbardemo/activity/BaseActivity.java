package com.wyjson.actionbardemo.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.utils.StatusBarUtils;
import com.wyjson.actionbardemo.view.SuperActionBar2;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isStatusBarEnabled())
            initStatusBar();
    }

    public SuperActionBar2 getSuperActionBar() {
        return findViewById(R.id.super_action_bar);
    }

    /**
     * 初始化沉浸式
     */
    protected void initStatusBar() {
        StatusBarUtils.setTransparent(this);
        StatusBarUtils.setStatusBarMode(this);
    }

    /**
     * 是否在Activity使用
     *
     * @return the boolean
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

}