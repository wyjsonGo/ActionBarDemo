package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.utils.AndroidBug5497Workaround;

public class Test13Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_13);
        AndroidBug5497Workaround.newInstance(this);
        getSuperActionBar().setTitleText("标题").setLeftText();
        ((TextView) findViewById(R.id.tv_content)).setText("带输入框的页面");
    }
}