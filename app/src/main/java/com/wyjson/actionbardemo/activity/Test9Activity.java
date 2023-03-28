package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.wyjson.actionbardemo.R;

public class Test9Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSuperActionBar().setTitleText("标题");
        getSuperActionBar().switchStatusBarHeight(false);

        ((TextView) findViewById(R.id.tv_content)).setText("关闭沉浸式状态栏,使用默认系统的");
    }

    @Override
    protected boolean isStatusBarEnabled() {
        return false;
    }
}