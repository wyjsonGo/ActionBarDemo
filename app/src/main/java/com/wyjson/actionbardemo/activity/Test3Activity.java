package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.wyjson.actionbardemo.R;

public class Test3Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSuperActionBar()
                .setTitleText("标题")
                .setLeftText()
                .setRightText(R.string.icon_setting, true, view -> Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show());

        ((TextView) findViewById(R.id.tv_content)).setText("标题,左右按钮");


    }
}