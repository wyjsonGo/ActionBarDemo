package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.wyjson.actionbardemo.R;

public class Test1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        /**
         * 链式调用
         */
        getSuperActionBar()
                .setTitleText("标题")
                .setLeftText();

        ((TextView) findViewById(R.id.tv_content)).setText("标题,左返回按钮");

    }
}